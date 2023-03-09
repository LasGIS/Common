/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.exception;

import lombok.Getter;
import ru.russianpost.tracking.portal.admin.model.errors.Error;

/**
 * Class for exceptions received from Barcode Provider service.
 *
 * @author KKiryakov
 */
@Getter
public class BarcodeProviderServiceException extends RuntimeException {

    private final Error error;

    /**
     * Constructor.
     * @param error Barcode provider service error
     */
    public BarcodeProviderServiceException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
