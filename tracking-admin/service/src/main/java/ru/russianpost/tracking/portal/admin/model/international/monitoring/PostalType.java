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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Тип почтового отправления
 *
 * @author spurtov
 * @version 1.0 23.04.2021
 */
@Getter
@RequiredArgsConstructor
public enum PostalType {
    /** EMS */
    EMS("EMS"),
    /** Parcel */
    PARCEL("Посылка"),
    /** Letter */
    LETTER("Мелкий пакет"),
    /** Undefined */
    UNDEFINED("Другие");

    private final String ruName;
}
