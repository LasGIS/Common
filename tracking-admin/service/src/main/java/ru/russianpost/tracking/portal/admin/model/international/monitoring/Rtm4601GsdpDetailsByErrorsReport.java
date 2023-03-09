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
 * Строки отчета
 * <br>"Детализация по ошибкам в данных РТМ-4601 или GSDP"
 * <br>или "RTM4601/GSDP Details By Errors Report"
 * и исходные данные для отчета
 *
 * @author vlaskin
 * @since <pre>24.01.2022</pre>
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpDetailsByErrorsReport {

    /** Строки отчета */
    private List<Rtm4601GsdpDetailsByErrorsReportLine> reportLines;

    /** Дата начала периода, за который собирается отчет */
    private String dateFrom;

    /** Дата окончания периода (включительно), за который собирается отчет */
    private String dateTo;

}
