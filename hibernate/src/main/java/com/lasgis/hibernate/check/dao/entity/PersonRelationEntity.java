/*
 *  @(#)PersonRelationEntity.java  last: 05.06.2023
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
@Table(name = "pr_person_relation")
public class PersonRelationEntity implements Serializable {
    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprl_person_relation_id", nullable = false)
    private long personRelationId;

    @Column(name = "prprs_person_from_id", nullable = false)
    private long from;

    @Column(name = "prprl_person_to_id", nullable = false)
    private long to;

    @Column(name = "prprl_relation_type", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private KindredType relation;
}
