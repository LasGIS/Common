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

/**
 * Kind of internal errors
 *
 * @author VLaskin
 * @since <pre>29.06.2020</pre>
 */
@Getter
public enum RestExceptionType {

    /** Системная ошибка приложения */
    INTERNAL_ERROR(0, "Системная ошибка приложения", "Internal Error"),
    /** Данная операция вам не доступна */
    ACCESS_DENIED(1, "Данная операция вам не доступна", "Access denied error"),
    /** Item of common validation error */
    VALIDATION_ERROR(2, "Ошибки проверки: \"%s\"", "Validation error: \"%s\"");

    /** error code */
    private final int code;
    /** error description by Cyrillic sign */
    private final String messageFormatRus;
    /** error description */
    private final String messageFormatEng;
    /** HTTP status (if empty - HttpStatus.INTERNAL_SERVER_ERROR) */
    private final HttpStatus httpStatus;

    /**
     * Simple constructor (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR)
     *
     * @param code             error code
     * @param messageFormatEng description
     */
    RestExceptionType(final int code, final String messageFormatRus, final String messageFormatEng) {
        this(code, messageFormatRus, messageFormatEng, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Full constructor
     *
     * @param code             error code
     * @param messageFormatRus Как это по-русски
     * @param messageFormatEng description
     * @param httpStatus       HTTP Status
     */
    RestExceptionType(final int code, final String messageFormatRus, final String messageFormatEng, final HttpStatus httpStatus) {
        this.code = code;
        this.messageFormatRus = messageFormatRus;
        this.messageFormatEng = messageFormatEng;
        this.httpStatus = httpStatus;
    }
}
