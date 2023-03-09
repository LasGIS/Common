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

import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.rest.response.ElasticsearchTrackingApiStatisticsResponse;
import ru.russianpost.tracking.portal.admin.model.elastic.ui.ElasticsearchValidationErrors4Ui;

/**
 * Service for Request to tracking elastic report generator.
 *
 * @author VLaskin
 * @since <pre>11.03.2020</pre>
 */
public interface ElasticSearchStatisticService {

    /**
     * Request to tracking elastic report generator.
     * Get number of operation processed by IS SORPO by date interval
     * <pre>
     * GET /api/v1/stat/is-sorpo/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return Get number of operation processed by IS SORPO
     */
    ElasticsearchStatisticsResponse getSorpoStatistics(String dateFrom, String dateTo);

    /**
     * Request to tracking elastic report generator for get validation errors statistic.
     * Get number of Main Listener validation errors by date interval
     * <pre>
     * GET /api/v1/stat/validation-errors/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return validation errors statistic
     */
    ElasticsearchValidationErrors4Ui getValidationErrorsStatistics(String dateFrom, String dateTo);

    /**
     * Request to tracking elastic report generator.
     * Get number of orders from marketplaces by date interval
     * <pre>
     * GET /api/v1/stat/marketplaces/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return number of orders from marketplaces
     */
    ElasticsearchStatisticsResponse getMarketplacesStatistics(String dateFrom, String dateTo);

    /**
     * Request to tracking elastic report generator.
     * Get number of requests to tracking API and count of unique clients by date interval
     * <pre>
     * GET /api/v1/stat/rtm34/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return number of requests to tracking API and count of unique clients
     */
    ElasticsearchTrackingApiStatisticsResponse getRtm34Statistics(String dateFrom, String dateTo);

    /**
     * Request to tracking elastic report generator.
     * Get number of requests to tracking API, count of unique clients and count of barcodes per ticket by date interval
     * <pre>
     * GET /api/v1/stat/fc/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return number of requests to tracking API, count of unique clients and count of barcodes per ticket
     */
    ElasticsearchTrackingApiStatisticsResponse getFcStatistics(String dateFrom, String dateTo);

    /**
     * Request to tracking elastic report generator.
     * Get count of barcodes allocated by date interval
     * <pre>
     * GET /api/v1/stat/barcodes/{dateFrom}/{dateTo}
     * </pre>
     *
     * @param dateFrom data from
     * @param dateTo   data to
     * @return Barcodes Allocated Statistics
     */
    ElasticsearchStatisticsResponse getBarcodesAllocatedStatistics(String dateFrom, String dateTo);
}
