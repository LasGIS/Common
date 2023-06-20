/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package com.lasgis.prototype.git.admin.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Внутренние настройки
 *
 * @author VLaskin
 * @since <pre>25.10.2022</pre>
 */
@Component
@ConfigurationProperties("settings")
@Data
public class AppSettingsConfig {
    private String name;
    private String version;
}
