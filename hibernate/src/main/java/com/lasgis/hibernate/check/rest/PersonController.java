/*
 *  @(#)PersonController.java  last: 05.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.hibernate.check.rest;

import com.lasgis.hibernate.check.dao.entity.PersonEntity;
import com.lasgis.hibernate.check.dao.repository.PersonRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for web
 */
@RestController
@CrossOrigin(origins = "*")
@Valid
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * @return All persons
     */
    @GetMapping()
    public List<PersonEntity> getPersons() {
        return personRepository.findAll();
    }

    /**
     * Get person by ID
     *
     * @param id person ID
     * @return person by ID
     */
    @GetMapping(path = "{id}")
    public Optional<PersonEntity> getPersonById(@PathVariable("id") final Long id) {
        return personRepository.findById(id);
    }

    /**
     * Create new Person
     *
     * @param newPerson new Person
     * @return Created Person
     */
    @PostMapping()
    PersonEntity newPerson(@RequestBody PersonEntity newPerson) {
        return personRepository.saveAndFlush(newPerson);
    }

    /**
     * Create some Persons
     *
     * @param newListPerson new list of Persons
     * @return Created list of Persons
     */
    @PostMapping("some")
    List<PersonEntity> newPersons(@RequestBody List<PersonEntity> newListPerson) {
        return personRepository.saveAllAndFlush(newListPerson);
    }

    /**
     * Update Person
     *
     * @param uppPerson info Person for Update
     * @param id        Person ID
     * @return Created or Updated Person
     */
    @PutMapping("{id}")
    Optional<PersonEntity> replaceEmployee(@RequestBody PersonEntity uppPerson, @PathVariable Long id) {
        final Optional<PersonEntity> person = personRepository.findById(id);
        PersonEntity[] outPerson = new PersonEntity[1];
        person.ifPresentOrElse(personEntity -> {
            personEntity.setFirstName(uppPerson.getFirstName());
            personEntity.setLastName(uppPerson.getLastName());
            personEntity.setMiddleName(uppPerson.getMiddleName());
            personEntity.setGender(uppPerson.getGender());
            outPerson[0] = personRepository.saveAndFlush(personEntity);
        }, () -> outPerson[0] = personRepository.saveAndFlush(uppPerson));
        return Optional.of(outPerson[0]);
    }

    /**
     * Delete person by ID
     *
     * @param id person ID
     */
    @DeleteMapping("{id}")
    void deleteEmployee(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
}
