/*
 *  @(#)UserServiceImpl.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import com.lasgis.reactive.repository.UserDtoRepository;
import com.lasgis.reactive.repository.UserRepository;
import com.lasgis.reactive.repository.UserRoleRepository;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_USER_DTO;
import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_USER_ROLE;
import static com.lasgis.reactive.service.converter.Converter.USER_DTO_2_ENTITY;
import static com.lasgis.reactive.service.converter.Converter.USER_ROLE_2_ENTITY;

@Slf4j
@Service
public class UserServiceImpl implements UserService, CommandLineRunner {

    private final UserDtoRepository userDtoRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(
        final UserDtoRepository userDtoRepository,
        final UserRepository userRepository,
        final UserRoleRepository userRoleRepository
    ) {
        this.userDtoRepository = userDtoRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Override
    public Flux<UserDto> findAll() {
        return userDtoRepository.getAllUserDto();
    }

    @Override
    public Mono<UserDto> findById(Long id) {
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
    public Mono<UserDto> save(UserDto userDto) {
        return userRepository.save(USER_DTO_2_ENTITY.apply(userDto))
            .map(ENTITY_2_USER_DTO)
            .flatMap(userOut -> {
                    userDto.setUserId(userOut.getUserId());
                    return Flux.fromIterable(userDto.getRoles())
                        .flatMap(userRole -> userRoleRepository.save(
                            USER_ROLE_2_ENTITY.apply(userOut.getUserId(), userRole)
                        ))
                        .map(ENTITY_2_USER_ROLE)
                        .collectList()
                        .flatMap(roleList -> {
                            userOut.setRoles(roleList);
                            return Mono.just(userOut);
                        });
                }
            );
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id);
    }
}
