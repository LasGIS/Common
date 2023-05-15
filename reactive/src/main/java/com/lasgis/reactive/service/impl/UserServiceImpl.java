/*
 *  @(#)UserService.java  last: 15.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.repository.UserDtoRepository;
import com.lasgis.reactive.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserDtoRepository userDtoRepository;

    public UserServiceImpl(UserDtoRepository userDtoRepository) {
        this.userDtoRepository = userDtoRepository;
    }

    @Override
    public Flux<UserDto> findAll() {
        return userDtoRepository.getAllUserDto();
    }

    @Override
    public Mono<UserDto> findByLogin(final String login) {
        return userDtoRepository.getUserDtoByLogin(login);
    }
}
