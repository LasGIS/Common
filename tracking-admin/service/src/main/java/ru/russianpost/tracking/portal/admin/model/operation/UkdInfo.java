/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ukd Info
 *
 * @author vlaskin
 * @since <pre>05.04.2022</pre>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UkdInfo {
    /** Empty UkdInfo object */
    public static final UkdInfo EMPTY_UKD_INFO = UkdInfo.of(null, null);
    private String index;
    private String name;
}
