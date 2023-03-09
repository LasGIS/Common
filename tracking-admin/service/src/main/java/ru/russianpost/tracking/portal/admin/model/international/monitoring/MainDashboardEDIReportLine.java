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
 * <br>"Мониторинг количества отправленных и полученных данных EDI. Основной Dashboard"
 * <br>или "Main Dashboard EDI Report"
 * <br>EDI - Electronic Data Interchange (Электронный обмен данными).
 * <br>Note. В comment-е к каждому свойству класса - название колонки отчета и примечание к ней.
 *
 * @author spurtov
 * @version 1.0 14.04.2021.
 */
@Data
@NoArgsConstructor
public class MainDashboardEDIReportLine {

    /**
     * Страна назначения.
     * <br>Название страны назначения отправления на русском.
     */
    private String countryToRu;

    /** A2 код страны назначения отправления */
    private String countryToA2Code;

    /**
     * Вид отправления.
     * <br>Тип почтового отправления по русски - EMS, Посылка, Мелкий пакет.
     */
    private String postalItemTypeRu;

    /**
     * Тип почтового отправления по английски - EMS, PARCEL, LETTER
     */
    private String postalItemTypeEn;

    /**
     * Количество отправлений.
     * <br>Общее количество отправлений считается на основе операций из РТМ 02 –
     * «Экспорт международной почты» (эквивалент статусного события EMC) в выбранном интервале времени.
     * Количество операций 10 "Экспорт международной почты" - "Export of international mail".
     */
    private Integer totalNumber;

    /**
     * EMC.
     * <br>Количество отправлений c EMC.
     * Количество EMC сегментов в EMSEVT сообщениях за период.
     */
    private Integer emcNumber;

    /**
     * Импорт международной почты (эквивалент EMD).
     * <br>Количество отправлений на основе операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EMD)
     * Количество операций 9 "Импорт международной почты" - "Import of international mail".
     */
    private Integer emdNumber;

    /**
     * PREDES
     * <br>Количество отправлений с PREDES
     * Количество ШПИ в PREDES-файлах за период
     */
    private Integer predesNumber;

    /**
     * Обработка назначенным оператором (RESDES).
     * <br>Количество отправлений на основе операций из РТМ 02 - "Обработка назначенным оператором" (эквивалент события RESDRES)
     * Количество операций 33-3 "Международная обработка. Обработка назначенным оператором"
     * - "Processing of international mail. Processing by designated postal operator".
     */
    private Integer resdesNumber;

    /**
     * PRECON.
     * <br>Количество отправлений с PRECON
     * Количество операций 33-1 "Международная обработка. Передано перевозчику".
     */
    private Integer preconNumber;

    /**
     * RESCON.
     * <br>Количество отправлений на основе операций из РТМ 02 - "Получено назначенным оператором" (эквивалент события RESCON)
     * Количество операций 33-2 "Международная обработка. Получено назначенным оператором" .
     */
    private Integer resconNumber;
}
