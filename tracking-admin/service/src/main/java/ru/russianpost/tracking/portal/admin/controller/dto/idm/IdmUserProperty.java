/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.dto.idm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedIdmUserPropertyException;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserInfo;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author Amosov Maxim
 * @since 07.09.2021 : 12:50
 */
@Getter
@RequiredArgsConstructor
public enum IdmUserProperty {
    /** Имя */
    Name("Имя", (UserInfo::setName), (UserInfo::getName)),
    /** Фамилия */
    Surname("Фамилия", (UserInfo::setSurname), (UserInfo::getSurname)),
    /** Отчество */
    Patronymic("Отчество", (UserInfo::setPatronymic), (UserInfo::getPatronymic)),
    /** Email */
    Email("Email", (UserInfo::setEmail), (UserInfo::getEmail));

    private final String description;
    private final BiConsumer<UserInfo, String> userEditor;
    private final Function<UserInfo, String> userExtractor;

    /**
     * Returns user property by name or throw Exception
     *
     * @param name name
     * @return user property, or Exception if user property is not recognized
     * @throws UnrecognizedIdmUserPropertyException if property can't be resolved
     */
    public static IdmUserProperty byNameOrThrow(final String name) throws UnrecognizedIdmUserPropertyException {
        return Stream.of(values())
            .filter(p -> p.name().equals(name)).findAny()
            .orElseThrow(() -> new UnrecognizedIdmUserPropertyException(name));
    }
}
