/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation;

/**
 * Constants
 * @author MKitchenko
 */
public final class Regexp {

    /**
     * Postal code validation pattern
     */
    static final String POSTAL_CODE = "^[1-9]{1}[0-9]{5}$";

    /**
     * Date validation pattern.
     */
    static final String DATE = "^(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})$";

    /**
     * Inn validation pattern.
     */
    static final String INN = "^([0-9]{10}|[0-9]{12})$";

    /**
     * Email validation pattern.
     */
    static final String EMAIL =
        "^[-!#$%&'*+\\/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+\\/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-?\\.?[a-zA-Z0-9])*\\.[a-zA-Z](-?[a-zA-Z0-9])+$";

    private Regexp() {
    }
}
