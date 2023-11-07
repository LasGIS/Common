/*
 *  @(#)OpenAPIConfiguration.java  last: 07.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class OpenAPIConfiguration definition.
 *
 * @author Vladimir Laskin
 * @since 02.11.2023 : 22:39
 */
@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(new Info().title("Contact Application API").description(
                "This is a sample Spring Boot RESTful service using springdoc-openapi and OpenAPI 3."));
    }
}
