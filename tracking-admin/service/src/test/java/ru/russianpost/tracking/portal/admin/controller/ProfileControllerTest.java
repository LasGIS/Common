/*
 * Copyright 2016 Russian Post
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.russianpost.tracking.portal.admin.model.ui.PostUser;
import ru.russianpost.tracking.portal.admin.model.ui.ProfilePage;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileManagementService;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.service.UserInfoPopulationService;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.russianpost.tracking.web.model.attributes.NotificationSettings;
import ru.russianpost.tracking.web.model.core.ProfileClientType;
import ru.russianpost.tracking.web.model.core.AccessType;

/**
 * Tests for {@link ProfileController}.
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class ProfileControllerTest {

    /** Sample profile for testing. */
    private static final ru.russianpost.tracking.web.model.core.Profile PROFILE = buildProfile();
    /** Sample post user for testing. */
    private static final PostUser POST_USER = buildPostUser();

    private static final int ID = 1;
    private static final String LOGIN = "login";
    private static final String HID = "hid1";
    private static final String EMAIL = "email";

    private MockMvc mockMvc;

    @InjectMocks
    private ProfileController controller;

    @Mock
    private ProfileManagementService profileManagementService;

    @Mock
    private UserInfoPopulationService userInfoPopulationService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testSuccessGet() throws Exception {
        final ProfilePage profile = new ProfilePage();
        profile.setId(ID);
        profile.setPostUsers(Collections.singletonList(POST_USER));

        when(profileManagementService.get(ID)).thenReturn(PROFILE);
        when(userInfoPopulationService.populate(POST_USER)).thenReturn(POST_USER);

        mockMvc.perform(get("/api/v1/profile/" + Integer.toString(ID)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID));

        verify(profileManagementService, times(1)).get(ID);
        verify(userInfoPopulationService, times(1)).populate(POST_USER);
        verifyNoMoreInteractions(profileManagementService);
        verifyNoMoreInteractions(userInfoPopulationService);
    }

    @Test
    public void testProfileNotFound() throws Exception {
        when(profileManagementService.get(ID))
            .thenThrow(new ProfileNotFoundServiceException(ID, new Exception("something")));

        mockMvc.perform(get("/api/v1/profile/" + Integer.toString(ID)))
            .andExpect(status().isNotFound());

        verify(profileManagementService, times(1)).get(ID);
        verifyNoMoreInteractions(profileManagementService);
    }

    @Test
    public void testSuccessChangeAccessType() throws Exception {
        final ProfilePage profile = new ProfilePage();
        long id = ID;
        profile.setId(id);
        profile.setPostUsers(Collections.singletonList(POST_USER));
        AccessType accessType = new AccessType("UNLIMITED");

        when(profileManagementService.setAccessType(eq(id), any(AccessType.class), eq("comment")))
            .thenReturn(PROFILE);
        when(userInfoPopulationService.populate(POST_USER)).thenReturn(POST_USER);

        mockMvc.perform(post("/api/v1/profile/" + Long.toString(id) + "/access")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"type\":\"UNLIMITED\","
                + "\"rtm34Params\":null,"
                + "\"fcParams\":null,"
                + "\"comment\":\"comment\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(ID));

        verify(profileManagementService, times(1))
            .setAccessType(eq(id), any(AccessType.class), eq("comment"));
        verify(userInfoPopulationService, times(1)).populate(POST_USER);
        verifyNoMoreInteractions(profileManagementService);
        verifyNoMoreInteractions(userInfoPopulationService);
    }

    @Test
    public void testChangeAccessTypeEmptyComment() throws Exception {
        final ProfilePage profile = new ProfilePage();
        profile.setId(ID);
        profile.setPostUsers(Collections.singletonList(POST_USER));
        AccessType accessType = new AccessType("UNLIMITED");

        when(profileManagementService.setAccessType(ID, accessType, "")).thenReturn(PROFILE);
        when(userInfoPopulationService.populate(POST_USER)).thenReturn(POST_USER);

        mockMvc.perform(post("/api/v1/profile/" + Integer.toString(ID) + "/access").contentType(
            MediaType.APPLICATION_JSON).content("{\"type\":\"UNLIMITED\"}"))
            .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(profileManagementService);
    }

    @Test
    public void testChangeAccessTypeInvalidAccessType() throws Exception {
        mockMvc.perform(post("/api/v1/profile/" + Integer.toString(ID) + "/access").contentType(
            MediaType.APPLICATION_JSON).content("{\"type\":\"INVALID\"}"))
            .andExpect(status().isBadRequest());

        AccessType accessType = new AccessType("UNLIMITED");
        verify(profileManagementService, times(0)).setAccessType(ID, accessType, "");
        verifyNoMoreInteractions(profileManagementService);
    }

    private static ru.russianpost.tracking.web.model.core.Profile buildProfile() {
        return new ru.russianpost.tracking.web.model.core.Profile.Builder().withId(ID)
            .withLogin(LOGIN)
            .withAccessType(new ru.russianpost.tracking.web.model.core.AccessType("LIMITED"))
            .addPostUser(new ru.russianpost.tracking.web.model.core.PostUser(ID,
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

    private static PostUser buildPostUser() {
        final PostUser postUser = new PostUser();
        postUser.setHid(HID);
        postUser.setPhone("123456789");
        postUser.setFirstName("firstName");
        postUser.setLastName("lastName");
        postUser.setMiddleName("middleName");
        return postUser;
    }
}
