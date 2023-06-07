/*
 *  @(#)KindredType.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity.type;

import lombok.Getter;

/**
 * The Class Kindred Type definition.
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:28
 */
public enum KindredType {
    PARENT("Отец или Мать"),
    SIBLING("Брат или Сестра"),
    CHILD("Сын или Дочь"),
    SPOUSE("Муж или Жена");
    @Getter
    private final String definition;

    KindredType(String definition) {
        this.definition = definition;
    }
}
