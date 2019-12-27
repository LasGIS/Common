package com.lasgis.kotlin.demo.web.config

import org.apache.ibatis.annotations.Mapper
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>27.12.2019</pre>
 */
@Configuration
@MapperScan(value = ["com.lasgis.kotlin.demo.web.mybatis"], annotationClass = Mapper::class)
class MyBatisConfig