/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchTrackingApiStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.ui.ElasticsearchValidationErrors4Ui;
import ru.russianpost.tracking.portal.admin.service.elastic.ElasticSearchStatisticService;

/**
 * Система сбора статистики по сервисам ИС СОРПО.
 * Создать страницу просмотра статистики данных сервиса Tracking Elastic Report Generator
 *
 * @author VLaskin
 * @since <pre>11.03.2020</pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elastic/stat")
public class ElasticSearchStatisticController extends BaseController {

    private final ElasticSearchStatisticService elasticSearchStatisticService;

    /**
     * <pre>
     * GET /api/v1/elastic/stat/is-sorpo/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return Get number of operation processed by IS SORPO
     */
    @GetMapping(value = "/is-sorpo/{dateFrom}/{dateTo:.+}")
    public ElasticsearchStatisticsResponse getSorpoStatistics(
        @PathVariable("dateFrom") final String dateFrom,
        @PathVariable("dateTo") final String dateTo
    ) {
        return elasticSearchStatisticService.getSorpoStatistics(dateFrom, dateTo);
    }

    /**
     * <pre>
     * GET /api/v1/stat/validation-errors/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return validation errors statistic
     */
    @GetMapping(value = "/validation-errors/{dateFrom}/{dateTo:.+}")
    public ElasticsearchValidationErrors4Ui getValidationErrorsStatistics(
        @PathVariable("dateFrom") final String dateFrom,
        @PathVariable("dateTo") final String dateTo
    ) {
        return elasticSearchStatisticService.getValidationErrorsStatistics(dateFrom, dateTo);
    }

    /**
     * <pre>
     * GET /api/v1/stat/marketplaces/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return number of orders from marketplaces
     */
    @GetMapping(value = "/marketplaces/{dateFrom}/{dateTo:.+}")
    public ElasticsearchStatisticsResponse getMarketplacesStatistics(
        @PathVariable("dateFrom") final String dateFrom,
        @PathVariable("dateTo") final String dateTo
    ) {
        return elasticSearchStatisticService.getMarketplacesStatistics(dateFrom, dateTo);
    }

    /**
     * <pre>
     * GET /api/v1/stat/rtm34/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return number of requests to tracking API and count of unique clients
     */
    @GetMapping(value = "/rtm34/{dateFrom}/{dateTo:.+}")
    public ElasticsearchTrackingApiStatisticsResponse getRtm34Statistics(
        @PathVariable("dateFrom") final String dateFrom,
        @PathVariable("dateTo") final String dateTo
    ) {
        return elasticSearchStatisticService.getRtm34Statistics(dateFrom, dateTo);
    }

    /**
     * <pre>
     * GET /api/v1/stat/fc/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return number of requests to tracking API, count of unique clients and count of barcodes per ticket
     */
    @GetMapping(value = "/fc/{dateFrom}/{dateTo:.+}")
    public ElasticsearchTrackingApiStatisticsResponse getFcStatistics(
        @PathVariable("dateFrom") final String dateFrom,
        @PathVariable("dateTo") final String dateTo
    ) {
        return elasticSearchStatisticService.getFcStatistics(dateFrom, dateTo);
    }

    /**
     * <pre>
     * GET /api/v1/stat/barcodes/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return Barcodes Allocated Statistics
     */
    @GetMapping(value = "/barcodes/{dateFrom}/{dateTo:.+}")
    public ElasticsearchStatisticsResponse getBarcodesAllocatedStatistics(
        @PathVariable("dateFrom") final String dateFrom,
        @PathVariable("dateTo") final String dateTo
    ) {
        return elasticSearchStatisticService.getBarcodesAllocatedStatistics(dateFrom, dateTo);
    }
}
