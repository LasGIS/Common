/*
 *  @(#)UserController.java  last: 08.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.entity.UserEntity;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.Affordances;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

/**
 * The Class UserController definition.
 *
 * @author VLaskin
 * @since 05.09.2023 : 11:42
 */
@Slf4j
@RestController
@RequestMapping("api/v2.0/user")
public class UserController {
    private final UserService userService;

    /**
     * Constructor
     *
     * @param userService service
     */
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Flux<EntityModel<UserEntity>> list() {
        return userService.findAll().flatMap(getUserEntityEntityModelFunction());
    }

    @GetMapping(path = "{id}")
    public Mono<EntityModel<UserEntity>> getUserById(@PathVariable("id") final Long id) {
        return userService.findById(id).flatMap(getUserEntityEntityModelFunction());
    }

    @GetMapping(path = "login")
    public Mono<EntityModel<UserEntity>> getUserByLogin(@RequestParam("login") final String login) {
        return userService.findByLogin(login).flatMap(getUserEntityEntityModelFunction());
    }

    @PostMapping()
    Mono<EntityModel<UserEntity>> createNewUser(@RequestBody UserEntity newUser) {
        return userService.save(newUser).flatMap(getUserEntityEntityModelFunction());
    }

    @PutMapping("{id}")
    Mono<EntityModel<UserEntity>> replaceEmployee(@RequestBody UserEntity newUser, @PathVariable Long id) {
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
            ).flatMap(getUserEntityEntityModelFunction());
    }

    @DeleteMapping("{id}")
    Mono<Long> deleteEmployee(@PathVariable Long id) {
        return userService.deleteById(id);
    }

    private static Function<UserEntity, Mono<EntityModel<UserEntity>>> getUserEntityEntityModelFunction() {
        return userEntity -> {
            final Class<UserController> controllerClass = UserController.class;
            final Long id = userEntity.getUserId();
            return Mono.just(EntityModel.of(userEntity,
                Affordances.of(
                    linkTo(methodOn(controllerClass).getUserById(id)).withSelfRel()
                ).afford(HttpMethod.GET).toLink(),
                Affordances.of(
                        linkTo(methodOn(controllerClass).createNewUser(userEntity))
                            .withRel("create")
                            .withName("Создать")
                            .withTitle("Создать нового пользователя")
                    )
                    .afford(HttpMethod.POST)
                    .withInputAndOutput(UserEntity.class)
                    .toLink(),
                Affordances.of(
                        linkTo(methodOn(controllerClass).replaceEmployee(userEntity, id))
                            .withRel("update")
                            .withName("Редактировать")
                            .withTitle("Редактируем параметры старого пользователя")
                    )
                    .afford(HttpMethod.PUT)
                    .withInputAndOutput(UserEntity.class)
                    .toLink(),
                Affordances.of(
                    linkTo(methodOn(controllerClass).deleteEmployee(id))
                        .withRel("delete")
                        .withMedia(MediaType.APPLICATION_JSON_VALUE)
                        .withName("Удалить")
                        .withTitle("Удаляем пользователя со всеми потрохами")
                ).afford(HttpMethod.DELETE).toLink()
            ));
        };
    }
}
