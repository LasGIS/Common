package com.lasgis.prototype.git.admin.model.user;

import lombok.Getter;

/**
 * The Class UserRole definition.
 *
 * @author Vladimir Laskin
 * @since 30.04.2023 : 22:01
 */
public enum UserRole {
    ADMIN("Администратор"),
    CHIEF("Начальник"),
    OPERATOR("Оператор"),
    SUPERVISOR("Старший смены");
    @Getter
    private final String definition;

    UserRole(String definition) {
        this.definition = definition;
    }
}
