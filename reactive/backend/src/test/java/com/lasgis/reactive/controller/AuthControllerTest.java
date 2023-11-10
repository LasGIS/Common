/*
 *  @(#)AuthControllerTest.java  last: 11.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

/**
 * The Class AuthControllerTest definition.
 *
 * @author VLaskin
 * @since 10.11.2023 : 21:52
 */
class AuthControllerTest extends IntegrationTest {

    @Override
    String basePath() {
        return "/api/v1/auth";
    }

    @Test
    void getAuthUser() {
        assertGet("/user")
            .statusCode(SC_OK)
            .body("username", equalTo("currentUser"))
            .body("firstName", equalTo("currentUser.getGivenName()"));
    }
}