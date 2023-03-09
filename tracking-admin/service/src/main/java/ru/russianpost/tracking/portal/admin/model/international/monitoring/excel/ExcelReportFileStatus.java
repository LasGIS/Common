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

/**
 * Type of EXCEL report file Statuses
 *
 * @author vlaskin
 * @since <pre>05.07.2022</pre>
 */
public enum ExcelReportFileStatus {
    /** создан, готов к созданию файла и выгрузке на databus */
    CREATED,
    /** Взят в работу для создания файла и выгрузке на databus */
    LOCKING,
    /** успешно загружен на databus (записан downloadUrl) */
    LOADED,
    /** построение EXCEL или загрузка на databus не удалась (ошибка в error) */
    FAILED,
    /** время построения EXCEL вышло за отведённые рамки */
    TIMEOUT
}
