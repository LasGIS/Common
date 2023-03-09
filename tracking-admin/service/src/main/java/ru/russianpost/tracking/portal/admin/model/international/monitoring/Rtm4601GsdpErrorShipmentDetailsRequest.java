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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект, содержащий параметры запроса для ReceptacleListEDI отчета.
 *
 * @author VLaskin
 * @since <pre>09.08.2022</pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Rtm4601GsdpErrorShipmentDetailsRequest {
    /** Дата начала периода (включительно), за который собирается отчет */
    private String dateFrom;

    /** Дата окончания периода (включительно), за который собирается отчет */
    private String dateTo;

    /** Содержимое колонки "Источник данных" ("AE" - AliExpress, "Joom", "iHerb") */
    private String dataSource;

    /** Содержимое колонки "Формат данных" ("GSDP", "4601") */
    private String dataFormat;

    /** ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN */
    private String ipa;

    /** Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD */
    private String postType;
}
