/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.errors;

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
    private List<ValidationError> validationErrors;

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
     */
    public Error(final ErrorCode code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Error{code='%s', message='%s', validationErrors=%s}", code, message, validationErrors);
    }
}
