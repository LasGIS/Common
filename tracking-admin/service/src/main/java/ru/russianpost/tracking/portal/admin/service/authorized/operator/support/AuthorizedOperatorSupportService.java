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

import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.BasketData;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.DataToProcess;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.DeferredDataInfo;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.UnprocessedData;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;

import java.util.List;

/**
 * AuthorizedOperatorSupportService interface.
 * @author MKitchenko
 */
public interface AuthorizedOperatorSupportService {

    /**
     * Returns deferred data response from 4601 service by requested type.
     * @param type requested deferred data type
     * @return deferred data response from 4601 service
     * @throws ServiceUnavailableException if service unavailable
     */
    DeferredDataInfo getDeferredData(String type) throws ServiceUnavailableException;

    /**
     * Send deferred data with record id to declaration.
     * @param data  deferred data to process
     * @param login author`s login
     * @return list of unprocessed deferred data
     */
    List<UnprocessedData> toDeclaration(List<DataToProcess> data, String login);

    /**
     * Send deferred data with record id to basket.
     * @param data  deferred data to process
     * @param login author`s login
     * @return list of unprocessed deferred data
     */
    List<UnprocessedData> toBasket(List<DataToProcess> data, String login);

    /**
     * Send basket data with record id to deferred.
     * @param data basket data to process
     * @param login author`s login
     * @return list of unprocessed basket data
     */
    List<UnprocessedData> toDeferred(List<DataToProcess> data, String login);

    /**
     * Returns basket data list with filter by date
     * @param dateFrom date from
     * @param dateTo   date to
     * @return list of basket data
     * @throws ServiceUnavailableException if service unavailable
     */
    List<BasketData> getBasketData(String dateFrom, String dateTo) throws ServiceUnavailableException;
}

