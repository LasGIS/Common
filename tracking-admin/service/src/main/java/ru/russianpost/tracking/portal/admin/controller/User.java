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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.postid.InfoProfile;
import ru.russianpost.tracking.portal.admin.model.ui.PostIdSearchResult;
import ru.russianpost.tracking.portal.admin.model.ui.PostUser;
import ru.russianpost.tracking.portal.admin.service.PostIdService;
import ru.russianpost.tracking.portal.admin.service.exception.PostUserNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.utils.PostIdUtils;

import java.util.stream.Stream;

/**
 * User Controller
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class User extends BaseController {

    private final PostIdService postIdService;

    /**
     * Looking for user by email
     *
     * @param email user email
     * @return user info
     * @throws PostUserNotFoundServiceException user with specified email not found
     * @throws ServiceUnavailableException      service unavailable
     * @throws BadRequestException              invalid email format
     */
    @GetMapping(value = "/find")
    @ResponseBody
    public PostUser find(@RequestParam("email") String email)
        throws PostUserNotFoundServiceException, BadRequestException, ServiceUnavailableException {
        final PostIdSearchResult userByEmail = postIdService.findUserByEmail(email);
        final InfoProfile userProfile = postIdService.getUserProfile(userByEmail.getHid());
        return Stream.of(userProfile.getPersons()).findFirst().map(p -> {
            final PostUser postUser = new PostUser();
            postUser.setHid(p.getPersonHid());
            postUser.setFirstName(PostIdUtils.getFirstName(p));
            postUser.setLastName(PostIdUtils.getLastName(p));
            postUser.setMiddleName(PostIdUtils.getMiddleName(p));
            postUser.setEmail(p.getEmail());
            return postUser;
        }).orElseThrow(PostUserNotFoundServiceException::new);
    }

    /**
     * User not found by email
     */
    @ExceptionHandler({PostUserNotFoundServiceException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post user not found")
    public void couldNotDetachUser() {
    }
}
