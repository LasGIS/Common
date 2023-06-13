/*
 *  @(#)UserController.java  last: 13.06.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.controller;

import com.lasgis.prototype.vue.exception.UserNotFoundException;
import com.lasgis.prototype.vue.model.UserDto;
import com.lasgis.prototype.vue.model.UserRole;
import com.lasgis.prototype.vue.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";
    public static final Random RANDOM_ID = new Random();
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @ResponseBody
    @GetMapping(path = "/hello")
    public String sayHello() {
        log.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @ResponseBody
    @PostMapping(path = "/user/{name}/{login}")
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser(@PathVariable("name") String name, @PathVariable("login") String login) {
        UserDto newUser = UserDto.builder().userId(RANDOM_ID.nextInt())
            .login(login)
            .name(name)
            .roles(List.of(UserRole.OPERATOR))
            .build();
        Integer userId = userDao.insert(newUser);
        newUser.setUserId(userId);
        log.info(newUser + " successfully saved into DB");
        return userId;
    }

    @ResponseBody
    @GetMapping(path = "/user/{id}")
    public UserDto getUserById(@PathVariable("id") int id) {
        return userDao.findById(id).map(user -> {
            log.info("Reading user with id " + id + " from database.");
            return user;
        }).orElseThrow(() -> {
            throw new UserNotFoundException("The user with the id " + id + " couldn't be found in the database.");
        });
    }

    @ResponseBody
    @GetMapping(path = "/secured")
    public String getSecured() {
        log.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }
}
