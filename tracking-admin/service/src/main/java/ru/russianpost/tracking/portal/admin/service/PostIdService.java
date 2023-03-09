/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service;

import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.postid.InfoProfile;
import ru.russianpost.tracking.portal.admin.model.ui.PostIdSearchResult;
import ru.russianpost.tracking.portal.admin.service.exception.PostUserNotFoundServiceException;

/**
 * PostIdService
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public interface PostIdService {

    /**
     * Requests user profile from service
     *
     * @param hid hid of profile owner
     * @return info profile
     * @throws PostUserNotFoundServiceException PostID user not found
     * @throws ServiceUnavailableException      PostID service unavailable
     */
    InfoProfile getUserProfile(String hid)
        throws PostUserNotFoundServiceException, ServiceUnavailableException;

    /**
     * Searches user by email
     *
     * @param email email
     * @return post id search result
     * @throws PostUserNotFoundServiceException PostID user not found
     * @throws ServiceUnavailableException      PostID service unavailable
     * @throws BadRequestException              invalid email format
     */
    PostIdSearchResult findUserByEmail(String email)
        throws PostUserNotFoundServiceException, BadRequestException, ServiceUnavailableException;
}
