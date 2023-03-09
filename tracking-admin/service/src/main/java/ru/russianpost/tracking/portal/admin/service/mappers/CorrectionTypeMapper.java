/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.mappers;

import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionType;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Jan 22, 2016)
 */
public interface CorrectionTypeMapper {

    /**
     * Map: HistoryRecordCorrectionType -> CorrectionType
     *
     * @param historyRecordCorrectionType historyRecordCorrectionType
     * @return CorrectionType
     */
    CorrectionType by(HistoryRecordCorrectionType historyRecordCorrectionType);

    /**
     * Map: ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType -> CorrectionType
     *
     * @param historyRecordCorrectionType historyRecordCorrectionType
     * @return CorrectionType
     */
    CorrectionType by(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType historyRecordCorrectionType);

    /**
     * Map: CorrectionType -> HistoryRecordCorrectionType
     *
     * @param correctionType correctionType
     * @return HistoryRecordCorrectionType
     */
    HistoryRecordCorrectionType by(CorrectionType correctionType);
}
