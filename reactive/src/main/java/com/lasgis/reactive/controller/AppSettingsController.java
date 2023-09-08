/*
 *  @(#)AppSettingsController.java  last: 08.09.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.controller;

import com.lasgis.reactive.configuration.AppSettingsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST для получения внутренних настроек приложения
 *
 * @author VLaskin
 * @since <pre>25.10.2022</pre>
 */
@RestController
@RequestMapping("api/v2.0")
@RequiredArgsConstructor
public class AppSettingsController {

    private final AppSettingsConfig appSettingsConfig;

    /**
     * Get app settings config
     *
     * @return app settings config
     */
    @GetMapping(value = "/settings")
    public AppSettingsConfig getAppSettings() {
        return appSettingsConfig;
    }
}
