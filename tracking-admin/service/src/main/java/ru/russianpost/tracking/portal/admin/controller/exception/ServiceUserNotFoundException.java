/*
 * Copyright 2016 Russian Post
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
 * Exception for getting Service User by unknown username.
 *
 * @author KKiryakov
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such service user")
public class ServiceUserNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Service user not found exception.
     * @param username the username
     */
    public ServiceUserNotFoundException(String username) {
        super("Service user '" + username + "' not found");
    }
}
