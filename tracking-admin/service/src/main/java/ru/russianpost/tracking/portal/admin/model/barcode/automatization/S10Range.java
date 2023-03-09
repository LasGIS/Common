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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * S10Range.
 * @author MKitchenko
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class S10Range {
    @NotNull
    private Integer id;
    @NotNull
    private Integer ufpsId;
    @NotEmpty
    private String mailType;
    @NotEmpty
    private String letterBeg;
    @NotNull
    private Integer start;
    @NotNull
    private Integer end;
    @NotEmpty
    private String type;
    private long operationTimestamp;
    private String author;
    private String comment;
}
