/*
 *  @(#)UserController.java  last: 01.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.controller;

import com.lasgis.prototype.vue.exception.UserNotFoundException;
import com.lasgis.prototype.vue.model.UserDto;
import com.lasgis.prototype.vue.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";
    public static final Random RANDOM_ID = new Random();
//    @Autowired
//    private UserRepository userRepository;

    @ResponseBody
    @GetMapping(path = "/hello")
    public String sayHello() {
        log.info("GET called on /hello resource");
        return HELLO_TEXT;
    }

    @ResponseBody
    @PostMapping(path = "/user/{lastName}/{firstName}")
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser(@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName) {
        UserDto savedUser = //userRepository.save(
            UserDto.builder().userId(RANDOM_ID.nextInt())
                .login(firstName)
                .name(lastName)
                .roles(List.of(UserRole.OPERATOR))
                .build();
//        );
        log.info(savedUser.toString() + " successfully saved into DB");
        return savedUser.getUserId();
    }

    @ResponseBody
    @GetMapping(path = "/user/{id}")
    public UserDto getUserById(@PathVariable("id") int id) {
        if (id == 200) {
            throw new UserNotFoundException("The user with the id " + id + " couldn't be found in the database.");
        }
        return UserDto.builder()
            .userId(id)
            .login("sina")
            .name("miller")
            .roles(List.of(UserRole.OPERATOR))
            .build();
//
//        return userRepository.findById(id).map(user -> {
//            log.info("Reading user with id " + id + " from database.");
//            return user;
//        }).orElseThrow(() -> new UserNotFoundException("The user with the id " + id + " couldn't be found in the database."));
    }

    @ResponseBody
    @GetMapping(path = "/secured")
    public String getSecured() {
        log.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }
}
