/*
 *  @(#)Person.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model;

import com.lasgis.reactive.model.entity.SexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Class Person definition.
 *
 * @author VLaskin
 * @since 12.09.2023 : 15:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long personId;
    private String firstName;
    private String lastName;
    private String middleName;
    private SexType sex;
    private List<PersonRelation> relations;
}
