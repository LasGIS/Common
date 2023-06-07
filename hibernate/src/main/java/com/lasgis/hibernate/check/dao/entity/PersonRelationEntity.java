/*
 *  @(#)PersonRelationEntity.java  last: 07.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import com.lasgis.hibernate.check.dao.entity.type.KindredType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Таблица персон
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:18
 */
@Data
@Entity
@Table(name = "pr_person_relation", schema = "hiber")
public class PersonRelationEntity {
    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprl_person_relation_id", nullable = false)
    private long personRelationId;

    /*
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "prprl_person_from_id", nullable = false,
            referencedColumnName = "prprs_person_id",
            foreignKey = @ForeignKey(name = "fk_person_from_person")
        )
    */
    @Column(name = "prprl_person_from_id", nullable = false)
    private long personFrom;

    /*
        @OneToMany(mappedBy = "personId", fetch = FetchType.LAZY)
        @JoinColumn(name = "prprs_person_id", nullable = false,
            referencedColumnName = "prprl_person_to_id",
            foreignKey = @ForeignKey(name = "fk_person_to_person")
        )
    */
    @Column(name = "prprl_person_to_id", nullable = false)
    private long personTo;

    @Column(name = "prprl_relation_type", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    private KindredType relation;
}





