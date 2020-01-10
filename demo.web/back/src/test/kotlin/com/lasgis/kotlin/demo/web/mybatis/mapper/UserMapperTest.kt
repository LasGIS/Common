package com.lasgis.kotlin.demo.web.mybatis.mapper

import com.lasgis.kotlin.demo.web.dao.User
import com.lasgis.kotlin.demo.web.dao.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.dao.DuplicateKeyException

/**
 * Тест для проверки запросов UserMapper
 *
 * @author VLaskin
 * @since <pre>31.12.2019</pre>
 */
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest(@Autowired val userMapper: UserMapper) {

    @Test
    fun findByIdTest() {
        val user = userMapper.findById(1L)
        assertThat(user).isNotNull
        user?.let {
            assertThat(it.userId).isEqualTo(1)
            assertThat(it.login).isEqualTo("LasGIS")
            assertThat(it.name).isEqualTo("Владимир Ласкин")
            assertThat(it.archived).isFalse()
            assertThat(it.roles).isNull()
        }
    }

    @Test
    fun findByLoginTest() {
        val user = userMapper.findByLogin("LasGIS")
        assertThat(user).isNotNull
        user?.let {
            assertThat(it.userId).isEqualTo(1)
            assertThat(it.login).isEqualTo("LasGIS")
            assertThat(it.name).isEqualTo("Владимир Ласкин")
            assertThat(it.archived).isFalse()
            assertThat(it.roles).isNotNull
            assertThat(it.roles).contains(UserRole.ADMIN, UserRole.CHIEF, UserRole.SUPERVISOR)
        }
    }

    @Test
    fun insertUserTest() {
        val user = User("Vasili", "Вассиссуарий Лоханкин", "21345")
        userMapper.insertUser(user)
        assertThat(user.userId).isNotNull()
        assertThat(user.userId).isGreaterThan(0)
        println(" --- userId = ${user.userId} ---")
    }

    @Test
    fun `insert already exist User` () {
        val user = User("VPupkin", "Вассиссуарий Пупкин", "4321")
        assertThrows<DuplicateKeyException> {
            userMapper.insertUser(user)
        }
    }
}