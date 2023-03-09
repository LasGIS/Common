/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.international.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.CustomsDeclarationErrorRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DateIntervalRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ReceptacleListEDIRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorShipmentDetailsRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.excel.ExcelReportFileTask;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.excel.ExcelReportFileType;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

/**
 * Service for read International Monitoring data from tracking-international-monitoring-web service.
 *
 * @author vlaskin
 * @since <pre>25.11.2021</pre>
 */
@Slf4j
@Service
public class InternationalMonitoringExcelServiceImpl implements InternationalMonitoringExcelService {

    private static final ParameterizedTypeReference<List<ExcelReportFileTask>> LIST_TASKS_RESPONSE_TYPE
        = new ParameterizedTypeReference<List<ExcelReportFileTask>>() {
    };
    private static final List<MediaType> ACCEPTABLE_MEDIA_TYPES = Arrays.stream(InternationalMonitoringExcelFileExtension.values())
        .map(ext -> MediaType.parseMediaType(ext.getContentType()))
        .collect(Collectors.toList());

    private final RestTemplate restTemplate;
    private final String listCompletedTasksUrl;
    private final String createRtm4601GsdpErrorTotalsTaskUrl;
    private final String createRtm4601GsdpErrorShipmentDetailsTaskUrl;
    private final String createMainDashboardEDITaskUrl;
    private final String createReceptacleListEDITaskUrl;
    private final String createDispatchMainTaskUrl;
    private final String createDispatchTaskUrl;
    private final String createCustomsDeclarationErrorTaskUrl;

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HttpHeaders httpHeadersDatabusStorage = new HttpHeaders();

