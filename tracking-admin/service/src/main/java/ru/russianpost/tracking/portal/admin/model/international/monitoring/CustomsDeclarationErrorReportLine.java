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
 * <br>"Ошибки в декларациях"
 * <br> или "Customs Declaration Error Report"
 *
 * @author Afanasev E.V.
 * @version 1.0 6/23/2022
 */
@Data
@NoArgsConstructor
public class CustomsDeclarationErrorReportLine {
    /** ШПИ */
    private String shipmentId;

    /** Система-источник */
    private String sourceSystemName;

    /** Описание ошибки */
    private String errorMessage;

    /** Значение (ошибки) */
    private String value;

    /** Тип ошибки */
    private String code;

    /** Поле, в котором найдена ошибка */
    private String fieldName;

    /** Общее количество ошибок */
    private Integer errorNumber;

    /** Ошибка бизнеса или нет */
    private Boolean businessValidation;
}
