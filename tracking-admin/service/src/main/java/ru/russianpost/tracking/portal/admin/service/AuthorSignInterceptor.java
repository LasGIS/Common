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

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * AuthorSignInterceptor
 * <p>
 * Adds author sign for any request with specified methods
 * @author Roman Prokhorov
 * @version 1.0
 */
@Slf4j
public class AuthorSignInterceptor implements ClientHttpRequestInterceptor {

    private final SecurityContext securityContext;
    private final Set<HttpMethod> forMethods;

    /**
     * Creates new interceptor
     * @param securityContext security context for username providing
     * @param forMethods      methods allowed for sign, other methods will be ignored
     */
    public AuthorSignInterceptor(SecurityContext securityContext, Set<HttpMethod> forMethods) {
        this.securityContext = securityContext;
        this.forMethods = Collections.unmodifiableSet(forMethods);
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final HttpRequest wrapper = new HttpRequestWrapper(request);
        final Optional<String> username = securityContext.getUsername();

        if (forMethods.contains(wrapper.getMethod())) {
            if (username.isPresent()) {
                wrapper.getHeaders().set("X-Change-Author", username.get());
            } else {
                log.warn("No authentication information is available for current user. URI: {}", wrapper.getURI());
            }
        }

        return execution.execute(wrapper, body);
    }
}
