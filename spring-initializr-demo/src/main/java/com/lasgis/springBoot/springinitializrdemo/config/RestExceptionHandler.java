/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.lasgis.springBoot.springinitializrdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.lasgis.springBoot.springinitializrdemo.controller.error.ErrorDto;
import com.lasgis.springBoot.springinitializrdemo.controller.error.RestException;
import com.lasgis.springBoot.springinitializrdemo.controller.error.RestExceptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author vlaskin
 * @since <pre>1/15/2018</pre>
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * System error exception handler
     *
     * @param ex exception
     * @return response entity
     */
    @ExceptionHandler({RestException.class})
    protected ResponseEntity<List<ErrorDto>> restException(RestException ex) {
        final List<ErrorDto> errorDtoList = new ArrayList<>();
        log.error(ex.getMessage(), ex);
        final AtomicReference<HttpStatus> httpStatus = new AtomicReference<>(ex.getHttpStatus());
        errorDtoList.add(ErrorDto.builder()
            .code(ex.getMessageCode())
            .message(ex.getMessageRus())
            .detail(ex.getDetailMessage())
            .parameters(ex.getParameters())
            .build());

        final List<RestException> subErrors = ex.getSubErrors();
        subErrors.forEach(error -> {
            log.error(error.getMessage(), error);
            httpStatus.set(error.getHttpStatus());
            errorDtoList.add(
                ErrorDto.builder()
                    .code(error.getMessageCode())
                    .message(error.getMessageRus())
                    .detail(error.getDetailMessage())
                    .parameters(error.getParameters())
                    .build()
            );
        });
        return ResponseEntity.status(httpStatus.get()).body(errorDtoList);
    }

    /**
     * Unknown system error exception handler
     *
     * @param ex exception
     * @return response entity
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<List<ErrorDto>> unknownSystemException(Exception ex) {
        return errorByRestExceptionType(RestExceptionType.INTERNAL_ERROR, ex);
    }

    /**
     * Spring security access denied error handler
     *
     * @param ex exception
     * @return response entity
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<List<ErrorDto>> accessDeniedException(Exception ex) {
        return errorByRestExceptionType(RestExceptionType.ACCESS_DENIED, ex);
    }

    private ResponseEntity<List<ErrorDto>> errorByRestExceptionType(final RestExceptionType type, final Exception ex) {
        log.error(type.getMessageFormatEng(), ex);
        return ResponseEntity.status(type.getHttpStatus())
            .body(Collections.singletonList(ErrorDto.builder()
                .code(type.getCode())
                .message(type.getMessageFormatRus())
                .detail(String.format("%s: %s", ex.getClass().getName(), (ex.getMessage() != null ? ex.getMessage() : "")))
                .build()));
    }
}
