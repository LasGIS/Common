package com.lasgis.kotlin.demo.web.mybatis.mapper

import com.lasgis.kotlin.demo.web.dao.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
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

    @Insert("""
        INSERT INTO um_user (umusr_name, umusr_login, umusr_password, umusr_archived)
         VALUES(#{name}, #{login}, #{password}, #{archived})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    fun insertUser(user: User): Int

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