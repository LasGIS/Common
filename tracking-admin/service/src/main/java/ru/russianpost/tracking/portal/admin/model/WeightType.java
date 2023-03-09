/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Weight type enum.
 * @author MKitchenko
 */
@Getter
@AllArgsConstructor
public enum WeightType {
    /**
     * Подавательский
     */
    DECLARED(1),
    /**
     * Фактический
     */
    ACTUAL(2),
    /**
     * Объемный (для отправлений EMS)
     */
    VOLUME(3);

    private final int type;
}
