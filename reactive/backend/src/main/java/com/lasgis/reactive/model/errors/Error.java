/*
 *  @(#)Error.java  last: 05.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class, representing some error.
 *
 * @author vlaskin
 * @since <pre>09.06.2022</pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {
    private ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
    private String message;

    /**
     * @param code Error type
     */
    public Error(final ErrorCode code) {
        this.code = code;
    }

    /**
     * Constructor for simple error (without of validationErrors)
     *
     * @param code    Error type
     * @param message Error message
    public Error(final ErrorCode code, final String message) {
        this.code = code;
        this.message = message;
    }
     */

    @Override
    public String toString() {
        return String.format("Error{code='%s', message='%s'}", code, message);
    }
}
