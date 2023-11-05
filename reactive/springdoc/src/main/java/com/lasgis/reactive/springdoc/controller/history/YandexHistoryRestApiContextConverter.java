/*
 *  @(#)YandexHistoryRestApiContextConverter.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.history;

import com.lasgis.reactive.springdoc.model.common.RequestState;
import com.lasgis.reactive.springdoc.model.common.ResourceId;
import com.lasgis.reactive.springdoc.model.exception.DomainException;
import com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode;
import com.lasgis.reactive.springdoc.model.history.YandexErrorHistoryRestResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.lasgis.reactive.springdoc.util.LangUtil.ifNotNull;
import static com.lasgis.reactive.springdoc.util.LangUtil.ifNotNullConsume;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Component
public class YandexHistoryRestApiContextConverter {

    private static final String INTERNAL_SERVER_ERROR = "Internal server error";

    public YandexErrorHistoryRestResponse toRest(
        final YandexHistoryRestApiContext context,
        final ServerWebExchange serverWebExchange,
        final YandexRestApiErrorCode errorCode,
        final Exception ex
    ) {
        return new YandexErrorHistoryRestResponse()
            .setUniq(ifNotNull(context, YandexHistoryRestApiContext::getUniq))
            .setResponse(toRest(context))
            .setRequestState(toRest(ifNotNull(serverWebExchange, ServerWebExchange::getRequest), errorCode, ex));
    }

    protected YandexErrorHistoryRestResponse.Response toRest(YandexHistoryRestApiContext context) {
        var response = new YandexErrorHistoryRestResponse.Response();
        response.setType(context.getType());
        response.setOrderStatusHistory(toOrderStatusHistory(context.getOrderId()));
        return response;
    }

    protected YandexErrorHistoryRestResponse.OrderStatusHistory toOrderStatusHistory(ResourceId orderId) {
        var orderStatusHistory = new YandexErrorHistoryRestResponse.OrderStatusHistory();
        orderStatusHistory.setOrderId(orderId);
        return orderStatusHistory;
    }

    protected RequestState toRest(
        final ServerHttpRequest request,
        final YandexRestApiErrorCode code,
        final Exception ex
    ) {
        final var errorCode = new RequestState.ErrorCode()
            .setDescription(getDescription(request, code))
            .setMessage(getMessage(ex));
        ifNotNullConsume(code, i -> errorCode.setCode(i.getCode()));
        return new RequestState()
            .setIsError(true)
            .setErrorCodes(List.of(errorCode));
    }

    private String getDescription(
        final ServerHttpRequest request,
        final YandexRestApiErrorCode code
    ) {
        return Stream.of(ifNotNull(code, YandexRestApiErrorCode::name), ifNotNull(request, ServerHttpRequest::getId))
            .filter(Objects::nonNull)
            .collect(joining(SPACE));
    }

    protected String getMessage(final Exception ex) {
        if (ex == null) {
            return null;
        }
        if (ex instanceof DomainException) {
            return ex.getMessage();
        }
        return INTERNAL_SERVER_ERROR;
    }

}
