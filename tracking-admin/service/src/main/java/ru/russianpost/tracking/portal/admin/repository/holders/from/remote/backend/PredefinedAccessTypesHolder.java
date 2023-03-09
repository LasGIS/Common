/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend;

import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.web.model.core.AccessType;

/**
 * Holder of predefined access types configuration.
 *
 * @author KKiryakov
 */
public class PredefinedAccessTypesHolder extends RestListHolder<AccessType> {

    /**
     * Constructor.
     *
     * @param uri uri
     * @param restTemplate restTemplate
     */
    public PredefinedAccessTypesHolder(
        String uri,
        RestTemplate restTemplate
    ) {
        super(restTemplate, uri);
    }

    @Override
    protected Class<? extends DictionaryResponse<AccessType, AccessType>> responseType() {
        return PredefinedAccessTypesResponse.class;
    }

    /** Predefined access types response. */
    private static final class PredefinedAccessTypesResponse
        extends DictionaryResponse<AccessType, AccessType> {
    }
}
