/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.automatization;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * BarcodesAllocateUiRequest.
 * @author MKitchenko
 */
@Data
public class BarcodesAllocateUiRequest {
    @NotNull
    @Min(1)
    private Integer ufpsId;
    @NotEmpty
    private String containerType;
    @Size(max = 255)
    private String comment;
    @NotNull
    @Min(1)
    private Integer quantity;
}
