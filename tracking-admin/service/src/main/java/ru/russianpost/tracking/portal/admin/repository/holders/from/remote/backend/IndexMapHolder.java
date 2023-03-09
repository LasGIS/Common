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
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import ru.russianpost.tracking.web.model.info.IndexInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 27, 2015)
 */
public class IndexMapHolder extends RestMapHolder<String, Object, IndexInfo, IndexInfo, Object> {

    private int batchSize = 10_000;

    /**
     * IndexMapHolder
     * @param uriBuilder   uriBuilder
     * @param restTemplate restTemplate
     */
    public IndexMapHolder(final UriComponentsBuilder uriBuilder, final RestTemplate restTemplate) {
        super(
            uriBuilder.queryParam("from", "{from}").queryParam("count", "{count}").build().toUriString(),
            restTemplate,
            IndexInfo::getId,
            identity(),
            identity(),
            v -> null
        );
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public void load() {
        final String uri = uri();
        log().info("Loading dictionary {}...", uri);

        int from = 0;
        final UriTemplate uriTpl = new UriTemplate(uri);
        DictionaryResponse<IndexInfo, Object> response = null;
        List<DictionaryResponse<IndexInfo, Object>> responses = new ArrayList<>();
        do {
            final URI expandedURI = uriTpl.expand(from, batchSize);
            response = restTemplate().getForObject(expandedURI, responseType());
            if (response != null) {
                final int size = response.getItems().size();
                log().info("Received dictionary {} entries: {}...", expandedURI, size);
                responses.add(response);
                from += size;
            }
        } while (response != null && requestDataAgain(response));
        processResponse(uri, mergeResponses(responses));
    }

    private DictionaryResponse<IndexInfo, Object> mergeResponses(List<DictionaryResponse<IndexInfo, Object>> responses) {
        final DictionaryResponse<IndexInfo, Object> result = new DictionaryResponse<>();
        result.setItems(responses.stream()
            .map(DictionaryResponse::getItems)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .collect(Collectors.toList()));
        result.setDefaultValues(responses.stream()
            .map(DictionaryResponse::getDefaultValues)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .collect(Collectors.toList()));
        return result;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    private boolean requestDataAgain(final DictionaryResponse<IndexInfo, Object> response) {
        return ofNullable(response.getItems()).map(l -> l.size() >= batchSize).orElse(FALSE);
    }

    @Override
    protected Class<? extends DictionaryResponse<IndexInfo, Object>> responseType() {
        return IndexMapResponse.class;
    }

    /**
     * IndexMapResponse
     */
    private static class IndexMapResponse extends DictionaryResponse<IndexInfo, Object> {
    }

}
