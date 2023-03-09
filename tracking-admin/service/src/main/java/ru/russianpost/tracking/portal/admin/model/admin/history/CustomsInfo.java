/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.admin.history;

import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

/**
 * CustomsInfo.
 * @author MKitchenko
 */
@Value
@Builder
public final class CustomsInfo {

    // Начисленный таможенный платёж в копейках
    private BigInteger customsPayment;
    // Статус таможенного оформления в РФ
    private Integer customsStatus;
    // Сумма задолженности по оплате таможенной пошлины в копейках
    private BigInteger amountOfUnpaid;
}
