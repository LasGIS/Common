/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend;

import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.web.model.info.BigIntegerLocalizedKey;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MKitchenko
 * @version 1.0 (May 29, 2019)
 */
public class PostMarkMapHolder extends RestMapHolder<
    BigIntegerLocalizedKey,
    Object,
    String,
    RestDictionaryHolder.SimpleDictionaryValue<BigIntegerLocalizedKey, String>,
    RestDictionaryHolder.SimpleDictionaryValue<Object, String>
    > {

    /**
     * PostMarkMapHolder
     * @param uri          uri
     * @param restTemplate restTemplate
     */
    public PostMarkMapHolder(final String uri, final RestTemplate restTemplate) {
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
    protected Class<? extends DictionaryResponse<SimpleDictionaryValue<BigIntegerLocalizedKey, String>, SimpleDictionaryValue<Object, String>>>
    responseType() {
        return LocalizedType.class;
    }

    /**
     * Gets map by locale.
     * @param langCode lang code
     * @return map by locale
     */
    public Map<BigInteger, String> getMapByLocale(String langCode) {
        return getMap().entrySet()
            .stream()
            .filter(e -> e.getKey().getLangCode().equals(langCode))
            .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));
    }

    /**
     * Localized Type
     */
    private static final class LocalizedType extends DictionaryResponse<
        SimpleDictionaryValue<BigIntegerLocalizedKey, String>,
        SimpleDictionaryValue<Object, String>
        > {
    }
}
