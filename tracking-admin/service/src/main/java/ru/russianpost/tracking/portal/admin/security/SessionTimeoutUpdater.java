/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.security;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.LoggerFactory;

/**
 * Http session listener, responsible for set session timeout programmatically, based on value from property file.
 *
 * @author sslautin
 * @version 1.0 07.10.2015
 */
public class SessionTimeoutUpdater implements HttpSessionListener {

    private static final String SESSION_TIMEOUT_PROPERTY = "session.timeout.seconds";

    private static final String PROPERTY_FILE = "session.properties";
    private static final int DEFAULT_SESSION_TIMEOUT = (int) TimeUnit.DAYS.toSeconds(2);

    private static final Properties PROPERTIES = loadProperties();

    @Override
    public void sessionCreated(final HttpSessionEvent se) {
        int timeout = DEFAULT_SESSION_TIMEOUT;
        String prop = PROPERTIES.getProperty(SESSION_TIMEOUT_PROPERTY, "");
        if (prop.chars().allMatch(Character::isDigit)) {
            timeout = Integer.parseInt(prop);
        }
        se.getSession().setMaxInactiveInterval(timeout);
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent se) {
        // NOP
    }

    /**
     * Loads properties from the resource files.
     *
     * @return option name-value pairs.
     */
    private static Properties loadProperties() {
        final Properties properties = new Properties();
        try (
            final Reader in = new InputStreamReader(
                SessionTimeoutUpdater.class.getClassLoader().getResourceAsStream(PROPERTY_FILE), UTF_8
            );
        ) {
            properties.load(in);
        } catch (IOException e) {
            LoggerFactory.getLogger(SessionTimeoutUpdater.class).error("Cannot load property file: {}", PROPERTY_FILE, e);
        }
        return properties;
    }
}
