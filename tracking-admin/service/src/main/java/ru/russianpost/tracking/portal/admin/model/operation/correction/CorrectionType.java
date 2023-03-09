/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation.correction;

import lombok.Getter;

import java.util.stream.Stream;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Jan 14, 2016)
 */
public enum CorrectionType {

    /**
     * Operation initialization - origin data
     */
    CREATE("create"),
    /**
     * Edited data
     */
    CHANGE("change"),
    /**
     * Operation deleted
     */
    DELETE("delete"),
    /**
     * Operation restored after deleted
     */
    RESTORE("restore");

    @Getter
    private final String code;

    private CorrectionType(String code) {
        this.code = code;
    }

    /**
     * CorrectionType by String code
     * @param code code
     * @return correction type
     */
    public static CorrectionType by(String code) {
        return Stream.of(values()).filter(v -> v.getCode().equalsIgnoreCase(code)).findAny().orElse(CHANGE);
    }

    @Override
    public String toString() {
        return code;
    }
}
