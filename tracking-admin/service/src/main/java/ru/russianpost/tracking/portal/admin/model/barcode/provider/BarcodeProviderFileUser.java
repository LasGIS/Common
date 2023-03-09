/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Barcode provider user from file DTO.
 * @author MKitchenko
 */
@RequiredArgsConstructor
@Getter
@ToString
public class BarcodeProviderFileUser {
    private final String companyName;
    private final String inn;
    private final String datDog;
    private final String postalCode;
    private final String notificationEmail;
}
