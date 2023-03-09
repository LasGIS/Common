/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MKitchenko
 * @version 1.0 (Dec 26, 2019)
 */
@Getter
@AllArgsConstructor
public enum MpoDeclaration {

    /**
     * Отсутствие значения
     */
    EMPTY(null),
    /**
     * С описью вложения
     */
    WITH_AN_INVENTORY_OF_THE_ATTACHMENT("С описью вложения"),
    /**
     * C электронной таможенной декларацией
     */
    WITH_ELECTRONIC_CUSTOMS_DECLARATION("С эл. ТД");

    private final String description;
}
