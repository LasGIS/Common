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
 * <br>"Мониторинг количества отправленных и полученных данных EDI. Drill-down (2) по отправлениям".
 * <br>или "Shipment List EDI Report".
 * <br>Это отчет со списком отправлений в заданной емкости
 * <br>EDI - Electronic Data Interchange (Электронный обмен данными).
 * <br>Note. В comment-е к каждому свойству класса - название колонки отчета и примечание к ней.
 *
 * @author spurtov
 * @version 1.0 23.04.2021.
 */
@Data
@NoArgsConstructor
public class ShipmentListEDIReportLine {

    /**
     * Страна назначения.
     * <br>Название страны назначения отправления на русском.
     */
    private String countryToRu;

    /** A2 код страны назначения отправления */
    private String countryToA2Code;

    /**
     * Номер отправления.
     * <br>Номер отправления из выбранной ёмкости.
     */
    private String shipmentId;

    /**
     * EMA.
     * <br>Дата формирования статусного события EMA в формате дд.мм.гггг чч.мм
     */
    private Long emaTimestamp;
    private String emaZoneOffset;

    /**
     * EMB.
     * <br>Дата формирования статусного события EMB в формате дд.мм.гггг чч.мм
     */
    private Long embTimestamp;
    private String embZoneOffset;

    /**
     * EMC.
     * <br>Дата формирования статусного события EMC в формате дд.мм.гггг чч.мм
     */
    private Long emcTimestamp;
    private String emcZoneOffset;

    /**
     * "Импорт международной почты" (EMD).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EMD) в формате дд.мм.гггг чч.мм
     */
    private Long emdTimestamp;
    private String emdZoneOffset;

    /**
     * Прием на таможню (EDB).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EDB) в формате дд.мм.гггг чч.мм
     */
    private Long edbTimestamp;
    private String edbZoneOffset;

    /**
     * Продление срока выпуска таможней (EME).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EME) в формате дд.мм.гггг чч.мм
     */
    private Long emeTimestamp;
    private String emeZoneOffset;

    /**
     * Таможенное оформление (EDC).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EDC) в формате дд.мм.гггг чч.мм
     */
    private Long edcTimestamp;
    private String edcZoneOffset;

    /**
     * Покинуло место международного обмена (EMF).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EMF) в формате дд.мм.гггг чч.мм
     */
    private Long emfTimestamp;
    private String emfZoneOffset;

    /**
     * Неудачная попытка вручения (EMH).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EMH) в формате дд.мм.гггг чч.мм
     */
    private Long emhTimestamp;
    private String emhZoneOffset;

    /**
     * Вручение (EMI).
     * <br>Дата формирования операций из РТМ 02 - "Импорт международной почты" (эквивалент статусного события EMI) в формате дд.мм.гггг чч.мм
     */
    private Long emiTimestamp;
    private String emiZoneOffset;

}
