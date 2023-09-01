/*
 *  @(#)UserController.java  last: 01.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.errors.Error;
import com.lasgis.reactive.model.errors.ErrorCode;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * The Class UserController definition.
 *
 * @author VLaskin
 * @since 15.05.2023 : 17:22
 */
@Slf4j
@Configuration
public class UserController {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    RouterFunction<ServerResponse> composedRoutes() {
        return route(GET("/api/v1/user"), request ->
            userService.findAll()
                .collectList()
                .flatMap(users -> ok().bodyValue(users))
        )
            .and(route(POST("/api/v1/user"), request ->
                request.bodyToMono(UserDto.class)
                    .flatMap(userService::save)
                    .flatMap(user -> ok().bodyValue(user))
            ))
            .and(route(GET("/api/v1/user/login"), request -> {
                final String login = request.queryParam("login").orElse(null);
                return userService.findByLogin(login)
                    .flatMap(user -> ok().bodyValue(user));
            }))
            .and(route(GET("/api/v1/user/{id}"), request ->
                userService.findById(Long.valueOf(request.pathVariable("id")))
                    .flatMap(user -> ok().bodyValue(user))
            ))
            .and(route(PUT("/api/v1/user/{id}"), request -> {
                final Long id = Long.valueOf(request.pathVariable("id"));
                final Mono<UserDto> newUserMono = request.bodyToMono(UserDto.class);
                return userService.findById(id)
                    .flatMap(user ->
                        newUserMono.flatMap(newUser -> {
                            user.setName(newUser.getName());
                            user.setLogin(newUser.getLogin());
                            user.setPassword(newUser.getPassword());
                            user.setArchived(newUser.getArchived());
                            user.setRoles(newUser.getRoles());
                            return userService.save(user);
                        })
                    )
                    .switchIfEmpty(
                        newUserMono.flatMap(newUser -> {
                            newUser.setUserId(id);
                            return userService.save(newUser);
                        })
                    ).flatMap(user -> ok().bodyValue(user));
            }))
            .and(route(DELETE("/api/v1/user/{id}"), request ->
                userService.deleteById(Long.valueOf(request.pathVariable("id")))
                    .flatMap(unused -> ok().build())
            ));
    }

    /**
     * Constructor
     *
     * @param userService service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Bean
    WebFilter dataNotFoundToBadRequest() {
        return (exchange, next) -> next.filter(exchange)
            .onErrorResume(ItemNotFoundException.class, ex -> {
                final ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.NOT_FOUND);
                try {
                    final byte[] buf = objectMapper.writeValueAsBytes(Error.of(ErrorCode.USER_NOT_FOUND, ex.getMessage()));
                    final DataBuffer dataBuffer = response.bufferFactory().wrap(buf);
                    return response.writeWith(Flux.just(dataBuffer));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
