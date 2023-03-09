/*
 * Copyright 2018 Russian Post
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
 * Operation info class. Contains operType and its attributes.
 * @author MKitchenko
 * @version 1.0 10.10.2018
 */
public class OperationInfo {
    private Integer operType;
    private List<Integer> operAttr;

    /**
     * Constructor.
     * @param operType operType
     * @param operAttr operType attributes list
     */
    public OperationInfo(Integer operType, List<Integer> operAttr) {
        this.operType = operType;
        this.operAttr = operAttr;
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public List<Integer> getOperAttr() {
        return operAttr;
    }

    public void setOperAttr(List<Integer> operAttr) {
        this.operAttr = operAttr;
    }
}
