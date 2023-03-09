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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 15, 2015)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRecordDeletion {
    @Valid
    @NotNull
    private HistoryRecordId id;
    @Size(max = 100)
    private String initiator;
    @Size(max = 255)
    private String comment;
}
