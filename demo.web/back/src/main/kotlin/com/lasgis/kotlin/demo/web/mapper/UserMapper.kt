package com.lasgis.kotlin.demo.web.mapper

import com.lasgis.kotlin.demo.web.dao.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>26.12.2019</pre>
 */
@Mapper
interface UserMapper {

//    @Insert("INSERT INTO um_user (umusr_name, umusr_login, umusr_archived) VALUES(#{name}, #{state}, #{country})")
//    @Options(useGeneratedKeys = true, keyProperty = "umusr_user_id")
//    fun insert(user: User?)

    @Select("""
      SELECT
        umusr_user_id as userId,
        umusr_name as name,
        umusr_login as login,
        umusr_password as password,
        umusr_archived as archived
      FROM um_user
      WHERE umusr_user_id = #{id}
     """)
    fun findById(@Param("id") id: Long): User?

    fun findByLogin(@Param("login") login: String): User?
}