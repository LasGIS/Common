/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.exception;

import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.errors.Error;

/**
 * Class for exceptions received from Rtm4601 service.
 * @author MKitchenko
 */
public class Rtm4601ServiceException extends RuntimeException {
    private final Error error;

    /**
     * Constructor.
     * @param error Rtm4601 service error
     */
    public Rtm4601ServiceException(Error error) {
        super(error.getDescription());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
