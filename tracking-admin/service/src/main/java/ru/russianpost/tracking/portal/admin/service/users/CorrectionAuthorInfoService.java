/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.users;

import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;

/**
 * Correction author info service.
 *
 * @author KKiryakov
 */
public interface CorrectionAuthorInfoService {

    /**
     * Returns AdminUser object by correction author
     * @param author author name
     * @return AdminUser object
     */
    AdminUser resolveAuthor(String author);

}
