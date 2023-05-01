/*
 *  @(#)UserDto.java  last: 01.05.2023
 *
 * Title: LG propotype for java-sprind-jdbc + vue-type-script
 * Description: Program for support Arduino.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer userId;
    private String login;
    private String name;
    private String password;
    private List<UserRole> roles;
    private Boolean archived;
}
