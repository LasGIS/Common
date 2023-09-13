/*
 *  @(#)PersonServiceImpl.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.model.Person;
import com.lasgis.reactive.model.entity.PersonRelationEntity;
import com.lasgis.reactive.repository.PersonRelationRepository;
import com.lasgis.reactive.repository.PersonRepository;
import com.lasgis.reactive.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_PERSON;
import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_PERSON_RELATION;
import static com.lasgis.reactive.service.converter.Converter.PERSON_2_ENTITY;

/**
 * The Class PersonServiceImpl definition.
 *
 * @author VLaskin
 * @since 12.09.2023 : 16:03
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonRelationRepository relationRepository;

    public PersonServiceImpl(
        final PersonRepository personRepository,
        final PersonRelationRepository relationRepository
    ) {
        this.personRepository = personRepository;
        this.relationRepository = relationRepository;
    }

    @Override
    public Flux<Person> findAll() {
        return personRepository.findAll().map(ENTITY_2_PERSON);
    }

    @Override
    public Mono<Person> findByPersonId(Long personId) {
        return Mono.zip(
            personRepository.findById(personId).map(ENTITY_2_PERSON),
            relationRepository.findByPersonId(personId).map(ENTITY_2_PERSON_RELATION)
                .flatMap(relation -> personRepository.findById(relation.getPersonToId())
                    .map(ENTITY_2_PERSON).map(person -> {
                        relation.setPersonTo(person);
                        return relation;
                    })
                )
                .collectList(),
            (person, list) -> {
                person.setRelations(list);
                return person;
            }
        );
    }

    @Override
    public Mono<Person> save(Person person) {
        return personRepository.save(PERSON_2_ENTITY.apply(person)).map(ENTITY_2_PERSON)
            .flatMap(personOut ->
                relationRepository.deleteAllForPersonId(personOut.getPersonId()).thenReturn(personOut)
            )
            .flatMap(personOut -> {
                person.setPersonId(personOut.getPersonId());
                return Flux.fromIterable(person.getRelations())
                    .flatMap(relation -> relationRepository.save(
                        PersonRelationEntity.builder()
                            .personId(personOut.getPersonId())
                            .personToId(relation.getPersonToId())
                            .type(relation.getType())
                            .build()
                    ))
                    .map(ENTITY_2_PERSON_RELATION)
                    .collectList()
                    .flatMap(relationList -> {
                        personOut.setRelations(relationList);
                        return Mono.just(personOut);
                    });
            });
    }

    @Override
    public Mono<Long> deleteByPersonId(Long personId) {
        return personRepository.deleteById(personId).thenReturn(personId);
    }
}
