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
 * @since <pre>05.08.2022</pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ReceptacleListEDIRequest {
    /** Дата начала периода (включительно), за который собирается отчет */
    private String dateFrom;

    /** Дата окончания периода (включительно), за который собирается отчет */
    private String dateTo;

    /** A2 код страны назначения отправления, например, RU */
    private String countryToA2Code;

    /** Тип почтового отправления - "EMS", "PARCEL", "LETTER" */
    private String postalItemType;
}
