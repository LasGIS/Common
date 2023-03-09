/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.utils;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * PostID Utils
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Build basic auth header value.
     * @param credentialsPlain credentials plain, separated with ':', like: 'login:password'
     * @return basic authentication header value
     */
    public static String buildBasicAuthHeaderValue(String credentialsPlain) {
        return "Basic " + new String(Base64.getEncoder().encode(credentialsPlain.getBytes()), UTF_8);
    }

    /**
     * Build basic auth header value.
     * @param login    login
     * @param password password
     * @return basic authentication header value
     */
    public static String buildBasicAuthHeaderValue(String login, String password) {
        return buildBasicAuthHeaderValue(login + ":" + password);
    }
}
