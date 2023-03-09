/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.elastic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.MainListenerValidationErrorInfoByDate;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchTrackingApiStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchValidationErrorsStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.ui.ElasticsearchValidationErrors4Ui;
import ru.russianpost.tracking.portal.admin.model.elastic.ui.ValidationErrorInfo4Ui;
import ru.russianpost.tracking.portal.admin.model.elastic.ui.ValidationErrorInfo4UiByDate;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for Request to tracking elastic report generator implementation.
 *
 * @author VLaskin
 * @since <pre>11.03.2020</pre>
 */
@Slf4j
@Service
public class ElasticSearchStatisticServiceImpl implements ElasticSearchStatisticService {

    private final String sorpoStatisticsUrl;
    private final String validationErrorsStatisticsUrl;
    private final String marketplacesStatisticsUrl;
    private final String rtm34StatisticsUrl;
    private final String fcStatisticsUrl;
    private final String barcodesAllocatedStatisticsUrl;

    private final HttpEntity<HttpHeaders> authorizationHttpEntity;
    private final RestTemplate restTemplate;
    private final Dictionary dictionary;

    /**
     * Constructor.
     *
     * @param restTemplate             restTemplate
     * @param dictionary               dictionary
     * @param elasticStatisticsRootUrl elastic report Root Url
     * @param commonPath               common part of reports
     * @param elasticLogin             login
     * @param elasticPassword          password
     */
    public ElasticSearchStatisticServiceImpl(
        @Qualifier("restTemplate") RestTemplate restTemplate,
        Dictionary dictionary,
        @Value("${ru.russianpost.elastic.statistics.root.url}") String elasticStatisticsRootUrl,
        @Value("${ru.russianpost.elastic.statistics.common.part}") String commonPath,
        @Value("${ru.russianpost.elastic.login}") String elasticLogin,
        @Value("${ru.russianpost.elastic.password}") String elasticPassword
    ) {
        this.restTemplate = restTemplate;
        this.dictionary = dictionary;
        this.sorpoStatisticsUrl = UriComponentsBuilder.fromUriString(elasticStatisticsRootUrl).path(commonPath)
            .path("/is-sorpo").path("/{dateFrom}").path("/{dateTo}").build().toUriString();
        this.validationErrorsStatisticsUrl = UriComponentsBuilder.fromUriString(elasticStatisticsRootUrl).path(commonPath)
            .path("/validation-errors").path("/{dateFrom}").path("/{dateTo}").build().toUriString();
        this.marketplacesStatisticsUrl = UriComponentsBuilder.fromUriString(elasticStatisticsRootUrl).path(commonPath)
            .path("/marketplaces").path("/{dateFrom}").path("/{dateTo}").build().toUriString();
        this.rtm34StatisticsUrl = UriComponentsBuilder.fromUriString(elasticStatisticsRootUrl).path(commonPath)
            .path("/rtm34").path("/{dateFrom}").path("/{dateTo}").build().toUriString();
        this.fcStatisticsUrl = UriComponentsBuilder.fromUriString(elasticStatisticsRootUrl).path(commonPath)
            .path("/fc").path("/{dateFrom}").path("/{dateTo}").build().toUriString();
        this.barcodesAllocatedStatisticsUrl = UriComponentsBuilder.fromUriString(elasticStatisticsRootUrl).path(commonPath)
            .path("/barcodes").path("/{dateFrom}").path("/{dateTo}").build().toUriString();
        this.authorizationHttpEntity = getAuthorizationHttpEntity(getAuthorization(elasticLogin, elasticPassword));
    }

    private String getAuthorization(final String elasticLogin, final String elasticPassword) {
        return MessageFormat.format("Basic {0}", Base64Utils.encodeToString(
            (elasticLogin + ":" + elasticPassword).getBytes(StandardCharsets.UTF_8)
        ));
    }

