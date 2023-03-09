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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Factory of holder of dictionary info of versions to names.
 *
 * @author aalekseenko
 */
@Slf4j
public class SoftwareHolderFactoryBean implements FactoryBean<SoftwareHolder>, InitializingBean {


    private Resource knownNamesResource;
    private SoftwareHolder holder;

    /**
     * Setter for {@link #knownNamesResource}
     *
     * @param value resource that contains json with known software names
     */
    public void setKnownNames(final Resource value) {
        this.knownNamesResource = value;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (
            this.knownNamesResource == null
            || !this.knownNamesResource.exists()
        ) {
            log.warn("knownNamesResource is null or not exist");
        } else {
            try (
                Reader configReader = new InputStreamReader(this.knownNamesResource.getInputStream(), UTF_8)
            ) {
                final Config config = new ObjectMapper().readValue(configReader, Config.class);
                this.holder = new SoftwareHolder(config.knownNames, new SoftwareVersionCodeExtractor(config.exclusions), config.notFoundName);
            }
        }
    }

    @Override
    public SoftwareHolder getObject() throws Exception {
        return this.holder;
    }
    @Override
    public Class<?> getObjectType() {
        return SoftwareHolder.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Config
     */
    private static final class Config {

        @JsonProperty(access = WRITE_ONLY, value = "knownNames")
        private Map<String, String> knownNames;

        @JsonProperty(access = WRITE_ONLY, value = "exclusions")
        private Collection<String> exclusions;

        @JsonProperty(access = WRITE_ONLY, value = "notFoundName")
        private String notFoundName;

    }
}
