/*
 *  @(#)PersonEntity.java  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import com.lasgis.hibernate.check.dao.entity.type.GenderType;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Таблица персон
 *
 * @author VLaskin
 * @since 05.06.2023 : 13:18
 */
@Data
@Entity
@Table(name = "pr_person", schema = "hiber")
public class PersonEntity implements Serializable {
    /**
     * Уникальный номер персоны
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prprs_person_id", nullable = false)
    private long personId;

    @Column(name = "prprs_first_name", columnDefinition = "text", nullable = false)
    private String firstName;

    @Column(name = "prprs_last_name", columnDefinition = "text", nullable = false)
    private String lastName;

    @Column(name = "prprs_middle_name", columnDefinition = "text")
    private String middleName;

    @Column(name = "prprs_gender", columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @OneToMany(mappedBy = "personFrom", cascade = CascadeType.ALL)
/*
    @JoinTable(name = "pr_person_relation",
        joinColumns = @JoinColumn(name = "prprl_person_from_id"),
        inverseJoinColumns = @JoinColumn(name = "prprs_person_id")
    )
*/
    private Set<PersonRelationEntity> fromRelations = new HashSet<>();

    @OneToMany(mappedBy = "personTo", cascade = CascadeType.ALL)
/*
    @JoinTable(name = "pr_person_relation",
        joinColumns = @JoinColumn(name = "prprl_person_to_id"),
        inverseJoinColumns = @JoinColumn(name = "prprs_person_id")
    )
*/
    private Set<PersonRelationEntity> toRelations = new HashSet<>();
}
