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
import ru.russianpost.tracking.web.model.info.LocalizedOperationKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 27, 2015)
 */
public class LocalizedOperationAttributeMapHolder extends RestMapHolder<
    LocalizedOperationKey,
    String,
    String,
    RestDictionaryHolder.SimpleDictionaryValue<LocalizedOperationKey, String>,
    RestDictionaryHolder.SimpleDictionaryValue<String, String>
    > {

    /**
     * LocalizedOperationAttributeMapHolder
     * @param uri          uri
     * @param restTemplate restTemplate
     */
    public LocalizedOperationAttributeMapHolder(final String uri, final RestTemplate restTemplate) {
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
    protected Class<? extends DictionaryResponse<
        SimpleDictionaryValue<LocalizedOperationKey, String>,
        SimpleDictionaryValue<String, String>>
        > responseType() {
        return OperAttr.class;
    }

    @Override
    protected String toDefaultKey(final LocalizedOperationKey key) {
        return key.getLangCode();
    }

    /**
     * Gets map by locale.
     *
     * <p>Method returns Map of maps. It's map where key is operation type and value is a map of operation attributes to its values.</p>
     * @param langCode lang code
     * @return map by locale
     */
    public Map<Integer, Map<Integer, String>> getMapByLocale(String langCode) {
        Objects.requireNonNull(langCode);
        Map<Integer, Map<Integer, String>> result = new HashMap<>();
        for (Map.Entry<LocalizedOperationKey, String> entry : getMap().entrySet()) {
            if (langCode.equals(entry.getKey().getLangCode())) {
                final int operTypeId = entry.getKey().getOperTypeId();
                final int operAttrId = entry.getKey().getOperAttributeId();
                if (!result.containsKey(operTypeId)) {
                    result.put(operTypeId, new HashMap<>());
                }
                result.get(operTypeId).put(operAttrId, entry.getValue());
            }
        }
        return result;
    }

    /**
     * Operation Attribute
     */
    private static final class OperAttr extends DictionaryResponse<
        SimpleDictionaryValue<LocalizedOperationKey,
            String>, SimpleDictionaryValue<String, String>
        > {
    }

}
