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

/**
 * Строка отчета:
 * <br>"Мониторинг количества отправленных и полученных данных EDI. Drill-down (1) по ёмкостям"
 * <br>или "Receptacle List EDI Report".
 * <br>Это отчет со списком емкостей для заданной страны и типа почтового отправления за период.
 * <br>EDI - Electronic Data Interchange (Электронный обмен данными).
 * <br>Note. В comment-е к каждому свойству класса - название колонки отчета и примечание к ней.
 *
 * @author spurtov
 * @version 1.0 23.04.2021.
 */
@Data
@NoArgsConstructor
public class ReceptacleListEDIReportLine {

    /** Название страны назначения отправления на русском */
    private String countryToRu;

    /** A2 код страны назначения отправления */
    private String countryToA2Code;


    /** Номер емкости */
    private String receptacleIdentifier;

    /**
     * Количество отправлений в емкости.
     */
    private Integer shipmentsNumber;

    /**
     * Доставлено (эквивалент RESDIT 21).
     * <br>Дата формирования операции из РТМ 02 "Доставлено" (эквивалент события RESDIT 21) в формате дд.мм.гггг чч.мм
     * Операция 33-21 "Обработка перевозчиком. Доставлено" - "Carrier processing. Delivered"
     */
    private Long resdit21Timestamp;
    private String resdit21ZoneOffset;

    /**
     * Получено назначенным оператором (эквивалент RESCON).
     * <br>Дата формирования операции из РТМ 02 "Получено назначенным оператором" (эквивалент события RESCON) в формате дд.мм.гггг чч.мм
     * Операция 33-3 "Международная обработка. Обработка назначенным оператором"
     * - "Processing of international mail. Processing by designated postal operator"
     */
    private Long resconTimestamp;
    private String resconZoneOffset;

    /**
     * Обработка назначенным оператором (эквивалент RESDES).
     * <br>Дата формирования операции из РТМ 02 "Обработка назначенным оператором" (эквивалент события RESDES)в формате дд.мм.гггг чч.мм
     * Операция 33-2 "Международная обработка. Получено назначенным оператором"
     * - "Processing of international mail. Received by the designated operator"
     */
    private Long resdesTimestamp;
    private String resdesZoneOffset;
}
