/*
 *  @(#)UserDto.java  last: 31.08.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model;

import com.lasgis.reactive.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * The Class User definition.
 *
 * @author Vladimir Laskin
 * @since 30.04.2023 : 21:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    private Long userId;
    private String login;
    private String name;
    private String password;
    private List<UserRole> roles;
    private Boolean archived;
}
