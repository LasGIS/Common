/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.elastic.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/****
 * MainListenerValidationErrorInfoByDate
 *
 * @author VVorobeva
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainListenerValidationErrorInfoByDate {

    private String date;
    private Map<String, List<MainListenerValidationErrorInfo>> statisticsInfo;
}
