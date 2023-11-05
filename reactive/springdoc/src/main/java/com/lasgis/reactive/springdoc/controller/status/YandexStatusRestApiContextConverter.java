/*
 *  @(#)YandexStatusRestApiContextConverter.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.status;

import com.lasgis.reactive.springdoc.model.common.RequestState;
import com.lasgis.reactive.springdoc.model.common.ResourceId;
import com.lasgis.reactive.springdoc.model.exception.DomainException;
import com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode;
import com.lasgis.reactive.springdoc.model.status.YandexErrorStatusRestResponse;
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
public class YandexStatusRestApiContextConverter {

    private static final String INTERNAL_SERVER_ERROR = "Internal server error";

    public YandexErrorStatusRestResponse toRest(
        final YandexStatusRestApiContext context,
        final ServerWebExchange serverWebExchange,
        final YandexRestApiErrorCode errorCode,
        final Exception ex
    ) {
        return new YandexErrorStatusRestResponse()
            .setUniq(ifNotNull(context, YandexStatusRestApiContext::getUniq))
            .setResponse(toRest(context))
            .setRequestState(toRest(ifNotNull(serverWebExchange, ServerWebExchange::getRequest), errorCode, ex));
    }

    protected YandexErrorStatusRestResponse.Response toRest(YandexStatusRestApiContext context) {
        var restResponse = new YandexErrorStatusRestResponse.Response();
        restResponse.setType(context.getType());
        restResponse.setOrderStatusHistories(toOrderStatusHistories(context.getOrdersId()));
        return restResponse;
    }

    protected List<YandexErrorStatusRestResponse.OrderStatusHistory> toOrderStatusHistories(List<ResourceId> ordersId) {
        return ordersId.stream().map(resourceId -> {
            var orderStatusHistory = new YandexErrorStatusRestResponse.OrderStatusHistory();
            orderStatusHistory.setOrderId(resourceId);
            return orderStatusHistory;
        }).toList();
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
