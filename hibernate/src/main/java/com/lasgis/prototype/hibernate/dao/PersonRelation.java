/*
 *  @(#)PersonRelation.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.dao;

import com.lasgis.prototype.hibernate.entity.type.KindredType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class PersonRelation {
    private Person person;
    private KindredType relation;
}





