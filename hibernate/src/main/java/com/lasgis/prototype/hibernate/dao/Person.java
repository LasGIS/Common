/*
 *  @(#)Person.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.dao;

import com.lasgis.prototype.hibernate.entity.type.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Таблица персон
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private long personId;
    private String firstName;
    private String lastName;
    private String middleName;
    private GenderType gender;
    private List<Person> from;
    private List<Person> to;
}
