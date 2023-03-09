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

import org.springframework.web.client.HttpStatusCodeException;

/**
 * UserAttachingServiceException
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public abstract class UserAttachingServiceException extends Exception {

    /**
     * Creates new instance
     *
     * @param profileId profile ID
     * @param ex        cause
     */
    public UserAttachingServiceException(long profileId, HttpStatusCodeException ex) {
        super("User can not be attached to profile: " + profileId, ex);
    }
}
