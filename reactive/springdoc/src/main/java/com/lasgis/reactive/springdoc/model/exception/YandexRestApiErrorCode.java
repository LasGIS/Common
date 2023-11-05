/*
 *  @(#)YandexRestApiErrorCode.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.model.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YandexRestApiErrorCode {
    SERVICE_UNAVAILABLE(1000),
    INVALID_AUTHORIZATION_TOKEN(1100),
    REQUEST_CANNOT_BE_EXECUTED(4000),
    INVALID_XML_SYNTAX(2000),
    DATA_TYPE_NOT_FOUND(9404),
    UNKNOWN_ERROR(9999);

    private final int code;
}
