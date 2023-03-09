/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.request;

/**
 * Configuration update request with author name.
 *
 * @param <T> Configuration type
 * @author KKiryakov
 */
public class AuthoredConfigurationUpdateRequest<T> extends ConfigurationUpdateRequest<T> {

    private final String author;

    /**
     * Constructor.
     * @param configuration configuration
     * @param author author
     * @param comment comment
     */
    public AuthoredConfigurationUpdateRequest(
        final T configuration,
        final String author,
        final String comment
    ) {
        super(configuration, comment);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
