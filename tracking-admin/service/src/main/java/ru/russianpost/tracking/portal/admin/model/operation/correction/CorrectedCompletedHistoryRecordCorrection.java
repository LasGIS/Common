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
import ru.russianpost.tracking.portal.admin.model.operation.CompletedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Jan 14, 2016)
 */
@Getter
@AllArgsConstructor
public class CorrectedCompletedHistoryRecordCorrection {
    private final CorrectionType type;
    private final String initiator;
    private final AdminUser author;
    private final Long date;
    private final String comment;
    private final Corrections data;
    private final CompletedHistoryRecord afterSet;
}
