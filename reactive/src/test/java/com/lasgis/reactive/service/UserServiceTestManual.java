/*
 *  @(#)UserServiceTestManual.java  last: 18.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * The Class  definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 13:07
 */
@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
class UserServiceTestManual {

    private final UserService service;

    @Autowired
    public UserServiceTestManual(UserService service) {
        this.service = service;
    }

    @Test
    void getAllUserDto() {
        final Flux<UserDto> flux = service.findAll();
        final List<UserDto> list = flux.collectList().block();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
        log.info("list = {}", list);
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
        final UserDto userDto = service.save(user).block();
        //service.deleteById(userDto.getUserId()).block();
        log.info("UserDto = {}", userDto);
    }
}