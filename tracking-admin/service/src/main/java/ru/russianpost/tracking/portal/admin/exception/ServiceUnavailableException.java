/*
 * Copyright 2015 Russian Post
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
 * This exception is thrown when external service unavailable.
 * It may be a common situation so this exception should handled
 *
 * @author RSProkhorov on 16.09.2015.
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "External service unavailable")
public class ServiceUnavailableException extends Exception {

    /**
     * Creates new instance caused by any throwable
     *
     * @param serviceUrl external service url throwing exception
     * @param cause      cause
     */
    public ServiceUnavailableException(String serviceUrl, Throwable cause) {
        super("Service unavailable: " + serviceUrl, cause);
    }
}
