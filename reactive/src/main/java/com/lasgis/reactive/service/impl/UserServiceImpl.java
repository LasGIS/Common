/*
 *  @(#)UserService.java  last: 15.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
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
    public Mono<UserDto> findById(Integer id) {
        return findUserDtoMono(userDtoRepository.getUserDtoById(id));
    }

    @Override
    public Mono<UserDto> findByLogin(final String login) {
        return findUserDtoMono(userDtoRepository.getUserDtoByLogin(login));
    }

    private Mono<UserDto> findUserDtoMono(Mono<UserDto> user) {
        return user.flatMap(userDto -> {
            if (userDto.getArchived()) {
                return Mono.error(new RuntimeException("User is Archived"));
            } else {
                return Mono.just(userDto);
            }
        }).switchIfEmpty(Mono.error(new ItemNotFoundException("User not found")));
    }

    @Override
    public Mono<UserDto> save(UserDto newUserDto) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return userDtoRepository.deleteById(id);
    }
}
