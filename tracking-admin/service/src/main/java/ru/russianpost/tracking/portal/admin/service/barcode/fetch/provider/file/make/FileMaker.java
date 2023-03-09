/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make;

import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponseData;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.ValidationError;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Barcode provider users file maker interface.
 * @author MKitchenko
 */
public interface FileMaker {

    /**
     * Make errors file as byte array
     * @param inputStream inputStream
     * @param errors      row number to validation error list map
     * @return errors file as byte array
     * @throws Exception exception
     */
    byte[] makeFileWithErrors(
        InputStream inputStream,
        Map<Integer, List<ValidationError>> errors
    ) throws Exception;

    /**
     * Make file with credentials as byte array
     * @param inputStream inputStream
     * @param credentials row number to user credentials map
     * @return file with credentials as byte array
     * @throws Exception exception
     */
    byte[] makeFileWithCredentials(
        InputStream inputStream,
        Map<Integer, BatchRegistrationResponseData> credentials
    ) throws Exception;

}
