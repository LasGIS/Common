/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.util;

import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.service.users.AdminServiceUserService;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Batch optimization
 * @param <T> collection item type
 * @author Roman Prokhorov
 * @version 1.0 (Jan 26, 2016)
 */
public class UserInfoCompletion<T> {

    private final Collection<T> target;
    private final Function<T, String> getAuthor;

    /**
     * UserInfoCompletion
     * @param target    target
     * @param getAuthor getAuthor from collection item function
     */
    public UserInfoCompletion(Collection<T> target, Function<T, String> getAuthor) {
        this.target = target;
        this.getAuthor = getAuthor;
    }

    /**
     * Builds local map consisting of all known users, unknown usernames won't be resolved and won't be added to final collection
     * @param userService user service: user info provider
     * @return map
     */
    public Map<String, ServiceUser> buildMap(final AdminServiceUserService userService) {
        return target
            .stream()
            .map(getAuthor)
            .distinct()
            .filter(Objects::nonNull)
            .map(userService::resolve)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(ServiceUser::getUsername, Function.identity()));
    }
}
