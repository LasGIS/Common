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
 * <br>"Отслеживание сообщений по обмену данными с ВПС"
 * <br>или "Upu Monitoring Report".
 *
 * @author spurtov
 * @version 1.0 23.04.2021.
 */
@Data
@NoArgsConstructor
public class UpuMonitoringReportLine {
    private String fileName;
    private String fileType;
    private String systemName;
    private Long creationTimestamp;
    private Long kafkaTimestamp;
    private Long insertTimestamp;
}
