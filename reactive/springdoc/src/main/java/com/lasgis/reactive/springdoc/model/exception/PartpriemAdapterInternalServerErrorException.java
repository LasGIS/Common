/*
 *  @(#)PartpriemAdapterInternalServerErrorException.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.model.exception;

import com.lasgis.reactive.springdoc.model.exception.DomainException;

public class PartpriemAdapterInternalServerErrorException extends DomainException {
    public PartpriemAdapterInternalServerErrorException(final String message, final Long code) {
        super(message, code);
    }

    public PartpriemAdapterInternalServerErrorException(final String message) {
        super(message);
    }
}
