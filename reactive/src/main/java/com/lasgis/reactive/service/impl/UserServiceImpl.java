/*
 *  @(#)UserServiceImpl.java  last: 07.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.entity.UserEntity;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import com.lasgis.reactive.repository.UserRepository;
import com.lasgis.reactive.repository.UserRoleRepository;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_USER_ROLE;
import static com.lasgis.reactive.service.converter.Converter.USER_ROLE_2_ENTITY;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImpl(
        final UserRepository userRepository,
        final UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Flux<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<UserEntity> findById(Long id) {
        return findUserEntityMono(userRepository.findById(id));
    }

    @Override
    public Mono<UserEntity> findByLogin(final String login) {
        return findUserEntityMono(userRepository.findByLogin(login));
    }

    private Mono<UserEntity> findUserEntityMono(Mono<UserEntity> user) {
        return user.flatMap(userEntity -> {
            if (userEntity.getArchived()) {
                return Mono.error(new RuntimeException("User is Archived"));
            } else {
                return Mono.just(userEntity);
            }
        }).switchIfEmpty(Mono.error(new ItemNotFoundException("User not found")));
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        return userRepository.save(userEntity)
            .flatMap(userOut ->
                userRoleRepository.deleteByUserId(userOut.getUserId()).thenReturn(userOut)
            )
            .flatMap(userOut -> {
                userEntity.setUserId(userOut.getUserId());
                return Flux.fromIterable(userEntity.getRoles())
                    .flatMap(userRole -> userRoleRepository.save(
                        USER_ROLE_2_ENTITY.apply(userOut.getUserId(), userRole)
                    ))
                    .map(ENTITY_2_USER_ROLE)
                    .collectList()
                    .flatMap(roleList -> {
                        userOut.setRoles(roleList);
                        return Mono.just(userOut);
                    });
            });
    }

    @Override
    public Mono<Long> deleteById(Long id) {
        return userRepository.deleteById(id).thenReturn(id);
    }
}
