/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.repository;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEvent;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEventType;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.lowerCase;

/**
 * JDBC Repository class responsible for accessing service user data in a storage.
 * @author sslautin
 */
@Slf4j
@Repository
public class ServiceUserDao {

    private static final RowMapper<String> AUTHORITY_STRING_MAPPER = (rs, rowNum) -> rs.getString("authorityString");
    private static final RowMapper<String> USERNAME_STRING_MAPPER = (rs, rowNum) -> rs.getString("username");

    private static final RowMapper<ServiceUser> SERVICE_USER_MAPPER = (rs, rowNum) -> ServiceUser.builder()
        .setUsername(rs.getString("username"))
        .setPassword(rs.getString("password"))
        .setEnabled(rs.getBoolean("enabled"))
        .setAccountNonExpired(rs.getBoolean("accountNonExpired"))
        .setCredentialsNonExpired(rs.getBoolean("credentialsNonExpired"))
        .setAccountNonLocked(rs.getBoolean("accountNonLocked"))
        .setAuthorityString(AUTHORITY_STRING_MAPPER.mapRow(rs, rowNum))
        .setName(rs.getString("name"))
        .setSurname(rs.getString("surname"))
        .setPatronymic(rs.getString("patronymic"))
        .setEmail(rs.getString("email"))
        .setAffiliate(rs.getString("affiliate"))
        .build();

