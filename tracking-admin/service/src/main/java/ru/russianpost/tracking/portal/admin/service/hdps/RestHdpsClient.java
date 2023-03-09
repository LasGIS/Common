/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.hdps;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.commons.hdps.dto.correction.HistoryRecordCorrectionSearchResult;
import ru.russianpost.tracking.commons.hdps.dto.eordershipments.v1.EOrderShipmentsResponse;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminHistoryResponseV7;
import ru.russianpost.tracking.commons.hdps.dto.historybyphone.v1.HistoryByPhoneResponse;
import ru.russianpost.tracking.commons.hdps.dto.multiplace.v1.MultiplaceRpoResponse;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionSourceSystem;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.controller.exception.InvalidPhoneFormatException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.customs.declaration.CustomsDeclaration;
import ru.russianpost.tracking.portal.admin.model.operation.HdpsScope;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsHistoryRecord;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 26, 2015)
 */
@Service
@Slf4j
public class RestHdpsClient implements HdpsClient {

    private static final ParameterizedTypeReference<List<CustomsDeclaration>> LIST_CUSTOMS_DECLARATIONS_RESPONSE_TYPE =
        new ParameterizedTypeReference<List<CustomsDeclaration>>() {
        };

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    private final String historyUriString;
    private final String smsHistoryUriString;
    private final String correctionHistoryUrl;
    private final String getMmoInfoByMmoId;
    private final String getEOrderShipments;
    private final String getAllCustomsDeclarations;
    private final String historyByPhoneUrl;

