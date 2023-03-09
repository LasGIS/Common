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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.exception.TimeoutCachedRequestException;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.CustomsDeclarationErrorReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DispatchDetailReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DispatchMainReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.MainDashboardEDIReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ReceptacleListEDIReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpDetailsByErrorsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorDetailsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorShipmentDetailsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorTotalsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorsByShipmentReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ShipmentListEDIReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.UpuFileRawPageResponse;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.UpuMonitoringReport;

import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

/**
 * Service for read International Monitoring data from tracking-international-monitoring-web service.
 *
 * @author vlaskin
 * @since <pre>25.11.2021</pre>
 */
@Slf4j
@Service
public class InternationalMonitoringServiceImpl implements InternationalMonitoringService {

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final String getMainDashboardEDIReportUrl;
    private final String getReceptacleListEDIReportUrl;
    private final String getShipmentListEDIReportUrl;
    private final String getDispatchMainReportUrl;
    private final String getDispatchDetailReportUrl;
    private final String getRtm4601GsdpErrorTotalsReportUrl;
    private final String getRtm4601GsdpErrorDetailsReportUrl;
    private final String getRtm4601GsdpErrorShipmentDetailsReportUrl;
    private final String getRtm4601GsdpErrorByShipmentIdReportUrl;
    private final String getRtm4601GsdpDetailsByErrorsReportUrl;
    private final String getUpuMonitoringReportUrl;
    private final String getUpuFileBodyUrl;
    private final String getCustomsDeclarationErrorReportUrl;
    private final String getCustomsDeclarationErrorReportWithoutBusinessValidationUrl;

