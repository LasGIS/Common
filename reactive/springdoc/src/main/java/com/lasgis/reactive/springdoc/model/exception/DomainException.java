/*
 *  @(#)DomainException.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.model.exception;

import lombok.Getter;
import lombok.Setter;

public abstract class DomainException extends RuntimeException {

    @Getter
    @Setter
    private Long code;

    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Long code) {
        super(message);
        this.code = code;
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DomainException(final String message, final Throwable cause, final Long code) {
        super(message, cause);
        this.code = code;
    }

}