    /**
     * @param hdpsUrl          hdps service url
     * @param credentialsPlain credentials
     * @param restTemplate     instance of {@link RestTemplate}
     */
    public RestHdpsClient(
        @Value("${ru.russianpost.hdps.url}") String hdpsUrl,
        @Value("${ru.russianpost.hdps.credentials}") String credentialsPlain,
        @Qualifier("restTemplate") RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(credentialsPlain));
        historyUriString = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v7/history/{barcode}/admin")
            .queryParam("scope", "{scopes}")
            .build()
            .toUriString();
        smsHistoryUriString = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v1/sms-history/{barcode}")
            .build()
            .toUriString();
        correctionHistoryUrl = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v3/correction")
            .queryParam("from", "{from}")
            .queryParam("to", "{to}")
            .queryParam("corrTypes", "{corrTypes}")
            .queryParam("sourceSystem", "{sourceSystem}")
            .queryParam("count", "{count}")
            .build()
            .toUriString();
        getMmoInfoByMmoId = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v1/multiplace/{multiplaceBarcode}")
            .build()
            .toUriString();
        getEOrderShipments = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v1/eorder-shipments")
            .queryParam("eorder", "{eorder}")
            .build()
            .toUriString();
        getAllCustomsDeclarations = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v1/customs-declaration/{barcode}/admin")
            .build()
            .toUriString();
        historyByPhoneUrl = UriComponentsBuilder.fromUriString(hdpsUrl)
            .path("/v1/history-by-phone")
            .queryParam("phone", "{phone}")
            .queryParam("limit", "{limit}")
            .build()
            .toUriString();
    }

    @Override
    public AdminHistoryResponseV7 getHistory(
        final String barcode,
        final EnumSet<HdpsScope> scope
    ) throws ServiceUnavailableException {
        final URI uri = toUri(historyUriString, barcode, resolveScope(scope));
        return executeRequest(
            () -> restTemplate.exchange(uri, GET, new HttpEntity<>(httpHeaders), AdminHistoryResponseV7.class).getBody(),
            "Could not load history from: " + uri
        );
    }

    @Override
    public HistoryRecordCorrectionSearchResult getCorrections(
        final long fromUtc,
        final long toUtc,
        final List<HistoryRecordCorrectionType> correctionTypes,
        HistoryRecordCorrectionSourceSystem sourceSystem,
        int count
    ) throws ServiceUnavailableException {
        final String types = (correctionTypes == null || correctionTypes.isEmpty()) ? null
            : correctionTypes.stream().map(t -> t.name().toLowerCase()).collect(Collectors.joining(","));
        final URI uri = toUri(correctionHistoryUrl, fromUtc, toUtc, types, sourceSystem, count);

        return executeRequest(
            () -> restTemplate.exchange(
                uri,
                GET,
                new HttpEntity<>(httpHeaders),
                HistoryRecordCorrectionSearchResult.class
            ).getBody(),
            "Could not load correction history from :" + uri
        );
    }

    @Override
    public List<SmsHistoryRecord> getSmsHistory(String barcode) throws ServiceUnavailableException {
        final URI uri = toUri(smsHistoryUriString, barcode);
        final HttpEntity<Object> requestEntity = new HttpEntity<>(new ArrayList<SmsHistoryRecord>(), httpHeaders);
        final ParameterizedTypeReference<List<SmsHistoryRecord>> responseType =
            new ParameterizedTypeReference<List<SmsHistoryRecord>>() {
            };
        return executeRequest(
            () -> restTemplate.exchange(uri, GET, requestEntity, responseType).getBody(),
            "Could not load sms history from: " + uri
        );
    }

    @Override
    public MultiplaceRpoResponse getMmoInfoByMmoId(String multiplaceBarcode) throws ServiceUnavailableException {
        final URI uri = toUri(getMmoInfoByMmoId, multiplaceBarcode);
        return executeRequest(
            () -> restTemplate.exchange(
                uri,
                GET,
                new HttpEntity<MultiplaceRpoResponse>(this.httpHeaders),
                MultiplaceRpoResponse.class
            ),
            "Could not load mmo info from: " + uri
        ).getBody();
    }

    @Override
    public EOrderShipmentsResponse getEOrderShipments(final String eorder) throws ServiceUnavailableException {
        final URI uri = toUri(getEOrderShipments, eorder);
        return executeRequest(
            () -> restTemplate.exchange(
                uri,
                GET,
                new HttpEntity<EOrderShipmentsResponse>(this.httpHeaders),
                EOrderShipmentsResponse.class
            ),
            "Could not load eorder shipments from: " + uri
        ).getBody();
    }

    @Override
    public List<CustomsDeclaration> getAllCustomsDeclarations(final String shipmentId) throws ServiceUnavailableException {
        final URI uri = toUri(getAllCustomsDeclarations, shipmentId);
        final List<CustomsDeclaration> list = executeRequest(() -> restTemplate.exchange(
                uri, GET, new HttpEntity<>(new ArrayList<CustomsDeclaration>(), httpHeaders),
                LIST_CUSTOMS_DECLARATIONS_RESPONSE_TYPE
            ).getBody(),
            "Could not load list customs declarations from: " + uri
        );
        return ListUtils.emptyIfNull(list);
    }

    @Override
    public HistoryByPhoneResponse getHistoryByPhone(final String phone, final Integer limit) throws ServiceUnavailableException {
        final URI uri = toUri(historyByPhoneUrl, phone, limit);
        return executeRequest(() -> {
                try {
                    final ResponseEntity<HistoryByPhoneResponse> response = restTemplate.exchange(
                        uri, GET, new HttpEntity<HistoryByPhoneResponse>(this.httpHeaders),
                        HistoryByPhoneResponse.class
                    );
                    return response.getBody();
                } catch (final HttpClientErrorException.BadRequest ex) {
                    final String body = ex.getResponseBodyAsString();
                    if (body.contains("Phone is invalid")) {
                        throw new InvalidPhoneFormatException(body);
                    }
                    throw ex;
                }
            },
            "Could not load history by phone from: " + uri
        );
    }

    private URI toUri(String uriString, Object... variables) {
        return UriComponentsBuilder.fromUriString(uriString).buildAndExpand(variables).encode().toUri();
    }

    private <T> T executeRequest(Supplier<T> requestSupplier, String errorMessage) throws ServiceUnavailableException {
        try {
            return requestSupplier.get();
        } catch (HttpServerErrorException | ResourceAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new ServiceUnavailableException(errorMessage, ex);
        } catch (final RestClientException ex) {
            log.error(ex.getMessage(), ex);
            throw new InternalServerErrorException(errorMessage, ex);
        }
    }

    private String resolveScope(EnumSet<HdpsScope> scope) {
        return scope.stream()
            .map(HdpsScope::name)
            .map(String::toLowerCase)
            .collect(Collectors.joining(","));
    }
}
