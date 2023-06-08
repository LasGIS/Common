/*
 *  @(#)PersonConverter.java  last: 08.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.service.converter;

import com.lasgis.prototype.hibernate.dao.Person;
import com.lasgis.prototype.hibernate.dao.PersonRelation;
import com.lasgis.prototype.hibernate.entity.PersonEntity;
import com.lasgis.prototype.hibernate.entity.PersonRelationEntity;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The Class PersonConverter definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 15:47
 */
public class PersonConverter {

    /**
     *
     */
    public static PersonRelation PERSON_RELATION_ENTITY_2_PERSON(
        final PersonRelationEntity entity, final boolean isFrom, final Integer deep
    ) {
        final Person person = PERSON_ENTITY_2_PERSON
            .apply(isFrom ? entity.getPersonFrom() : entity.getPersonTo(), deep - 1);
        return PersonRelation.builder()
            .type(entity.getType())
            .person(person)
            .build();
    }

    /**
     *
     */
    public static final BiFunction<PersonEntity, Integer, Person> PERSON_ENTITY_2_PERSON =
        (entity, deep) -> {
            Person.PersonBuilder builder = Person.builder()
                .personId(entity.getPersonId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .gender(entity.getGender());
            if (deep > 0) {
                final Set<PersonRelationEntity> fromRelations = entity.getFromRelations();
                final Set<PersonRelationEntity> toRelations = entity.getToRelations();
                builder.from(fromRelations.stream()
                    .map(pr -> PERSON_RELATION_ENTITY_2_PERSON(pr, true, deep)).toList());
                builder.to(toRelations.stream()
                    .map(pr -> PERSON_RELATION_ENTITY_2_PERSON(pr, false, deep)).toList());
            }
            return builder.build();
        };

    /**
     *
     */
    public static final Function<Person, PersonEntity> PERSON_2_PERSON_ENTITY =
        person -> {
            final PersonEntity entity = new PersonEntity();
            entity.setPersonId(person.getPersonId());
            entity.setFirstName(person.getFirstName());
            entity.setLastName(person.getLastName());
            entity.setMiddleName(person.getMiddleName());
            entity.setGender(person.getGender());
//            entity.setFromRelations(person.getFrom());
//            entity.setToRelations(person.getTo());
            return entity;
        };
}
