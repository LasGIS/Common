/*
 *  @(#)UserConverter.java  last: 08.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.service.converter;

import com.lasgis.prototype.hibernate.dao.User;
import com.lasgis.prototype.hibernate.entity.UserEntity;
import com.lasgis.prototype.hibernate.entity.type.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * The Class UserConverter definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 15:47
 */
public class UserConverter {

    /**
     *
     */
    public static final Function<UserEntity, User> USER_ENTITY_2_USER =
        entity -> User.builder()
            .userId(entity.getUserId())
            .login(entity.getLogin())
            .name(entity.getName())
            .password(entity.getPassword())
            .archived(entity.getArchived())
            .roles(List.of(Optional.ofNullable(entity.getRoles()).orElse(new UserRole[0])))
            .build();

    /**
     *
     */
    public static final Function<User, UserEntity> USER_2_USER_ENTITY =
        user -> {
            UserEntity entity = new UserEntity();
            entity.setUserId(user.getUserId());
            entity.setLogin(user.getLogin());
            entity.setName(user.getName());
            entity.setPassword(user.getPassword());
            entity.setArchived(user.getArchived());
            entity.setRoles(user.getRoles().toArray(new UserRole[0]));
            return entity;
        };
}
