/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.parse;

import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderFileUser;

import java.io.InputStream;
import java.util.Map;

/**
 * Barcode provider users file parser interface.
 * @author MKitchenko
 */
public interface FileParser {

    /**
     * Parse inputstream to row number to barcode provider file user map
     * @param inputStream inputStream
     * @return row number to barcode provider file user map
     * @throws Exception exception
     */
    Map<Integer, BarcodeProviderFileUser> parseUsers(InputStream inputStream) throws Exception;

}
