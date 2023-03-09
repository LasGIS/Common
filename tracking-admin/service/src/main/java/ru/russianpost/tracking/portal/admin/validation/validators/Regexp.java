/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.validation.validators;

/**
 * Constants
 * @author Roman Prokhorov
 * @version 1.0 (Jan 14, 2016)
 */
public final class Regexp {

    /**
     * Index validation pattern
     */
    public static final String INDEX = "[0-9]{6}";

    /**
     * Date validation pattern.
     */
    public static final String DATE = "\\d{2}.\\d{2}.\\d{4}";

    /**
     * Time validation pattern.
     */
    public static final String TIME = "\\d{2}:\\d{2}(:\\d{2})?";

    /**
     * Email validation pattern.
     */
    public static final String EMAIL =
        "^[-!#$%&'*+\\/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+\\/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-?\\.?[a-zA-Z0-9])*\\.[a-zA-Z](-?[a-zA-Z0-9])+$";

    private Regexp() {
    }
}
