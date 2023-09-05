/*
 *  @(#)ControllerConfiguration.java  last: 05.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

/**
 * The Class ControllerConfiguration definition.
 *
 * @author VLaskin
 * @since 05.09.2023 : 16:50
 */
@Slf4j
@Configuration
public class ControllerConfiguration {
    @Bean
    ForwardedHeaderTransformer forwardedHeaderTransformer() {
        ForwardedHeaderTransformer transformer = new ForwardedHeaderTransformer();
        transformer.setRemoveOnly(true);
        return transformer;
    }
}
