/*
 *  @(#)UserRoleEntity.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The Class UserRoleEntity definition.
 *
 * @author VLaskin
 * @since 17.05.2023 : 15:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRoleEntity {
    private Long userId;
    private UserRole role;

    public static UserRoleEntity of(final Long userId, final UserRole role) {
        return UserRoleEntity.builder().userId(userId).role(role).build();
    }
}
