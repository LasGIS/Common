/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.mmo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * RpoInfo
 * @author MKitchenko
 * @version 1.0 27.11.2020
 */
@Data
@Accessors(chain = true)
public class RpoInfo {
    private String shipmentId;
    private Long operDate;
    private Integer zoneOffsetSeconds;
    private Integer operType;
    private Integer operAttr;
    private String indexOper;
    private Long weight;
}
