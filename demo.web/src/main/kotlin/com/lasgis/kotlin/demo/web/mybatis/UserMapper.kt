package com.lasgis.kotlin.demo.web.mybatis

import com.lasgis.kotlin.demo.web.dao.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>26.12.2019</pre>
 */
@Mapper
interface UserMapper {
    @Insert("INSERT INTO city (name, state, country) VALUES(#{name}, #{state}, #{country})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(user: User?)

    @Select("SELECT id, name, state, country FROM city WHERE id = #{id}")
    fun findById(id: Long): User?
}