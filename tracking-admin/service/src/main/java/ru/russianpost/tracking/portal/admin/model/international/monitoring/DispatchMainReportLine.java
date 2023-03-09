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

import java.util.List;

/**
 * Строка отчета:
 * <br>"Трекинг по депешам, основной"
 * <br> или "Dispatch Main Report"
 *
 * @author Afanasev E.V.
 * @version 1.0 12/24/2021
 */
@Data
@NoArgsConstructor
public class DispatchMainReportLine {

    /** Номер депеши */
    private String dispatchIdentifier;

    /**
     * Страна назначения.
     * <br>Название страны назначения отправления на русском.
     */
    private String countryToRu;

    /** A2 код страны назначения отправления */
    private String countryToA2Code;

    /** Емкости */
    private List<DispatchMainReportReceptacle> receptacleList;
}
