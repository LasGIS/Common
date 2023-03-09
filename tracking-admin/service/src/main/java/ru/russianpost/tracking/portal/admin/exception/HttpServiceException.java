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


import lombok.Getter;
import ru.russianpost.tracking.portal.admin.model.errors.Error;

/**
 * Class for exceptions received from some Http service.
 *
 * @author KKiryakov
 * @author Amosov Maxim
 * @since 04.08.2021 : 05:08
 */
@Getter
public class HttpServiceException extends RuntimeException {
    private final int httpCode;
    private final Error error;

    /**
     * @param httpCode HTTP code
     * @param error    error response
     */
    public HttpServiceException(final int httpCode, final Error error) {
        super(error.getMessage());
        this.httpCode = httpCode;
        this.error = error;
    }

    /**
     * @param httpCode HTTP code
     * @param error    error response
     * @param cause    cause
     */
    public HttpServiceException(final int httpCode, final Error error, final Exception cause) {
        super(error.getMessage(), cause);
        this.httpCode = httpCode;
        this.error = error;
    }
}
