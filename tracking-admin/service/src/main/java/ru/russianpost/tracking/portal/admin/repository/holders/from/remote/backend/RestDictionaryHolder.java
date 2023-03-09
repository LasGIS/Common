/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * RestDictionaryHolder
 *
 * @param <ItemsType> type of response items
 * @param <DefaultsType> type of default values
 */
public abstract class RestDictionaryHolder<ItemsType, DefaultsType> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String uri;
    private final RestTemplate restTemplate;

    /**
     * RestDictionaryHolder
     *
     * @param uri uri
     * @param restTemplate restTemplate
     */
    protected RestDictionaryHolder(final String uri, final RestTemplate restTemplate) {
        this.uri = uri;
        this.restTemplate = restTemplate;
    }

    /**
     * Loading dictionary
     */
    public void load() {
        final String dictionaryUri = uri();
        this.log.info("Loading dictionary {}...", dictionaryUri);

        try {
            processResponse(dictionaryUri, restTemplate().getForObject(dictionaryUri, responseType()));
        } catch (RestClientException e) {
            log.error("Error to load dictionary '{}'. Error message: {}", dictionaryUri, e.getMessage());
            throw e;
        }
    }

    /**
     * @param partUri uri of part of dictionary
     * @param response received response
     */
    protected abstract void processResponse(String partUri, DictionaryResponse<ItemsType, DefaultsType> response);

    /**
     * @return parametrized class of dictionary response
     */
    protected abstract Class<? extends DictionaryResponse<ItemsType, DefaultsType>> responseType();

    /**
     * @return uri
     */
    public String uri() {
        return this.uri;
    }

    /**
     * @return restTemplate
     */
    public RestTemplate restTemplate() {
        return this.restTemplate;
    }

    /**
     * @return get logger
     */
    protected Logger log() {
        return this.log;
    }

    /**
     * DictionaryResponse
     *
     * @param <ItemsType> type of item element
     * @param <DefaultsType> type of default element
     */
    protected static class DictionaryResponse<ItemsType, DefaultsType> {

        private List<ItemsType> items;
        private List<DefaultsType> defaultValues;

        public List<ItemsType> getItems() {
            return this.items;
        }

        public void setItems(final List<ItemsType> items) {
            this.items = items;
        }

        public List<DefaultsType> getDefaultValues() {
            return this.defaultValues;
        }

        public void setDefaultValues(final List<DefaultsType> defaultValues) {
            this.defaultValues = defaultValues;
        }

    }

    /** SimpleDictionaryValue
     *
     * @param <K> type of key
     * @param <V> type of value
     */
    protected static class SimpleDictionaryValue<K, V> {

        private K key;
        private V value;

        public K getKey() {
            return this.key;
        }

        public void setKey(final K key) {
            this.key = key;
        }

        public V getValue() {
            return this.value;
        }

        public void setValue(final V value) {
            this.value = value;
        }

    }

}
