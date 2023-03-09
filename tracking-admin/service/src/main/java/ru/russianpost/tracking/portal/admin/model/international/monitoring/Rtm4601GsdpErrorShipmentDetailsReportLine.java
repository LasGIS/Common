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
 * Строка отчета:
 * <br>"Отчёт по РПО, в которых были выявлены ошибки в данных РТМ-4601 или GSDP"
 * <br>или "RTM4601/GSDP Error Shipment Details Report"
 *
 * @author spurtov
 * @version 1.0 21.05.2021.
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpErrorShipmentDetailsReportLine {

    /** Содержимое колонки "Источник данных" ("AE" - AliExpress, "Joom", "iHerb") */
    private String dataSource;

    /** Содержимое колонки "Формат данных" ("GSDP", "4601") */
    private String dataFormat;

    /** ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN */
    private String ipa;

    /** Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD */
    private String postType;

    /** ШПИ */
    private String shipmentId;

    /** Кол-во ошибочных запросов по данному РПО */
    private Integer errorNumber;

    /** Список полей в запросе с ошибками для данного РПО */
    private List<Rtm4601GsdpErrorShipmentDetailsReportField> fieldErrors;
}
