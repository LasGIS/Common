/*
 *  @(#)UserController.java  last: 15.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.errors.Error;
import com.lasgis.reactive.model.errors.ErrorCode;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class UserController definition.
 *
 * @author VLaskin
 * @since 15.05.2023 : 17:22
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    /**
     * Constructor
     *
     * @param userService service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user")
    public Flux<UserDto> list() {
        return userService.findAll();
    }

    @ResponseBody
    @GetMapping(path = "/user/{login}")
    public Mono<UserDto> getUserById(@PathVariable("login") final String login) {
        return userService.findByLogin(login);
//        if (id == 200) {
//            throw new UserNotFoundException("The user with the id " + id + " couldn't be found in the database.");
//        }
//
//        return userRepository.findById(id).map(user -> {
//            log.info("Reading user with id " + id + " from database.");
//            return user;
//        }).orElseThrow(() -> new UserNotFoundException("The user with the id " + id + " couldn't be found in the database."));
    }

    @ExceptionHandler()
    public Error externalServiceError(Exception ex) {
        final String message = String.format("External service error: %s", ex.getMessage());
        log.warn(message);
        return Error.of(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }

}
