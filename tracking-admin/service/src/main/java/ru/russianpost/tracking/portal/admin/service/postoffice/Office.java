/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.postoffice;

import lombok.Data;

/**
 * Office
 * @author MKitchenko
 * @version 1.0 03.08.2020
 */
@Data
public final class Office {
    private String region;
    private String district;
    private String settlement;
}
