/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security.ad;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;
import ru.russianpost.tracking.portal.admin.security.LdapAndDBUserDetailsMapper;

/**
 * ActiveDirectoryProvidersFactoryBean
 *
 * @author aalekseenko
 */
public class ActiveDirectoryProvidersFactoryBean implements FactoryBean<Collection<ActiveDirectoryLdapAuthenticationProvider>>, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveDirectoryProvidersFactoryBean.class);
    private static final ArrayType CONFIG_COLLECTION_TYPE = TypeFactory.defaultInstance().constructArrayType(Config.class);

    private Collection<ActiveDirectoryLdapAuthenticationProvider> providers = emptyList();

    private Resource adConfigResource;
    private ServiceUserDao userRepo;

    public void setAdConfigResource(final Resource value) {
        this.adConfigResource = value;
    }

    public void setUserRepo(final ServiceUserDao value) {
        this.userRepo = value;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (
            this.adConfigResource == null
            || !this.adConfigResource.exists()
        ) {
            LOG.warn("adConfigResource is null or not exist");
        } else {
            try (
                Reader configReader = new InputStreamReader(this.adConfigResource.getInputStream(), UTF_8)
            ) {
                final Config[] configs = new ObjectMapper().readValue(configReader, CONFIG_COLLECTION_TYPE);
                if (!isEmpty(configs)) {
                    this.providers = asList(
                        stream(configs)
                            .map(cfg -> {
                                final ActiveDirectoryLdapAuthenticationProvider result = (
                                    new ActiveDirectoryLdapAuthenticationProvider(cfg.domain, cfg.url, cfg.rootDn)
                                );
                                if (hasText(cfg.searchFilter)) {
                                    result.setSearchFilter(cfg.searchFilter);
                                }
                                result.setUserDetailsContextMapper(
                                    new LdapAndDBUserDetailsMapper(new LdapUserDetailsMapper(), this.userRepo)
                                );
                                return result;
                            }).toArray(ActiveDirectoryLdapAuthenticationProvider[]::new)
                    );
                }
            }
        }
    }

    @Override
    public Collection<ActiveDirectoryLdapAuthenticationProvider> getObject() throws Exception {
        return this.providers;
    }

    @Override
    public Class<?> getObjectType() {
        return Collection.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * Config
     */
    private static final class Config {

        @JsonProperty(access = WRITE_ONLY, value = "url")
        private String url;

        @JsonProperty(access = WRITE_ONLY, value = "domain")
        private String domain;

        @JsonProperty(access = WRITE_ONLY, value = "rootDn")
        private String rootDn;

        @JsonProperty(access = WRITE_ONLY, value = "searchFilter")
        private String searchFilter;

    }
}
