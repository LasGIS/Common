/*
 *  @(#)Converter.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.converter;

import com.lasgis.reactive.entity.UserEntity;
import com.lasgis.reactive.entity.UserRole;
import com.lasgis.reactive.entity.UserRoleEntity;
import com.lasgis.reactive.model.UserDto;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The Class Converter definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 15:47
 */
public class Converter {

    public static final Function<UserRoleEntity, UserRole> ENTITY_2_USER_ROLE =
        userRoleEntity -> UserRole.valueOf(userRoleEntity.getRoleId());

    public static final BiFunction<Long, UserRole, UserRoleEntity> USER_ROLE_2_ENTITY =
        (userId, userRole) -> UserRoleEntity.builder()
            .roleId(userRole.name())
            .userId(userId)
            .build();

    public static final Function<UserEntity, UserDto> ENTITY_2_USER_DTO =
        userEntity -> UserDto.builder()
            .userId(userEntity.getUserId())
            .login(userEntity.getLogin())
            .name(userEntity.getName())
            .password(userEntity.getPassword())
            .archived(userEntity.getArchived())
            .roles(new ArrayList<>())
            .build();

    public static final Function<UserDto, UserEntity> USER_DTO_2_ENTITY =
        userDto -> UserEntity.builder()
            .userId(userDto.getUserId())
            .login(userDto.getLogin())
            .name(userDto.getName())
            .password(userDto.getPassword())
            .archived(userDto.getArchived())
            .build();
}
