/*
 *  @(#)Assertions.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.util;


import com.lasgis.reactive.springdoc.model.exception.InvalidProgrammingCodeException;

public final class Assertions {
    private Assertions() {
    }

    public static <T> T assertNotNull(final T value, final String message) {
        if (value == null) {
            throw new InvalidProgrammingCodeException(message);
        }
        return value;
    }
}
