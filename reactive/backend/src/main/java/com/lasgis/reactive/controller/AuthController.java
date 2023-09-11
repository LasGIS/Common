/*
 *  @(#)AuthController.java  last: 08.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.entity.UserRole;
import com.lasgis.reactive.model.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * The Class AuthController definition.
 *
 * @author VLaskin
 * @since 02.08.2023 : 14:31
 */
@RequiredArgsConstructor
@RestController
@Valid
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    /**
     * @return AuthUser
     */
    @GetMapping("user")
    public Mono<AuthUser> getAuthUser(/*@AuthenticationPrincipal OidcUser currentUser, Authentication authentication*/) {
        return Mono.just(
            AuthUser.builder()
                .id("currentUser.getSubject()")
                .email("user@mail.ru")
                .username("currentUser")
                .firstName("currentUser.getGivenName()")
                .lastName("currentUser.getFamilyName()")
                .roles(List.of(UserRole.ADMIN.name(), UserRole.OPERATOR.name()))
                .build()
        );

    }
}
