/*
 *  @(#)UserService.java  last: 15.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.model.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDto> findAll();

    Mono<UserDto> findByLogin(final String login);

    Mono<UserDto> findById(final Integer id);

    Mono<UserDto> save(UserDto newUserDto);

    Mono<Void> deleteById(Integer id);
}
