/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.exception;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 09, 2015)
 */
public class AccessTypeAlreadySetException extends Exception {

    private static final long serialVersionUID = 2287402147543018756L;

    /**
     * New instance
     *
     * @param message message
     */
    public AccessTypeAlreadySetException(String message) {
        super(message);
    }

    /**
     * New instance
     *
     * @param message message
     * @param cause cause
     */
    public AccessTypeAlreadySetException(String message, Throwable cause) {
        super(message, cause);
    }

}
