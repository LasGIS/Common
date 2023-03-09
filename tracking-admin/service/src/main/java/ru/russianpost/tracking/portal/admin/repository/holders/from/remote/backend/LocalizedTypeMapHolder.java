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

import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.web.model.info.LocalizedKey;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 27, 2015)
 */
public class LocalizedTypeMapHolder extends RestMapHolder<
    LocalizedKey,
    Object,
    String,
    RestDictionaryHolder.SimpleDictionaryValue<LocalizedKey, String>,
    RestDictionaryHolder.SimpleDictionaryValue<Object, String>
    > {

    /**
     * LocalizedTypeMapHolder
     * @param uri          uri
     * @param restTemplate restTemplate
     */
    public LocalizedTypeMapHolder(final String uri, final RestTemplate restTemplate) {
        super(
            uri,
            restTemplate,
            SimpleDictionaryValue::getKey,
            SimpleDictionaryValue::getValue,
            SimpleDictionaryValue::getKey,
            SimpleDictionaryValue::getValue
        );
    }

    @Override
    protected Class<? extends DictionaryResponse<SimpleDictionaryValue<LocalizedKey, String>, SimpleDictionaryValue<Object, String>>> responseType() {
        return LocalizedType.class;
    }

    /**
     * Gets map by locale.
     * @param langCode lang code
     * @return map by locale
     */
    public Map<Integer, String> getMapByLocale(String langCode) {
        return getMap().entrySet()
            .stream()
            .filter(e -> e.getKey().getLangCode().equals(langCode))
            .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));
    }

    /**
     * Localized Type
     */
    private static final class LocalizedType extends DictionaryResponse<
        SimpleDictionaryValue<LocalizedKey, String>,
        SimpleDictionaryValue<Object, String>
        > {
    }

}
