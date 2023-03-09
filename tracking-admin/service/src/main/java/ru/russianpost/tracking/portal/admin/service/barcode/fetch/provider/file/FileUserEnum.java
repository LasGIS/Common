/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file;

import java.util.Arrays;

/**
 * File user enum.
 * @author MKitchenko
 */
public enum FileUserEnum {
    /**
     * companyName
     */
    COMPANY_NAME("Организация", 0),
    /**
     * inn
     */
    INN("ИНН", 1),
    /**
     * datDog (Дата договора)
     */
    DAT_DOG("Дата договора", 2),
    /**
     * postalCode
     */
    POSTAL_CODE("Почтовый индекс", 3),
    /**
     * .
     * notificationEmail
     */
    NOTIFICATION_EMAIL("email", 4);

    private final String field;
    private final int cellNum;

    FileUserEnum(final String field, final int cellNum) {
        this.field = field;
        this.cellNum = cellNum;
    }

    public String getField() {
        return field;
    }

    public int getCellNum() {
        return cellNum;
    }

    /**
     * Return {@link FileUserEnum} by field
     * @param field field
     * @return {@link FileUserEnum} by field
     */
    public static FileUserEnum byField(String field) {
        return Arrays.stream(values())
            .filter(value -> value.getField().equals(field))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown file user field!"));
    }

}
