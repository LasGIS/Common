/*
 * Copyright 2015 Russian Post
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
import ru.russianpost.tracking.portal.admin.model.operation.HistoryRecordId;
import ru.russianpost.tracking.portal.admin.model.operation.Money;
import ru.russianpost.tracking.portal.admin.validation.constraints.EmptyOrPatternIndex;
import ru.russianpost.tracking.portal.admin.validation.validators.Regexp;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * HistoryRecordEdition DTO
 * @author Roman Prokhorov
 * @version 1.0 (Dec 03, 2015)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRecordEdition {
    @NotNull
    @Valid
    private HistoryRecordId id;
    @Min(0L)
    private Long operDate;
    @Pattern(regexp = Regexp.INDEX)
    private String indexOper;
    @Min(-1)
    private Integer mass;
    @EmptyOrPatternIndex
    private String indexTo;
    @Size(max = 100)
    private String initiator;
    @Size(max = 255)
    private String comment;
    @Size(max = 150)
    private String rcpn;
    @Size(max = 150)
    private String sndr;
    private Money payment;
    private Money value;
    private Integer mailType;
    private Integer mailCategory;

    /**
     * Checks for changes
     * @return true if new field values detected, false otherwise
     */
    public boolean containsChanges() {
        return operDate != null ||
            indexOper != null ||
            mass != null ||
            indexTo != null ||
            rcpn != null ||
            sndr != null ||
            mailType != null ||
            mailCategory != null ||
            (payment != null && (payment.getValue() != null || payment.getCurrency() != null)) ||
            (value != null && (value.getValue() != null || value.getCurrency() != null));
    }
}
