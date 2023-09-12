/*
 *  @(#)Converter.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.converter;

import com.lasgis.reactive.model.Person;
import com.lasgis.reactive.model.PersonRelation;
import com.lasgis.reactive.model.entity.PersonEntity;
import com.lasgis.reactive.model.entity.PersonRelationEntity;
import com.lasgis.reactive.model.entity.UserRole;
import com.lasgis.reactive.model.entity.UserRoleEntity;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The Class Converter definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 15:47
 */
public class Converter {

    public static final Function<UserRoleEntity, UserRole> ENTITY_2_USER_ROLE = UserRoleEntity::getRole;

    public static final BiFunction<Long, UserRole, UserRoleEntity> USER_ROLE_2_ENTITY =
        (userId, userRole) -> UserRoleEntity.builder()
            .role(userRole)
            .userId(userId)
            .build();

    public static final Function<PersonEntity, Person> ENTITY_2_PERSON =
        (entity) -> Person.builder()
            .personId(entity.getPersonId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .middleName(entity.getMiddleName())
            .sex(entity.getSex())
            .build();

    public static final Function<PersonRelationEntity, PersonRelation> ENTITY_2_PERSON_RELATION =
        (entity) -> PersonRelation.builder()
            .personId(entity.getPersonId())
            .personToId(entity.getPersonToId())
            .type(entity.getType())
            .build();

    public static final Function<Person, PersonEntity> PERSON_2_ENTITY =
        (entity) -> PersonEntity.builder()
            .personId(entity.getPersonId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .middleName(entity.getMiddleName())
            .sex(entity.getSex())
            .build();

    public static final Function<PersonRelation, PersonRelationEntity> PERSON_RELATION_2_ENTITY =
        (entity) -> PersonRelationEntity.builder()
            .personId(entity.getPersonId())
            .personToId(entity.getPersonToId())
            .type(entity.getType())
            .build();
}
