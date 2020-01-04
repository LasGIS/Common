package com.lasgis.kotlin.demo.web.rest

import com.lasgis.kotlin.demo.web.dao.Greeting
import com.lasgis.kotlin.demo.web.mybatis.UserMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>04.01.2020</pre>
 */
@RestController
@RequestMapping("/greeting")
class GreetingController {

    val counter = AtomicLong()

    @GetMapping("/greeting")
    fun greeting(@RequestParam(value = "name", defaultValue = "World") name: String) =
        Greeting(counter.incrementAndGet(), "Hello, $name")
}