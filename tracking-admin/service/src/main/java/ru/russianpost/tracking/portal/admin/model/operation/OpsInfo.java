/*
 * Copyright 2015 Russian Post
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
 * POJO representation of part of ops info for a given index.
 * @author sslautin
 * @version 1.0 13.10.2015
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpsInfo {

    private static final OpsInfo EMPTY = new OpsInfo("", "", "", "");

    private String index;
    private String region;
    private String district;
    private String settlement;


    /**
     * @return instance of {@link OpsInfo} with empty fields
     */
    public static OpsInfo empty() {
        return EMPTY;
    }
}
