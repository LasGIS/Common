/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.international.monitoring;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Строки отчета "Upu Monitoring Report" и исходные данные для отчета
 *
 * @author spurtov
 * @version 1.0 23.04.2021
 */
@Data
@NoArgsConstructor
public class UpuMonitoringReport {
    /** Номер емкости или ШПИ */
    private String id;
    private int createdItmattNumber;
    private int receivedItmattNumber;
    private int createdCarditNumber;
    private int receivedResditNumber;
    private int createdPredesNumber;
    private int receivedPredesNumber;
    private int createdPreconNumber;
    private int receivedPreconNumber;
    private int createdResconNumber;
    private int receivedResconNumber;
    private int createdResdesNumber;
    private int receivedResdesNumber;
    private int createdEmsevtNumber;
    private int receivedEmsevtNumber;
    private int createdUnknownNumber;
    private int receivedUnknownNumber;

    /** Строки отчета */
    private List<UpuMonitoringReportLine> reportLines;

}
