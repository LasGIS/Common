/*
 *  @(#)Converter.java  last: 17.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.service.converter;

import com.lasgis.reactive.entity.UmUser;
import com.lasgis.reactive.entity.UmUserRole;
import com.lasgis.reactive.model.UserDto;
import com.lasgis.reactive.model.UserRole;

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

    public static final Function<UmUserRole, UserRole> UM_USER_ROLE_2_USER_ROLE =
        umUserRole -> UserRole.valueOf(umUserRole.getUmrleRoleId());

    public static final BiFunction<Long, UserRole, UmUserRole > USER_ROLE_2_UM_USER_ROLE =
        (userId, userRole) -> UmUserRole.builder()
            .umrleRoleId(userRole.name())
            .umusrUserId(userId)
            .build();

    public static final Function<UmUser, UserDto> UM_USER_2_USER_DTO =
        umUser -> UserDto.builder()
            .userId(umUser.getUmusrUserId())
            .login(umUser.getUmusrLogin())
            .name(umUser.getUmusrName())
            .password(umUser.getUmusrPassword())
            .archived(umUser.getUmusrArchived())
            .roles(new ArrayList<>())
            .build();

    public static final Function<UserDto, UmUser> USER_DTO_2_UM_USER =
        userDto -> UmUser.builder()
            .umusrUserId(userDto.getUserId())
            .umusrLogin(userDto.getLogin())
            .umusrName(userDto.getName())
            .umusrPassword(userDto.getPassword())
            .umusrArchived(userDto.getArchived())
            .build();
}
