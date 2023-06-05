/*
 *  @(#)UserEntity.java  last: 05.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType;
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
@Table(name = "um_user")
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umusr_user_id", nullable = false)
    private long userId;

    /**
     * Имя пользователя для входа в систему
     */
    @Column(name = "umusr_login", columnDefinition = "text", unique = true, nullable = false)
    private String login;

    /**
     * ФИО пользователя
     */
    @Column(name = "umusr_name", columnDefinition = "text", nullable = false)
    private String name;

    /**
     * пароль пользователя
     */
    @Column(name = "umusr_password", columnDefinition = "text")
    private String password;

    /**
     * Признак архивации (не активная запись)
     */
    @Column(name = "umusr_archived", nullable = false)
    private Boolean archived;

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
        name = "umusr_roles",
        columnDefinition = "TEXT[]"
    )
//    @Type(type = "com.lasgis.hibernate.check.dao.entity.UserRoleArrayType")
//    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    private UserRole[] roles;
}
