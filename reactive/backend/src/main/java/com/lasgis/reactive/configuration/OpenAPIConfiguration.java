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
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

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

    @Bean
    public OpenApiCustomiser sortOperationsByTagName() {
        return openApi -> {
            Paths paths = openApi.getPaths().entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> getOperationTag(entry.getValue())))
                .collect(Paths::new, (map, item) -> map.addPathItem(item.getKey(), item.getValue()), Paths::putAll);

            openApi.setPaths(paths);
        };
    }

    private String getOperationTag(PathItem pathItem) {
        return Stream.of(pathItem.getGet(), pathItem.getPost(), pathItem.getDelete(), pathItem.getPut(),
                pathItem.getHead(), pathItem.getOptions(), pathItem.getTrace(), pathItem.getPatch())
            .filter(Objects::nonNull)
            .map(Operation::getTags)
            .flatMap(Collection::stream)
            .findFirst()
            .orElse("");
    }
}
