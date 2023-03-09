/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.exception;

import org.springframework.validation.BindingResult;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 26, 2015)
 */
public class ValidationException extends Exception {

    private static final long serialVersionUID = -7727673495420176988L;

    private final BindingResult bindingResult;

    private final String errorCode;

    /**
     * new exception
     * @param bindingResult binding result
     */
    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
        this.errorCode = null;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

}
