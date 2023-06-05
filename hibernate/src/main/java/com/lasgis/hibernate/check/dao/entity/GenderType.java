/*
 *  @(#)GenderType.java  last: 05.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import lombok.Getter;

/**
 * The Class SexType definition.
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:28
 */
public enum GenderType {
    MALE("мужчина"), FEMALE("женщина");
    @Getter
    private final String rus;

    GenderType(String rus) {
        this.rus = rus;
    }
}
