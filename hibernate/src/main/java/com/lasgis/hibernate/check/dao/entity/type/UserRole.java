/*
 *  @(#)UserRole.java  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity.type;

import lombok.Getter;

/**
 * The Class UserRole definition.
 *
 * @author Vladimir Laskin
 * @since 30.04.2023 : 22:01
 */
public enum UserRole {
    ADMIN("Администратор"), CHIEF("Начальник"), OPERATOR("Оператор"), SUPERVISOR("Старший смены");
    @Getter
    private final String definition;

    UserRole(String definition) {
        this.definition = definition;
    }
}
