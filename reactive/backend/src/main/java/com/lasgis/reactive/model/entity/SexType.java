/*
 *  @(#)SexType.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.entity;

import lombok.Getter;

/**
 * The Class SexType definition.
 *
 * @author VLaskin
 * @since 11.09.2023 : 15:23
 */
public enum SexType {
    MALE("Мужчина"),
    FEMALE("Женщина");
    @Getter
    private final String definition;

    SexType(String definition) {
        this.definition = definition;
    }
}
