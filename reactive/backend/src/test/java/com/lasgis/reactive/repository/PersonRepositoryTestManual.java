/*
 *  @(#)PersonRepositoryTestManual.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.repository;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.model.entity.PersonRelationEntity;
import com.lasgis.reactive.model.entity.PersonRelationType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * The Class PersonRepositoryTestManual definition.
 *
 * @author VLaskin
 * @since 12.09.2023 : 12:09
 */
@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTestManual {
    private final PersonRepository repository;
    private final PersonRelationRepository relationRepository;

    @Autowired
    public PersonRepositoryTestManual(final PersonRepository repository, final PersonRelationRepository relationRepository) {
        this.repository = repository;
        this.relationRepository = relationRepository;
    }

    @Test
    @Order(1)
    void getAllPerson() {
        StepVerifier.create(repository.findAll())
            .thenConsumeWhile(person -> {
                log.info("\n  person = {}", person);
                return nonNull(person.getPersonId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    @Order(2)
    void deleteRelation() {
        StepVerifier.create(relationRepository.deleteAllForPersonId(1L).doOnSuccess(unused -> {
                log.info(".doOnSuccess(");
            }).thenReturn(".thenReturn()"))
            .expectNextCount(1)
            .expectComplete()
            .verify();
    }
    @Test
    @Order(3)
    void saveRelation() {
        saveRelation(List.of(
            PersonRelationEntity.builder().personId(1L).personToId(2L).type(PersonRelationType.PARENT).build(),
            PersonRelationEntity.builder().personId(2L).personToId(1L).type(PersonRelationType.CHILD).build(),
            PersonRelationEntity.builder().personId(1L).personToId(3L).type(PersonRelationType.PARENT).build(),
            PersonRelationEntity.builder().personId(3L).personToId(1L).type(PersonRelationType.CHILD).build(),
            PersonRelationEntity.builder().personId(2L).personToId(3L).type(PersonRelationType.SPOUSE).build(),
            PersonRelationEntity.builder().personId(3L).personToId(2L).type(PersonRelationType.SPOUSE).build()
        ));
    }

    void saveRelation(final List<PersonRelationEntity> relationList) {
        StepVerifier.create(relationRepository.saveAll(relationList))
            .expectNextCount(relationList.size())
            .expectComplete()
            .verify();
    }
}