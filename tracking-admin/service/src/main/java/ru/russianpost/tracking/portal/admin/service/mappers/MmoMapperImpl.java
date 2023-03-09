/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.mappers;

import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.hdps.dto.MoneyInfo;
import ru.russianpost.tracking.commons.hdps.dto.multiplace.v1.MultiplaceRpoResponse;
import ru.russianpost.tracking.commons.hdps.dto.multiplace.v1.RpoStatus;
import ru.russianpost.tracking.portal.admin.model.mmo.MmoSearchResult;
import ru.russianpost.tracking.portal.admin.model.mmo.RpoInfo;
import ru.russianpost.tracking.portal.admin.model.operation.Money;
import ru.russianpost.tracking.portal.admin.utils.WeightResolver;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MmoMapperImpl
 * @author MKitchenko
 * @version 1.0 27.11.2020
 */
@Service
public class MmoMapperImpl implements MmoMapper {

    @Override
    public MmoSearchResult toMmoSearchResult(MultiplaceRpoResponse resp) {
        return new MmoSearchResult()
            .setMultiplaceBarcode(resp.getMultiplaceBarcode())
            .setMoney(toMoney(resp.getMoney()))
            .setRpoNum(resp.getRpoNum())
            .setWeight(WeightResolver.convertToGrams(resp.getGroupWeight()).orElse(null))
            .setRpoInfoList(toRpoInfoList(resp.getStatuses()));
    }

    private List<RpoInfo> toRpoInfoList(List<RpoStatus> rpoStatusList) {
        return rpoStatusList.stream()
            .map(item -> new RpoInfo()
                .setShipmentId(item.getShipmentId())
                .setOperDate(item.getOperDate())
                .setZoneOffsetSeconds(item.getZoneOffsetSeconds())
                .setOperType(item.getOperType())
                .setOperAttr(item.getOperAttr())
                .setIndexOper(item.getIndexOper())
                .setWeight(WeightResolver.convertToGrams(item.getWeight()).orElse(null))
            )
            .collect(Collectors.toList());
    }

    private Money toMoney(List<MoneyInfo> moneyList) {
        return moneyList.stream()
            .findFirst()
            .map(m -> new Money(BigInteger.valueOf(m.getValue()), m.getMeasurement()))
            .orElse(null);
    }
}
