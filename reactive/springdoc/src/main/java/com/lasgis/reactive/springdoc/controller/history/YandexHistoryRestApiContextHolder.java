/*
 *  @(#)YandexHistoryRestApiContextHolder.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.history;

import com.lasgis.reactive.springdoc.model.history.GetYandexOrderHistoryRestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class YandexHistoryRestApiContextHolder {

    private static final String REST_API_CONTEXT_KEY = "yandex.rest.api.query.history.context";

    public Mono<GetYandexOrderHistoryRestRequest> initContext(
        final GetYandexOrderHistoryRestRequest request,
        final ServerWebExchange serverWebExchange
    ) {
        return Mono.justOrEmpty(request)
            .map(req -> YandexHistoryRestApiContext.builder()
                .uniq(req.getUniq())
                .orderId(req.getRequest().getOrderId())
                .build()
            )
            .doOnNext(ctx -> serverWebExchange.getAttributes().put(REST_API_CONTEXT_KEY, ctx))
            .thenReturn(request);
    }

    public Mono<YandexHistoryRestApiContext> getContext(final ServerWebExchange serverWebExchange) {
        return Mono.justOrEmpty(serverWebExchange.getAttributes().get(REST_API_CONTEXT_KEY))
            .filter(YandexHistoryRestApiContext.class::isInstance)
            .map(YandexHistoryRestApiContext.class::cast);
    }
}
