/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations;

import java.util.List;

/**
 * Operation order list holder.
 * @author MKitchenko
 * @version 2.0 01.03.2019
 */
public class OperationOrderHolder {

    private List<OperationInfo> operationOrderList;

    /**
     * Constructor.
     * @param operationOrderList operation order list
     */
    public OperationOrderHolder(List<OperationInfo> operationOrderList) {
        this.operationOrderList = operationOrderList;
    }

    /**
     * Get operation info order list.
     * @return Operation info order list.
     */
    public List<OperationInfo> getOperationOrderList() {
        return this.operationOrderList;
    }
}
