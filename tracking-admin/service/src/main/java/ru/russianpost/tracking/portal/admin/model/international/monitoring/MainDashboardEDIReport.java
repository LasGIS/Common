/*
 * Copyright 2021 Russian Post
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
 * Строки отчета "Main Dashboard EDI Report"
 *
 * @author spurtov
 * @version 1.0 15.04.2021
 */
@Data
@NoArgsConstructor
public class MainDashboardEDIReport {

    /** Строки отчета */
    private List<MainDashboardEDIReportLine> reportLines;

    /** Дата начала периода, за который собирается отчет */
    private String dateFrom;

    /** Дата окончания периода (включительно), за который собирается отчет */
    private String dateTo;
}
