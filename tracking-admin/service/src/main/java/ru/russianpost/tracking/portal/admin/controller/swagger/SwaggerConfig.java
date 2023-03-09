/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmErrorResponse;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static java.util.Collections.singletonList;

/**
 * @author Amosov Maxim
 * @since 04.09.2021 : 19:03
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(final TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(createApiInfo())
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(ShowInSwagger.class))
            .paths(PathSelectors.any())
            .build()
            .additionalModels(typeResolver.resolve(IdmErrorResponse.class))
            .securitySchemes(singletonList(new BasicAuth("Basic")));
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo(
            "Tracking portal admin",
            "",
            getClass().getPackage().getImplementationVersion(),
            null,
            null,
            null,
            null,
            Collections.emptyList()
        );
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .docExpansion(DocExpansion.LIST)
            .build();
    }
}
