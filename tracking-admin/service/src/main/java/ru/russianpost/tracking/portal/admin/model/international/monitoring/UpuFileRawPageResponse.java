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
 * Содержимое файла
 *
 * @author Afanasev E.V.
 * @version 1.0 3/2/2022
 */
@Data
@NoArgsConstructor
public class UpuFileRawPageResponse {

    /**
     * оффсет
     */
    private int offset;

    /**
     * Лимит по количеству файлов на странице. всегда = 1
     */
    private int limit;

    /**
     * Общее количество найденных файлов по имени
     */
    private long totalElements;

    /**
     * Файл
     */
    private UpuFileRawDTO element;

}
