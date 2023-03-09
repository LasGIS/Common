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

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;

/**
 * Service class responsible for providing user details for spring security authorization purposes.
 * @author sslautin
 * @version 1.0 24.09.2015
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsProvider implements UserDetailsService {

    private final StopWatchFactory stopWatchFactory;
    private final ServiceUserDao userRepo;

    @Override
    public UserDetails loadUserByUsername(final String username) {

        final StopWatch stopWatch = stopWatchFactory.getStopWatch("UserDetailsProvider:loadUserByUsername");

        if (username == null || username.isEmpty()) {
            stopWatch.stop("UserDetailsProvider:loadUserByUsername:fail");
            throw new UsernameNotFoundException("Username is empty.");
        }

        log.debug("Loading user by username: {}.", username);
        final ServiceUser serviceUser = this.userRepo.getServiceUser(username);

        if (serviceUser == null) {
            log.debug("User \"{}\" not found.", username);
            stopWatch.stop("UserDetailsProvider:loadUserByUsername:fail");
            throw new UsernameNotFoundException(username);
        }

        final String authorityString = serviceUser.getAuthorityString();
        final User user = new User(
            serviceUser.getUsername(),
            serviceUser.getPassword(),
            serviceUser.isEnabled(),
            serviceUser.isAccountNonExpired(),
            serviceUser.isCredentialsNonExpired(),
            serviceUser.isAccountNonLocked(),
            AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString != null ? authorityString : "")
        );
        log.debug("Loaded user: {}.", user);
        stopWatch.stop();
        return user;
    }
}
