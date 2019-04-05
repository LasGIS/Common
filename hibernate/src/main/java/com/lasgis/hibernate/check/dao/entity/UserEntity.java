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
@Table(name = "UM_USER")
@UniqueElements
public class UserEntity implements Serializable {

    /**
     * Уникальный номер пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "UMUSR_USER_ID", nullable = false)
    private long userId;

    /**
     * Имя пользователя для входа в систему
     */
    @Column(name = "UMUSR_LOGIN", nullable = false)
    private String login;

    /**
     * ФИО пользователя
     */
    @Column(name = "UMUSR_NAME", nullable = false)
    private String name;

    /**
     * пароль пользователя
     */
    @Column(name = "UMUSR_PASSWORD", nullable = true)
    private String password;

    /**
     * Признак архивации (не активная запись)
     */
    @Column(name = "UMUSR_ARCHIVED", nullable = false)
    private String archived;

    /**
     * Роли пользователя
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "um_user_role",
        joinColumns = @JoinColumn(name = "umusr_user_id", referencedColumnName = "umusr_user_id"),
        inverseJoinColumns = @JoinColumn(name = "umrle_role_id", referencedColumnName = "umrle_role_id")
    )
    private List<RoleEntity> roles;

}
