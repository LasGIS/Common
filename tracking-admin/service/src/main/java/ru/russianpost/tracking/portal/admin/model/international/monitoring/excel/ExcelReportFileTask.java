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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные для создания отчетов в формате EXCEL
 *
 * @author vlaskin
 * @since <pre>05.07.2022</pre>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelReportFileTask {
    /** (PK) Тип файла отчета */
    private ExcelReportFileType type;
    /** (PK) login пользователя */
    private String author;
    /** (PK) Уникальный идентификатор UUID */
    private String uuid;
    /** объект в JSON формате, содержащий параметры запроса */
    private String paramObject;
    /** Дата создания файла (UTC) */
    private Long createdTime;
    /** Имя файла */
    private String filename;
    /** URL для скачивания */
    private String downloadUrl;
    /**
     * Состояние запроса:
     * CREATED - создан, готов к созданию файла и выгрузке на databus
     * LOADED - успешно загружен на databus (записан downloadUrl)
     * FAILED - построение EXCEL или загрузка на databus не удалась (ошибка в error)
     */
    private ExcelReportFileStatus status;
    /** описание ошибки */
    private String error;

    /**
     * @return report prefix
     */
    public String getPrefix() {
        return type.getPath();
    }
}
