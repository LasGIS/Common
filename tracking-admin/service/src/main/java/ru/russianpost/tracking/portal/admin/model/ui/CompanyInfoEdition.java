/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.ui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

/**
 * CompanyInfoEdition request
 * @author Roman Prokhorov
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CompanyInfoEdition {
    private CompanyInfo company;
    @Size(max = 255)
    private String comment;
    @Size(max = 50)
    private String serviceName;
    @Size(max = 20)
    private String clientType;
    @Size(max = 500)
    private String internalComment;
}
