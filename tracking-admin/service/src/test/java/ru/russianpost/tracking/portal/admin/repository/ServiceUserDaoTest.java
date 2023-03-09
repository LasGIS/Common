package ru.russianpost.tracking.portal.admin.repository;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;

public class ServiceUserDaoTest {

    private static final String USERNAME = "test";

    @Test
    public void findServiceUserShouldReturnInstance() {
        ServiceUser answer = ServiceUser.builder().setUsername(USERNAME).build();

        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        when(jdbc.queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME))).thenReturn(answer);

        ServiceUser actual = new ServiceUserDao(jdbc).getServiceUser(USERNAME);
        assertSame(answer, actual);

        verify(jdbc).queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME));
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    public void findServiceUserShouldReturnNull() {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        when(jdbc.queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME)))
            .thenThrow(
                EmptyResultDataAccessException.class,
                IncorrectResultSizeDataAccessException.class
            );

        ServiceUserDao dao = new ServiceUserDao(jdbc);
        assertThat(dao.getServiceUser(USERNAME), nullValue());
        assertThat(dao.getServiceUser(USERNAME), nullValue());

        verify(jdbc, times(2)).queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME));
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    public void findAuthorityStringShouldReturnInstance() {
        String answer = "AuthorityString";

        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        when(jdbc.queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME))).thenReturn(answer);

        ServiceUserDao dao = new ServiceUserDao(jdbc);
        assertSame(answer, dao.findAuthorityString(USERNAME));

        verify(jdbc).queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME));
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    public void findAuthorityStringShouldThrownException() {
        String answer = "AuthorityString";

        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        when(jdbc.queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME)))
            .thenThrow(
                EmptyResultDataAccessException.class,
                IncorrectResultSizeDataAccessException.class
            );

        ServiceUserDao dao = new ServiceUserDao(jdbc);
        try {
            dao.findAuthorityString(USERNAME);
            fail("method should thrown EmptyResultDataAccessException");
        } catch (EmptyResultDataAccessException ignore) {}
        try {
            dao.findAuthorityString(USERNAME);
            fail("method should thrown IncorrectResultSizeDataAccessException");
        } catch (IncorrectResultSizeDataAccessException ignore) {}

        verify(jdbc, times(2)).queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME));
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    public void testAddUser() {
        ServiceUser user = ServiceUser.builder().setUsername(USERNAME).build();

        JdbcTemplate jdbc = mock(JdbcTemplate.class);

        new ServiceUserDao(jdbc).addUser(user);

        verify(jdbc).update(
            any(String.class),
            eq(user.getUsername()),
            eq(user.getPassword()),
            eq(user.getAuthorityString()),
            isNull(),
            isNull(),
            isNull(),
            isNull()
        );
        verifyNoMoreInteractions(jdbc);
    }

    @Test
    public void testExists() {
        JdbcTemplate jdbc = mock(JdbcTemplate.class);
        when(jdbc.queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME))).thenReturn(true, false);

        ServiceUserDao dao = new ServiceUserDao(jdbc);
        assertTrue(dao.exists(USERNAME));
        assertFalse(dao.exists(USERNAME));

        verify(jdbc, times(2)).queryForObject(any(String.class), any(RowMapper.class), eq(USERNAME));
        verifyNoMoreInteractions(jdbc);
    }

}
