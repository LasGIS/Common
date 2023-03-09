/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils.monitoring;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.diagnostic.checker.Checker;
import ru.russianpost.tracking.diagnostic.model.Report;

import static java.text.MessageFormat.format;
import static ru.russianpost.tracking.diagnostic.selftest.Status.CRITICAL;

/**
 * RestTemplateChecker
 * @author MKitchenko
 * @version 1.0 22.09.2020
 */
public class RestTemplateChecker implements Checker {

    private static final String UNEXPECTED_RESPONSE_MESSAGE_PATTERN = "" +
        "Unexpected response from remote server: got status {0}, message {1}.";

    private final String testName;
    private final String url;
    private final RestTemplate restTemplate;
    private final Report reportOk;

    /**
     * Constructor
     * @param testName     test name
     * @param url          checked url
     * @param restTemplate instance of {@link RestTemplate}
     */
    public RestTemplateChecker(String testName, String url, RestTemplate restTemplate) {
        this.testName = testName;
        this.url = url;
        this.restTemplate = restTemplate;
        this.reportOk = new Report(testName);
    }

    @Override
    public String testName() {
        return this.testName;
    }

    @Override
    public Report diagnose() {
        try {
            final HttpStatus httpStatus = restTemplate.getForEntity(url, Void.class).getStatusCode();
            if (httpStatus.is2xxSuccessful()) {
                return reportOk;
            }
            return new Report(
                CRITICAL,
                testName,
                format(UNEXPECTED_RESPONSE_MESSAGE_PATTERN, httpStatus, httpStatus.getReasonPhrase())
            );
        } catch (RestClientException e) {
            return new Report(CRITICAL, testName, e.getMessage());
        }
    }
}
