/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.diagnostic.checker.Checker;
import ru.russianpost.tracking.diagnostic.model.Report;
import ru.russianpost.tracking.diagnostic.model.SelfTestResponse;
import ru.russianpost.tracking.diagnostic.util.SelftestResponseUtils;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.toList;

/**
 * Monitoring2
 * @author MKitchenko
 * @version 1.0 02.09.2020
 */
@Slf4j
@RestController
@RequestMapping("/monitoring")
@CrossOrigin
public class Monitoring extends BaseController {

    private final StopWatchFactory stopWatchFactory;

    private final List<Checker> checkers;
    private final ForkJoinPool forkJoinPool;
    private final ResponseEntity<String> versionResponse;

    /**
     * Constructor
     * @param checkers         list
     * @param stopWatchFactory instance of {@link StopWatchFactory}
     * @param forkJoinPool     instance of {@link ForkJoinPool}
     */
    public Monitoring(
        List<Checker> checkers,
        StopWatchFactory stopWatchFactory,
        @Qualifier("selftestForkJoinPool") ForkJoinPool forkJoinPool
    ) {
        this.checkers = checkers;
        this.stopWatchFactory = stopWatchFactory;
        this.forkJoinPool = forkJoinPool;
        this.versionResponse = ResponseEntity.ok("Version is " + getClass().getPackage().getImplementationVersion());
    }

    /**
     * Return version of application.
     * @return version of application in plain text.
     */
    @GetMapping(value = "/version")
    public ResponseEntity<String> version() {
        return versionResponse;
    }

    /**
     * Ping method
     */
    @GetMapping(value = "/ping")
    @ResponseStatus(HttpStatus.OK)
    public void ping() {
        // NOP
    }

    /**
     * Return self-health information.
     * @return self-health information.
     */
    @GetMapping(value = "selftest")
    public SelfTestResponse getSelftest() {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("selftest");
        try {
            return SelftestResponseUtils.makeSelftestResponse(buildReports());
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Return all self-health information, including passed tests.
     * @return all self-health information.
     */
    @GetMapping(value = "selftest/all")
    public SelfTestResponse getSelftestAll() {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("selftestAll");
        try {
            return SelftestResponseUtils.makeSelftestResponseAll(buildReports());
        } finally {
            stopWatch.stop();
        }
    }

    private List<Report> buildReports() {
        return forkJoinPool.submit(
            () -> checkers.parallelStream().map(Checker::diagnose).collect(toList())
        ).join();
    }
}
