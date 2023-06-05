/*
 *  @(#)PersonEntity.java  last: 05.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Таблица персон
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:18
 */
@Data
@Entity
@Table(name = "pr_person")
public class PersonEntity implements Serializable {
    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprs_person_id", nullable = false)
    private long personId;

    @Column(name = "prprs_first_name", nullable = false)
    private String firstName;

    @Column(name = "prprs_last_name", nullable = false)
    private String lastName;

    @Column(name = "prprs_middle_name")
    private String middleName;

    @Column(name = "prprs_gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;
}
