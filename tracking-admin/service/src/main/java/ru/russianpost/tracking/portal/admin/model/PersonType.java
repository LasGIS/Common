/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model;

/**
 * Person type enum.
 * Contains only used values!
 * @author MKitchenko
 */
public enum PersonType {
    /**
     * Отправитель
     */
    SENDER(2),
    /**
     * Получатель (адресат)
     */
    RECIPIENT(3);

    private final int type;

    PersonType(final int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

