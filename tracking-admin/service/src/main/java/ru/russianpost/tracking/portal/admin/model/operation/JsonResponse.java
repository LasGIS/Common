/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * POJO representation of a json response.
 * @author sslautin
 * @version 1.0 16.09.2015
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {
    private boolean success = true;
    private String message;
    private List<ValidationError> validationErrors;

    /**
     * Simple constructor.
     * @param success true, if request has been successfully processed, false otherwise.
     * @param message some response message.
     */
    public JsonResponse(final boolean success, final String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Information about a field validation error.
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}
