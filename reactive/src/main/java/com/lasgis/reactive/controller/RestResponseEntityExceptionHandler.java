/*
 *  @(#)RestResponseEntityExceptionHandler.java  last: 16.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.model.errors.Error;
import com.lasgis.reactive.model.errors.ErrorCode;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class RestResponseEntityExceptionHandler definition.
 *
 * @author VLaskin
 * @since 16.05.2023 : 14:59
 */
@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ItemNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected Error handleNotFoundException(ItemNotFoundException ex) {
        return Error.of(ErrorCode.USER_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Error externalServiceError(Exception ex) {
        final String message = String.format("External service error: %s", ex.getMessage());
        log.warn(message);
        return Error.of(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}