    private HttpEntity<HttpHeaders> getAuthorizationHttpEntity(final String authorization) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorization);
        return new HttpEntity<>(headers);
    }

    @Override
    public ElasticsearchStatisticsResponse getSorpoStatistics(final String dateFrom, final String dateTo) {
        return restTemplate.exchange(
            sorpoStatisticsUrl, HttpMethod.GET, authorizationHttpEntity,
            ElasticsearchStatisticsResponse.class, dateFrom, dateTo
        ).getBody();
    }

    @Override
    public ElasticsearchValidationErrors4Ui getValidationErrorsStatistics(final String dateFrom, final String dateTo) {
        return validationErrors4UiFunction(
            restTemplate.exchange(
                validationErrorsStatisticsUrl, HttpMethod.GET, authorizationHttpEntity,
                ElasticsearchValidationErrorsStatisticsResponse.class, dateFrom, dateTo
            ).getBody()
        );
    }

    @Override
    public ElasticsearchStatisticsResponse getMarketplacesStatistics(final String dateFrom, final String dateTo) {
        return restTemplate.exchange(
            marketplacesStatisticsUrl, HttpMethod.GET, authorizationHttpEntity,
            ElasticsearchStatisticsResponse.class, dateFrom, dateTo
        ).getBody();
    }

    @Override
    public ElasticsearchTrackingApiStatisticsResponse getRtm34Statistics(final String dateFrom, final String dateTo) {
        return restTemplate.exchange(
            rtm34StatisticsUrl, HttpMethod.GET, authorizationHttpEntity,
            ElasticsearchTrackingApiStatisticsResponse.class, dateFrom, dateTo
        ).getBody();
    }

    @Override
    public ElasticsearchTrackingApiStatisticsResponse getFcStatistics(final String dateFrom, final String dateTo) {
        return restTemplate.exchange(
            fcStatisticsUrl, HttpMethod.GET, authorizationHttpEntity,
            ElasticsearchTrackingApiStatisticsResponse.class, dateFrom, dateTo
        ).getBody();
    }

    @Override
    public ElasticsearchStatisticsResponse getBarcodesAllocatedStatistics(final String dateFrom, final String dateTo) {
        return restTemplate.exchange(
            barcodesAllocatedStatisticsUrl, HttpMethod.GET, authorizationHttpEntity,
            ElasticsearchStatisticsResponse.class, dateFrom, dateTo
        ).getBody();
    }

    /**
     * Convert {@link MainListenerValidationErrorInfoByDate} to {@link ValidationErrorInfo4UiByDate}
     *
     * @param infoByDate MainListenerValidationErrorInfoByDate
     * @return ValidationErrorInfo4UiByDate
     */
    public ValidationErrorInfo4UiByDate errorInfo4UiByDateFunction(final MainListenerValidationErrorInfoByDate infoByDate) {
        final List<ValidationErrorInfo4Ui> errorInfoList = new ArrayList<>();
        infoByDate.getStatisticsInfo().forEach((softwareVersion, value) -> {
            final String softwareName = dictionary.getSoftwareNameByVersion(softwareVersion);
            errorInfoList.addAll(
                value.stream().map(info ->
                    ValidationErrorInfo4Ui.of(
                        softwareName,
                        softwareVersion,
                        info.getErrorType(),
                        info.getDataProvider(),
                        info.getValidationErrorCount()
                    )
                ).collect(Collectors.toList())
            );
        });
        return ValidationErrorInfo4UiByDate.of(infoByDate.getDate(), errorInfoList);
    }

    /**
     * Convert {@link ElasticsearchValidationErrorsStatisticsResponse} to {@link ElasticsearchValidationErrors4Ui}
     *
     * @param response ElasticsearchValidationErrorsStatisticsResponse
     * @return ElasticsearchValidationErrors4Ui
     */
    public final ElasticsearchValidationErrors4Ui validationErrors4UiFunction(final ElasticsearchValidationErrorsStatisticsResponse response) {
        return ElasticsearchValidationErrors4Ui.of(
            response.getDateFrom(),
            response.getDateTo(),
            response.getStatisticsData().stream().map(this::errorInfo4UiByDateFunction).collect(Collectors.toList())
        );
    }
}
