/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation.correction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidHistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.validation.validators.Regexp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * HistoryRecordCreation DTO.
 * @author KKiryakov
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidHistoryRecordCreation
public class HistoryRecordCreation {
    @NotNull
    @Size(max = 15)
    private String shipmentId;
    @NotNull
    @Pattern(regexp = Regexp.DATE)
    private String operDate;
    @NotNull
    @Pattern(regexp = Regexp.TIME)
    private String operTime;
    @NotNull
    private Integer operType;
    @NotNull
    private Integer operAttr;
    @NotNull
    @Pattern(regexp = Regexp.INDEX)
    private String indexOper;
    @Min(-1)
    private Integer mass;
    @Size(max = 100)
    private String initiator;
    @Size(max = 255)
    private String comment;
}
