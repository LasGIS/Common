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
 * ProfileNotFoundException
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class ProfileNotFoundServiceException extends Exception {

    private static final long serialVersionUID = -2851390710774193313L;

    /**
     * Creates new instance
     *
     * @param profileId not found profile id
     */
    public ProfileNotFoundServiceException(long profileId) {
        this(profileId, null);
    }

    /**
     * Creates new instance
     *
     * @param profileId not found profile id
     * @param cause     external service cause
     */
    public ProfileNotFoundServiceException(long profileId, Throwable cause) {
        super("Profile not found, profileId = " + profileId, cause);
    }
}
