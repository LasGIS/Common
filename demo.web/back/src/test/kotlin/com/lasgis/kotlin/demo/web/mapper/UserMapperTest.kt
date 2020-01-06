package com.lasgis.kotlin.demo.web.mapper

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase

/**
 * Тест для проверки запросов UserMapper
 *
 * @author VLaskin
 * @since <pre>31.12.2019</pre>
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    lateinit var userMapper: UserMapper

    @Test
    fun findByIdTest() {
        val user = userMapper.findById(1L)
        assertNotNull(user)
        user?.let {
            assertThat(it.userId).isEqualTo(1)
            assertThat(it.login).isEqualTo("LasGIS")
            assertThat(it.name).isEqualTo("Владимир Ласкин")
            assertThat(it.archived).isFalse()
        }
    }

    @Test
    fun findByLoginTest() {
        val user = userMapper.findByLogin("LasGIS")
        assertNotNull(user)
        user?.let {
            assertThat(it.userId).isEqualTo(1)
            assertThat(it.login).isEqualTo("LasGIS")
            assertThat(it.name).isEqualTo("Владимир Ласкин")
            assertThat(it.archived).isFalse()
            assertThat(it.roles).isNotNull
            assertThat(it.roles?.stream()?.map { role -> role.roleId }).contains("ADMIN", "CHIEF", "SUPERVISOR")
        }
    }

}