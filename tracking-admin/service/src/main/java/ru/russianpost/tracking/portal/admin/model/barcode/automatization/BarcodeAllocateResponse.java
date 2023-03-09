/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.automatization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.russianpost.tracking.portal.admin.model.errors.ServiceError;

/**
 * BarcodeAllocateResponse.
 * @author MKitchenko
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarcodeAllocateResponse extends BarcodeAutomatizationResponse {
    private String mailType;
    private String letterBeg;
    private Long start;
    private Long end;
    private ServiceError error;
}
