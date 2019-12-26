package com.lasgis.kotlin.demo.web.dao

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>26.12.2019</pre>
 */
data class User(
    var userId: Long,
    var login: String,
    var name: String,
    var password: String,
    var roles: Array<Roles>,
    var archived: Boolean
)
///*    {
//
//
//    /**
//     * Уникальный номер пользователя
//     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name = "umusr_user_id", nullable = false)
//    private ;
//
//    /**
//     * Имя пользователя для входа в систему
//     */
//    @Column(name = "umusr_login", nullable = false)
//    private  ;
//
//    /**
//     * ФИО пользователя
//     */
//    @Column(name = "umusr_name", nullable = false)
//
//    /**
//     * пароль пользователя
//     */
//    @Column(name = "umusr_password", nullable = true)
//    val  name: String;
//    val  password: String;
//
//    /**
//     * Признак архивации (не активная запись)
//     */
//    @Column(name = "umusr_archived", nullable = false)
//    private String archived;
//
//    /**
//     * Роли пользователя
//     */
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "um_user_role",
//        joinColumns = @JoinColumn(name = "umusr_user_id"),
//        inverseJoinColumns = @JoinColumn(name = "umrle_role_id")
//    )
//    private List<RoleEntity> roles;
//
//}
//*/