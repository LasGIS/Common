package com.lasgis.kotlin.demo.web.mybatis

import com.lasgis.kotlin.demo.web.dao.User
import org.junit.jupiter.api.Test
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>31.12.2019</pre>
 */
@MybatisTest
class UserMapperTest(
    @Autowired private val userMapper: UserMapper
) {

    @Test
    fun findByLoginTest() {
        val user = userMapper.findByLogin("lasgis")
        Assertions.assertNotNull(user)
        user?.let {
            assertThat(it.userId).isEqualTo(1)
            assertThat(it.login).isEqualTo("LasGIS")
            assertThat(it.name).isEqualTo("San Francisco")
            assertThat(it.archived).isFalse()
        }
    }

}