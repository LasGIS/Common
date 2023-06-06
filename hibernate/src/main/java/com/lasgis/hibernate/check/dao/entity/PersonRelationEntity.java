/*
 *  @(#)PersonRelationEntity.java  last: 06.06.2023
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
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pr_person_relation", schema = "hiber")
public class PersonRelationEntity implements Serializable {
    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprl_person_relation_id", nullable = false)
    private long personRelationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prprl_person_from_id", nullable = false,
        referencedColumnName = "prprs_person_id",
        foreignKey = @ForeignKey(name = "fk_person_from_person")
    )
    private PersonEntity personFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prprl_person_to_id", nullable = false,
        referencedColumnName = "prprs_person_id",
        foreignKey = @ForeignKey(name = "fk_person_to_person")
    )
    private PersonEntity personTo;

    @Column(name = "prprl_relation_type", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private KindredType relation;
}
