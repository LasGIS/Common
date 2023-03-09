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

/**
 * UserHistoryEventType.
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public enum UserHistoryEventType {

    /**
     * User roles updated.
     */
    USER_ROLES_UPDATED("Настройки доступа изменены."),
    /**
     * User information updated.
     */
    USER_INFORMATION_UPDATED("Информация о пользователе была обновлена."),
    /**
     * User has been created.
     */
    USER_CREATED("Пользователь был создан"),
    /**
     * User password has been reset.
     */
    USER_PASSWORD_RESET("Пароль был сброшен"),
    /**
     * User has been deleted
     */
    USER_DELETED("Пользователь был удалён");

    private final String defaultDescription;

    UserHistoryEventType(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }
}
