/*
 *  @(#)PersonServiceTestManual.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service;

import com.lasgis.reactive.ReactiveApplication;
import com.lasgis.reactive.model.Person;
import com.lasgis.reactive.model.PersonRelation;
import com.lasgis.reactive.model.entity.PersonRelationType;
import com.lasgis.reactive.model.entity.SexType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.List;

import static java.util.Objects.nonNull;

/**
 * The Class PersonServiceImplTestManual definition.
 *
 * @author VLaskin
 * @since 12.09.2023 : 17:19
 */
@Slf4j
@SpringBootTest(classes = ReactiveApplication.class)
@AutoConfigureTestEntityManager
class PersonServiceTestManual {

    private final PersonService service;

    @Autowired
    PersonServiceTestManual(PersonService service) {
        this.service = service;
    }

    @Test
    void findAll() {
        StepVerifier.create(service.findAll())
            .thenConsumeWhile(person -> {
                log.info("\n  Person = {}", person);
                return nonNull(person.getPersonId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void findByPersonId() {
        StepVerifier.create(service.findByPersonId(1L))
            .thenConsumeWhile(person -> {
                log.info("\n  Person = {}", person);
                return nonNull(person.getPersonId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void save() {
        final Person ivanka = Person.builder()
//            .personId(7L)
            .firstName("Матрона")
            .middleName("Ивановна")
            .lastName("Салтыкова")
            .sex(SexType.FEMALE)
            .relations(List.of(
                PersonRelation.builder().personToId(1L).type(PersonRelationType.SIBLING).build(),
                PersonRelation.builder().personToId(2L).type(PersonRelationType.PARENT).build(),
                PersonRelation.builder().personToId(3L).type(PersonRelationType.PARENT).build()
            ))
            .build();
        StepVerifier.create(service.save(ivanka))
            .thenConsumeWhile(person -> {
                log.info("\n  Person = {}", person);
                return nonNull(person.getPersonId());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void deleteByPersonId() {
    }
}
