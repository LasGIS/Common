/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Container.
 *
 * @author KKiryakov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {

    /** Year. */
    private Integer year;
    /** Month. */
    private Integer month;
    /** Container's minimum. */
    private Integer min;
    /** Container's maximum. */
    private Integer max;
    /** Current start for next booking. */
    private Integer current;
}
