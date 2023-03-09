/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.opm;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.opm.CreateOpsUsersRequest;
import ru.russianpost.tracking.portal.admin.model.opm.NewOpsUser;
import ru.russianpost.tracking.portal.admin.model.opm.OpmHistory;
import ru.russianpost.tracking.portal.admin.model.opm.OpsIndex;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import java.util.List;
import java.util.function.Supplier;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.USER_ALREADY_EXIST;

/**
 * OnlinePaymentMarkService implementation.
 *
 * @author MKitchenko
 */
@Slf4j
@Service
public class OnlinePaymentMarkServiceImpl implements OnlinePaymentMarkService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final String getHistory;
    private final String getOpmIdsByBarcode;
    private final String generateOpsUsers;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor.
     *
     * @param restTemplate     instance of {@link RestTemplate}
     * @param url              url
     * @param internalUrl      internal url
     * @param credentialsPlain credentials plain
     */
    public OnlinePaymentMarkServiceImpl(
        @Qualifier("restTemplate") final RestTemplate restTemplate,
        @Value("${ru.russianpost.online.payment.mark.service.url}") final String url,
        @Value("${ru.russianpost.online.payment.mark.service.internal.url}") final String internalUrl,
        @Value("${ru.russianpost.online.payment.mark.service.credentials}") final String credentialsPlain
    ) {
        this.restTemplate = restTemplate;
        httpHeaders.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(credentialsPlain));
        this.getHistory = UriComponentsBuilder.fromUriString(url).path("/v2.0/history/{id}").build().toUriString();
        this.getOpmIdsByBarcode = UriComponentsBuilder.fromUriString(url)
            .path("/v2.0/opm-id/{barcode}")
            .build()
            .toUriString();
        this.generateOpsUsers = UriComponentsBuilder.fromUriString(internalUrl)
            .path("/v1.0/user/ops/generate")
            .build()
            .toUriString();
    }

    @Override
    public OpmHistory getHistory(String onlinePaymentMarkId) throws ServiceUnavailableException {
        return executeRequest(
            getHistory,
            () -> restTemplate.exchange(
                getHistory, GET, new HttpEntity<>(httpHeaders), OpmHistory.class, onlinePaymentMarkId
            ).getBody()
        );
    }

    @Override
    public List<String> getOpmIdsByBarcode(String barcode) throws ServiceUnavailableException {
        return executeRequest(
            getOpmIdsByBarcode,
            () -> restTemplate.exchange(
                getOpmIdsByBarcode,
                GET,
                new HttpEntity<>(httpHeaders),
                new ParameterizedTypeReference<List<String>>() {
                },
                barcode
            ).getBody()
        );
    }

    @Override
    public List<NewOpsUser> generateOpsUsers(final List<OpsIndex> opsIndices, final boolean overrideExisting) {
        try {
            return restTemplate.exchange(
                generateOpsUsers,
                POST,
                new HttpEntity<>(new CreateOpsUsersRequest(opsIndices, overrideExisting), httpHeaders),
                new ParameterizedTypeReference<List<NewOpsUser>>() {
                }
            ).getBody();
        } catch (final HttpClientErrorException.Conflict ex) {
            throw new HttpServiceException(ex.getRawStatusCode(),
                new Error(USER_ALREADY_EXIST, "Индексы, для которых уже сгенерирован логин: " + String.join(",", extractIndexes(ex)))
            );
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new InternalServerErrorException(generateOpsUsers, ex);
        }
    }

    @SneakyThrows
    private List<String> extractIndexes(final HttpStatusCodeException ex) {
        return mapper.readValue(ex.getResponseBodyAsByteArray(), OpmErrorResponse.class).getIndexes();
    }

    private <T> T executeRequest(String uri, Supplier<T> supplier) throws ServiceUnavailableException {
        try {
            return supplier.get();
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(uri, ex);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new InternalServerErrorException(uri, ex);
        }
    }
}
