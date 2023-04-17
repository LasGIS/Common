/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.lasgis.springBoot.springinitializrdemo.controller.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Common System Exception
 *
 * @author VLaskin
 * @since <pre>29.06.2020</pre>
 */
@Getter
public class RestException extends RuntimeException {

    /** Exception Type */
    private final RestExceptionType exceptionType;
    /** Cyrillic message description (for UI) */
    private final String messageRus;
    /** message description (for logging) */
    private final String messageEng;
    /** Exception cause message */
    private final String detailMessage;
    /** Exception sub error list */
    private final List<RestException> subErrors = new ArrayList<>();
    /** list of additional parameters */
    private final List<String> parameters = new ArrayList<>();

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param exceptionType тип ошибки
     * @param addInfo       дополнительный параметры
     */
    public RestException(final RestExceptionType exceptionType, final Object... addInfo) {
        super(String.format(exceptionType.getMessageFormatEng(), addInfo));
        this.exceptionType = exceptionType;
        this.messageRus = String.format(exceptionType.getMessageFormatRus(), addInfo);
        this.messageEng = String.format(exceptionType.getMessageFormatEng(), addInfo);
        this.detailMessage = null;
    }

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param ex            main Exception
     * @param detailMessage Detail Message
     * @param exceptionType тип ошибки
     * @param addInfo       дополнительный параметры
     */
    public RestException(final Throwable ex, final String detailMessage, final RestExceptionType exceptionType, final Object... addInfo) {
        super(String.format(exceptionType.getMessageFormatEng(), addInfo) + "; detailMessage = " + detailMessage, ex);
        this.exceptionType = exceptionType;
        this.detailMessage = detailMessage;
        this.messageRus = String.format(exceptionType.getMessageFormatRus(), addInfo);
        this.messageEng = String.format(exceptionType.getMessageFormatEng(), addInfo);
        if (ex instanceof RestException) {
            final RestException restException = (RestException) ex;
            if (restException.subErrors.isEmpty()) {
                this.subErrors.add(restException);
            } else {
                this.subErrors.addAll(restException.subErrors);
            }
        }
    }

    public int getMessageCode() {
        return exceptionType.getCode();
    }

    public HttpStatus getHttpStatus() {
        return exceptionType.getHttpStatus();
    }

    /**
     * Add sub error to RestException
     *
     * @param subError sub error for adding
     * @return this Rest Exception
     */
    public RestException addSubError(final RestException subError) {
        if (subError.getSubErrors().isEmpty()) {
            this.subErrors.add(subError);
        } else {
            this.subErrors.addAll(subError.subErrors);
        }
        return this;
    }

    /**
     * Add sub errors to RestException
     *
     * @param errors list of sub error for adding
     * @return this Rest Exception
     */
    public RestException addSubErrors(final List<RestException> errors) {
        this.subErrors.addAll(errors);
        return this;
    }

    /**
     * Add additional parameters to RestException
     *
     * @param additions list of additional parameters for adding
     * @return this Rest Exception
     */
    public RestException addParameters(final List<String> additions) {
        this.parameters.addAll(additions);
        return this;
    }
}
