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
import ru.russianpost.tracking.portal.admin.model.ui.PostIdSearchResult;
import ru.russianpost.tracking.portal.admin.service.PostIdService;
import ru.russianpost.tracking.portal.admin.service.exception.PostUserNotFoundServiceException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private User controller;

    @Mock
    private PostIdService postIdService;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testFind() throws Exception {
        final String hid1 = "hid1";
        final String email = "email@email.com";

        final PostIdSearchResult postIdSearchResult = new PostIdSearchResult();
        postIdSearchResult.setHid(hid1);

        when(postIdService.findUserByEmail(email)).thenReturn(postIdSearchResult);

        final InfoProfile infoProfile = new InfoProfile();
        Person p1 = new Person();
        p1.setFirstNameRawSource("firstName");
        p1.setLastNameRawSource("lastName");
        p1.setMiddleNameRawSource("middleName");
        infoProfile.setPersons(new Person[]{p1});

        when(postIdService.getUserProfile(hid1)).thenReturn(infoProfile);

        mockMvc.perform(get("/api/v1/user/find?email=" + email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.middleName").value("middleName"));

        verify(postIdService, times(1)).findUserByEmail(email);
        verify(postIdService, times(1)).getUserProfile(hid1);
        verifyNoMoreInteractions(postIdService);
    }

    @Test
    public void testFindUserNotFound1Step() throws Exception {
        final String email = "email@email.com";

        when(postIdService.findUserByEmail(email)).thenThrow(new PostUserNotFoundServiceException());

        mockMvc.perform(get("/api/v1/user/find?email=" + email))
                .andExpect(status().isNotFound());

        verify(postIdService, times(1)).findUserByEmail(email);
        verifyNoMoreInteractions(postIdService);
    }

    @Test
    public void testFindUserNotFound2Step() throws Exception {
        final String hid1 = "hid1";
        final String email = "email@email.com";

        final PostIdSearchResult postIdSearchResult = new PostIdSearchResult();
        postIdSearchResult.setHid(hid1);

        when(postIdService.findUserByEmail(email)).thenReturn(postIdSearchResult);
        when(postIdService.getUserProfile(hid1)).thenThrow(new PostUserNotFoundServiceException());

        mockMvc.perform(get("/api/v1/user/find?email=" + email))
                .andExpect(status().isNotFound());

        verify(postIdService, times(1)).findUserByEmail(email);
        verify(postIdService, times(1)).getUserProfile(hid1);
        verifyNoMoreInteractions(postIdService);
    }
}
