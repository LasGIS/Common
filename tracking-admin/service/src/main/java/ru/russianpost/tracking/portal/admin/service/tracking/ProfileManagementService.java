/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.tracking;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.exception.AccessTypeAlreadySetException;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserDetachingServiceException;
import ru.russianpost.tracking.web.model.admin.CustomUserSeed;
import ru.russianpost.tracking.web.model.core.AccessType;
import ru.russianpost.tracking.web.model.core.Profile;

/**
 * TrackingService interface
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public interface ProfileManagementService {

    /**
     * Returns profile by specified id
     *
     * @param profileId profile id
     * @return profile
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    Profile get(long profileId) throws ProfileNotFoundServiceException, ServiceUnavailableException;

    /**
     * Sets new access type for specified profile.
     * @param profileId Id of profile
     * @param newType New access type
     * @param comment Comment  @return profile info
     * @throws AccessTypeAlreadySetException access type already set for profile
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException service unavailable
     * @return Profile
     */
    Profile setAccessType(
        final long profileId,
        final AccessType newType,
        final String comment
    ) throws
        AccessTypeAlreadySetException,
        ProfileNotFoundServiceException,
        ServiceUnavailableException;

    /**
     * Asks to send info for specified profile id
     *
     * @param profileId profile id
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    void sendInfo(long profileId) throws ProfileNotFoundServiceException, ServiceUnavailableException;

    /**
     * Resets password of specified profile
     *
     * @param profileId profile id
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    void resetPassword(long profileId) throws ProfileNotFoundServiceException, ServiceUnavailableException;

    /**
     * Attaches user to specified profile
     *
     * @param profileId         target profile id
     * @param userAttachingArgs userAttaching arguments
     * @return updated profile info
     * @throws UserAttachingServiceException   user can not be attached to profile
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    Profile attachUser(long profileId, CustomUserSeed userAttachingArgs)
            throws UserAttachingServiceException, ProfileNotFoundServiceException, ServiceUnavailableException;

    /**
     * Detaches user from specified profile
     *
     * @param profileId target profile id
     * @param userId    user id
     * @return updated profile info
     * @throws UserDetachingServiceException   user can not be detached
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    Profile detachUser(long profileId, long userId)
            throws UserDetachingServiceException, ProfileNotFoundServiceException, ServiceUnavailableException;
}
