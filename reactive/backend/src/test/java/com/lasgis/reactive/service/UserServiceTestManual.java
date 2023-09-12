/*
 *  @(#)UserServiceTestManual.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.model.entity.UserEntity;
import com.lasgis.reactive.model.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

import static com.lasgis.reactive.model.entity.UserRole.ADMIN;
import static com.lasgis.reactive.model.entity.UserRole.CHIEF;
import static com.lasgis.reactive.model.entity.UserRole.SUPERVISOR;
import static java.util.Objects.nonNull;

/**
 * The Class  definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 13:07
 */
@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
//@RestClientTest
class UserServiceTestManual {

    private final UserService service;
    private static Long newUserId;

    @Autowired
    public UserServiceTestManual(UserService service) {
        this.service = service;
    }

    @Test
    void getAllUser() {
        StepVerifier.create(service.findAll())
            .thenConsumeWhile(user -> {
                log.info("\n  user = {}", user);
                return nonNull(user.getUserId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void getUserById() {
        StepVerifier.create(service.findById(1L))
            .expectNextMatches(user -> {
                Assertions.assertEquals("Владимир Ласкин", user.getName());
                Assertions.assertEquals("LasGIS", user.getLogin());
                Assertions.assertEquals("123", user.getPassword());
                Assertions.assertEquals(false, user.getArchived());
                assertEqualRoles(List.of(ADMIN, CHIEF, SUPERVISOR), user.getRoles());
                return nonNull(user.getUserId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    @Order(1)
    void saveNewUser() {
        final UserEntity user = UserEntity.builder()
            .name("name")
            .login("login")
            .password("password")
            .archived(true)
            .roles(List.of(CHIEF, SUPERVISOR))
            .build();
        StepVerifier.create(service.save(user))
            .expectNextMatches(userNew -> {
                Assertions.assertEquals(user.getName(), userNew.getName());
                Assertions.assertEquals(user.getLogin(), userNew.getLogin());
                Assertions.assertEquals(user.getPassword(), userNew.getPassword());
                Assertions.assertEquals(user.getArchived(), userNew.getArchived());
                assertEqualRoles(user.getRoles(), userNew.getRoles());
                return nonNull(userNew.getUserId());
            })
            .expectComplete()
            .verify();
        newUserId = user.getUserId();
    }
    @Test
    @Order(2)
    void deleteNewUser() {
        Assertions.assertNotNull(newUserId);
        StepVerifier.create(service.deleteById(newUserId))
            .expectNext(newUserId)
            .expectComplete()
            .verify();
    }

    @Test
    void updateOldUser() {
        UserEntity[] users = new UserEntity[1];
        StepVerifier.create(service.findByLogin("LasGIS"))
            .expectNextMatches(foundUser -> {
                users[0] = foundUser;
                return true;
            }).expectComplete()
            .verify();

        UserEntity user = users[0];
        user.setPassword("password");
        StepVerifier.create(service.save(user))
            .expectNextMatches(userNew -> {
                Assertions.assertEquals(1L, userNew.getUserId());
                Assertions.assertEquals("LasGIS", userNew.getLogin());
                Assertions.assertEquals("Владимир Ласкин", userNew.getName());
                Assertions.assertEquals("password", userNew.getPassword());
                Assertions.assertEquals(false, userNew.getArchived());
                assertEqualRoles(List.of(ADMIN, CHIEF, SUPERVISOR), userNew.getRoles());
                return true;
            })
            .expectComplete()
            .verify();

        user.setPassword("123");
        StepVerifier.create(service.save(user)).expectNextCount(1).expectComplete().verify();
    }

    private static void assertEqualRoles(final List<UserRole> expected, final List<UserRole> actual) {
        Assertions.assertEquals(expected.size(), actual.size());
        for (final UserRole role : expected) {
            Assertions.assertTrue(actual.contains(role));
        }
    }
}