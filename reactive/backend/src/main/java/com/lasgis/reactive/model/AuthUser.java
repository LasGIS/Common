/*
 *  @(#)AuthUser.java  last: 08.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Class User definition.
 *
 * @author VLaskin
 * @since 27.06.2023 : 12:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private List<String> roles;
}
