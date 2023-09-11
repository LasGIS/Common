/*
 *  @(#)ItemNotFoundException.java  last: 16.05.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.exception;

/**
 * The Class ItemNotFoundException definition.
 *
 * @author VLaskin
 * @since 16.05.2023 : 14:31
 */
public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
