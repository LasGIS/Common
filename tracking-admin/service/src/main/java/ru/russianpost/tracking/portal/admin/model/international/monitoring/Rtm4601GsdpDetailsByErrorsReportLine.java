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

/**
 * Строка отчета:
 * <br>"Детализация по ошибкам в данных РТМ-4601 или GSDP"
 * <br>или "RTM4601/GSDP Details By Errors Report"
 *
 * @author vlaskin
 * @since <pre>24.01.2022</pre>
 */
@Data
@NoArgsConstructor
public class Rtm4601GsdpDetailsByErrorsReportLine {

    /** ШПИ */
    private String shipmentId;

    /** Название поля запроса, в к-м обнаружена ошибка, на английском, например: "parcel.category" */
    private String fieldNameEn;

    /** Название поля запроса, в к-м обнаружена ошибка, на русском, например: "Телефон получателя". Может быть пустым */
    private String fieldNameRu;

//    /** Описание ошибки, например: "The field value must be not less than 0" */
//    private String errorText;

    /** Кол-во ошибочных запросов по данному РПО */
    private Integer errorNumber;

}
