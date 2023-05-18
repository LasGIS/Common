/*
 *  @(#)UserServiceImpl.java  last: 18.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import com.lasgis.reactive.repository.UmUserRepository;
import com.lasgis.reactive.repository.UmUserRoleRepository;
import com.lasgis.reactive.repository.UserDtoRepository;
import com.lasgis.reactive.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.lasgis.reactive.service.converter.Converter.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDtoRepository userDtoRepository;
    private final UmUserRepository umUserRepository;
    private final UmUserRoleRepository umUserRoleRepository;

    public UserServiceImpl(
        final UserDtoRepository userDtoRepository,
        final UmUserRepository umUserRepository,
        final UmUserRoleRepository umUserRoleRepository
    ) {
        this.userDtoRepository = userDtoRepository;
        this.umUserRepository = umUserRepository;
        this.umUserRoleRepository = umUserRoleRepository;
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
        return umUserRepository.save(USER_DTO_2_UM_USER.apply(userDto))
            .map(UM_USER_2_USER_DTO)
            .flatMap(userOut ->
                Flux.fromIterable(userDto.getRoles())
                    .flatMap(userRole -> umUserRoleRepository.save(
                        USER_ROLE_2_UM_USER_ROLE.apply(userOut.getUserId(), userRole)
                    ))
                    .map(UM_USER_ROLE_2_USER_ROLE)
                    .collectList()
                    .flatMap(roleList -> {
                        userOut.setRoles(roleList);
                        return Mono.just(userOut);
                    })
            );
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return umUserRepository.deleteById(id);
    }
}