    /**
     * Constructor.
     *
     * @param restTemplate              rest template
     * @param baseUrl                   base url
     * @param login                     user login (for portal admin == 'portal-admin')
     * @param password                  user password
     * @param databusStorageAccessToken databus Storage Access Token
     */
    public InternationalMonitoringExcelServiceImpl(
        @Qualifier("restTemplateInternationalMonitoring")
        final RestTemplate restTemplate,
        @Value("${international-monitoring.service.url}")
        final String baseUrl,
        @Value("${international-monitoring.service.login}")
        final String login,
        @Value("${international-monitoring.service.password}")
        final String password,
        @Value("${databus-storage.access-token}")
        final String databusStorageAccessToken
    ) {
        this.restTemplate = restTemplate;
        final String commonUrl = baseUrl + "/api/v1/report/excel";
        this.listCompletedTasksUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/tasks")
            .queryParam("type", "{type}")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.createRtm4601GsdpErrorTotalsTaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/rtm4601-gsdp/error/totals/")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.createRtm4601GsdpErrorShipmentDetailsTaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/rtm4601-gsdp/error/shipment-details/")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.createMainDashboardEDITaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/edi/main-dashboard/")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.createReceptacleListEDITaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/edi/receptacle-list/")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.createDispatchMainTaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/dispatch-main/")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.createDispatchTaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/dispatch/")
            .queryParam("author", "{author}")
            .queryParam("dispatchId", "{dispatchId}")
            .build()
            .toUriString();
        this.createCustomsDeclarationErrorTaskUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("/customs-declaration/error/")
            .queryParam("author", "{author}")
            .build()
            .toUriString();
        this.httpHeaders.setBasicAuth(login, password);
        this.httpHeadersDatabusStorage.setAccept(ACCEPTABLE_MEDIA_TYPES);
        this.httpHeadersDatabusStorage.add(HttpHeaders.AUTHORIZATION, databusStorageAccessToken);
    }

    @Override
    public ExcelReportFileTask createRtm4601GsdpErrorTotalsTask(
        final String author, final DateIntervalRequest request
    ) throws ServiceUnavailableException {
        final URI uri = toUri(createRtm4601GsdpErrorTotalsTaskUrl, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(request, httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format(
                "Error on createRtm4601GsdpErrorTotalsTask(author = {0}, dateFrom = {1}, dateTo = {2})",
                author, request.getDateFrom(), request.getDateTo()
            )
        );
    }

    @Override
    public ExcelReportFileTask createRtm4601GsdpErrorShipmentDetailsTask(
        final String author, final Rtm4601GsdpErrorShipmentDetailsRequest request
    ) throws ServiceUnavailableException {
        final URI uri = toUri(createRtm4601GsdpErrorShipmentDetailsTaskUrl, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(request, httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format(
                "Error on createRtm4601GsdpErrorShipmentDetailsTask(author = {0}, dateFrom = {1}, dateTo = {2},"
                    + " dataSource = {3}, dataFormat = {4}, ipa = {5}, postType = {6})",
                author, request.getDateFrom(), request.getDateTo(),
                request.getDataSource(), request.getDataFormat(), request.getIpa(), request.getPostType()
            )
        );
    }

    @Override
    public ExcelReportFileTask createMainDashboardEDITask(
        final String author, final DateIntervalRequest request
    ) throws ServiceUnavailableException {
        final URI uri = toUri(createMainDashboardEDITaskUrl, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(request, httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format(
                "Error on createMainDashboardEDITask(author = {0}, dateFrom = {1}, dateTo = {2})",
                author, request.getDateFrom(), request.getDateTo()
            )
        );
    }

    @Override
    public ExcelReportFileTask createReceptacleListEDITask(
        final String author, final ReceptacleListEDIRequest request
    ) throws ServiceUnavailableException {
        final URI uri = toUri(createReceptacleListEDITaskUrl, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(request, httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format(
                "Error on createReceptacleListEDITask(author = {0}, dateFrom = {1}, dateTo = {2})",
                author, request.getDateFrom(), request.getDateTo()
            )
        );
    }

    @Override
    public ExcelReportFileTask createDispatchMainTask(
        final String author, final DateIntervalRequest request
    ) throws ServiceUnavailableException {
        final URI uri = toUri(createDispatchMainTaskUrl, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(request, httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format(
                "Error on createDispatchMainTask(author = {0}, dateFrom = {1}, dateTo = {2})",
                author, request.getDateFrom(), request.getDateTo()
            )
        );
    }

    @Override
    public ExcelReportFileTask createDispatchTask(final String author, final String dispatchId) throws ServiceUnavailableException {
        final URI uri = toUri(createDispatchTaskUrl, author, dispatchId);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format("Error on createDispatchTask(author = {0}, dispatchId = {1})", author, dispatchId)
        );
    }

    @Override
    public ExcelReportFileTask createCustomsDeclarationErrorTask(
        final String author, final CustomsDeclarationErrorRequest request
    ) throws ServiceUnavailableException {
        final URI uri = toUri(createCustomsDeclarationErrorTaskUrl, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, PUT, new HttpEntity<>(request, httpHeaders), ExcelReportFileTask.class).getBody(),
            MessageFormat.format(
                "Error on createCustomsDeclarationErrorTask(author = {0}, dateFrom = {1}, dateTo = {2}, includeBusinessValidation: {3})",
                author, request.getDateFrom(), request.getDateTo(), request.getIncludeBusinessValidation()
            )
        );
    }

    @Override
    public List<ExcelReportFileTask> listCompletedTasks(
        final ExcelReportFileType type, final String author
    ) throws ServiceUnavailableException {
        final URI uri = toUri(listCompletedTasksUrl, type, author);
        return executeRequest(
            () -> restTemplate.exchange(uri, GET, new HttpEntity<>(httpHeaders), LIST_TASKS_RESPONSE_TYPE).getBody(),
            MessageFormat.format("Error on get listCompletedTasks(ExcelReportFileType = {0}, author = {1}) from: {2}", type, author, uri)
        );
    }

    @Override
    public ResponseEntity<byte[]> getResultFile(final String downloadUrl) throws ServiceUnavailableException {
        final URI uri = URI.create(downloadUrl);
        try {
            return restTemplate.exchange(uri, GET, new HttpEntity<>(httpHeadersDatabusStorage), byte[].class);
        } catch (final HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new HttpServiceException(ex.getStatusCode().value(), new Error(ErrorCode.FILE_NOT_FOUND, ex.getMessage()));
            }
            log.warn("Request to Databus Storage service failed with StatusCode: {} and message: {}.", ex.getStatusCode(), ex.getMessage());
            throw new ServiceUnavailableException(uri.toString(), ex);
        } catch (final Exception ex) {
            log.warn("Request to Databus Storage service failed with message: {}.", ex.getMessage());
            throw new ServiceUnavailableException(uri.toString(), ex);
        }
    }


    private URI toUri(String uriString, Object... variables) {
        return UriComponentsBuilder.fromUriString(uriString).buildAndExpand(variables).encode().toUri();
    }

    private <T> T executeRequest(Supplier<T> requestSupplier, String errorMessage) throws ServiceUnavailableException {
        try {
            return requestSupplier.get();
        } catch (final HttpServerErrorException | ResourceAccessException ex) {
            log.error(errorMessage, ex);
            throw new ServiceUnavailableException(errorMessage, ex);
        } catch (final RestClientException ex) {
            log.error(errorMessage, ex);
            throw new InternalServerErrorException(errorMessage, ex);
        }
    }
}
