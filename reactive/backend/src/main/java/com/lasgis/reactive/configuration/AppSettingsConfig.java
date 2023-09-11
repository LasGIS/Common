/*
 *  @(#)AppSettingsConfig.java  last: 05.09.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.configuration;

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
