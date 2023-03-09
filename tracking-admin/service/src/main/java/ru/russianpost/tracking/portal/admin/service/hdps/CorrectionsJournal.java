/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.hdps;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;

import java.util.List;

/**
 * CorrectionsJournal
 * @author MKitchenko
 * @version 1.0 18.05.2020
 */
@Getter
@AllArgsConstructor
public class CorrectionsJournal {
    private List<HistoryRecordCorrection> corrections;
    private Long nextPageTimestamp;
}
