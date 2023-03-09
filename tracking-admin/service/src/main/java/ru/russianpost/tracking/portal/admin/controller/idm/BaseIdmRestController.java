/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.idm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmErrorResponse;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedIdmUserPropertyException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedRoleException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserAlreadyExistException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Amosov Maxim
 * @since 03.09.2021 : 10:43
 */
@Slf4j
public class BaseIdmRestController {
    /**
     * User already exist exception handler
     *
     * @param ex exception
     * @return ResponseEntity with 'CONFLICT' error
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    protected ResponseEntity<IdmErrorResponse> userAlreadyExistError(final UserAlreadyExistException ex) {
        log.warn("User already exist!", ex);
        final IdmErrorResponse response = new IdmErrorResponse(CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(CONFLICT).body(response);
    }

    /**
     * Bad request exception handler
     *
     * @param ex exception
     * @return ResponseEntity with 'BAD_REQUEST' error
     */
    @ExceptionHandler({
        BadRequestException.class,
        UserNotFoundException.class,
        UnrecognizedRoleException.class,
        UnrecognizedIdmUserPropertyException.class
    })
    protected ResponseEntity<IdmErrorResponse> badRequestError(final Exception ex) {
        log.warn("Bad request!", ex);
        final IdmErrorResponse response = new IdmErrorResponse(BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    /**
     * Internal server error exception handler
     *
     * @param ex exception
     * @return ResponseEntity with 'INTERNAL_SERVER_ERROR' error
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<IdmErrorResponse> internalServerError(final Exception ex) {
        log.error("Internal server error!", ex);
        final IdmErrorResponse response = new IdmErrorResponse(INTERNAL_SERVER_ERROR.value(), "Internal server error!");
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }
}
