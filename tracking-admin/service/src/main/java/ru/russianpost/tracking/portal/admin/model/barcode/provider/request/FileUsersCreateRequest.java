/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderFileUser;

import java.util.Map;

/**
 * FileUsersCreateRequest Dto.
 * @author MKitchenko
 */
@RequiredArgsConstructor
@Getter
public class FileUsersCreateRequest {
    /**
     * Row number to user map
     */
    private final Map<Integer, BarcodeProviderFileUser> users;

    /**
     * Author of the change
     */
    private final String author;
}
