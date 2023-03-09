/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * I had to do some crutches here because of compatibility with already written code
 *
 * @author vlaskin
 * @version 1.0 26.12.2019.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ValidationError {
    private String field;
    private ErrorCode errorCode;
    private String message;
}
