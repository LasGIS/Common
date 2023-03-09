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
 * @author vlaskin
 * @since <pre>06.10.2022</pre>
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpErrorsByShipmentReportLine {
    /** ШПИ */
    private String shipmentId;
    /** Содержимое колонки "Формат данных" ("GSDP", "4601") */
    private String dataFormat;
    /** Кол-во ошибочных запросов по данному РПО */
    private Integer errorNumber;
    /** Список полей в запросе с ошибками для данного РПО */
    private List<Rtm4601GsdpErrorsByShipmentReportField> fieldErrors;
}
