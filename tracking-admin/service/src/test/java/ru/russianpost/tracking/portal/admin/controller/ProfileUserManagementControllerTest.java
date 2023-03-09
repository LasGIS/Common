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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.russianpost.tracking.portal.admin.model.postid.InfoProfile;
import ru.russianpost.tracking.portal.admin.model.postid.Person;
import ru.russianpost.tracking.portal.admin.model.ui.PostUser;
import ru.russianpost.tracking.portal.admin.model.ui.ProfilePage;
import ru.russianpost.tracking.portal.admin.service.PostIdService;
import ru.russianpost.tracking.portal.admin.service.UserInfoPopulationService;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileManagementService;
import ru.russianpost.tracking.web.model.admin.CustomUserSeed;
import ru.russianpost.tracking.web.model.attributes.NotificationSettings;
import ru.russianpost.tracking.web.model.core.ProfileClientType;

import java.util.Collections;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileUserManagementControllerTest {

    /** Sample profile for testing. */
    private static final ru.russianpost.tracking.web.model.core.Profile PROFILE = buildProfile();

    private MockMvc mockMvc;

    @InjectMocks
    private ProfileUserManagement controller;

    @Mock
    private PostIdService postIdService;

    @Mock
    private ProfileManagementService profileManagementService;

    @Mock
    private UserInfoPopulationService userInfoPopulationService;
    private static final int PROFILE_ID = 1;
    private static final String HID = "hid1";
    private static final String EMAIL = "email@supermail.com";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testAttachUserToProfile() throws Exception {

        final InfoProfile infoProfile = new InfoProfile();
        Person p1 = new Person();
        p1.setEmail(EMAIL);
        p1.setPhone("123456789");

        infoProfile.setPersons(new Person[]{p1});

        when(postIdService.getUserProfile(HID)).thenReturn(infoProfile);

        final ProfilePage profile = new ProfilePage();
        profile.setId(PROFILE_ID);
        final PostUser postUser = new PostUser();
        postUser.setHid(HID);
        postUser.setEmail(EMAIL);
        postUser.setPhone("123456789");
        postUser.setFirstName("firstName");
        postUser.setFirstName("firstName");
        postUser.setLastName("lastName");
        postUser.setMiddleName("middleName");
        profile.setPostUsers(Collections.singletonList(postUser));

        final CustomUserSeed attachingArgs = new CustomUserSeed(EMAIL, HID);

        when(profileManagementService.attachUser(PROFILE_ID, attachingArgs)).thenReturn(PROFILE);
        when(userInfoPopulationService.populate(postUser)).thenReturn(postUser);

        mockMvc.perform(post("/api/v1/profile/" + PROFILE_ID + "/user/" + HID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PROFILE_ID));

        verify(postIdService, times(1)).getUserProfile(HID);
        verify(profileManagementService, times(1)).attachUser(PROFILE_ID, attachingArgs);
        verifyNoMoreInteractions(postIdService);
        verifyNoMoreInteractions(profileManagementService);
    }

    private static ru.russianpost.tracking.web.model.core.Profile buildProfile() {
        return new ru.russianpost.tracking.web.model.core.Profile.Builder().withId(PROFILE_ID)
            .withLogin("login")
            .withAccessType(
                new ru.russianpost.tracking.web.model.core.AccessType("LIMITED")
            )
            .addPostUser(new ru.russianpost.tracking.web.model.core.PostUser(
                PROFILE_ID,
                HID,
                EMAIL,
                new NotificationSettings(false, 7, false),
                new NotificationSettings(false, 14, true)
            ))
            .withServiceName("serviceName")
            .withClientType(ProfileClientType.GOVERMENTAL)
            .withInternalComment("comment")
            .build();
    }

}
