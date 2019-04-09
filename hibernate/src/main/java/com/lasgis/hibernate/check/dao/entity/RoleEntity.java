package com.lasgis.hibernate.check.dao.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "um_role")
public class RoleEntity implements Serializable {

    /**
     * Уникальный номер роли
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umrle_role_id", nullable = false)
    private String roleId;

    /**
     * Описание роли
     */
    @Column(name = "umrle_description", nullable = false)
    private String description;
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<UserEntity> users;
}
