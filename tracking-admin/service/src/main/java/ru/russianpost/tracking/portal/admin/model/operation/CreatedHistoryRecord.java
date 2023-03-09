/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

/**
 * CreatedHistoryRecord
 * @author MKitchenko
 * @version 1.0 04.03.2021
 */
@Getter
@Builder
public class CreatedHistoryRecord {
    private final String shipmentId;
    private final Integer operType;
    private final Integer operAttr;
    private final Long operDate;
    private final Integer zoneOffsetSeconds;
    private final String indexOper;
    private final BigInteger mass;
    private final String softwareVersion;
    private final String dataProviderType;
    private final Integer countryOper;
    private final Long loadDate;
}
