/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Check Barcode Provider Service for
 *
 * @author vlaskin
 * @since <pre>14.06.2022</pre>
 */
@RunWith(MockitoJUnitRunner.class)
public class BarcodeProviderServiceImplTest {

    @Mock
    private RestTemplate restTemplateMock;

    private BarcodeProviderService service;

    private final HttpHeaders httpHeaders = new HttpHeaders();

    private final byte[] ERROR_BYTES
        = ("{\"success\":false,\"error\":{\"code\":\"VALIDATION_ERROR\",\"message\":\"Validation failed.\","
        + "\"validationErrors\":[{\"field\":\"bookingId\",\"errorCode\":\"INVALID_FIELD_VALUE\",\"message\":\"Value must be bigger than 0\"}]}}")
        .getBytes(StandardCharsets.UTF_8);

    private final byte[] VALID_BYTES
        = ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
        + "<ns:Range Inn=\"000000000000\" IndexFrom=\"0\" DateInfo=\"10.06.2022 15:36:29\" CRC=\"8A6BEA6A\""
        + " xsi:schemaLocation=\"http://russianpost.org range.xsd\" xmlns:ns=\"http://russianpost.org\""
        + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
        + "    <ns:Segment MailTypePref=\"ER\" NumBeg=\"2000001\" NumEnd=\"2000001\" State=\"1\"/>\n"
        + "</ns:Range>\n")
        .getBytes(StandardCharsets.UTF_8);

    @Before
    public void setup() {
        final String credentialsPlain = "tracking-portal-admin:password";
        service = new BarcodeProviderServiceImpl(
            null, restTemplateMock,
            "http://localhost:10103/barcode-fetch/v1/",
            credentialsPlain
        );
        httpHeaders.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(credentialsPlain));
    }

    @Test
    public void getBookingXml() {
        when(this.restTemplateMock.exchange(
            eq("http://localhost:10103/barcode-fetch/v1/bookings/internal/xml/{id}"),
            eq(HttpMethod.GET), eq(new HttpEntity<>(httpHeaders)), eq(byte[].class), eq(191L)
        )).thenReturn(
            ResponseEntity.ok(VALID_BYTES)
        );
        final ResponseEntity<byte[]> result = service.getBookingXml(191);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(VALID_BYTES, result.getBody());
    }

    @Test
    public void getBookingInternationalXml() {
        when(this.restTemplateMock.exchange(
            eq("http://localhost:10103/barcode-fetch/v1/bookings/international/xml/{id}"),
            eq(HttpMethod.GET), eq(new HttpEntity<>(httpHeaders)), eq(byte[].class), eq(192L)
        )).thenReturn(ResponseEntity.ok(VALID_BYTES));
        final ResponseEntity<byte[]> result = service.getBookingInternationalXml(192);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(VALID_BYTES, result.getBody());
    }

    @Test
    public void getBookingXmlThenThrow() {
        when(this.restTemplateMock.exchange(
            eq("http://localhost:10103/barcode-fetch/v1/bookings/internal/xml/{id}"),
            eq(HttpMethod.GET), eq(new HttpEntity<>(httpHeaders)), eq(byte[].class), eq(193L)
        )).thenThrow(
            new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request", ERROR_BYTES, StandardCharsets.UTF_8)
        );
        final ResponseEntity<byte[]> result = service.getBookingXml(193);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertNull(result.getBody());
        final List<String> location = result.getHeaders().get("Location");
        assertNotNull(location);
        assertEquals("/admin.html#/barcode-provider/view/internal?errorCode=VALIDATION_ERROR", location.get(0));
    }

    @Test
    public void getBookingInternationalXmlThenThrow() {
        when(this.restTemplateMock.exchange(
            eq("http://localhost:10103/barcode-fetch/v1/bookings/international/xml/{id}"),
            eq(HttpMethod.GET), eq(new HttpEntity<>(httpHeaders)), eq(byte[].class), eq(194L)
        )).thenThrow(
            new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request", ERROR_BYTES, StandardCharsets.UTF_8)
        );
        final ResponseEntity<byte[]> result = service.getBookingInternationalXml(194);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertNull(result.getBody());
        final List<String> location = result.getHeaders().get("Location");
        assertNotNull(location);
        assertEquals("/admin.html#/barcode-provider/view/internal?errorCode=VALIDATION_ERROR", location.get(0));
    }
}
