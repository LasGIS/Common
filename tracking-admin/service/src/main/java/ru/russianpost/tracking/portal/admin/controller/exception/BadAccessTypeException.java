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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadAccessTypeException
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad access type")
public class BadAccessTypeException extends Exception {

    /**
     * Creates new instance
     */
    public BadAccessTypeException() {
        super("Bad access type");
    }
}
