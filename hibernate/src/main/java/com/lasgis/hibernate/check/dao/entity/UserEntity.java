/*
 *  @(#)UserEntity.java  last: 06.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.hibernate.check.dao.entity;

import com.lasgis.hibernate.check.dao.entity.add.UserGroup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Таблица пользователей
 */
@Entity
@Table(name = "um_user", schema = "hiber")
/*
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
*/
public class UserEntity implements Serializable {
    private long userId;
    private String login;
    private String name;
    private String password;
    private Boolean archived;
//    private UserRole[] roles;
    private Set<UserGroup> userGroups = new HashSet();

    public UserEntity() {
    }

    /**
     * Уникальный номер пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umusr_user_id", nullable = false)
    public long getUserId() {
        return this.userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    /**
     * Имя пользователя для входа в систему
     */
    @Column(name = "umusr_login", columnDefinition = "text", unique = true, nullable = false)
    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * ФИО пользователя
     */
    @Column(name = "umusr_name", columnDefinition = "text", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * пароль пользователя
     */
    @Column(name = "umusr_password", columnDefinition = "text")
    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Признак архивации (не активная запись)
     */
    @Column(name = "umusr_archived", nullable = false)
    public Boolean getArchived() {
        return this.archived;
    }

    public void setArchived(final Boolean archived) {
        this.archived = archived;
    }

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
//    @Column(
//        name = "umusr_roles",
//        columnDefinition = "TEXT[]"
//    )
//    @Type(type = "com.lasgis.hibernate.check.dao.entity.UserRoleArrayType")
//    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
//    public UserRole[] getRoles() {
//        return this.roles;
//    }
//    public void setRoles(final UserRole[] roles) {
//        this.roles = roles;
//    }
    @OneToMany(mappedBy = "user")
    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(final Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
