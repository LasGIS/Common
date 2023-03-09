/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils.monitoring.databus.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.diagnostic.checker.Checker;
import ru.russianpost.tracking.diagnostic.model.Report;
import ru.russianpost.tracking.diagnostic.selftest.Status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.text.MessageFormat.format;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.GET;

/**
 * DatabusStorageChecker
 * @author MKitchenko
 * @version 1.0 22.09.2020
 */
@Slf4j
public class DatabusStorageChecker implements Checker {

    private static final List<String> GOOD_STATUSES = Arrays.asList("OK", "WARNING");
    private static final String TEST_NAME = "Databus Storage";
    private static final Report REPORT_OK = new Report(TEST_NAME);

    private final String url;
    private final HttpEntity<Object> entity;
    private final RestTemplate restTemplate;

    /**
     * Constructor.
     * @param restTemplate rest template
     * @param url          databus storage ping url
     * @param accessToken  databus storage access token
     */
    public DatabusStorageChecker(
        final RestTemplate restTemplate,
        final String url,
        final String accessToken
    ) {
        this.restTemplate = restTemplate;
        this.url = url;
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, accessToken);
        entity = new HttpEntity<>(headers);
    }

    @Override
    public String testName() {
        return TEST_NAME;
    }

    @Override
    public Report diagnose() {
        try {
            final ResponseEntity<DatabusStorageStatus> response = restTemplate
                .exchange(url, GET, entity, DatabusStorageStatus.class);
            final boolean isOk = response.getStatusCode().is2xxSuccessful() && Optional.ofNullable(response.getBody())
                .map(DatabusStorageStatus::getData)
                .map(Data::getServiceStatus)
                .map(GOOD_STATUSES::contains)
                .orElse(FALSE);
            if (!isOk) {
                final String error = format("Databus Storage status is bad: {0}", response.getBody());
                log.warn(error);
                return new Report(Status.CRITICAL, TEST_NAME, error);
            }
            return REPORT_OK;
        } catch (RestClientException e) {
            final String error = "Error to check Databus Storage status: " + e.getMessage();
            log.warn(error, e);
            return new Report(Status.CRITICAL, TEST_NAME, error);
        }
    }
}
