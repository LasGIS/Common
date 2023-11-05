/*
 *  @(#)YandexHistoryRestControllerAdvice.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.controller.history;

import com.lasgis.reactive.springdoc.model.exception.DomainException;
import com.lasgis.reactive.springdoc.model.exception.PartpriemAdapterDomainEntityNotFoundException;
import com.lasgis.reactive.springdoc.model.exception.PartpriemAdapterDomainException;
import com.lasgis.reactive.springdoc.model.exception.PartpriemAdapterInternalServerErrorException;
import com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode;
import com.lasgis.reactive.springdoc.model.history.YandexErrorHistoryRestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode.DATA_TYPE_NOT_FOUND;
import static com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode.REQUEST_CANNOT_BE_EXECUTED;
import static com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode.SERVICE_UNAVAILABLE;
import static com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode.UNKNOWN_ERROR;
import static com.lasgis.reactive.springdoc.util.LangUtil.ifNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice(assignableTypes = YandexHistoryController.class)
@RequiredArgsConstructor
public class YandexHistoryRestControllerAdvice {

    private final YandexHistoryRestApiContextHolder contextTool;
    private final YandexHistoryRestApiContextConverter converter;

    @ExceptionHandler(PartpriemAdapterDomainEntityNotFoundException.class)
    public Mono<ResponseEntity<YandexErrorHistoryRestResponse>> handle(
        final ServerWebExchange serverWebExchange,
        final PartpriemAdapterDomainEntityNotFoundException exception
    ) {
        return toErrorResponse(serverWebExchange, NOT_FOUND, DATA_TYPE_NOT_FOUND, exception);
    }

    @ExceptionHandler(PartpriemAdapterInternalServerErrorException.class)
    public Mono<ResponseEntity<YandexErrorHistoryRestResponse>> handle(
        final ServerWebExchange serverWebExchange,
        final PartpriemAdapterInternalServerErrorException exception
    ) {
        return toErrorResponse(serverWebExchange, INTERNAL_SERVER_ERROR, SERVICE_UNAVAILABLE, exception);
    }

    @ExceptionHandler(PartpriemAdapterDomainException.class)
    public Mono<ResponseEntity<YandexErrorHistoryRestResponse>> handle(
        final ServerWebExchange serverWebExchange,
        final PartpriemAdapterDomainException exception
    ) {
        return toErrorResponse(serverWebExchange, BAD_REQUEST, REQUEST_CANNOT_BE_EXECUTED, exception);
    }

    @ExceptionHandler(DomainException.class)
    public Mono<ResponseEntity<YandexErrorHistoryRestResponse>> handle(
        final ServerWebExchange serverWebExchange,
        final DomainException exception
    ) {
        return toErrorResponse(serverWebExchange, BAD_REQUEST, REQUEST_CANNOT_BE_EXECUTED, exception);
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<YandexErrorHistoryRestResponse>> handle(
        final ServerWebExchange serverWebExchange,
        final Exception exception
    ) {
        return toErrorResponse(serverWebExchange, INTERNAL_SERVER_ERROR, UNKNOWN_ERROR, exception);
    }

    private Mono<ResponseEntity<YandexErrorHistoryRestResponse>> toErrorResponse(
        final ServerWebExchange serverWebExchange,
        final HttpStatus httpStatus,
        final YandexRestApiErrorCode errorCode,
        final Exception ex
    ) {
        return contextTool
            .getContext(serverWebExchange)
            .flatMap(ctx -> toErrorResponse(ctx, serverWebExchange, httpStatus, errorCode, ex));
    }

    private Mono<ResponseEntity<YandexErrorHistoryRestResponse>> toErrorResponse(
        final YandexHistoryRestApiContext context,
        final ServerWebExchange serverWebExchange,
        final HttpStatus httpStatus,
        final YandexRestApiErrorCode errorCode,
        final Exception ex
    ) {
        log.error("Error processing request " + ifNotNull(context, Objects::toString), ex);
        return Mono.just(ResponseEntity
            .status(httpStatus)
            .contentType(MediaType.TEXT_XML)
            .body(converter.toRest(context, serverWebExchange, errorCode, ex)));
    }
}
