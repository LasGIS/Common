/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception: Bad Query with specific error Code for following redirect
 *
 * @author Vladimir Laskin
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad query with errorCode")
public class BadQueryRedirectException extends Exception {

    @Getter
    private final String errorCode;

    /**
     * Creates new instance
     *
     * @param errorCode error Code
     * @param message   message
     */
    public BadQueryRedirectException(final String errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
