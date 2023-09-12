/*
 *  @(#)UserEntityRepositoryTestManual.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.model.entity.UserRole;
import com.lasgis.reactive.model.entity.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
class UserEntityRepositoryTestManual {

    private final UserRepository repository;
    private final UserRoleRepository roleRepository;

    @Autowired
    public UserEntityRepositoryTestManual(UserRepository repository, UserRoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    @Test
    void getAllUser() {
        StepVerifier.create(repository.findAll())
            .thenConsumeWhile(user -> {
                log.info("\n  user = {}", user);
                return nonNull(user.getUserId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    @Order(1)
    void deleteRole() {
        StepVerifier.create(roleRepository.deleteByUserId(1L).doOnSuccess(unused -> {
                log.info(".doOnSuccess(");
            }).thenReturn(".thenReturn()"))
            .expectNextCount(1)
            .expectComplete()
            .verify();

        saveRole(List.of(
            UserRole.ADMIN, UserRole.CHIEF, UserRole.SUPERVISOR
        ));
    }

    void saveRole(final List<UserRole> roleList) {
        StepVerifier.create(roleRepository.saveAll(
                roleList.stream().map(role -> UserRoleEntity.of(1L, role)).toList()
            ))
            .expectNextCount(3)
            .expectComplete()
            .verify();
    }
}