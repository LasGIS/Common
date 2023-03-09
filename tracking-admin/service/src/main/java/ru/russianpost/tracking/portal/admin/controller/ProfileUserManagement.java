/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.postid.InfoProfile;
import ru.russianpost.tracking.portal.admin.model.postid.Person;
import ru.russianpost.tracking.portal.admin.model.ui.ProfilePage;
import ru.russianpost.tracking.portal.admin.service.PostIdService;
import ru.russianpost.tracking.portal.admin.service.UserInfoPopulationService;
import ru.russianpost.tracking.portal.admin.service.exception.PostUserNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingConflictServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingLimitExceededServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserDetachingServiceException;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileManagementService;
import ru.russianpost.tracking.web.model.admin.CustomUserSeed;

import java.util.stream.Stream;

/**
 * ProfileUserManagement Controller
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileUserManagement extends BaseController {

    private final ProfileManagementService profileManagementService;
    private final PostIdService postIdService;
    private final UserInfoPopulationService userInfoPopulationService;

    /**
     * Attaches specified user to specified profile
     *
     * @param profileId profile id
     * @param hid       user hid
     * @return profile with attached user
     * @throws UserAttachingServiceException    user attaching exception
     * @throws ProfileNotFoundServiceException  profile not found
     * @throws PostUserNotFoundServiceException post user not found
     * @throws ServiceUnavailableException      service unavailable
     */
    @PostMapping(value = "/{profileId}/user/{hid}")
    @ResponseBody
    public ProfilePage attachUserToProfile(@PathVariable long profileId, @PathVariable String hid)
            throws UserAttachingServiceException, ProfileNotFoundServiceException,
            PostUserNotFoundServiceException, ServiceUnavailableException {
        final InfoProfile userProfile = postIdService.getUserProfile(hid);
        final String email = Stream.of(userProfile.getPersons())
                .findFirst()
                .map(Person::getEmail)
                .orElseThrow(() -> new ProfileNotFoundServiceException(profileId));
        final ProfilePage profile = ProfilePage.from(profileManagementService.attachUser(profileId, new CustomUserSeed(email, hid)));
        profile.getPostUsers().forEach(userInfoPopulationService::populate);
        return profile;
    }

    /**
     * Detaches specified user from specified profile
     *
     * @param profileId profile id
     * @param userId    user id
     * @return profile with no detached user
     * @throws UserDetachingServiceException   user detaching error
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    @DeleteMapping(value = "/{profileId}/user/{userId}")
    @ResponseBody
    public ProfilePage detachUserFromProfile(@PathVariable long profileId, @PathVariable long userId)
            throws UserDetachingServiceException, ProfileNotFoundServiceException, ServiceUnavailableException {
        final ProfilePage profile = ProfilePage.from(profileManagementService.detachUser(profileId, userId));
        profile.getPostUsers().forEach(userInfoPopulationService::populate);
        return profile;
    }

    /**
     * Post user not found
     */
    @ExceptionHandler({PostUserNotFoundServiceException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post user not found")
    public void postUserNotFound() {
    }

    /**
     * Profile not found
     */
    @ExceptionHandler({ProfileNotFoundServiceException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Profile not found")
    public void profileNotFound() {
    }

    /**
     * The user can not be detached from the profile
     */
    @ExceptionHandler({UserDetachingServiceException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Can not detach user")
    public void couldNotDetachUser() {
    }

    /**
     * The user can not be attached to the profile
     */
    @ExceptionHandler({UserAttachingConflictServiceException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Can not attach user: user already attached to somewhere")
    public void couldNotAttachUserConflict() {
    }

    /**
     * The user can not be attached to the profile
     */
    @ExceptionHandler({UserAttachingLimitExceededServiceException.class})
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED, reason = "Can not attach user: maximum users attached")
    public void couldNotAttachUserLimitExceeded() {
    }
}
