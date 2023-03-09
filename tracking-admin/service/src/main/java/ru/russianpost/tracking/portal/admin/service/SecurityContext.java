/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service;

import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

/**
 * SecurityContext
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class SecurityContext {

    /**
     * Gets user name of current user
     * @return user name of current user
     */
    public Optional<String> getUsername() {
       return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Principal::getName);
    }
}
