/*
 *  @(#)WebConfiguration.java  last: 02.07.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.prototype.git.admin.configuration;

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
