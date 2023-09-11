/*
 *  @(#)WebConfiguration.java  last: 11.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for resolving paths of frontend SPA.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/{spring:\\w+}")
            .setViewName("forward:/");
      registry.addViewController("/*/{spring:\\w+}")
            .setViewName("forward:/");
//      registry.addViewController("/{spring:\\w+}/{spring:?!(\\.js|\\.css)$}")
//            .setViewName("forward:/");
  }
}
