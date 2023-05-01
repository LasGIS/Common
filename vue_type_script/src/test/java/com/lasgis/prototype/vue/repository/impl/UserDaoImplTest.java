/*
 *  @(#)UserDaoImplTest.java  last: 01.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Arduino.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.repository.impl;

import com.lasgis.prototype.vue.VueApplication;
import com.lasgis.prototype.vue.model.UserDto;
import com.lasgis.prototype.vue.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The Class UserDaoImplTest definition.
 *
 * @author Vladimir Laskin
 * @since 01.05.2023 : 15:05
 */
@Slf4j
@SpringBootTest(classes = VueApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserDaoImplTest {

    private final UserDaoImpl userDao;

    @Autowired
    public UserDaoImplTest(final UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Test
    void insert() {
        final UserDto user = UserDto.builder().login("login").name("name").build();
        final Integer id = userDao.insert(user);
        Assertions.assertEquals(id, user.getUserId());
    }

    @Test
    void findByLogin() {
        final Optional<UserDto> optUser = userDao.findByLogin("LasGIS");
        Assertions.assertTrue(optUser.isPresent());
        final UserDto user = optUser.get();
        log.info("findByLogin() = {}", user);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("LasGIS", user.getLogin());
        Assertions.assertEquals("Владимир Ласкин", user.getName());
        Assertions.assertEquals("123", user.getPassword());
        final List<UserRole> roles = user.getRoles();
        Assertions.assertNotNull(roles);
        Assertions.assertEquals(3, roles.size());
    }

    @Test
    void findAllUsers() {
        final List<UserDto> list = userDao.findAllUsers();
        Assertions.assertFalse(list.isEmpty());
        log.info("findAllUsers() = {}", list);
        /* [
UserDto(userId=1, login=LasGIS, name=Владимир Ласкин, password=123, roles=[ADMIN, CHIEF, SUPERVISOR], archived=false),
UserDto(userId=2, login=VPupkin, name=Василий Пупкин, password=321, roles=[SUPERVISOR, OPERATOR], archived=false)
         ] */
    }
}
