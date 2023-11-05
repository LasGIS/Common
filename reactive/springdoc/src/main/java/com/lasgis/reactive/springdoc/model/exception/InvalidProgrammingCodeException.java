/*
 *  @(#)InvalidProgrammingCodeException.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.model.exception;

/**
 * Exception class for case when exception can be thrown only if programming code has mistake
 */
public class InvalidProgrammingCodeException extends RuntimeException {
    public InvalidProgrammingCodeException(String message) {
        super(message);
    }

    public InvalidProgrammingCodeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
