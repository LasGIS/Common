/*
 *  @(#)UserDaoImpl.java  last: 13.06.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.prototype.vue.repository.impl;

import com.lasgis.prototype.vue.model.UserDto;
import com.lasgis.prototype.vue.model.UserRole;
import com.lasgis.prototype.vue.repository.UserDao;
import com.lasgis.prototype.vue.repository.extractor.UserDtoResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * The Class UserDaoImpl definition.
 *
 * @author Vladimir Laskin
 * @since 01.05.2023 : 13:59
 */
@Repository
public class UserDaoImpl implements UserDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public UserDaoImpl(
        final NamedParameterJdbcTemplate namedJdbcTemplate
    ) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    @Transactional
    public Integer insert(final UserDto user) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedJdbcTemplate.update(
            "INSERT INTO um_user(umusr_login, umusr_name, umusr_archived) VALUES (:login, :name, false)",
            new MapSqlParameterSource()
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
            , keyHolder, new String[]{"umusr_user_id"});
        final Integer userId = keyHolder.getKeyAs(Integer.class);
        user.setUserId(userId);
        return userId;
    }

    @Override
    @Transactional
    public void update(final UserDto user) {
        namedJdbcTemplate.update(
            "UPDATE um_user SET" +
                " umusr_login = :login," +
                " umusr_name = :name," +
                " umusr_password = :password," +
                " umusr_archived = :archived" +
                " WHERE umusr_user_id = :userId",
            new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("password", user.getPassword())
                .addValue("archived", user.getArchived())
        );
        final Integer userId = user.getUserId();
        deleteAllUserRoles(userId);
        user.getRoles().forEach(role -> insertUserRole(userId, role));
    }

    @Transactional
    public void deleteAllUserRoles(final Integer userId) {
        namedJdbcTemplate.update(
            "DELETE FROM um_user_role WHERE umusr_user_id = :userId",
            new MapSqlParameterSource("userId", userId)
        );
    }

    @Transactional
    public void insertUserRole(final Integer userId, final UserRole role) {
        namedJdbcTemplate.update(
            "INSERT INTO um_user_role (umusr_user_id, umrle_role_id) VALUES (:userId, :role)",
            new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("role", role.name())
        );
    }

    @Override
    @Transactional
    public void delete(final Integer userId) {

    }

    @Override
    public Optional<UserDto> findById(final Integer userId) {
        return Objects.requireNonNull(namedJdbcTemplate.query(
            "Select usr.*, rol.umrle_role_id" +
                "  from um_user usr" +
                "    left join um_user_role rol on usr.umusr_user_id = rol.umusr_user_id" +
                " where usr.umusr_user_id = :userId",
            new MapSqlParameterSource("userId", userId),
            UserDtoResultSetExtractor.INSTANCE
        )).stream().filter(Objects::nonNull).findFirst();
    }

    @Override
    public Optional<UserDto> findByLogin(final String login) {
        return Objects.requireNonNull(namedJdbcTemplate.query(
            "Select usr.*, rol.umrle_role_id" +
                "  from um_user usr" +
                "    left join um_user_role rol on usr.umusr_user_id = rol.umusr_user_id" +
                " where usr.umusr_login = :login",
            new MapSqlParameterSource("login", login),
            UserDtoResultSetExtractor.INSTANCE
        )).stream().filter(Objects::nonNull).findFirst();
    }

    @Override
    public List<UserDto> findAllUsers() {
        return namedJdbcTemplate.query(
            "Select usr.*, rol.umrle_role_id" +
                "  from um_user usr" +
                "    inner join um_user_role rol on usr.umusr_user_id = rol.umusr_user_id" +
                " order by usr.umusr_user_id",
            UserDtoResultSetExtractor.INSTANCE
        );
    }
}
