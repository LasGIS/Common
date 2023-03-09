/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.hdps;

import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;

import java.util.List;

/**
 * Corrections response contract.
 *
 * @author KKiryakov
 */
public class CorrectionsResponse {

    private List<HistoryRecordCorrection> corrections;
    private Long nextPageTimestamp;

    /** Default constructor. */
    public CorrectionsResponse() {
    }

    /**
     * Constructor.
     * @param corrections corrections
     * @param nextPageTimestamp timestamp for next page search
     */
    public CorrectionsResponse(List<HistoryRecordCorrection> corrections, Long nextPageTimestamp) {
        this.corrections = corrections;
        this.nextPageTimestamp = nextPageTimestamp;
    }

    public void setCorrections(List<HistoryRecordCorrection> corrections) {
        this.corrections = corrections;
    }

    public void setNextPageTimestamp(Long nextPageTimestamp) {
        this.nextPageTimestamp = nextPageTimestamp;
    }

    public List<HistoryRecordCorrection> getCorrections() {
        return corrections;
    }

    public Long getNextPageTimestamp() {
        return nextPageTimestamp;
    }
}
