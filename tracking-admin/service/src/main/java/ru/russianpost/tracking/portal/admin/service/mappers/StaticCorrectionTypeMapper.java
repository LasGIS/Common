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

import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionType;

import java.util.EnumMap;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Jan 22, 2016)
 */
@Service
public class StaticCorrectionTypeMapper implements CorrectionTypeMapper {

    private final EnumMap<HistoryRecordCorrectionType, CorrectionType> correctionTypeMapping
        = new EnumMap<>(HistoryRecordCorrectionType.class);

    private final EnumMap<ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType, CorrectionType> dtoCorrectionTypeMapping
        = new EnumMap<>(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType.class);

    private final EnumMap<CorrectionType, HistoryRecordCorrectionType> historyCorrectionTypeMapping
        = new EnumMap<>(CorrectionType.class);

    /**
     * Default
     */
    public StaticCorrectionTypeMapper() {
        put(HistoryRecordCorrectionType.UPDATE, CorrectionType.CHANGE);
        put(HistoryRecordCorrectionType.DELETE, CorrectionType.DELETE);
        put(HistoryRecordCorrectionType.RESTORE, CorrectionType.RESTORE);
        put(HistoryRecordCorrectionType.CREATE, CorrectionType.CREATE);
        dtoCorrectionTypeMapping.put(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType.UPDATE, CorrectionType.CHANGE);
        dtoCorrectionTypeMapping.put(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType.DELETE, CorrectionType.DELETE);
        dtoCorrectionTypeMapping.put(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType.RESTORE, CorrectionType.RESTORE);
        dtoCorrectionTypeMapping.put(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType.CREATE, CorrectionType.CREATE);
    }

    private void put(HistoryRecordCorrectionType h, CorrectionType c) {
        correctionTypeMapping.put(h, c);
        historyCorrectionTypeMapping.put(c, h);
    }

    @Override
    public CorrectionType by(HistoryRecordCorrectionType historyRecordCorrectionType) {
        return correctionTypeMapping.getOrDefault(historyRecordCorrectionType, CorrectionType.CHANGE);
    }

    @Override
    public CorrectionType by(ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType correctionType) {
        return dtoCorrectionTypeMapping.getOrDefault(correctionType, CorrectionType.CHANGE);
    }

    @Override
    public HistoryRecordCorrectionType by(CorrectionType correctionType) {
        return historyCorrectionTypeMapping.getOrDefault(correctionType, HistoryRecordCorrectionType.UPDATE);
    }
}
