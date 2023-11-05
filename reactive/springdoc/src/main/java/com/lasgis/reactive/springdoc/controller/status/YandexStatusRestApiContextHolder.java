/*
 *  @(#)YandexStatusRestApiContextHolder.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.status;

import com.lasgis.reactive.springdoc.model.status.GetYandexOrderStatusRestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class YandexStatusRestApiContextHolder {

    private static final String REST_API_CONTEXT_KEY = "yandex.rest.api.query.status.context";

    public Mono<GetYandexOrderStatusRestRequest> initContext(
        final GetYandexOrderStatusRestRequest request,
        final ServerWebExchange serverWebExchange
    ) {
        return Mono.justOrEmpty(request)
            .map(req -> YandexStatusRestApiContext.builder()
                .uniq(req.getUniq())
                .ordersId(req.getRequest().getOrdersId())
                .build()
            )
            .doOnNext(ctx -> serverWebExchange.getAttributes().put(REST_API_CONTEXT_KEY, ctx))
            .thenReturn(request);
    }

    public Mono<YandexStatusRestApiContext> getContext(final ServerWebExchange serverWebExchange) {
        return Mono.justOrEmpty(serverWebExchange.getAttributes().get(REST_API_CONTEXT_KEY))
            .filter(YandexStatusRestApiContext.class::isInstance)
            .map(YandexStatusRestApiContext.class::cast);
    }
}
