/*
 *  @(#)UserServiceTestManual.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.entity.UserRole;
import com.lasgis.reactive.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Autowired
    public UserServiceTestManual(UserService service) {
        this.service = service;
    }

    @Test
    void getAllUserDto() {
        StepVerifier.create(service.findAll())
            .thenConsumeWhile(user -> {
                log.info("\n  user = {}", user);
                return nonNull(user.getUserId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void saveUserDto() {
        final UserDto user = UserDto.builder()
            .name("name")
            .login("login")
            .password("password")
            .archived(true)
            .roles(List.of(UserRole.CHIEF, UserRole.SUPERVISOR))
            .build();
        StepVerifier.create(service.save(user))
            .expectNextMatches(userDto -> nonNull(userDto.getUserId()))
            .expectComplete()
            .verify();
        Assertions.assertNotNull(user.getUserId());
        StepVerifier.create(service.deleteById(user.getUserId()))
            .expectNext()
            .expectComplete()
            .verify();
    }
}