/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import lombok.Data;

/**
 * International Container data
 *
 * @author vlaskin
 */
@Data
public class InternationalContainer {
    /** Mail type. */
    private String mailType;
    /** Letter beg. */
    private String letterBeg;
    /** Start of container */
    private Integer containerStart;
    /** End of container */
    private Integer containerEnd;
    /** Current position. */
    private Integer containerCurrent;
    /** exhausted */
    private Boolean exhausted;
}
