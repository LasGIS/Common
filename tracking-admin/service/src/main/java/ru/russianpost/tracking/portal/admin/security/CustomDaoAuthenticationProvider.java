/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Amosov Maxim
 * @since 08.09.2021 : 15:44
 */
@Slf4j
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(
        final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication
    ) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        final String password = authentication.getCredentials().toString();
        if (StringUtils.isBlank(password)) {
            log.warn("Attempt to login with empty password! username = {}", userDetails.getUsername());
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
