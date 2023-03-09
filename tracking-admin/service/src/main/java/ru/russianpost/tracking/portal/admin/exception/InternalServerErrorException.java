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
import org.springframework.web.client.HttpStatusCodeException;

/**
 * Class for all exceptions caused by portal logic errors.
 * @author RSProkhorov on 16.09.2015.
 * @author KKiryakov
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
public class InternalServerErrorException extends RuntimeException {

    private final HttpStatusCodeException httpStatusCodeException;

    /**
     * Constructor.
     * @param message the detail message
     */
    public InternalServerErrorException(String message) {
        super(message);
        httpStatusCodeException = null;
    }

    /**
     * Constructor.
     * @param message the detail message
     * @param cause   the cause
     */
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
        httpStatusCodeException = (cause instanceof HttpStatusCodeException) ? (HttpStatusCodeException) cause : null;
    }

    /**
     * Creates new instance caused by http status code exception
     * @param targetUrl url which throws error
     * @param cause     http status code exception
     */
    public InternalServerErrorException(String targetUrl, HttpStatusCodeException cause) {
        super("Error requesting: " + targetUrl + ". Cause message: " + cause.getMessage(), cause);
        this.httpStatusCodeException = cause;
    }

    /**
     * Creates new instance caused by another internal server exception
     * @param cause another internal server exception
     */
    public InternalServerErrorException(InternalServerErrorException cause) {
        super(cause);
        this.httpStatusCodeException = cause.getHttpStatusCodeException();
    }

    public HttpStatusCodeException getHttpStatusCodeException() {
        return httpStatusCodeException;
    }
}
