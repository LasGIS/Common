/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * POJO representation of a shipment operation fullness validation.
 * @author sslautin
 * @version 1.0 08.10.2015
 */
@Setter
@Getter
@NoArgsConstructor
public class FullnessValidationResult {

    private List<String> errorMessages;

    /**
     * Checks if there are any error message.
     * @return true if there are at least one error message.
     */
    public boolean hasErrors() {
        return errorMessages != null && !errorMessages.isEmpty();
    }
}