    private static final RowMapper<Boolean> EXISTS_MAPPER = (rs, rowNum) -> rs.getBoolean(1);
    private static final String SQL_UPDATE_LOCK = "UPDATE service_users SET \"accountNonLocked\" = ? WHERE username = ?";
    private static final String SQL_SELECT_USER_HISTORY = "SELECT username, "
        + "datetime, "
        + "author, "
        + "event_type, "
        + "event_args, "
        + "comment "
        + "FROM service_user_history h "
        + "WHERE username = ? "
        + "ORDER BY h.datetime ASC";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor
     * @param jdbcTemplate instance of {@link JdbcTemplate}
     * @throws NullPointerException if {@code jdbcTemplate} is null
     */
    public ServiceUserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate must not be null");
    }

    /**
     * Finds service user by his username.
     * @param username user's login.
     * @return found user or null, if not found.
     */
    public ServiceUser getServiceUser(final String username) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM service_users WHERE username = ?",
                SERVICE_USER_MAPPER,
                lowerCase(username)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            // ignore if user is not found
            log.error("there is duplicates by username '{}' in DB", username, e);
            return null;
        }
    }

    /**
     * Finds service user by query string.
     * @param query query
     * @param limit limit
     * @return found user or null, if not found.
     */
    public List<ServiceUser> findServiceUser(final String query, int limit) {
        return jdbcTemplate.query(
            (
                "SELECT * FROM service_users WHERE "
                    + "username ilike '%' || ? || '%'"
                    + "or name ilike '%' || ? || '%'"
                    + "or surname ilike '%' || ? || '%'"
                    + "or patronymic ilike '%' || ? || '%'"
                    + "or email ilike '%' || ? || '%'"
                    + "limit ?"
            ),
            SERVICE_USER_MAPPER,
            query,
            query,
            query,
            query,
            query,
            limit
        );
    }

    /**
     * Finds authority string of service user by given username.
     * @param username user's login.
     * @return found authority string.
     * @throws IncorrectResultSizeDataAccessException if found more than 1 users by username
     * @throws EmptyResultDataAccessException         if user not found by username
     */
    public String findAuthorityString(String username) {
        try {
            return jdbcTemplate.queryForObject(
                "select \"authorityString\" from service_users where username = ?",
                AUTHORITY_STRING_MAPPER,
                lowerCase(username)
            );
        } catch (EmptyResultDataAccessException e) {
            throw e;
        } catch (IncorrectResultSizeDataAccessException e) {
            // ignore if user is not found
            log.error("there is duplicates by username '{}' in DB", username, e);
            throw e;
        }
    }

    /**
     * Adds given user in DB.
     * @param user added user.
     */
    public void addUser(ServiceUser user) {
        jdbcTemplate.update(
            "insert into service_users(username, password, \"authorityString\", name, surname, patronymic, email) "
                + "values (?, ?, ?, ?, ?, ?, ?)",
            lowerCase(user.getUsername()),
            user.getPassword(),
            user.getAuthorityString(),
            user.getName(),
            user.getSurname(),
            user.getPatronymic(),
            user.getEmail()
        );
    }

    /**
     * Updates roles of the given user in DB.
     * @param username   username
     * @param name       name
     * @param surname    surname
     * @param patronymic patronymic
     * @param email      email
     */
    public void updateUserInfo(String username, String name, String surname, String patronymic, String email) {
        jdbcTemplate.update(
            "UPDATE public.service_users SET name = ?, surname = ?, patronymic = ?, email = ? WHERE username = ?",
            name,
            surname,
            patronymic,
            email,
            lowerCase(username)
        );
    }

    /**
     * Check for the existence of user in DB.
     * @param username user's login.
     * @return true only if user exists, otherwise false
     */
    public boolean exists(String username) {
        return jdbcTemplate.queryForObject(
            "select count(username) from service_users where username = ?",
            EXISTS_MAPPER,
            lowerCase(username)
        );
    }

    /**
     * Update authority string.
     * @param username        the username
     * @param authorityString the authority string
     */
    public void updateAuthorityString(String username, String authorityString) {
        jdbcTemplate.update(
            "UPDATE public.service_users SET \"authorityString\" = ? WHERE username = ?",
            authorityString,
            lowerCase(username)
        );
    }

    /**
     * Log user history event.
     * @param event event
     */
    public void logEvent(UserHistoryEvent event) {
        jdbcTemplate.update((
            "insert into service_user_history (username, datetime, author, event_type, event_args, comment) "
                + "values (?, now() at time zone 'utc', ?, ?, ?, ?)"
        ), lowerCase(event.getUsername()), event.getAuthor(), event.getEventType().name(), event.getEventArgs(), event.getComment());
    }

    /**
     * Returns user history events.
     * @param username username
     * @return user history events
     */
    public List<UserHistoryEvent> getUserHistory(String username) {
        return jdbcTemplate.query(SQL_SELECT_USER_HISTORY, (rs, i) -> new UserHistoryEvent(
            rs.getString("username"),
            rs.getTimestamp("datetime", GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"))).getTime(),
            rs.getString("author"),
            UserHistoryEventType.valueOf(rs.getString("event_type")),
            rs.getString("event_args"),
            rs.getString("comment")
        ), lowerCase(username));
    }

    /**
     * @param page page number
     * @param size page size
     * @return active usernames on specific page
     */
    @Speed4J
    public List<String> getActiveUserNames(@NotNull final Integer page, @NotNull final Integer size) {
        return jdbcTemplate.query(
            "SELECT username FROM service_users WHERE "
                + "enabled IS TRUE AND "
                + "\"accountNonExpired\" IS TRUE AND "
                + "\"credentialsNonExpired\" IS TRUE AND "
                + "\"accountNonLocked\" IS TRUE "
                + "LIMIT ? "
                + "OFFSET ? ",
            USERNAME_STRING_MAPPER,
            size,
            page * size
        );
    }

    /**
     * @return total size of active users
     */
    @Speed4J
    public Long getSizeOfActiveUsers() {
        return jdbcTemplate.queryForObject(
            "SELECT count(username) FROM service_users WHERE "
                + "enabled IS TRUE AND "
                + "\"accountNonExpired\" IS TRUE AND "
                + "\"credentialsNonExpired\" IS TRUE AND "
                + "\"accountNonLocked\" IS TRUE ", Long.class
        );
    }

    /**
     * @param userName userName
     */
    public void lockUser(final String userName) {
        jdbcTemplate.update(SQL_UPDATE_LOCK, false, lowerCase(userName));
    }

    /**
     * @param userName userName
     */
    public void unlockUser(final String userName) {
        jdbcTemplate.update(SQL_UPDATE_LOCK, true, lowerCase(userName));
    }
}
