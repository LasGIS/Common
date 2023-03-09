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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmRoleDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserStatus;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.security.Role;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Amosov Maxim
 * @since 01.09.2021 : 12:50
 */
@Component
@RequiredArgsConstructor
public class IdmUserConverter extends Converter<ServiceUser, IdmUserDto> {
    private final IdmRoleConverter roleConverter;

    @Override
    protected IdmUserDto apply(final ServiceUser user) {
        return new IdmUserDto(
            user.getUsername(),
            user.getName(),
            user.getSurname(),
            user.getPatronymic(),
            user.getEmail(),
            getRoles(user.getAuthorityString()),
            getStatus(user)
        );
    }

    private List<IdmRoleDto> getRoles(final String authorityString) {
        return Arrays.stream(StringUtils.tokenizeToStringArray(authorityString, ","))
            .map(Role::by)
            .filter(r -> r != Role.UNKNOWN)
            .map(roleConverter::convert)
            .collect(toList());
    }

    private IdmUserStatus getStatus(final ServiceUser user) {
        if (!user.isEnabled()) {
            return IdmUserStatus.Disabled;
        } else if (!user.isAccountNonExpired() || !user.isCredentialsNonExpired() || !user.isAccountNonLocked()) {
            return IdmUserStatus.Locked;
        }
        return IdmUserStatus.Working;
    }
}
