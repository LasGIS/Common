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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.postid.InfoProfile;
import ru.russianpost.tracking.portal.admin.model.postid.Person;
import ru.russianpost.tracking.portal.admin.model.ui.PostUser;
import ru.russianpost.tracking.portal.admin.service.exception.PostUserNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.utils.PostIdUtils;

import java.util.stream.Stream;

/**
 * PostID UserInfoPopulationService
 * @author Roman Prokhorov
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostIdUserInfoPopulationService implements UserInfoPopulationService {

    private final PostIdService postIdService;

    @Override
    public PostUser populate(PostUser postUser) {
        try {
            InfoProfile userProfile = postIdService.getUserProfile(postUser.getHid());
            final Person person = Stream.of(userProfile.getPersons())
                .findFirst()
                .orElseGet(Person::new);
            postUser.setEmail(person.getEmail());
            postUser.setFirstName(PostIdUtils.getFirstName(person));
            postUser.setLastName(PostIdUtils.getLastName(person));
            postUser.setMiddleName(PostIdUtils.getMiddleName(person));
            postUser.setPhone(StringUtils.hasLength(person.getPhone()) ? person.getPhone() : person.getPrimaryPhone());
        } catch (PostUserNotFoundServiceException ex) {
            log.warn("User not found on Post ID", ex);
        } catch (InternalServerErrorException | ServiceUnavailableException ex) {
            log.warn("Post ID connection error. User info won't be populated", ex);
        }
        return postUser;
    }
}
