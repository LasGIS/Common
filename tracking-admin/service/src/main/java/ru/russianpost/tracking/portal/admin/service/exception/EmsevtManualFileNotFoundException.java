/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.exception;

/**
 * @author Amosov Maxim
 * @since 25.08.2021 : 18:04
 */
public class EmsevtManualFileNotFoundException extends RuntimeException {
    /**
     * @param downloadUrl file download url
     * @param cause       cause
     */
    public EmsevtManualFileNotFoundException(final String downloadUrl, final Throwable cause) {
        super("File not found! downloadUrl = " + downloadUrl, cause);
    }
}
