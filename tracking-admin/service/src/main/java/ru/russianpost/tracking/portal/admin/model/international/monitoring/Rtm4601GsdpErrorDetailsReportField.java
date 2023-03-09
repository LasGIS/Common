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
 * Данные по количеству ошибок в поле для отчета:
 * <br>"Детализация ошибок обработки РТМ-4601 и GSDP"
 * <br>или "RTM4601/GSDP Error Details Report"
 *
 * @author spurtov
 * @version 1.0 21.05.2021.
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpErrorDetailsReportField {

    /** ID поля запроса, в к-м обнаружена ошибка */
    private Integer fieldId;

    /** Название поля запроса, в к-м обнаружена ошибка, на английском, например: "parcel.category" */
    private String fieldNameEn;

    /** Название поля запроса, в к-м обнаружена ошибка, на русском, например: "Телефон получателя". Может быть пустым */
    private String fieldNameRu;

    /** Количество запросов с ошибками в данном поле */
    private Integer badNumber;

    /** Порядковый номер поля в отчете */
    private Integer orderNumber;

}
