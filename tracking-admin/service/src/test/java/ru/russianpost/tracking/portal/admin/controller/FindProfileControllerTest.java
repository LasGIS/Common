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
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileSearchService;
import ru.russianpost.tracking.web.model.admin.AdminProfileSearchResult;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * FindProfileControllerTest
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class FindProfileControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FindProfile controller;

    @Mock
    private ProfileSearchService profileSearchServiceMock;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testFind() throws Exception {
        String query = "1234";
        int count = 1;

        when(profileSearchServiceMock.find(query, count)).thenReturn(Arrays.asList(new AdminProfileSearchResult(123, "123")));

        mockMvc.perform(get("/api/v1/profile/find")
                        .param("query", query)
                        .param("count", Integer.toString(count))
        ).andExpect(status().isOk());

        verify(profileSearchServiceMock, times(1)).find(query, count);
        verifyNoMoreInteractions(profileSearchServiceMock);
    }

    @Test
    public void testBadQuery() throws Exception {
        String query = "123";
        int count = 1;

        mockMvc.perform(get("/api/v1/profile/find")
                        .param("query", query)
                        .param("count", Integer.toString(count))
        ).andExpect(status().isBadRequest());

        verify(profileSearchServiceMock, times(0)).find(query, count);
        verifyNoMoreInteractions(profileSearchServiceMock);
    }

    @Test
    public void testOptionalCount() throws Exception {
        String query = "1234";
        int count = FindProfile.MAX_RESULT_COUNT;

        when(profileSearchServiceMock.find(query, count)).thenReturn(Arrays.asList(new AdminProfileSearchResult(123, "123")));

        mockMvc.perform(get("/api/v1/profile/find")
                        .param("query", query)
        ).andExpect(status().isOk());

        verify(profileSearchServiceMock, times(1)).find(query, count);
        verifyNoMoreInteractions(profileSearchServiceMock);
    }

    @Test
    public void testLimitedCount() throws Exception {
        String query = "1234";
        int count = FindProfile.MAX_RESULT_COUNT + 100;

        mockMvc.perform(get("/api/v1/profile/find")
                        .param("query", query)
        ).andExpect(status().isOk());

        verify(profileSearchServiceMock, times(1)).find(query, FindProfile.MAX_RESULT_COUNT);
        verifyNoMoreInteractions(profileSearchServiceMock);
    }
}
