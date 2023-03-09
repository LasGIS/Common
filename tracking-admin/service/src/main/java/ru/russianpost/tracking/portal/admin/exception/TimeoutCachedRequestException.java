/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when cached request is timed out.
 *
 * @author VLaskin on 02.08.2022.
 */
@ResponseStatus(value = HttpStatus.LOCKED, reason = "Cachable request is timed out")
public class TimeoutCachedRequestException extends RuntimeException {
    /**
     * Creates new instance caused by any throwable
     *
     * @param serviceUrl external service url throwing exception
     * @param cause      cause
     */
    public TimeoutCachedRequestException(String serviceUrl, Throwable cause) {
        super("Cached request is timed out: " + serviceUrl, cause);
    }
}
