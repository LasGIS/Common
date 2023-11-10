/*
 *  @(#)IntegrationTest.java  last: 11.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.ReactiveApplication;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.function.Consumer;

import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

/**
 * The Class IntegrationTest definition.
 *
 * @author VLaskin
 * @since 10.11.2023 : 23:52
 */
@SpringJUnitConfig
@SpringBootTest(
    classes = ReactiveApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public abstract class IntegrationTest {

    @LocalServerPort
    int localServerPort;

    abstract String basePath();

    public RequestSpecification requestSpecification(final String basePath) {
        return new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(localServerPort)
            .setBasePath(basePath)
            .setRelaxedHTTPSValidation()
            .setContentType(JSON)
            .setAccept(JSON)
            .build();
    }

    public ResponseSpecification responseSpecificationScOk() {
        return new ResponseSpecBuilder()
            .log(LogDetail.STATUS)
            .expectContentType(JSON)
            .expectStatusCode(HttpStatus.SC_OK)
            .expectResponseTime(lessThanOrEqualTo(3L), SECONDS)
            .build();
    }

    public ValidatableResponse assertGet(final String path) {
        return assertGet(path, i -> {});
    }

    public ValidatableResponse assertGet(final String path, final Consumer<RequestSpecification> beforeGet) {
        final var request = RestAssured.given(requestSpecification(basePath()))
            .log().all()
            .when();
        beforeGet.accept(request);
        return request
            .get(path)
            .then()
            .log().all();
    }

}
