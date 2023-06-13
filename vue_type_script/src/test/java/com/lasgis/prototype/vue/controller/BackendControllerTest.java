/*
 *  @(#)BackendControllerTest.java  last: 13.06.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.controller;

import com.lasgis.prototype.vue.VueApplication;
import com.lasgis.prototype.vue.model.UserDto;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(
    classes = VueApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class BackendControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void saysHello() {
        when()
            .get("/api/hello")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .body(is(equalTo(UserController.HELLO_TEXT)));
    }

    @Test
    public void addNewUserAndRetrieveItBack() {
        final Integer userId = given()
            .pathParam("name", "Siegmund")
            .pathParam("login", "Norbert")
            .when()
            .post("/api/user/{name}/{login}")
            .then()
            .statusCode(is(HttpStatus.SC_CREATED))
            .extract()
            .body().as(Integer.class);

        final UserDto responseUser = given()
            .pathParam("id", userId)
            .when()
            .get("/api/user/{id}")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .extract().as(UserDto.class);

        // Did Norbert came back?
        assertThat(responseUser.getName()).isEqualTo("Siegmund");
        assertThat(responseUser.getLogin()).isEqualTo("Norbert");
    }

    @Test
    public void user_api_should_give_http_404_not_found_when_user_not_present_in_db() {
        Long someId = 200L;
        given()
            .pathParam("id", someId)
            .when()
            .get("/api/user/{id}")
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void secured_api_should_react_with_unauthorized_per_default() {

        given()
            .when()
            .get("/api/secured")
            .then()
            .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void secured_api_should_give_http_200_when_authorized() {
        given()
            .auth().basic("sina", "password")
            .when()
            .get("/api/secured")
            .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .body(is(equalTo(UserController.SECURED_TEXT)));
    }

}
