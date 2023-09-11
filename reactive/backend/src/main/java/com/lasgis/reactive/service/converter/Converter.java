/*
 *  @(#)Converter.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.converter;

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
}
