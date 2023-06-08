/*
 *  @(#)PersonEntity.java  last: 08.06.2023
 *
 * Title: LG prototype for spring + mvc + hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.hibernate.entity;

import com.lasgis.prototype.hibernate.entity.type.GenderType;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Таблица персон
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:18
 */
@Entity
@Table(name = "pr_person", schema = "hiber")
public class PersonEntity {
    private long personId;
    private String firstName;
    private String lastName;
    private String middleName;
    private GenderType gender;
    private Set<PersonRelationEntity> fromRelations = new HashSet<>();
    private Set<PersonRelationEntity> toRelations = new HashSet<>();

    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprs_person_id", nullable = false)
    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @Column(name = "prprs_first_name", columnDefinition = "text", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "prprs_last_name", columnDefinition = "text", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "prprs_middle_name", columnDefinition = "text")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name = "prprs_gender", columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    @OneToMany(mappedBy = "personFrom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<PersonRelationEntity> getFromRelations() {
        return fromRelations;
    }

    public void setFromRelations(Set<PersonRelationEntity> fromRelations) {
        this.fromRelations = fromRelations;
    }

    @OneToMany(mappedBy = "personTo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<PersonRelationEntity> getToRelations() {
        return fromRelations;
    }

    public void setToRelations(Set<PersonRelationEntity> toRelations) {
        this.toRelations = toRelations;
    }
}
