package ru.russianpost.tracking.portal.admin.security;

import org.junit.Test;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl.Essence;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static ru.russianpost.tracking.portal.admin.security.LdapAndDBUserDetailsMapper.DEFAULT_LDAP_AUTHORITIES_AS_STRING;
import static ru.russianpost.tracking.portal.admin.security.LdapAndDBUserDetailsMapper.FAKE_LDAP_PASSWORD;

public class LdapAndDBUserDetailsMapperTest {

    private static final String USERNAME = "test";
    private static final String TEST_DN = "CN=Иванов Иван Иванович,OU=Подрядчики,DC=main,DC=russianpost,DC=ru";

    private final LdapUserDetailsMapper delegate = mock(LdapUserDetailsMapper.class);
    private final ServiceUserDao userRepo = mock(ServiceUserDao.class);

    public void resetMocks() {
        reset(this.delegate, this.userRepo);
        verifyNoInteractions(this.delegate, this.userRepo);
    }

    @Test
    public void userShouldNotBeCreated() {
        test(true, NO_AUTHORITIES, "");
        test(true, NO_AUTHORITIES, "TEST0,TEST1");
        test(true, commaSeparatedStringToAuthorityList("TEST0,TEST1"), "");
        test(true, commaSeparatedStringToAuthorityList("TEST0,TEST1"), "TEST2,TEST3");
    }

    @Test
    public void userShouldBeCreated() {
        test(false, NO_AUTHORITIES, "");
        test(false, commaSeparatedStringToAuthorityList("TEST0,TEST1"), "");
    }

    private void test(
        boolean exists,
        List<GrantedAuthority> authoritiesFromLDAP,
        String authoritiesStringFromDB
    ) {
        assertThat(USERNAME, not(nullValue()));
        assertThat(authoritiesFromLDAP, not(nullValue()));
        assertThat(authoritiesStringFromDB, not(nullValue()));

        resetMocks();
        DirContextOperations ctx = null;

        if (!exists) {
            doAnswer(inv -> {
                ServiceUser user = (ServiceUser) inv.getArguments()[0];
                assertThat(user.getUsername(), is(USERNAME));
                assertThat(user.getPassword(), is(FAKE_LDAP_PASSWORD));
                assertThat(user.getAuthorityString(), is(DEFAULT_LDAP_AUTHORITIES_AS_STRING));
                return null;
            }).when(this.userRepo).addUser(any(ServiceUser.class));
        }
        when(this.userRepo.getServiceUser(USERNAME)).thenReturn(
            exists
                ? ServiceUser.builder()
                .setUsername(USERNAME)
                .setName("Иван")
                .setSurname("Иванов")
                .setPatronymic("Иванович")
                .setAuthorityString(authoritiesStringFromDB)
                .build()
                : null
        );
        LdapUserDetailsImpl answer = createAnswer(authoritiesFromLDAP);
        when(this.delegate.mapUserFromContext(any(), anyString(), anyCollection())).thenReturn(answer);

        LdapAndDBUserDetailsMapper mapper = new LdapAndDBUserDetailsMapper(this.delegate, this.userRepo);
        UserDetails userDetails = mapper.mapUserFromContext(ctx, USERNAME, authoritiesFromLDAP);
        assertThat(userDetails, instanceOf(LdapUserDetailsImpl.class));

        LdapUserDetailsImpl actual = (LdapUserDetailsImpl) userDetails;

        assertThat(actual.getUsername(), is(USERNAME));
        GrantedAuthority[] expectedAuthorities = expectedAuthorities(
            authoritiesFromLDAP,
            exists ? authoritiesStringFromDB : DEFAULT_LDAP_AUTHORITIES_AS_STRING
        );
        if (expectedAuthorities.length == 0) {
            assertThat(actual.getAuthorities(), empty());
        } else {
            assertThat(actual.getAuthorities(), hasSize(expectedAuthorities.length));
            assertThat(actual.getAuthorities(), contains(expectedAuthorities));
        }

        assertThat(actual.isAccountNonExpired(), is(answer.isAccountNonExpired()));
        assertThat(actual.isAccountNonLocked(), is(answer.isAccountNonLocked()));
        assertThat(actual.isCredentialsNonExpired(), is(answer.isCredentialsNonExpired()));
        assertThat(actual.getDn(), is(answer.getDn()));
        assertThat(actual.isEnabled(), is(answer.isEnabled()));
        assertThat(actual.getGraceLoginsRemaining(), is(answer.getGraceLoginsRemaining()));
        assertThat(actual.getPassword(), is(answer.getPassword()));
        assertThat(actual.getTimeBeforeExpiration(), is(answer.getTimeBeforeExpiration()));

        verify(this.delegate).mapUserFromContext(ctx, USERNAME, authoritiesFromLDAP);
        verify(this.userRepo).getServiceUser(USERNAME);
        if (!exists) {
            verify(this.userRepo).addUser(any(ServiceUser.class));
        }

        verifyNoMoreInteractions(delegate);
        verifyNoMoreInteractions(userRepo);
    }

    private GrantedAuthority[] expectedAuthorities(List<GrantedAuthority> authoritiesFromLDAP, String authoritiesStringFromDB) {
        List<GrantedAuthority> result = new ArrayList<>();
        result.addAll(authoritiesFromLDAP);
        result.addAll(commaSeparatedStringToAuthorityList(authoritiesStringFromDB));
        return result.toArray(new GrantedAuthority[0]);
    }

    private LdapUserDetailsImpl createAnswer(Collection<GrantedAuthority> authorities) {
        Random rnd = new Random();
        Essence result = new LdapUserDetailsImpl.Essence();
        result.setAccountNonExpired(rnd.nextBoolean());
        result.setAccountNonLocked(rnd.nextBoolean());
        result.setAuthorities(authorities);
        result.setCredentialsNonExpired(rnd.nextBoolean());
        result.setDn(TEST_DN);
        result.setEnabled(rnd.nextBoolean());
        result.setGraceLoginsRemaining(rnd.nextInt());
        result.setPassword("password");
        result.setTimeBeforeExpiration(rnd.nextInt());
        result.setUsername(USERNAME);
        return (LdapUserDetailsImpl) result.createUserDetails();
    }

}
