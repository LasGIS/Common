/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

/**
 * PlusEncoderInterceptor *
 *
 * @author MKitchenko
 * @version 1.0 09.02.2021
 * @see <a href="https://stackoverflow.com/a/54343680">
 * '+' (plus sign) not encoded with RestTemplate using String url, but interpreted as ' ' (space)
 * </a>
 */
public class PlusEncoderInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public @NotNull ClientHttpResponse intercept(
            @NotNull HttpRequest request,
            byte @NotNull [] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        return execution.execute(new HttpRequestWrapper(request) {
            @Override
            public @NotNull URI getURI() {
                URI uri = super.getURI();
                String strictlyEscapedQuery = StringUtils.replace(uri.getRawQuery(), "+", "%2B");
                return UriComponentsBuilder.fromUri(uri)
                        .replaceQuery(strictlyEscapedQuery)
                        .build(true).toUri();
            }
        }, body);
    }
}
