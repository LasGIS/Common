/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.operation.correction;

import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionSourceSystem;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.hdps.CorrectionsJournal;

import java.util.List;

/**
 * @author MKitchenko
 * @version 2.0 (May 18, 2020)
 */
public interface OperationCorrectionHistoryService {
    /**
     * Returns the corrections within specified interval (inclusive). Loads day by day until it gets
     * @param from            starting from timestamp
     * @param to              ending with timestamp
     * @param correctionTypes correction types to include
     * @param sourceSystem    source system
     * @return list of record corrections
     * @throws ServiceUnavailableException correction history provider is unavailable
     */
    CorrectionsJournal getCorrections(
        long from, long to, List<HistoryRecordCorrectionType> correctionTypes, HistoryRecordCorrectionSourceSystem sourceSystem
    ) throws ServiceUnavailableException;
}
