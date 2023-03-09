/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception: No results found by specified query
 *
 * @author Roman Prokhorov
 * @author KKiryakov
 * @version 1.1
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad query")
public class BadQueryException extends Exception {

    /**
     * Creates new instance
     * @param message message
     */
    public BadQueryException(String message) {
        super(message);
    }
}
