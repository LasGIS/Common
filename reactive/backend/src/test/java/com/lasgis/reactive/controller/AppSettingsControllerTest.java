/*
 *  @(#)AppSettingsControllerTest.java  last: 11.11.2023
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
 * The Class AppSettingsControllerTest definition.
 *
 * @author VLaskin
 * @since 10.11.2023 : 15:59
 */
public class AppSettingsControllerTest extends IntegrationTest {

    @Override
    String basePath() {
        return "/api/v2.0";
    }

    @Test
    void getAppSettings() {
        assertGet("/settings")
            .statusCode(SC_OK)
            .body("name", equalTo("UI reactive"))
            .body("version", equalTo("0.2.1-SNAPSHOT"));
    }
}