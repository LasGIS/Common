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
 * @version 1.0 3/5/2022
 */
@Data
@NoArgsConstructor
public class UpuFileRawDTO {
    /**
     * Имя файла
     */
    private String fileName;

    /**
     * Содержимое файла
     */
    private String body;

    /**
     * Имя ящика в РФ, в который попал файл
     */
    private String recipientMailbox;

    /**
     * Дата создания файла (UTC)
     */
    private Long createdTime;

    /**
     * Топик, из которого был получен файл
     */
    private String sourceTopic;

    /**
     * партиция
     */
    private Integer topicPartition;

    /**
     * топик оффсет
     */
    private Long topicOffset;

    /**
     * Дата сообщения из кафки
     */
    private Long fileTimestamp;

    /**
     * Направление следования файла (IN/OUT)
     */
    private String way;
}
