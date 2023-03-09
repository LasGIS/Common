/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.authorized.operator.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.BasketData;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.DataToProcess;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.errors.Error;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.errors.ErrorResponse;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.request.FindBasketDataRequest;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.request.ProcessDataRequest;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.BasketDataResponse;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.DeferredDataInfo;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.UnprocessedData;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import javax.annotation.PreDestroy;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * AuthorizedOperatorSupportService implementation.
 * @author MKitchenko
 */
@Slf4j
@Service
public class AuthorizedOperatorSupportServiceImpl implements AuthorizedOperatorSupportService {

    private final long sendTimeoutMilliseconds;

    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    private final String getDeferredDataUrl;
    private final String toDeclarationUrl;
    private final String toBasketUrl;
    private final String toDeferredUrl;
    private final String getBasketDataUrl;

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor.
     * @param sendTimeoutMilliseconds                  time for the computation to complete
     * @param credentialsPlain                         credentials
     * @param url                                      url to rtm 4601 service
     * @param restTemplate                             instance of {@link RestTemplate}
     * @param authorizedOperatorSupportExecutorService executorService that transfers data to rtm 4601 service
     */
    public AuthorizedOperatorSupportServiceImpl(
        @Value("${ru.russianpost.rtm4601.service.send.timeout.milliseconds}") final long sendTimeoutMilliseconds,
        @Value("${ru.russianpost.rtm4601.service.credentials}") final String credentialsPlain,
        @Value("${ru.russianpost.rtm4601.service.postponed.url}") final String url,
        @Qualifier("restTemplate") final RestTemplate restTemplate,
        final ExecutorService authorizedOperatorSupportExecutorService
    ) {
        this.sendTimeoutMilliseconds = sendTimeoutMilliseconds;
        this.restTemplate = restTemplate;
        this.executorService = authorizedOperatorSupportExecutorService;

        httpHeaders.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(credentialsPlain));

        this.getDeferredDataUrl = UriComponentsBuilder.fromUriString(url).path("/v2/{type}").build().toUriString();
        this.toDeclarationUrl = UriComponentsBuilder.fromUriString(url).path("/process").build().toUriString();
        this.toBasketUrl = UriComponentsBuilder.fromUriString(url).path("/archive").build().toUriString();
        this.getBasketDataUrl = UriComponentsBuilder.fromUriString(url).path("/archived").build().toUriString();
        this.toDeferredUrl = UriComponentsBuilder.fromUriString(url).path("/unarchive").build().toUriString();
    }

    @Override
    public DeferredDataInfo getDeferredData(String type) throws ServiceUnavailableException {
        try {
            return restTemplate.exchange(getDeferredDataUrl, HttpMethod.GET,
                new HttpEntity<>(httpHeaders), DeferredDataInfo.class, type).getBody();
        } catch (Exception ex) {
            throw handleException(ex, "Could not load deferred data from: " + getDeferredDataUrl);
        }
    }

    @Override
    public List<UnprocessedData> toDeclaration(final List<DataToProcess> data, final String login) {
        return processData(data, login, toDeclarationUrl);
    }

    @Override
    public List<UnprocessedData> toBasket(final List<DataToProcess> data, final String login) {
        return processData(data, login, toBasketUrl);
    }

    @Override
    public List<UnprocessedData> toDeferred(List<DataToProcess> data, String login) {
        return processData(data, login, toDeferredUrl);
    }

    @Override
    public List<BasketData> getBasketData(String dateFrom, String dateTo) throws ServiceUnavailableException {
        try {
            final FindBasketDataRequest request = new FindBasketDataRequest(dateFrom, dateTo);
            return restTemplate.exchange(getBasketDataUrl, HttpMethod.POST,
                new HttpEntity<>(request, httpHeaders), BasketDataResponse.class).getBody().getOrders();
        } catch (Exception ex) {
            throw handleException(ex, "Could not load basket data from: " + getBasketDataUrl);
        }
    }

    private InternalServerErrorException handleException(Exception ex, String errorMessage) throws ServiceUnavailableException {
        if (ex instanceof HttpServerErrorException || ex instanceof ResourceAccessException) {
            throw new ServiceUnavailableException(errorMessage, ex);
        } else {
            return new InternalServerErrorException(errorMessage, ex);
        }
    }

    private List<UnprocessedData> processData(
        final List<DataToProcess> data,
        final String login,
        final String urlToProcess
    ) {
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        return data.stream()
            .map(dataToProcess -> Pair.of(dataToProcess, executorService.submit(
                new DelegatingSecurityContextCallable<>(
                    () -> processData(urlToProcess, dataToProcess, login), securityContext
                ))
            ))
            .map(pair -> resolveFuture(pair.getKey(), pair.getValue()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    private Optional<UnprocessedData> processData(
        final String urlToProcess,
        final DataToProcess dataToProcess,
        final String login
    ) {
        try {
            final ProcessDataRequest request = new ProcessDataRequest(dataToProcess.getId(), login);

            final ResponseEntity<Object> response = restTemplate.exchange(
                new URI(urlToProcess), HttpMethod.POST, new HttpEntity<>(request, httpHeaders), Object.class
            );

            if (Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
                return Optional.empty();
            }

            return resolveNonOkStatusResponse(response, dataToProcess);
        } catch (HttpClientErrorException ex) {
            try {
                final Error error = mapper.readValue(ex.getResponseBodyAsByteArray(), ErrorResponse.class)
                    .getErrors().get(0);

                log.error("Could not process data: " + error.getDescription(), ex);

                return Optional.of(new UnprocessedData(
                    dataToProcess.getId(), dataToProcess.getTrackingNumber(), error.getCode().toString()
                ));
            } catch (Exception e) {
                log.warn("Error to parse rtm4601 error message. {}", e.getMessage());
                return Optional.of(new UnprocessedData(
                    dataToProcess.getId(), dataToProcess.getTrackingNumber(), e.getMessage()
                ));
            }
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
            return Optional.of(new UnprocessedData(
                dataToProcess.getId(), dataToProcess.getTrackingNumber(),
                "Could not process data: " + ex.getMessage()
            ));
        }
    }

    private Optional<UnprocessedData> resolveFuture(
        final DataToProcess dataToProcess, final Future<Optional<UnprocessedData>> future
    ) {
        try {
            return future.get(sendTimeoutMilliseconds, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Optional.of(new UnprocessedData(
                dataToProcess.getId(), dataToProcess.getTrackingNumber(), ex.getMessage()
            ));
        }
    }

    private Optional<UnprocessedData> resolveNonOkStatusResponse(
        final ResponseEntity<Object> response, final DataToProcess dataToProcess
    ) {
        final String errorMessage = Optional.ofNullable(response.getBody())
            .map(Object::toString).orElse("Unresolved error message.");

        log.error("Process data with id {} and trackingNumber {} crashed with error: {}",
            dataToProcess.getId(), dataToProcess.getTrackingNumber(), errorMessage
        );

        return Optional.of(new UnprocessedData(
            dataToProcess.getId(), dataToProcess.getTrackingNumber(), errorMessage
        ));
    }

    /**
     * ExecutorServices pre destroy shutdown.
     */
    @PreDestroy
    public void executorShutdown() {
        final String executorServiceName = "authorizedOperatorSupportExecutorService";
        log.info("Shutting down instance of {}.", executorServiceName);
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                log.info("Force shut down {} instance.", executorServiceName);
                executorService.shutdownNow();
            } else {
                log.info("{} instance shutdown successfully finished.", executorServiceName);
            }
        } catch (InterruptedException ex) {
            log.error(executorService + " shutdown task was interrupted.", ex);
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
