package com.lasgis.kotlin.demo.web.rest

import com.lasgis.kotlin.demo.web.dao.User
import com.lasgis.kotlin.demo.web.dao.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>10.01.2020</pre>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class UserControllerTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun `Получаем пользователя по login = LasGIS`() {
        val response: ResponseEntity<User> = restTemplate.getForEntity<User>("/user?login=LasGIS", User::javaClass)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        val user: User? = response.body
        assertNotNull(user)
        user?.let {
            assertThat(it.userId).isEqualTo(1)
            assertThat(it.login).isEqualTo("LasGIS")
            assertThat(it.name).isEqualTo("Владимир Ласкин")
            assertThat(it.archived).isFalse()
            assertThat(it.roles).isNotNull
            assertThat(it.roles).contains(UserRole.ADMIN, UserRole.CHIEF, UserRole.SUPERVISOR)
        }
    }
}
