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

import ru.russianpost.tracking.portal.admin.model.operation.CompletedHistoryRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Jan 14, 2016)
 */
public class CorrectedCompletedHistoryRecord extends CompletedHistoryRecord {

    private final List<CorrectedCompletedHistoryRecordCorrection> changes;

    /**
     * Constructs history record with changes
     * @param completedHistoryRecord record
     * @param changes                changes
     */
    public CorrectedCompletedHistoryRecord(
        CompletedHistoryRecord completedHistoryRecord,
        List<CorrectedCompletedHistoryRecordCorrection> changes
    ) {
        super(completedHistoryRecord);
        this.changes = new ArrayList<>(changes);
    }

    public List<CorrectedCompletedHistoryRecordCorrection> getChanges() {
        return Collections.unmodifiableList(changes);
    }
}
