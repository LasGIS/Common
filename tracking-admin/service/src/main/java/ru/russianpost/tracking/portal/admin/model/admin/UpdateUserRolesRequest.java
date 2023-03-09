/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.admin;

import lombok.Data;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidRoles;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Update user roles request.
 * @author KKiryakov
 */
@Data
public class UpdateUserRolesRequest {
    @NotNull
    @ValidRoles
    private String[] roles;

    @NotNull
    @Size(min = 1)
    private String comment;
}
