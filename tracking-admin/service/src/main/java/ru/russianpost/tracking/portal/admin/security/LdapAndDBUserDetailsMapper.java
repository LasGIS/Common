/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl.Essence;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;

import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_ONLINE_PAYMENT_MARK;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_TRACKING_USER;

/**
 * Implementation of {@link UserDetailsContextMapper} that adds authorities from DB in returned instance {@link UserDetails}.
 *
 * @author aalekseenko
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LdapAndDBUserDetailsMapper implements UserDetailsContextMapper {

    // db has "not null"-constraint for field
    static final String FAKE_LDAP_PASSWORD = "";
    static final String DEFAULT_LDAP_AUTHORITIES_AS_STRING = (
            Stream.of(ROLE_TRACKING_USER, ROLE_ONLINE_PAYMENT_MARK).map(Role::name).collect(joining(","))
    );

    private final LdapUserDetailsMapper delegate;
    private final ServiceUserDao userRepo;

    /**
     * Delegates a call to mapper after that finds authorities in DB by username of returned object.
     *
     * <p>Note:</p> If user does not exists in DB then it will be created with default authorities.
     *
     * @see LdapUserDetailsMapper#mapUserFromContext(DirContextOperations, String, Collection)
     * @see ServiceUserDao#exists(String)
     * @see ServiceUserDao#addUser(ServiceUser)
     * @see ServiceUserDao#findAuthorityString(String)
     */
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        final LdapUserDetailsImpl ldapUserDetails = (LdapUserDetailsImpl) this.delegate.mapUserFromContext(ctx, username, authorities);

        final ServiceUser ldapUser = convertToServiceUser(ldapUserDetails);
        final String ldapUsername = ldapUser.getUsername();
        ServiceUser userFromDb = userRepo.getServiceUser(ldapUsername);
        if (isNull(userFromDb)) {
            log.debug("User '{}' does not exist.", ldapUsername);
            userRepo.addUser(ldapUser);
            userFromDb = ldapUser;
            log.debug("User '{}' has been created.", ldapUsername);
        } else if (
            (userFromDb.getName() == null && ldapUser.getName() != null)
                || (userFromDb.getSurname() == null && ldapUser.getSurname() != null)
                || (userFromDb.getPatronymic() == null && ldapUser.getPatronymic() != null)
        ) {
            enrichUserInfoIfNeeded(userFromDb, ldapUser);
        }

        final Collection<GrantedAuthority> resultAuthorities = Stream.concat(
            ldapUserDetails.getAuthorities().stream(),
            parseAuthorityString(userFromDb.getAuthorityString()).stream()
        ).collect(toList());

        final Essence result = new Essence();
        result.setUsername(ldapUsername);
        result.setAuthorities(resultAuthorities);

        result.setAccountNonExpired(ldapUserDetails.isAccountNonExpired());
        result.setAccountNonLocked(ldapUserDetails.isAccountNonLocked());
        result.setCredentialsNonExpired(ldapUserDetails.isCredentialsNonExpired());
        result.setDn(ldapUserDetails.getDn());
        result.setEnabled(ldapUserDetails.isEnabled());
        result.setGraceLoginsRemaining(ldapUserDetails.getGraceLoginsRemaining());
        result.setPassword(ldapUserDetails.getPassword());
        result.setTimeBeforeExpiration(ldapUserDetails.getTimeBeforeExpiration());

        return result.createUserDetails();
    }

    private void enrichUserInfoIfNeeded(ServiceUser serviceUser, ServiceUser ldapUser) {
        if (
            (serviceUser.getName() == null && ldapUser.getName() != null)
            || (serviceUser.getSurname() == null && ldapUser.getSurname() != null)
            || (serviceUser.getPatronymic() == null && ldapUser.getPatronymic() != null)
        ) {
            userRepo.updateUserInfo(
                    serviceUser.getUsername(),
                    nonNull(serviceUser.getName()) ? serviceUser.getName() : ldapUser.getName(),
                    nonNull(serviceUser.getSurname()) ? serviceUser.getSurname() : ldapUser.getSurname(),
                    nonNull(serviceUser.getPatronymic()) ? serviceUser.getPatronymic() : ldapUser.getPatronymic(),
                    serviceUser.getEmail()
            );
        }
    }

    private String getFullName(String dn) {
        try {
            final LdapName ldapName = LdapUtils.newLdapName(dn);
            for (Rdn rdn : ldapName.getRdns()) {
                if ("CN".equalsIgnoreCase(rdn.getType())) {
                    return rdn.getValue().toString();
                }
            }
        } catch (Exception e) {
            log.warn("Error to get user's full name from LDAP DN", e);
        }
        return null;
    }

    private String cleanupUsername(String username) {
        final int atIndex = username.indexOf('@');
        return atIndex > 0 ? username.substring(0, atIndex) : username;
    }

    private ServiceUser convertToServiceUser(LdapUserDetails userDetails) {
        final ServiceUser.Builder userBuilder = ServiceUser.builder()
                .setUsername(cleanupUsername(userDetails.getUsername()).toLowerCase())
                .setPassword(FAKE_LDAP_PASSWORD)
                .setAuthorityString(DEFAULT_LDAP_AUTHORITIES_AS_STRING);
        final String fullName = getFullName(userDetails.getDn());
        if (nonNull(fullName)) {
            String[] words = fullName.split(" ");
            if (words.length >= 2 && words.length <= 3) {
                userBuilder.setSurname(words[0]);
                userBuilder.setName(words[1]);
            }
            if (words.length == 3) {
                userBuilder.setPatronymic(words[2]);
            }
        }
        return userBuilder.build();
    }

    private Collection<GrantedAuthority> parseAuthorityString(String authorityString) {
        return commaSeparatedStringToAuthorityList(ofNullable(authorityString).orElse("").trim());
    }

    /**
     * Delegates a call to {@link LdapUserDetailsMapper}.
     * @see LdapUserDetailsMapper#mapUserToContext(UserDetails, DirContextAdapter)
     */
    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        this.delegate.mapUserToContext(user, ctx);
    }

}
