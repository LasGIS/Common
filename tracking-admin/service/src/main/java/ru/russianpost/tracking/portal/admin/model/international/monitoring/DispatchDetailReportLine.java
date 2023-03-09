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
 * <br>"Трекинг по депешам"
 * <br> или "Dispatch Report"
 *
 * @author spurtov
 * @version 1.0 23.04.2021
 */
@Data
@NoArgsConstructor
public class DispatchDetailReportLine {

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

    /** ID емкости */
    private String receptacleIdentifier;

    /**
     * Номер отправления.
     * <br>Номер отправления из выбранной ёмкости.
     */
    private String shipmentId;

    private Long predesTimestamp;
    private Long resdesTimestamp;
    private Long preconTimestamp;
    private Long resconTimestamp;
    private Long carditTimestamp;
    private Long resditTimestamp;
    private String predesZoneOffset;
    private String resdesZoneOffset;
    private String preconZoneOffset;
    private String resconZoneOffset;
    private String carditZoneOffset;
    private String resditZoneOffset;
}
