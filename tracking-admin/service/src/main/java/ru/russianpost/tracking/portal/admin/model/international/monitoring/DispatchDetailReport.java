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
import ru.russianpost.tracking.portal.admin.model.errors.ServiceError;

import java.util.List;

/**
 * Строки отчета "Трекинг по депешам" - "Dispatch Report" и исходные данные для отчета
 *
 * @author spurtov
 * @version 1.0 23.04.2021
 */
@Data
@NoArgsConstructor
public class DispatchDetailReport {
    /** Строки отчета */
    private List<DispatchDetailReportLine> reportLines;
    /** Номер депеши */
    private String dispatchIdentifier;
    /** Описание ошибки */
    private ServiceError error;
}
