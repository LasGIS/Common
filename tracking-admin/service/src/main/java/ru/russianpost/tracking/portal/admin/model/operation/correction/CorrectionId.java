/*
 * Copyright 2018 Russian Post
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
import lombok.NoArgsConstructor;
import ru.russianpost.tracking.portal.admin.model.operation.HistoryRecordId;

/**
 * Correction ID.
 * @author KKiryakov
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorrectionId {
    private CorrectionType type;
    private Long correctionDate;
    private String originShipmentId;
    private Long originOperDate;
    private Integer originOperType;
    private Integer originOperAttr;
    private String originIndexOper;

    /**
     * Constructor.
     * @param type           correction type
     * @param correctionDate correction date
     * @param originId       origin record id
     */
    public CorrectionId(CorrectionType type, Long correctionDate, HistoryRecordId originId) {
        this(
            type,
            correctionDate,
            originId.getShipmentId(),
            originId.getOperDate(),
            originId.getOperType(),
            originId.getOperAttr(),
            originId.getIndexOper()
        );
    }

    public String getType() {
        return type.toString();
    }
}
