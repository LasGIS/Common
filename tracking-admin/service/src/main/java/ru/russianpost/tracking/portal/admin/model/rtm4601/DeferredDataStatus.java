/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.rtm4601;

/**
 * @author MKitchenko
 * @version 1.0 27.08.19.
 */
public enum DeferredDataStatus {
    /** unprocessed orders*/
    NEW,
    /** send to processing */
    PROCESSED,
    /** archived */
    ARCHIVED
}
