/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * BatchRegistrationResponse
 * @author MKitchenko
 */
@Getter
@Setter
public class BatchRegistrationResponse extends BarcodeProviderResponse {
    private Map<Integer, BatchRegistrationResponseData> users;
}
