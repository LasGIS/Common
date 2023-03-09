/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.russianpost.tracking.portal.admin.controller.exception.BadQueryException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileSearchService;
import ru.russianpost.tracking.web.model.admin.AdminProfileSearchResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * SearchController
 * @author Roman Prokhorov
 * @version 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class FindProfile extends BaseController {

    /** Maximal number of records in result of "find" query */
    public static final int MAX_RESULT_COUNT = 10;
    /** Minimal length of query to start searching */
    public static final int MIN_QUERY_LENGTH = 4;

    private static final String BAD_QUERY_ERROR_MSG = "Bad query: it must contain at least " + MIN_QUERY_LENGTH + " characters";

    private final ProfileSearchService profileSearchService;

    /**
     * Searches profiles on tracking service
     * @param query TIN, email or login
     * @param count max response count
     * @return profile info
     * @throws BadQueryException           bad query
     * @throws ServiceUnavailableException tracking service unavailable
     */
    @GetMapping(value = "/find")
    @ResponseBody
    public List<AdminProfileSearchResult> find(
        @RequestParam("query") String query,
        @RequestParam(value = "count", required = false) Optional<Integer> count
    ) throws BadQueryException, ServiceUnavailableException {
        if (query.length() < MIN_QUERY_LENGTH) {
            throw new BadQueryException(BAD_QUERY_ERROR_MSG);
        }
        return profileSearchService.find(query, Math.min(count.orElse(10), MAX_RESULT_COUNT));
    }

    /**
     * Bad query exception handler.
     * @param req HttpServletRequest
     * @param ex  BadQueryException
     */
    @ExceptionHandler({BadQueryException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = BAD_QUERY_ERROR_MSG)
    public void badQuery(HttpServletRequest req, BadQueryException ex) {
        log.info("Bad request to URI '{}': {}", req.getRequestURI(), ex.getMessage());
    }
}
