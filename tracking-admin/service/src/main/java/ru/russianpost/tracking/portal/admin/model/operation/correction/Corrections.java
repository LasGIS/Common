/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation.correction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.russianpost.tracking.commons.hdps.dto.correction.Correction;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;

import java.math.BigInteger;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Jan 14, 2016)
 */
@Getter
@AllArgsConstructor(access = PRIVATE)
public final class Corrections {
    private final String operIndex;
    private final Long operDate;
    private final Integer mass;
    private final String destIndex;
    private final String rcpn;
    private final String sndr;
    private final BigInteger paymentValue;
    private final String paymentCurrency;
    private final BigInteger valueValue;
    private final String valueCurrency;
    private final Integer mailType;
    private final Integer mailCategory;

    /**
     * Build corrections from common object
     * @param correction correction
     * @return new corrections info
     */
    public static Corrections from(HistoryRecordCorrection correction) {
        return new Corrections(
            correction.getIndexOper(),
            correction.getOperDate(),
            correction.getMass(),
            correction.getIndexTo(),
            correction.getRcpn(),
            correction.getSndr(),
            correction.getPayment(),
            correction.getPaymentCurrency(),
            correction.getValue(),
            correction.getValueCurrency(),
            correction.getMailType(),
            correction.getMailCategory()
        );
    }

    /**
     * Build corrections from common object
     * @param correction correction
     * @return new corrections info
     */
    public static Corrections from(Correction correction) {
        return new Corrections(
            correction.indexOper,
            correction.operDate,
            correction.mass,
            correction.indexTo,
            correction.rcpn,
            correction.sndr,
            correction.payment != null ? correction.payment.getValue() : null,
            correction.payment != null ? correction.payment.getCurrency() : null,
            correction.value != null ? correction.value.getValue() : null,
            correction.value != null ? correction.value.getCurrency() : null,
            correction.mailType,
            correction.mailCategory
        );
    }
}
