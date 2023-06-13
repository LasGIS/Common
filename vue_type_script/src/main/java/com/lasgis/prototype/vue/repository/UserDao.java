/*
 *  @(#)UserDao.java  last: 13.06.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.repository;

import com.lasgis.prototype.vue.model.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * The Class UserDao definition.
 * all can throws DataAccessException
 *
 * @author Vladimir Laskin
 * @since 01.05.2023 : 13:58
 */
public interface UserDao {
    Integer insert(UserDto user);
    void update(UserDto user);
    void delete(Integer userId);
    Optional<UserDto> findById(Integer id);
    Optional<UserDto> findByLogin(String login);
    List<UserDto> findAllUsers();
}
