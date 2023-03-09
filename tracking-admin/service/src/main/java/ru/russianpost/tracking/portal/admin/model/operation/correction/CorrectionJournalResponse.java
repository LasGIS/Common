/*
 * Copyright 2017 Russian Post
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

import java.util.List;

/**
 * CorrectionJournalResponse.
 *
 * @author KKiryakov
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CorrectionJournalResponse {
    private List<CorrectionJournalEntry> corrections;
    private Long nextPageTimestamp;
}
