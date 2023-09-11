/*
 *  @(#)UserRole.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.entity;

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
