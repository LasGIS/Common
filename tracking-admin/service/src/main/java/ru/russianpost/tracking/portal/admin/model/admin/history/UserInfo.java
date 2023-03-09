/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.admin.history;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;

/**
 * Service user information fields.
 * @author KKiryakov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String name;
    private String surname;
    private String patronymic;
    private String email;

    /**
     * Creates UserInfo by ServiceUser object.
     * @param user ServiceUser
     */
    public UserInfo(ServiceUser user) {
        this(user.getName(), user.getSurname(), user.getPatronymic(), user.getEmail());
    }

    /**
     * Make normalized user info.
     * @param userInfo user info
     * @return normalized user info
     */
    public static UserInfo makeNormalized(UserInfo userInfo) {
        return new UserInfo(
            normalizeValue(userInfo.name),
            normalizeValue(userInfo.surname),
            normalizeValue(userInfo.patronymic),
            normalizeValue(userInfo.email)
        );
    }

    private static String normalizeValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            return value.trim();
        }
    }
}
