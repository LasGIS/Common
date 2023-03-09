/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.repository.holders.from.file;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static ru.russianpost.tracking.web.model.utils.Strings.isBlank;


/**
 * Holder of dictionary info of versions to names.
 *
 * @author aalekseenko
 */
public class SoftwareHolder {

    private static final VersionPreprocessor NOOP_PREPROCESSOR = v -> v;

    private final Map<String, String> names;
    private final VersionPreprocessor preprocessor;
    private final String notFoundName;

    /**
     * Constructor.
     *
     * @param names known software names. If argument is {@code null} then {@link java.util.Collections#emptyMap()} will be used.
     * @param notFoundName value that will be returned if name not found for given version. The value can be {@code null}.
     *
     * @see #SoftwareHolder(Map, VersionPreprocessor, String)
     */
    public SoftwareHolder(
        final Map<String, String> names,
        final String notFoundName
    ) {
        this(names, null, notFoundName);
    }

    /**
     * Constructor.
     *
     * @param names known software names. If argument is {@code null} then {@link java.util.Collections#emptyMap()} will be used.
     * @param preprocessor preprocessor of software version. If argument is {@code null} then {@link SoftwareHolder#NOOP_PREPROCESSOR} will be used.
     * @param notFoundName value that will be returned if name not found for given version. The value can be {@code null}.
     */
    public SoftwareHolder(
        final Map<String, String> names,
        final VersionPreprocessor preprocessor,
        final String notFoundName
    ) {
        this.names = names == null ? emptyMap() : names;
        this.preprocessor = preprocessor == null ? NOOP_PREPROCESSOR : preprocessor;
        this.notFoundName = notFoundName;
    }

    /**
     * Returns software name for the given version.
     *
     * @param version version of software
     * @return name or second argument of constructor {@link #SoftwareHolder(Map, String)} when
     *      <ul>
     *          <li>if the {@code version} is blank</li>
     *          <li>if the {@code version} is mapped to blank</li>
     *      </ul>
     *
     * @see ru.russianpost.tracking.web.model.utils.Strings#isBlank(CharSequence)
     */
    public String getSoftwareNameByVersion(final String version) {
        final String code = this.preprocessor.process(version);
        if (isBlank(code)) {
            return this.notFoundName;
        }
        final String result = this.names.get(code);
        return isBlank(result) ? this.notFoundName : result;
    }

    /**
     * Check is the software code known.
     * @param softwareCode software code
     * @return {@code true} if the software code is known
     */
    public boolean isKnownSoftwareCode(final String softwareCode) {
        return names.containsKey(softwareCode);
    }

    /**
     * Gets software name by code.
     * @param softwareCode software code
     * @return software name by code
     */
    public String getSoftwareNameByCode(final String softwareCode) {
        return names.getOrDefault(softwareCode, notFoundName);
    }

    /**
     * VersionPreprocessor
     *
     * @author aalekseenko
     */
    @FunctionalInterface
    public interface VersionPreprocessor {

        /**
         * @param version value that should be processed
         * @return processed version
         */
        String process(String version);

    }

}
