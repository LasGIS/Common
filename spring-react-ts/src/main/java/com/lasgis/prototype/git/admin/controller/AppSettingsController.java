/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.lasgis.prototype.git.admin.controller;

import com.lasgis.prototype.git.admin.configuration.AppSettingsConfig;
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
@RequestMapping("/v1.0")
@RequiredArgsConstructor
public class AppSettingsController {

    private final AppSettingsConfig appSettingsConfig;

    /**
     * Get app settings config
     * @return app settings config
     */
    @GetMapping(value = "/settings")
    public AppSettingsConfig getAppSettings() {
        return appSettingsConfig;
    }
}
