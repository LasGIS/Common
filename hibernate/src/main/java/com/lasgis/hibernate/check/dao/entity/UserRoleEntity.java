package com.lasgis.hibernate.check.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "um_user_role")
public class UserRoleEntity implements Serializable {

    /**
     * Уникальный номер связи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "umurl_user_role_id", nullable = false)
    private long userRoleId;

/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "umusr_user_id")
    private UserEntity user;
*/

    /**
     * Уникальный номер пользователя
     */
    @Column(name = "umusr_user_id", nullable = false)
    private long userId;

    /**
     * Уникальный код роли
     */
    @Column(name = "umrle_role_id", nullable = false)
    private String roleId;

}
