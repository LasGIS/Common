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
 * @author Amosov Maxim
 * @since 22.07.2022 : 13:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MultiplaceRpoInfo {
    private String multiplaceBarcode;
    private Integer deliveryMethod;
    private Integer rpoNum;
}
