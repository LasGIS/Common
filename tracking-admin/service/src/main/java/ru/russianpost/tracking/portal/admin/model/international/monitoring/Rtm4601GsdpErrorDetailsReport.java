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
 * Строки отчета
 * <br>"Детализация ошибок обработки РТМ-4601 и GSDP"
 * и исходные данные для отчета
 *
 * @author spurtov
 * @version 1.0 19.05.2021
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpErrorDetailsReport {

    /** Строки отчета */
    private List<Rtm4601GsdpErrorDetailsReportLine> reportLines;

    /** Содержимое колонки "Формат данных" ("GSDP", "4601") */
    private String dataFormat;

    /** Содержимое колонки "Источник данных" ("AE" - AliExpress, "Joom", "iHerb") */
    private String dataSource;

    /** ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN */
    private String ipa;

    /** Дата начала периода, за который собирается отчет */
    private String dateFrom;

    /** Дата окончания периода (включительно), за который собирается отчет */
    private String dateTo;

    /** Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD */
    private String postType;

}
