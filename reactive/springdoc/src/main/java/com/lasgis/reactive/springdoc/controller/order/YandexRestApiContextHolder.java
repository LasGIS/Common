/*
 *  @(#)YandexRestApiContextHolder.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.order;

import com.lasgis.reactive.springdoc.model.order.GetYandexOrderRestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class YandexRestApiContextHolder {

    private static final String REST_API_CONTEXT_KEY = "yandex.rest.api.query.context";

    public Mono<GetYandexOrderRestRequest> initContext(
        final GetYandexOrderRestRequest request,
        final ServerWebExchange serverWebExchange
    ) {
        return Mono.justOrEmpty(request)
            .map(req -> YandexRestApiContext.builder()
                .uniq(req.getUniq())
                .orderId(req.getRequest().getOrderId())
                .build()
            )
            .doOnNext(ctx -> serverWebExchange.getAttributes().put(REST_API_CONTEXT_KEY, ctx))
            .thenReturn(request);
    }

    public Mono<YandexRestApiContext> getContext(final ServerWebExchange serverWebExchange) {
        return Mono.justOrEmpty(serverWebExchange.getAttributes().get(REST_API_CONTEXT_KEY))
            .filter(YandexRestApiContext.class::isInstance)
            .map(YandexRestApiContext.class::cast);
    }
}
