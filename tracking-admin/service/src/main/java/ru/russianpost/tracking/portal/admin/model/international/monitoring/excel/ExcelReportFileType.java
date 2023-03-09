/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.international.monitoring.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Возможные типы Excel файла отчета.
 *
 * @author vlaskin
 * @since <pre>05.07.2022</pre>
 */
@Getter
@AllArgsConstructor
public enum ExcelReportFileType {
    /** Мониторинг качества/Количество ошибок обработки РТМ-4601 и GSDP */
    RTM4601_GSDP_TOTAL("rtm4601-gsdp-total"),

    /** Мониторинг качества/Детализация ошибок обработки РТМ-4601 и GSDP */
    RTM4601_GSDP_DETAILS("rtm4601-gsdp-details"),

    /** Мониторинг количества/Количество отправленных и полученных данных EDI */
    EDI_REPORT_MAIN("edi-report-main"),

    /** Мониторинг количества/Отчет по емкостям */
    EDI_REPORT_RECEPTACLES("edi-report-receptacles"),

    /** Трекинг по депешам/Трекинг по депешам */
    DISPATCH_MAIN("dispatch-main"),

    /** Трекинг по депешам/Отчет для депеши */
    DISPATCH_SHIPMENTS("dispatch-shipments"),

    /** Ошибки при сохранении электронной таможенной декларации (ЭТД) */
    DECLARATION_ERROR("declaration-error");

    private final String path;
}
