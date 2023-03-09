/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.russianpost.tracking.web.model.core.AccessType;
import ru.russianpost.tracking.web.model.core.Profile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

/**
 * ProfilePage
 * @author Roman Prokhorov
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public final class ProfilePage {
    private long id;
    private String login;
    private CompanyInfo company;
    private boolean unlimited;
    private AccessType accessType;
    private List<PostUser> postUsers;
    private String serviceName;
    private String clientType;
    private String internalComment;

    /**
     * Constructs from a common profile object
     * @param profile common profile object
     * @return profile page
     */
    public static ProfilePage from(Profile profile) {
        if (profile == null) {
            return null;
        }
        return new ProfilePage(
            profile.getId(),
            profile.getLogin(),
            CompanyInfo.from(profile.getCompany()),
            profile.getAccessType().isUnlimited(),
            profile.getAccessType(),
            Collections.unmodifiableList(profile.getPostUsers()
                .stream()
                .map(PostUser::new)
                .collect(Collectors.toList())),
            profile.getServiceName(),
            profile.getClientType().getCode(),
            profile.getInternalComment()
        );
    }
}
