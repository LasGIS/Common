/*
 *  @(#)PrincipalController.java  last: 16.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.git.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * The Class PrincipalController definition.
 *
 * @author VLaskin
 * @since 15.06.2023 : 12:02
 */
@RestController
@RequestMapping("/principal")
public class PrincipalController {

    @GetMapping
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }
}
