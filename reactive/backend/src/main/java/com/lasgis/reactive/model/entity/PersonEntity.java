/*
 *  @(#)PersonEntity.java  last: 12.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * The Class Person definition.
 *
 * @author VLaskin
 * @since 11.09.2023 : 15:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity {
    @Id
    private Long personId;
    private String firstName;
    private String lastName;
    private String middleName;
    private SexType sex;
}
