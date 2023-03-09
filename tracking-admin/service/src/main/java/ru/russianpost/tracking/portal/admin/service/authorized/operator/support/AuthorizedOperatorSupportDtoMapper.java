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

import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.BasketData;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.DeferredOrder;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.DeferredDataInfo;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.ui.BasketDataUi;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.ui.DeferredDataInfoUi;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.ui.DeferredOrderUi;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AuthorizedOperatorSupportDtoMapper
 */
@Service
public class AuthorizedOperatorSupportDtoMapper {

    /**
     * Map deferred data from rtm4601 service to ui.
     * @param info info of {@link DeferredDataInfo}
     * @return deferred data as list
     */
    public DeferredDataInfoUi mapDeferredDataToUi(DeferredDataInfo info) {
        final List<DeferredOrderUi> orders = info.getOrders().stream()
            .map(this::mapToUi)
            .collect(Collectors.toList());

        return new DeferredDataInfoUi(orders, info.getTotal(), info.getTypeCount());
    }

    private DeferredOrderUi mapToUi(DeferredOrder deferredOrder) {
        return DeferredOrderUi.builder()
            .id(deferredOrder.getId())
            .rtm4601OrderDate(deferredOrder.getOrderDateTime().toLocalDate())
            .clientId(deferredOrder.getClientId())
            .apiVersion(deferredOrder.getApiVersion())
            .rtm4601(deferredOrder.getOrder())
            .status(deferredOrder.getStatus())
            .convertedPrice(deferredOrder.getConvertedPrice())
            .count(deferredOrder.getCount())
            .build();
    }

    /**
     * Map basket data from rtm4601 service to ui.
     * @param basketData basket data as list
     * @return basket data as list
     */
    public List<BasketDataUi> mapBasketDataListToUi(List<BasketData> basketData) {
        return basketData.stream()
            .map(this::mapToUi)
            .collect(Collectors.toList());
    }

    private BasketDataUi mapToUi(BasketData basketData) {
        return BasketDataUi.builder()
            .id(basketData.getId())
            .rtm4601OrderDate(basketData.getOrderDateTime().toLocalDate())
            .processDate(basketData.getProcessDateTime().toLocalDate())
            .clientId(basketData.getClientId())
            .apiVersion(basketData.getApiVersion())
            .rtm4601(basketData.getOrder())
            .status(basketData.getStatus())
            .convertedPrice(basketData.getConvertedPrice())
            .build();
    }
}
