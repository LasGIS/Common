/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.automatization;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import ru.russianpost.tracking.portal.admin.exception.BarcodeAutomatizationServiceException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.AllocationSearchResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodeAllocateResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodeAutomatizationResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodesAllocateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.GetUserResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.S10Range;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SetUserRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SuitableRangeResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPS;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPSList;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPSUser;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;

import static ru.russianpost.tracking.portal.admin.service.ServiceName.AUTOMATIZATION_SERVICE;

/**
 * Implementation of {@link BarcodeAutomatizationService}.
 *
 * @author MKitchenko
 */
@Slf4j
@Service
public class BarcodeAutomatizationServiceImpl implements BarcodeAutomatizationService {

    private static final String AUTOMATIZATION_SERVICE_IS_UNAVAILABLE = AUTOMATIZATION_SERVICE + " is unavailable";
    private static final String ERROR_PARSING_MESSAGE_PATTERN = MessageFormat.format(
        "Error to parse {0} error message. '{}'", AUTOMATIZATION_SERVICE
    );
    private static final String HTTP_METHOD_PROCESSING_ERROR = MessageFormat.format(
        "Http method processing to {0} caused an error.", AUTOMATIZATION_SERVICE
    );

    private final RestTemplate restTemplate;
    private final String barcodeAutomatizationUrl;

    private final String setUfpsUserUrl;
    private final String getUfpsUserUrl;
    private final String getUfpsListUrl;
    private final String allocateUrl;
    private final String findAllocationsUrl;
    private final String suitableRangeUrl;

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     *
     * @param restTemplate             restTemplate
     * @param barcodeAutomatizationUrl barcodeAutomatization service url
     * @param credentialsPlain         credentialsPlain
     */
    public BarcodeAutomatizationServiceImpl(
        @Qualifier("restTemplate") RestTemplate restTemplate,
        @Value("${ru.russianpost.barcode.automatization.service.url}") String barcodeAutomatizationUrl,
        @Value("${ru.russianpost.barcode.automatization.service.credentials}") String credentialsPlain
    ) {
        this.restTemplate = restTemplate;
        this.barcodeAutomatizationUrl = barcodeAutomatizationUrl;

        httpHeaders.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(credentialsPlain));

        setUfpsUserUrl = buildUriString("/user");
        getUfpsUserUrl = buildUriString("/user/{user}");
        getUfpsListUrl = buildUriString("/ufps");
        allocateUrl = buildUriString("/allocate");
        findAllocationsUrl = buildUriString("/find?ufpsId={ufpsId}&dateFrom={from}&dateTo={to}");
        suitableRangeUrl = buildUriString("/suitable-range?ufpsId={ufpsId}&containerType={containerType}&quantity={quantity}");
    }

    private String buildUriString(String path) {
        return UriComponentsBuilder.fromUriString(barcodeAutomatizationUrl)
            .path("/api")
            .path("/v1")
            .path(path)
            .build().toUriString();
    }

    @Override
    public void setUfpsUser(SetUserRequest setUserRequest) throws ServiceUnavailableException {
        processPut(setUfpsUserUrl, setUserRequest);
    }

    @Override
    public UFPSUser getUfpsUser(String userLogin) throws ServiceUnavailableException {
        final GetUserResponse getUserResponse = processGet(getUfpsUserUrl, GetUserResponse.class, userLogin);
        return new UFPSUser(getUserResponse.getLogin(), getUserResponse.getUfps());
    }

    @Override
    public List<UFPS> getUfpsList() throws ServiceUnavailableException {
        final List<UFPS> list = processGet(getUfpsListUrl, UFPSList.class).getUfpsList();
        list.sort(Comparator.comparing(UFPS::getName));
        return list;
    }

    @Override
    public List<S10Range> getAllocationResults(int ufpsId, String from, String to) throws ServiceUnavailableException {
        return processGet(findAllocationsUrl, AllocationSearchResponse.class, ufpsId, from, to).getRanges();
    }

    @Override
    public BarcodeAllocateResponse allocate(BarcodesAllocateRequest request) throws ServiceUnavailableException {
        return processPostForEntity(allocateUrl, request, BarcodeAllocateResponse.class);
    }

    @Override
    public SuitableRangeResponse getSuitableRange(int ufpsId, final String containerType, final Integer quantity) throws ServiceUnavailableException {
        return processGet(suitableRangeUrl, SuitableRangeResponse.class, ufpsId, containerType, quantity);
    }

    private <T extends BarcodeAutomatizationResponse> T processPostForEntity(
        final String url,
        final Object requestBody,
        final Class<T> responseType,
        final Object... urlVariables
    ) throws ServiceUnavailableException {
        return processHttpMethod(HttpMethod.POST, url, requestBody, responseType, urlVariables);
    }

    private void processPut(final String url, final Object requestBody, final Object... urlVariables) throws ServiceUnavailableException {
        processHttpMethod(HttpMethod.PUT, url, requestBody, BarcodeAutomatizationResponse.class, urlVariables);
    }

    private <T extends BarcodeAutomatizationResponse> T processGet(final String url, final Class<T> responseType, final Object... urlVariables)
        throws ServiceUnavailableException {
        return processHttpMethod(HttpMethod.GET, url, null, responseType, urlVariables);
    }

    private <T extends BarcodeAutomatizationResponse> T processHttpMethod(
        HttpMethod httpMethod,
        final String url,
        final Object requestBody,
        final Class<T> responseType,
        final Object... urlVariables
    ) throws ServiceUnavailableException {
        try {
            final ResponseEntity<T> response = restTemplate.exchange(
                url,
                httpMethod,
                new HttpEntity<>(requestBody, httpHeaders),
                responseType,
                urlVariables
            );
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new InternalServerErrorException(response.getBody().getError().getMessage());
            }
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
                throw new InternalServerErrorException(url, e);
            }
            try {
                final BarcodeAutomatizationResponse resp = mapper.readValue(e.getResponseBodyAsByteArray(),
                    BarcodeAutomatizationResponse.class);
                throw new BarcodeAutomatizationServiceException(resp.getError());
            } catch (IOException ex) {
                log.warn(ERROR_PARSING_MESSAGE_PATTERN, ex.getMessage());
                throw new ServiceUnavailableException(new UriTemplate(url).expand(urlVariables).toString(), e);
            }
        } catch (RestClientException e) {
            log.warn(AUTOMATIZATION_SERVICE_IS_UNAVAILABLE, e);
            throw new ServiceUnavailableException(new UriTemplate(url).expand(urlVariables).toString(), e);
        } catch (Exception e) {
            log.warn(HTTP_METHOD_PROCESSING_ERROR, e);
            throw new ServiceUnavailableException(new UriTemplate(url).expand(urlVariables).toString(), e);
        }
    }
}
