/*
 *  @(#)UserController.java  last: 17.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.model.UserDto;
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
@RequestMapping("/api/v1/user")
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

    @GetMapping()
    public Flux<UserDto> list() {
        return userService.findAll();
    }

    @GetMapping(path = "{id}")
    public Mono<UserDto> getUserById(@PathVariable("id") final Long id) {
        return userService.findById(id);
    }

    @GetMapping(path = "login")
    public Mono<UserDto> getUserByLogin(@RequestParam("login") final String login) {
        return userService.findByLogin(login);
    }

    @PostMapping()
    Mono<UserDto> newUser(@RequestBody UserDto newUser) {
        return userService.save(newUser);
    }

    @PutMapping("{id}")
    Mono<UserDto> replaceEmployee(@RequestBody UserDto newUser, @PathVariable Long id) {
        return userService.findById(id)
            .flatMap(user -> {
                user.setName(newUser.getName());
                user.setLogin(newUser.getLogin());
                user.setPassword(newUser.getPassword());
                user.setArchived(newUser.getArchived());
                user.setRoles(newUser.getRoles());
                return userService.save(user);
            })
            .switchIfEmpty(
                Mono.defer(() -> {
                    newUser.setUserId(id);
                    return userService.save(newUser);
                })
            );
    }

    @DeleteMapping("{id}")
    Mono<Void> deleteEmployee(@PathVariable Integer id) {
        return userService.deleteById(id);
    }
}
