/*
 *  @(#)UserEntity.java  last: 01.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import lombok.Data;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Таблица пользователей
 */
@Data
@Entity
@Table(name = "um_user", schema = "hiber")
@TypeDef(
    typeClass = EnumArrayType.class,
    defaultForType = UserRole[].class,
    parameters = {
        @Parameter(
            name = AbstractArrayType.SQL_ARRAY_TYPE,
            value = "TEXT"
        )
    }
)
public class UserEntity implements Serializable {

    /**
     * Уникальный номер пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "umusr_user_id", nullable = false)
    private long userId;

    /**
     * Имя пользователя для входа в систему
     */
    @Column(name = "umusr_login", nullable = false)
    private String login;

    /**
     * ФИО пользователя
     */
    @Column(name = "umusr_name", nullable = false)
    private String name;

    /**
     * пароль пользователя
     */
    @Column(name = "umusr_password", nullable = true)
    private String password;

    /**
     * Признак архивации (не активная запись)
     */
    @Column(name = "umusr_archived", nullable = false)
    private String archived;

    /**
     * Роли пользователя
     */
/*
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "um_user_role",
        joinColumns = @JoinColumn(name = "umusr_user_id"),
        inverseJoinColumns = @JoinColumn(name = "umrle_role_id")
    )
*/
/*
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
*/
    @Column(
        name = "um_user_roles",
        columnDefinition = "TEXT[]"
    )
    private UserRole[] roles;
}
