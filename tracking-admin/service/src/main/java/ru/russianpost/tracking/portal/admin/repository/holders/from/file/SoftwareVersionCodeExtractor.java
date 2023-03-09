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

import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder.VersionPreprocessor;
import ru.russianpost.tracking.web.model.utils.Strings;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptySet;

/**
 * SoftwareVersionCodeExtractor
 *
 * @author aalekseenko
 */
public class SoftwareVersionCodeExtractor implements VersionPreprocessor {

    private static final Pattern CODE_PATTERN = Pattern.compile("^([^\\d\\p{Punct}]+)");
    private final Collection<String> exclusions;

    /**
     * Default constructor.
     */
    public SoftwareVersionCodeExtractor() {
        this(emptySet());
    }

    /**
     * Constructor
     *
     * @param exclusions list of values that should not be processed
     */
    public SoftwareVersionCodeExtractor(final Collection<String> exclusions) {
        this.exclusions = Optional.ofNullable(exclusions).orElseGet(Collections::emptySet);
    }

    @Override
    public String process(final String version) {
        if (Strings.isBlank(version)) {
            return null;
        }

        if (!this.exclusions.contains(version)) {
            final Matcher m = CODE_PATTERN.matcher(version);
            if (m.find()) {
                return m.group(1);
            }
        }
        return version;
    }
}
