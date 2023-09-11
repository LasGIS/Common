/*
 *  @(#)PersonRelationType.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.entity;

import lombok.Getter;

/**
 * The Class PersonRelationType definition.
 *
 * @author VLaskin
 * @since 11.09.2023 : 15:31
 */
public enum PersonRelationType {
    PARENT("родитель (мать или отец)"),
    CHILD("ребёнок (сын или дочь)"),
    SPOUSE("супруг (муж или жена)"),
    SIBLING("родной брат или сестра"),
    RELATIVE("родственник, родственница"),
    COLLEAGUE("коллега по работе");
    @Getter
    private final String definition;

    PersonRelationType(String definition) {
        this.definition = definition;
    }
}
