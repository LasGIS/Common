package com.lasgis.hibernate.check.dao.entity;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * Таблица пользователей
 */
@Data
@Entity
@Table(name = "um_user")
@UniqueElements
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "um_user_role",
        joinColumns = @JoinColumn(name = "umusr_user_id"),
        inverseJoinColumns = @JoinColumn(name = "umrle_role_id")
    )
    private List<RoleEntity> roles;

}