    /**
     * Constructor.
     *
     * @param restTemplate rest template
     * @param baseUrl      base url
     * @param login        user login (for portal admin == 'portal-admin')
     * @param password     user password
     */
    public InternationalMonitoringServiceImpl(
        @Qualifier("restTemplateInternationalMonitoring") final RestTemplate restTemplate,
        @Value("${international-monitoring.service.url}") final String baseUrl,
        @Value("${international-monitoring.service.login}") final String login,
        @Value("${international-monitoring.service.password}") final String password
    ) {
        this.restTemplate = restTemplate;
        this.httpHeaders.setBasicAuth(login, password);
        final String commonUrl = baseUrl + "/api/v1/report/";
        getMainDashboardEDIReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("edi/main-dashboard/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .build()
            .toUriString();
        getReceptacleListEDIReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("edi/receptacle-list/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .queryParam("countryTo", "{countryTo}")
            .queryParam("postalItemType", "{postalItemType}")
            .build()
            .toUriString();
        getShipmentListEDIReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("edi/shipment-list/")
            .queryParam("receptacleId", "{receptacleId}")
            .build()
            .toUriString();
        getDispatchMainReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("dispatch-main/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .build()
            .toUriString();
        getDispatchDetailReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("dispatch/")
            .queryParam("dispatchId", "{dispatchId}")
            .build()
            .toUriString();
        getRtm4601GsdpErrorTotalsReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("rtm4601-gsdp/error/totals/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .build()
            .toUriString();
        getRtm4601GsdpErrorDetailsReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("rtm4601-gsdp/error/details/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .queryParam("dataSource", "{dataSource}")
            .queryParam("dataFormat", "{dataFormat}")
            .build()
            .toUriString();
        getRtm4601GsdpErrorShipmentDetailsReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("rtm4601-gsdp/error/shipment-details/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .queryParam("dataSource", "{dataSource}")
            .queryParam("dataFormat", "{dataFormat}")
            .build()
            .toUriString();
        getRtm4601GsdpErrorByShipmentIdReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("rtm4601-gsdp/error/errors-by-shipment/")
            .queryParam("shipmentId", "{shipmentId}")
            .build()
            .toUriString();
        getRtm4601GsdpDetailsByErrorsReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("rtm4601-gsdp/error/details-by-errors/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .build()
            .toUriString();
        getUpuMonitoringReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("upu-monitoring/{id}")
            .build()
            .toUriString();
        getUpuFileBodyUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/api/v1/upu-file-raw")
            .queryParam("fileName", "{fileName}")
            .queryParam("offset", "{offset}")
            .build()
            .toUriString();
        getCustomsDeclarationErrorReportUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("customs-declaration/error/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .queryParam("includeBusinessValidation", "{includeBusinessValidation}")
            .build()
            .toUriString();
        getCustomsDeclarationErrorReportWithoutBusinessValidationUrl = UriComponentsBuilder.fromHttpUrl(commonUrl)
            .path("customs-declaration/error/")
            .queryParam("dateFrom", "{dateFrom}")
            .queryParam("dateTo", "{dateTo}")
            .build()
            .toUriString();

    }

    @Override
    public MainDashboardEDIReport getMainDashboardEDIReport(final String dateFrom, final String dateTo) throws ServiceUnavailableException {
        final URI uri = toUri(getMainDashboardEDIReportUrl, dateFrom, dateTo);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), MainDashboardEDIReport.class).getBody(),
            MessageFormat.format("Error on getMainDashboardEDIReport(dateFrom = {0}, dateTo = {1}) from: {2}", dateFrom, dateTo, uri)
        );
    }

    @Override
    public ReceptacleListEDIReport getReceptacleListEDIReport(
        final String dateFrom, final String dateTo, final String countryToA2Code, final String postalItemType
    ) throws ServiceUnavailableException {
        final URI uri = toUri(getReceptacleListEDIReportUrl, dateFrom, dateTo, countryToA2Code, postalItemType);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), ReceptacleListEDIReport.class).getBody(),
            MessageFormat.format(
                "Error on getReceptacleListEDIReport(dateFrom = {0}, dateTo = {1}, countryToA2Code = {2}, postalItemType = {3}) from: {4}",
                dateFrom, dateTo, countryToA2Code, postalItemType, uri
            )
        );
    }

    @Override
    public ShipmentListEDIReport getShipmentListEDIReport(final String receptacleIdentifier) throws ServiceUnavailableException {
        final URI uri = toUri(getShipmentListEDIReportUrl, receptacleIdentifier);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), ShipmentListEDIReport.class).getBody(),
            MessageFormat.format("Error on getShipmentListEDIReport(receptacleIdentifier = {0}) from: {1}", receptacleIdentifier, uri)
        );
    }

    @Override
    public DispatchMainReport getDispatchMainReport(final String dateFrom, final String dateTo) throws ServiceUnavailableException {
        final URI uri = toUri(getDispatchMainReportUrl, dateFrom, dateTo);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), DispatchMainReport.class).getBody(),
            MessageFormat.format("Error on getDispatchMainReport(dateFrom = {0}, dateTo = {1}) from: {2}", dateFrom, dateTo, uri)
        );
    }

    @Override
    public DispatchDetailReport getDispatchDetailReport(final String dispatchIdentifier) throws ServiceUnavailableException {
        final URI uri = toUri(getDispatchDetailReportUrl, dispatchIdentifier);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), DispatchDetailReport.class).getBody(),
            MessageFormat.format("Error on getDispatchDetailReport(dispatchIdentifier = {0}) from: {1}", dispatchIdentifier, uri)
        );
    }

    @Override
    public Rtm4601GsdpErrorTotalsReport getRtm4601GsdpErrorTotalsReport(
        final String dateFrom, final String dateTo
    ) throws ServiceUnavailableException {
        final URI uri = toUri(getRtm4601GsdpErrorTotalsReportUrl, dateFrom, dateTo);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), Rtm4601GsdpErrorTotalsReport.class).getBody(),
            MessageFormat.format("Error on getRtm4601GsdpErrorTotalsReport(dateFrom = {0}, dateTo = {1}) from: {2}", dateFrom, dateTo, uri)
        );
    }

    @Override
    public Rtm4601GsdpErrorDetailsReport getRtm4601GsdpErrorDetailsReport(
        final String dateFrom, final String dateTo, final String reportDataSource, final String dataFormat, final String ipa, final String postType
    ) throws ServiceUnavailableException {
        final URI uri = createUriForGsdpDetailsRequest(
            getRtm4601GsdpErrorDetailsReportUrl, dateFrom, dateTo, reportDataSource, dataFormat, ipa, postType
        );
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), Rtm4601GsdpErrorDetailsReport.class).getBody(),
            MessageFormat.format("Error on getRtm4601GsdpErrorDetailsReport("
                    + "dateFrom = {0}, dateTo = {1}, reportDataSource = {2}, dataFormat = {3}, ipa = {4}, postType = {5}) from: {6}",
                dateFrom, dateTo, reportDataSource, dataFormat, ipa, postType, uri
            )
        );
    }

    @Override
    public Rtm4601GsdpErrorShipmentDetailsReport getRtm4601GsdpErrorShipmentDetailsReport(
        final String dateFrom, final String dateTo, final String reportDataSource, final String dataFormat, final String ipa, final String postType
    ) throws ServiceUnavailableException {
        final URI uri = createUriForGsdpDetailsRequest(
            getRtm4601GsdpErrorShipmentDetailsReportUrl, dateFrom, dateTo, reportDataSource, dataFormat, ipa, postType
        );
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), Rtm4601GsdpErrorShipmentDetailsReport.class).getBody(),
            MessageFormat.format("Error on getRtm4601GsdpErrorShipmentDetailsReport("
                    + "dateFrom = {0}, dateTo = {1}, reportDataSource = {2}, dataFormat = {3}, ipa = {4}, postType = {5}) from: {6}",
                dateFrom, dateTo, reportDataSource, dataFormat, ipa, postType, uri
            )
        );
    }

    @Override
    public Rtm4601GsdpErrorsByShipmentReport getRtm4601GsdpErrorsByShipmentIdReport(final String shipmentId) throws ServiceUnavailableException {
        final URI uri = toUri(getRtm4601GsdpErrorByShipmentIdReportUrl, shipmentId);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), Rtm4601GsdpErrorsByShipmentReport.class).getBody(),
            MessageFormat.format("Error on getRtm4601GsdpErrorByShipmentIdReportUrl(shipmentId = {0}) from: {1}", shipmentId, uri)
        );
    }

    @Override
    public Rtm4601GsdpDetailsByErrorsReport getRtm4601GsdpDetailsByErrorsReport(
        final String dateFrom, final String dateTo
    ) throws ServiceUnavailableException {
        final URI uri = toUri(getRtm4601GsdpDetailsByErrorsReportUrl, dateFrom, dateTo);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), Rtm4601GsdpDetailsByErrorsReport.class).getBody(),
            MessageFormat.format("Error on getRtm4601GsdpDetailsByErrorsReport(dateFrom = {0}, dateTo = {1}) from: {2}", dateFrom, dateTo, uri)
        );
    }

    @Override
    public UpuMonitoringReport getUpuMonitoringReport(final String id) throws ServiceUnavailableException {
        final URI uri = toUri(getUpuMonitoringReportUrl, id);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), UpuMonitoringReport.class).getBody(),
            MessageFormat.format("Error on getUpuMonitoringReport(id = {0}) from: {1}", id, uri)
        );
    }

    @Override
    public UpuFileRawPageResponse getUpuFileBody(final String fileName, int offset) throws ServiceUnavailableException {
        final URI uri = toUri(getUpuFileBodyUrl, fileName, offset);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), UpuFileRawPageResponse.class).getBody(),
            MessageFormat.format("Error on getUpuFileBodyUrl(fileName = {0}, offset = {1}) from: {2}", fileName, offset, uri)
        );
    }

    @Override
    public CustomsDeclarationErrorReport getCustomsDeclarationErrorReport(
        final String dateFrom, final String dateTo, final Boolean includeBusinessValidation
    ) throws ServiceUnavailableException {
        final URI uri = Objects.isNull(includeBusinessValidation)
            ? toUri(getCustomsDeclarationErrorReportWithoutBusinessValidationUrl, dateFrom, dateTo)
            : toUri(getCustomsDeclarationErrorReportUrl, dateFrom, dateTo, includeBusinessValidation);
        return executeRequest(
            () -> restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders), CustomsDeclarationErrorReport.class).getBody(),
            MessageFormat.format(
                "Error on getCustomsDeclarationErrorReport(dateFrom = {0}, dateTo = {1}) includeBusinessValidation: {2}",
                dateFrom, dateTo, includeBusinessValidation
            )
        );
    }

    private URI createUriForGsdpDetailsRequest(
        final String reportUrl,
        final String dateFrom,
        final String dateTo,
        final String reportDataSource,
        final String dataFormat,
        final String ipa,
        final String postType
    ) {
        final ArrayList<Object> variables = new ArrayList<>(Arrays.asList(dateFrom, dateTo, reportDataSource, dataFormat));
        final UriComponentsBuilder uriComponentsBuilder = fromUriString(reportUrl);
        if (nonNull(ipa)) {
            variables.add(ipa);
            uriComponentsBuilder.queryParam("ipa", "{ipa}");
        }
        if (nonNull(postType)) {
            variables.add(postType);
            uriComponentsBuilder.queryParam("postType", "{postType}");
        }
        return toUri(uriComponentsBuilder.build().toUriString(), variables.toArray(new Object[0]));
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
        } catch (final HttpClientErrorException ex) {
            final HttpStatus status = ex.getStatusCode();
            if (HttpStatus.LOCKED.equals(status)) {
                log.warn("Cached request is timed out! HttpStatus = '{}'; errorMessage = {}", status, errorMessage);
                throw new TimeoutCachedRequestException(errorMessage, ex);
            }
            log.error("HttpClientErrorException! HttpStatus = '{}'; errorMessage = {}", status, errorMessage);
            throw new InternalServerErrorException(errorMessage, ex);
        } catch (final RestClientException ex) {
            log.error(errorMessage, ex);
            throw new InternalServerErrorException(errorMessage, ex);
        }
    }
}
