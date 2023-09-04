/*
 *  @(#)UserService.java  last: 04.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.entity.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserEntity> findAll();

    Mono<UserEntity> findByLogin(final String login);

    Mono<UserEntity> findById(final Long id);

    Mono<UserEntity> save(UserEntity userEntity);

    Mono<Void> deleteById(Long id);
}
