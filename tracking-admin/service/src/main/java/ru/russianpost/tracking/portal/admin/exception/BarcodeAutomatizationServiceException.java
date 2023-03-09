/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.exception;

import lombok.Getter;
import ru.russianpost.tracking.portal.admin.model.errors.ServiceError;

/**
 * Exceptions received from Barcode Automatization service.
 * @author MKitchenko
 */
@Getter
public class BarcodeAutomatizationServiceException extends RuntimeException {

    private final ServiceError error;

    /**
     * Constructor.
     * @param error Barcode Automatization service error
     */
    public BarcodeAutomatizationServiceException(ServiceError error) {
        super(error.getMessage());
        this.error = error;
    }
}
