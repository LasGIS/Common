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

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Booking;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BookingType;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Container;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.DefaultContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.IndexContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ConfigurationUpdateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.IndexMonthContainerConfiguration;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.BarcodeProviderService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for {@link BarcodeProviderController}.
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class BarcodeProviderControllerTest {

    private static final int INDEX = 123456;
    private static final int YEAR = 2017;
    private static final int MONTH = 2;
    private static final String USERNAME = "username";
    private static final String DATE_TIME = "31.05.2017 13:46:09";
    private MockMvc mockMvc;

    @InjectMocks
    private BarcodeProviderController controller;

    @Mock
    private BarcodeProviderService barcodeProviderService;
    @Mock
    private StopWatchFactory stopWatchFactory;
    @Mock
    private StopWatch stopWatch;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(stopWatchFactory.getStopWatch(anyString())).thenReturn(stopWatch);
        when(stopWatch.stop()).thenReturn(stopWatch);
    }

    @Test
    public void testViewContainers() throws Exception {
        final List<Container> list = Arrays.asList(
            new Container(2016, 7, 80000, 90000, 85000),
            new Container(2016, 8, 80000, 90000, 83000),
            new Container(2016, 9, 80000, 90000, 84000),
            new Container(2016, 10, 80000, 88000, 87000),
            new Container(2016, 11, 80000, 90000, 81000),
            new Container(2016, 12, 80000, 95000, 83000)
        );
        when(barcodeProviderService.getRanges(INDEX)).thenReturn(list);

        mockMvc.perform(get("/api/v1/barcode-provider/view/" + INDEX))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(content().json(new Gson().toJson(list)));

        verify(barcodeProviderService, times(1)).getRanges(INDEX);
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testViewContainersEmpty() throws Exception {
        when(barcodeProviderService.getRanges(INDEX)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/barcode-provider/view/" + INDEX))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));

        verify(barcodeProviderService, times(1)).getRanges(INDEX);
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testViewContainersInvalidIndexFormat() throws Exception {
        mockMvc.perform(get("/api/v1/barcode-provider/view/1")).andExpect(status().isNotFound());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testViewBookings() throws Exception {
        long id = 0;
        final List<Booking> list = Arrays.asList(
            new Booking(id++, "Рога и Копыта", "1234567890", 80000, 84999, 84999, BookingType.AUTO, DATE_TIME),
            new Booking(id++, "ООО Суперфирма", "1234567891", 80500, 81499, 80777, BookingType.AUTO, DATE_TIME),
            new Booking(id++, "ИП Васильев", "1234567892", 81500, 81999, 81555, BookingType.SOAP, DATE_TIME),
            new Booking(id++, "Рога и Копыта", "1234567890", 82000, 82999, 82222, BookingType.AUTO, DATE_TIME),
            new Booking(id++, "Копыта и Рога", "1234567893", 83000, 83299, 83299, BookingType.SOAP, DATE_TIME),
            new Booking(id++, "Копыта и Рога", "1234567893", 83300, 84999, 84444, BookingType.AUTO, DATE_TIME)
        );
        when(barcodeProviderService.getBookings(INDEX, YEAR, MONTH)).thenReturn(list);

        mockMvc.perform(get("/api/v1/barcode-provider/view/" + INDEX + "/" + YEAR + "/" + MONTH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(content().json(new Gson().toJson(list)));

        verify(barcodeProviderService, times(1)).getBookings(INDEX, YEAR, MONTH);
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testViewBookingsEmpty() throws Exception {
        when(barcodeProviderService.getBookings(INDEX, YEAR, MONTH)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/barcode-provider/view/" + INDEX + "/" + YEAR + "/" + MONTH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(0)));

        verify(barcodeProviderService, times(1)).getBookings(INDEX, YEAR, MONTH);
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testViewBookingsInvalidMonth() throws Exception {
        mockMvc.perform(get("/api/v1/barcode-provider/view/" + INDEX + "/" + YEAR + "/" + 0))
            .andExpect(status().isNotFound());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testGetDefaultConfiguration() throws Exception {
        final DefaultContainerConfiguration conf = new DefaultContainerConfiguration(
            80000,
            99999,
            500,
            true,
            asList("rupost+101@mailinator.com", "rupost+102@mailinator.com")
        );

        when(barcodeProviderService.getDefaultConfiguration()).thenReturn(conf);

        mockMvc.perform(get("/api/v1/barcode-provider/config/default"))
            .andExpect(status().isOk())
            .andExpect(content().json(new Gson().toJson(conf)));

        verify(barcodeProviderService, times(1)).getDefaultConfiguration();
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testGetIndexConfiguration() throws Exception {
        final IndexContainerConfiguration conf = new IndexContainerConfiguration(
            11111,
            22222,
            123,
            null,
            true,
            Collections.singletonList("kkiryakov@luxoft.com"),
            Collections.singletonList(new IndexMonthContainerConfiguration(2017, 3, null, 90000, 500, false))
        );

        when(barcodeProviderService.getIndexConfiguration(INDEX)).thenReturn(conf);

        mockMvc.perform(get("/api/v1/barcode-provider/config/" + INDEX))
            .andExpect(status().isOk())
            .andExpect(content().json(new Gson().toJson(conf)));

        verify(barcodeProviderService, times(1)).getIndexConfiguration(INDEX);
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfiguration() throws Exception {
        String query = (
            "{"
                + "\"configuration\": {"
                    + "\"min\": 80001,"
                    + "\"max\": 99998,"
                    + "\"allocationSize\": 100,"
                    + "\"notificationEnabled\": true,"
                    + "\"emails\": ["
                        + "\"rupost+105@mailinator.com\","
                        + "\"rupost+103@mailinator.com\""
                    + "]"
                + "},"
                + "\"comment\": \"Тест\""
            + "}"
        );
        final DefaultContainerConfiguration expectedConfig = new DefaultContainerConfiguration(
            80001,
            99998,
            100,
            true,
            Arrays.asList("rupost+105@mailinator.com", "rupost+103@mailinator.com")
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isOk());


        verify(barcodeProviderService, times(1)).updateDefaultConfiguration(eq(expectedConfig), eq(USERNAME), eq("Тест"));
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationInvalidMinimum() throws Exception {
        // min is lower than zero
        String query = (
            "{"
                + "\"configuration\": {"
                    + "\"min\": -1,"
                    + "\"max\": 99998,"
                    + "\"allocationSize\": 100,"
                    + "\"notificationEnabled\": true,"
                    + "\"emails\": ["
                        + "\"rupost+105@mailinator.com\","
                        + "\"rupost+103@mailinator.com\""
                    + "]"
                + "},"
                + "\"comment\": \"Тест\""
            + "}"
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationInvalidMaximum() throws Exception {
        // max is greater than 99999
        String query = (
            "{"
                + "\"configuration\": {"
                    + "\"min\": 80001,"
                    + "\"max\": 100000,"
                    + "\"allocationSize\": 100,"
                    + "\"notificationEnabled\": true,"
                + "\"emails\": ["
                    + "\"rupost+105@mailinator.com\","
                    + "\"rupost+103@mailinator.com\""
                + "]"
            + "},"
                + "\"comment\": \"Тест\""
            + "}"
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationWithoutRequiredField() throws Exception {
        // without "min"
        String query = (
            "{"
                + "\"configuration\": {"
                    + "\"max\": 99998,"
                    + "\"allocationSize\": 100,"
                    + "\"notificationEnabled\": true,"
                    + "\"emails\": ["
                        + "\"rupost+105@mailinator.com\","
                        + "\"rupost+103@mailinator.com\""
                    + "]"
                + "},"
                + "\"comment\": \"Тест\""
            + "}"
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationMaxNotGreaterThanMin() throws Exception {
        String query = (
            "{"
                + "\"configuration\": {"
                + "\"min\": 80001,"
                + "\"max\": 80000,"
                + "\"allocationSize\": 100,"
                + "\"notificationEnabled\": true,"
                + "\"emails\": ["
                + "\"rupost+105@mailinator.com\","
                + "\"rupost+103@mailinator.com\""
                + "]"
                + "},"
                + "\"comment\": \"Тест\""
                + "}"
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationBigAllocationSize() throws Exception {
        String query = (
            "{"
                + "\"configuration\": {"
                + "\"min\": 80001,"
                + "\"max\": 99999,"
                + "\"allocationSize\": 20000,"
                + "\"notificationEnabled\": true,"
                + "\"emails\": ["
                + "\"rupost+105@mailinator.com\","
                + "\"rupost+103@mailinator.com\""
                + "]"
                + "},"
                + "\"comment\": \"Тест\""
                + "}"
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationWithoutComment() throws Exception {
        String query = (
            "{\"configuration\": {"
                + "\"min\": 80001,"
                + "\"max\": 99998,"
                + "\"allocationSize\": 100,"
                + "\"notificationEnabled\": true,"
                + "\"emails\": ["
                    + "\"rupost+105@mailinator.com\","
                    + "\"rupost+103@mailinator.com\""
                + "]"
            + "}}"
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateDefaultConfigurationEmptyEmails() throws Exception {
        String query = (
            "{"
                + "\"configuration\": {"
                    + "\"min\": 80001,"
                    + "\"max\": 99998,"
                    + "\"allocationSize\": 100,"
                    + "\"notificationEnabled\": false,"
                    + "\"emails\": []"
                + "},"
                + "\"comment\": \"Тест\""
            + "}"
        );
        final DefaultContainerConfiguration expectedConfig = new DefaultContainerConfiguration(
            80001,
            99998,
            100,
            false,
            Collections.emptyList()
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/default").contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> USERNAME)).andExpect(status().isOk());

        verify(barcodeProviderService, times(1)).updateDefaultConfiguration(eq(expectedConfig), eq(USERNAME), eq("Тест"));
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateIndexConfiguration() throws Exception {
        String query = (
            "{"
                + "\"configuration\": {"
                    + "\"min\": 85000,"
                    + "\"max\": 90000,"
                + "\"emails\": ["
                    + "\"rupost+999@mailinator.com\""
                + "]},"
                + "\"comment\": \"Тест\""
            + "}"
        );
        final IndexContainerConfiguration expectedConfig = new IndexContainerConfiguration(85000,
            90000,
            null,
            null,
            null,
            Collections.singletonList("rupost+999@mailinator.com"),
            null
        );

        mockMvc.perform(put("/api/v1/barcode-provider/config/" + INDEX).contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isOk());

        verify(barcodeProviderService, times(1)).updateIndexConfiguration(eq(INDEX), eq(expectedConfig), eq(USERNAME), eq("Тест"));
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateOnlyCompressAttachment() throws Exception {
        String query = ("{\"configuration\": {\"compressAttachment\": true},\"comment\": \"Тест\"}");
        final IndexContainerConfiguration expectedConfig = new IndexContainerConfiguration(null, null, null, null, true, null, null);

        mockMvc.perform(put("/api/v1/barcode-provider/config/" + INDEX).contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isOk());

        verify(barcodeProviderService, times(1)).updateIndexConfiguration(eq(INDEX), eq(expectedConfig), eq(USERNAME), eq("Тест"));
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateIndexWithEmptyConfiguration() throws Exception {
        String query = "{\"configuration\": { },\"comment\": \"Тест\"}";
        final IndexContainerConfiguration expectedConfig = new IndexContainerConfiguration(null, null, null, null, null, null, null);

        mockMvc.perform(put("/api/v1/barcode-provider/config/" + INDEX).contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isOk());

        verify(barcodeProviderService, times(1)).updateIndexConfiguration(eq(INDEX), eq(expectedConfig), eq(USERNAME), eq("Тест"));
        verifyNoMoreInteractions(barcodeProviderService);
    }

    @Test
    public void testUpdateIndexWithEmptyComment() throws Exception {
        final IndexContainerConfiguration configuration = new IndexContainerConfiguration(
            85000,
            90000,
            null,
            null,
            null,
            Collections.singletonList("rupost+999@mailinator.com"),
            null
        );
        final ConfigurationUpdateRequest<IndexContainerConfiguration> request = new ConfigurationUpdateRequest<>(
            configuration, ""
        );
        final String query = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(put("/api/v1/barcode-provider/config/" + INDEX).contentType(MediaType.APPLICATION_JSON)
            .content(query)
            .principal(() -> "username")).andExpect(status().isBadRequest());
        verifyNoInteractions(barcodeProviderService);
    }
}
