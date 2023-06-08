/*
 *  @(#)PersonRelationEntity.java  last: 08.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.entity;

import com.lasgis.prototype.hibernate.entity.type.KindredType;

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

/**
 * Таблица персон
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:18
 */
@Entity
@Table(name = "pr_person_relation", schema = "hiber")
public class PersonRelationEntity {
    private long personRelationId;
    private KindredType type;
    private PersonEntity personFrom;
    private PersonEntity personTo;

    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprl_person_relation_id", nullable = false)
    public long getPersonRelationId() {
        return personRelationId;
    }

    public void setPersonRelationId(long personRelationId) {
        this.personRelationId = personRelationId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prprl_person_from_id", nullable = false,
        referencedColumnName = "prprs_person_id",
        foreignKey = @ForeignKey(name = "fk_person_from_person")
    )
    public PersonEntity getPersonFrom() {
        return personFrom;
    }

    public void setPersonFrom(PersonEntity personFrom) {
        this.personFrom = personFrom;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prprl_person_to_id", nullable = false,
        referencedColumnName = "prprs_person_id",
        foreignKey = @ForeignKey(name = "fk_person_to_person")
    )
    public PersonEntity getPersonTo() {
        return personTo;
    }

    public void setPersonTo(PersonEntity personTo) {
        this.personTo = personTo;
    }

    @Column(name = "prprl_relation_type", columnDefinition = "text")
    @Enumerated(EnumType.STRING)
    public KindredType getType() {
        return type;
    }

    public void setType(KindredType type) {
        this.type = type;
    }
}
