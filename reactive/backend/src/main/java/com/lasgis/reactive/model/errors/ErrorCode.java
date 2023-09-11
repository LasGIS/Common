/*
 *  @(#)ErrorCode.java  last: 18.05.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.errors;

/**
 * The Class ErrorCode definition.
 *
 * @author VLaskin
 * @since 15.05.2023 : 18:30
 */
public enum ErrorCode {
    /** internal service error */
    INTERNAL_SERVER_ERROR,
    /** ufps not found */
    USER_NOT_FOUND,
    /** duplicate key value violates uniqueness constraint */
    DUPLICATE_KEY_VALUE
}
