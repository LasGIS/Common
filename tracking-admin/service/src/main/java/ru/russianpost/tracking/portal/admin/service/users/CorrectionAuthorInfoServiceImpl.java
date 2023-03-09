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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder;

/**
 * Correction Author Info Service implementation.
 *
 * @author KKiryakov
 */
@Service
@RequiredArgsConstructor
public class CorrectionAuthorInfoServiceImpl implements CorrectionAuthorInfoService {

    private final AdminServiceUserService userService;
    private final SoftwareHolder softwareHolder;

    @Override
    public AdminUser resolveAuthor(String author) {
        return userService.resolve(author).map(AdminUser::new).orElseGet(() -> {
            if (softwareHolder.isKnownSoftwareCode(author)) {
                return new AdminUser(softwareHolder.getSoftwareNameByCode(author));
            } else {
                return new AdminUser(author);
            }
        });
    }
}
