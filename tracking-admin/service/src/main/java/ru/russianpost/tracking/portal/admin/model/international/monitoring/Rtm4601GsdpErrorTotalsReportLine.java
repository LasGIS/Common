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

/**
 * Строка отчета:
 * <br>"Кол-во ошибок обработки РТМ-4601 и GSDP"
 * <br>или "RTM4601/GSDP Error Totals Report"
 *
 * @author spurtov
 * @version 1.0 19.05.2021.
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpErrorTotalsReportLine {

    /** Содержимое колонки "Источник данных" ("AE" - AliExpress, "Joom", "iHerb") */
    private String dataSource;

    /** Содержимое колонки "Формат данных" ("GSDP", "4601") */
    private String dataFormat;

    /** ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN */
    private String ipa;

    /** Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD */
    private String postType;

    /** Количество запросов без ошибок */
    private Integer goodNumber;

    /** Количество запросов с ошибками */
    private Integer badNumber;
}
