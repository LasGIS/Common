/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.converter;

import org.springframework.stereotype.Component;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmRoleDto;
import ru.russianpost.tracking.portal.admin.security.Role;

/**
 * @author Amosov Maxim
 * @since 31.08.2021 : 11:55
 */
@Component
public class IdmRoleConverter extends Converter<Role, IdmRoleDto> {
    @Override
    protected IdmRoleDto apply(final Role role) {
        return new IdmRoleDto(
            role.name(),
            role.getFullName(),
            role.getDescription()
        );
    }
}
