/*
 *  @(#)RestResponseEntityExceptionHandler.java  last: 18.05.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.model.errors.Error;
import com.lasgis.reactive.model.errors.ErrorCode;
import com.lasgis.reactive.model.exception.ItemNotFoundException;
import io.r2dbc.spi.R2dbcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
        log.warn(ex.getMessage());
        return Error.of(ErrorCode.USER_NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    protected Error handleNotFoundException(DataIntegrityViolationException ex) {
        final String message;
        final Throwable cause = ex.getCause();
        if (cause instanceof R2dbcException) {
            message = String.format("%s: for SQL:\n %s", cause.getMessage(), ((R2dbcException) cause).getSql());
        } else {
            message = ex.getMessage();
        }
        log.warn(message);
        return Error.of(ErrorCode.DUPLICATE_KEY_VALUE, message);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Error externalServiceError(Exception ex) {
        final String message = String.format("External service error: %s", ex.getMessage());
        log.warn(message);
        return Error.of(ErrorCode.INTERNAL_SERVER_ERROR, message);
    }
}