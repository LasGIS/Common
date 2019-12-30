package com.lasgis.kotlin.demo.web.dao

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>26.12.2019</pre>
 * @param userId Уникальный номер пользователя
 *      Column(name = "umusr_user_id", nullable = false)
 * @param login Имя пользователя для входа в систему
 *      Column(name = "umusr_login", nullable = false)
 * @param name ФИО пользователя
 *      Column(name = "umusr_name", nullable = false)
 * @param password Пароль пользователя
 *      Column(name = "umusr_password", nullable = true)
 * @param roles Роли пользователя
 *      ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
 *      JoinTable(name = "um_user_role",
 *         joinColumns = @JoinColumn(name = "umusr_user_id"),
 *         inverseJoinColumns = @JoinColumn(name = "umrle_role_id")
 *      )
 * @param archived Признак архивации (не активная запись)
 *      Column(name = "umusr_archived", nullable = false)
 */
data class User(
    var userId: Long? = null,
    var login: String? = null,
    var name: String? = null,
    var password: String? = null,
    var roles: Array<Roles>? = null,
    var archived: Boolean? = null
)
