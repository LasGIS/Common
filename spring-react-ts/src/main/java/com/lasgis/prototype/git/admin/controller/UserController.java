package com.lasgis.prototype.git.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * The Class UserController definition.
 *
 * @author VLaskin
 * @since 15.06.2023 : 12:02
 */
@RestController
@RequestMapping("/principal")
public class UserController {

    @GetMapping
    public Principal retrievePrincipal(Principal principal) {
        return principal;
    }
}
