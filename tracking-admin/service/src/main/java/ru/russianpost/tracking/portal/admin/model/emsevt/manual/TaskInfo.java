/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.emsevt.manual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author Amosov Maxim
 * @since 04.08.2021 : 17:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TaskInfo {
    private UUID uuid;
    private String author;
    private int size;
    private String extension;
    private String error;
    private Date createDate;
    private boolean complete;
    private Date completeDate;
    private String downloadUrl;
}
