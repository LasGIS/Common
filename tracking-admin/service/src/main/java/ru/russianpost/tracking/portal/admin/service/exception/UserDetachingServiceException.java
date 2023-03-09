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

import org.springframework.web.client.HttpClientErrorException;

/**
 * UserDetachingException
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class UserDetachingServiceException extends Exception {

    /**
     * Creates new instance
     *
     * @param profileId profile id
     * @param ex cause
     */
    public UserDetachingServiceException(long profileId, HttpClientErrorException ex) {
        super("Can not detach user from profile[id = " + profileId + "]", ex);
    }
}
