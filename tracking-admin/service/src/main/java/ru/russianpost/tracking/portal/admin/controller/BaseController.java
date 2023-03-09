/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.ValidationException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;
import ru.russianpost.tracking.portal.admin.model.errors.ValidationError;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * BaseController handling common exceptions
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public abstract class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    /**
     * Common internal error exception
     *
     * @param e exception details
     */
    @ExceptionHandler({InternalServerErrorException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void internalError(InternalServerErrorException e) {
        LOG.warn("Internal server error: {}", e.getMessage());
    }

    /**
     * Common external service error exception
     *
     * @param e exception details
     */
    @ExceptionHandler({ServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void externalServiceError(ServiceUnavailableException e) {
        LOG.warn("External service error: {}", e.getMessage());
    }

    /**
     * Validation errors handler.
     *
     * @param ex exception details
     * @return Error to display on frontend
     */
    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error validationErrors(
        final ValidationException ex
    ) {
        final BindingResult binding = ex.getBindingResult();
        LOG.warn("Validation errors: {}", concatValidationErrors(binding));
        return extructValidationErrors(binding);
    }

    /**
     * Validation errors handler.
     *
     * @param e exception details
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Validation errors")
    public void validationErrorHandler(MethodArgumentNotValidException e) {
        final String validationErrors = concatValidationErrors(e.getBindingResult());
        LOG.warn("Validation errors: {}", validationErrors);
    }

    private String concatValidationErrors(BindingResult br) {
        return Stream.concat(
            br.getFieldErrors().stream().map(err -> "Field '" + err.getField() + "': " + err.getDefaultMessage()),
            br.getGlobalErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        ).collect(joining("; "));
    }

    private Error extructValidationErrors(BindingResult br) {
        final Error error = new Error();
        error.setCode(ErrorCode.VALIDATION_ERROR);
        error.setValidationErrors(
            br.getFieldErrors().stream().map(err -> ValidationError.of(
                err.getField(), ErrorCode.VALIDATION_ERROR, err.getDefaultMessage()
            )).collect(Collectors.toList())
        );
        error.setMessage(Stream.concat(
            br.getGlobalErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage),
            error.getValidationErrors().stream().map(ValidationError::getMessage)
        ).collect(joining("; ")));
        return error;
    }

    /**
     * Bad request exception handler.
     *
     * @param req HttpServletRequest
     * @param ex  BadRequestException
     */
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void badRequestErrors(HttpServletRequest req, BadRequestException ex) {
        LOG.warn("Bad request to URI '{}': {}", req.getRequestURI(), ex.getMessage());
    }
}
