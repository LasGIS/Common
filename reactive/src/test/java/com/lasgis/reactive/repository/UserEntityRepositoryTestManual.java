/*
 *  @(#)UserEntityRepositoryTestManual.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.ReactiveApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static java.util.Objects.nonNull;

@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
//@Transactional
class UserEntityRepositoryTestManual {

    private final UserRepository repository;

    @Autowired
    public UserEntityRepositoryTestManual(UserRepository repository) {
        this.repository = repository;
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
}