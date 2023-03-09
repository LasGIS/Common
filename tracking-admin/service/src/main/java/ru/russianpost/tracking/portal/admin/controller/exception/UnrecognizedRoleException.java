/*
 * Copyright 2021 Russian Post
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
 * @author Amosov Maxim
 * @since 01.09.2021 : 16:02
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unrecognized role")
public class UnrecognizedRoleException extends RuntimeException {
    /**
     * @param roleName roleName
     */
    public UnrecognizedRoleException(final String roleName) {
        super("Unrecognized role = " + roleName);
    }
}
