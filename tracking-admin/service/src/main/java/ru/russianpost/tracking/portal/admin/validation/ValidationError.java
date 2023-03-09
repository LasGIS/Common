/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation error DTO
 *
 * @author KKiryakov
 */
public class ValidationError {

    private List<FieldError> fieldErrors = new ArrayList<>();

    /**
     * Add field error.
     *
     * @param path the path
     * @param message the message
     */
    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
