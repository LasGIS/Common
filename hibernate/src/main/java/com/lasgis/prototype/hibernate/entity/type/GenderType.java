/*
 *  @(#)GenderType.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.entity.type;

import lombok.Getter;

/**
 * The Class Gender Type definition.
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:28
 */
public enum GenderType {
    MALE("мужчина"), FEMALE("женщина");
    @Getter
    private final String definition;

    GenderType(final String definition) {
        this.definition = definition;
    }
}
