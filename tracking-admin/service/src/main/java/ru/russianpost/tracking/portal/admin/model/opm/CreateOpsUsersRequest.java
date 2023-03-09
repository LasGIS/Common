/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.opm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 17.08.2022 : 13:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOpsUsersRequest {
    private List<OpsIndex> opsIndices;
    private boolean overrideExisting;
}
