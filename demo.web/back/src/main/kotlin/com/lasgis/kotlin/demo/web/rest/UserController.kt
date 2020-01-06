package com.lasgis.kotlin.demo.web.rest

import com.lasgis.kotlin.demo.web.mybatis.mapper.UserMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>26.12.2019</pre>
 */
@RestController
@RequestMapping("/user")
class UserController(private val userMapper: UserMapper) {

    @GetMapping
    fun user(@RequestParam(value = "login", defaultValue = "VPupkin") login: String) =
        userMapper.findByLogin(login)
}