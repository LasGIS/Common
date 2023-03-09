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
 * Объект, содержащий параметры запроса для CustomsDeclarationError отчета.
 *
 * @author vlaskin
 * @since <pre>05.07.2022</pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomsDeclarationErrorRequest {
    /** Дата начала периода (включительно), за который собирается отчет */
    private String dateFrom;

    /** Дата окончания периода (включительно), за который собирается отчет */
    private String dateTo;

    /** Включить в отчет ошибки, не препятствующие сохранению эТД в БД */
    private Boolean includeBusinessValidation;
}
