/*
 *  @(#)PersonController.java  last: 13.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.model.Person;
import com.lasgis.reactive.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The Class PersonController definition.
 *
 * @author VLaskin
 * @since 13.09.2023 : 13:00
 */
@Slf4j
@RestController
@RequestMapping("api/v2.0/person")
public class PersonController {
    private final PersonService service;

    /**
     * Constructor
     *
     * @param service service
     */
    @Autowired
    public PersonController(final PersonService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<Flux<Person>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "{personId}")
    public ResponseEntity<Mono<Person>> getPersonById(@PathVariable("personId") final Long personId) {
        return ResponseEntity.ok(service.findByPersonId(personId));
    }

    @PostMapping()
    public ResponseEntity<Mono<Person>> createNewPerson(@RequestBody Person newPerson) {
        return ResponseEntity.ok(service.save(newPerson));
    }

    @PutMapping("{personId}")
    public ResponseEntity<Mono<Person>> replaceEmployee(@RequestBody Person newPerson, @PathVariable Long personId) {
        return ResponseEntity.ok(service.findByPersonId(personId)
            .flatMap(person -> {
                person.setPersonId(newPerson.getPersonId());
                person.setFirstName(newPerson.getFirstName());
                person.setLastName(newPerson.getLastName());
                person.setMiddleName(newPerson.getMiddleName());
                person.setSex(newPerson.getSex());
                person.setRelations(newPerson.getRelations());
                return service.save(person);
            })
            .switchIfEmpty(
                Mono.defer(() -> {
                    newPerson.setPersonId(personId);
                    return service.save(newPerson);
                })
            ));
    }

    @DeleteMapping("{personId}")
    ResponseEntity<Mono<Long>> deleteEmployee(@PathVariable Long personId) {
        return ResponseEntity.ok(service.deleteByPersonId(personId));
    }
}
