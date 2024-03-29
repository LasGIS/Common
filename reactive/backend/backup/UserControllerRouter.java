/*
 *  @(#)UserControllerRouter.java  last: 07.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lasgis.reactive.model.entity.UserEntity;
import com.lasgis.reactive.model.errors.Error;
import com.lasgis.reactive.model.errors.ErrorCode;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * The Class UserControllerRouter definition.
 * <pre>
 *     <a href="https://docs.spring.io/spring-framework/reference/web/webflux-functional.html">Functional Endpoints</a>
 *     <a href="https://github.com/springdoc/springdoc-openapi-demos/tree/master/springdoc-openapi-spring-boot-2-webflux-functional">demo: springdoc-openapi-spring-boot-2-webflux-functional</a>
 * </pre>
 *
 * @author VLaskin
 * @since 15.05.2023 : 17:22
 */
@Slf4j
@Configuration
public class UserControllerRouter {
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Constructor
     *
     * @param userService service
     */
    @Autowired
    public UserControllerRouter(UserService userService) {
        this.userService = userService;
    }

    @Bean
    RouterFunction<ServerResponse> composedRoutes() {
        return route().GET("/api/v1/user", accept(MediaType.APPLICATION_JSON), request ->
                userService.findAll()
                    .map(getUserEntityEntityModelFunction())
                    .collectList()
                    .flatMap(models -> ok().bodyValue(models))
            ).build()
            .and(route().POST("/api/v1/user", accept(MediaType.APPLICATION_JSON), request ->
                request.bodyToMono(UserEntity.class)
                    .flatMap(userService::save)
                    .map(getUserEntityEntityModelFunction())
                    .flatMap(model -> ok().bodyValue(model))
            ).build())
            .and(route().GET("/api/v1/user/login", request -> {
                final String login = request.queryParam("login").orElse(null);
                return userService.findByLogin(login)
                    .map(getUserEntityEntityModelFunction())
                    .flatMap(model -> ok().bodyValue(model));
            }).build())
            .and(route().GET("/api/v1/user/{id}", request ->
                userService.findById(Long.valueOf(request.pathVariable("id")))
                    .map(getUserEntityEntityModelFunction())
                    .flatMap(model -> ok().bodyValue(model))
            ).build())
            .and(route().PUT("/api/v1/user/{id}", request -> {
                final Long id = Long.valueOf(request.pathVariable("id"));
                final Mono<UserEntity> newUserMono = request.bodyToMono(UserEntity.class);
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
                    )
                    .map(getUserEntityEntityModelFunction())
                    .flatMap(user -> ok().bodyValue(user));
            }).build())
            .and(route().DELETE("/api/v1/user/{id}", request ->
                userService.deleteById(Long.valueOf(request.pathVariable("id")))
                    .flatMap(unused -> ok().build())
            ).build());
    }

    private static Function<UserEntity, EntityModel<UserEntity>> getUserEntityEntityModelFunction() {
        return userEntity -> {
            final EntityModel<UserEntity> model = EntityModel.of(userEntity);
            model.add(Link.of("/api/v1/user/{id}", LinkRelation.of(Long.toString(userEntity.getUserId()))).withSelfRel());
            return model;
        };
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
