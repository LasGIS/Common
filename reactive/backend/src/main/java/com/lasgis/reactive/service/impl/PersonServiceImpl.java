/*
 *  @(#)PersonServiceImpl.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.impl;

import com.lasgis.reactive.model.Person;
import com.lasgis.reactive.model.PersonRelation;
import com.lasgis.reactive.repository.PersonRelationRepository;
import com.lasgis.reactive.repository.PersonRepository;
import com.lasgis.reactive.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_PERSON;
import static com.lasgis.reactive.service.converter.Converter.ENTITY_2_PERSON_RELATION;
import static com.lasgis.reactive.service.converter.Converter.PERSON_2_ENTITY;
import static com.lasgis.reactive.service.converter.Converter.PERSON_RELATION_2_ENTITY;
import static com.lasgis.reactive.service.utils.Utils.NORMALIZED_PERSON_RELATION_LIST;
import static java.util.Objects.nonNull;

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
        final Mono<Person> personFnd = personRepository.save(PERSON_2_ENTITY.apply(person)).map(ENTITY_2_PERSON);
        final List<PersonRelation> personRelation = person.getRelations();
        if (nonNull(personRelation)) {
            return personFnd.flatMap(personOut ->
                    relationRepository.deleteAllForPersonId(personOut.getPersonId()).thenReturn(personOut)
                )
                .flatMap(personOut -> {
                    final Long personId = personOut.getPersonId();
                    person.setPersonId(personId);
                    person.getRelations().forEach(rel -> rel.setPersonId(personId));
                    return Flux.fromIterable(NORMALIZED_PERSON_RELATION_LIST.apply(person.getRelations()))
                        .flatMap(relation -> relationRepository.save(PERSON_RELATION_2_ENTITY.apply(relation)))
                        .map(ENTITY_2_PERSON_RELATION)
                        .collectList()
                        .flatMap(relationList -> {
                            personOut.setRelations(relationList);
                            return Mono.just(personOut);
                        });
                });
        } else {
            return personFnd;
        }
    }

    @Override
    public Mono<Long> deleteByPersonId(Long personId) {
        return personRepository.deleteById(personId).thenReturn(personId);
    }
}
