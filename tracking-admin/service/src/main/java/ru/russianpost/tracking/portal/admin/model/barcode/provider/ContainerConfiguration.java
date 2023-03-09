/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

/**
 * Container configuration.
 *
 * @author KKiryakov
 */
public interface ContainerConfiguration {

    /**
     * Returns container's minimum value
     * @return container's minimum value
     */
    Integer getMin();

    /**
     * @return container's maximum value
     * Returns container's maximum value
     */
    Integer getMax();

    /**
     * Returns allocations size
     * @return allocations size
     */
    Integer getAllocationSize();
}
