/*
 *  @(#)YandexRestApiContextConverter.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.order;

import com.lasgis.reactive.springdoc.model.common.RequestState;
import com.lasgis.reactive.springdoc.model.exception.DomainException;
import com.lasgis.reactive.springdoc.model.exception.YandexRestApiErrorCode;
import com.lasgis.reactive.springdoc.model.order.YandexErrorRestResponse;
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
public class YandexRestApiContextConverter {

    private static final String INTERNAL_SERVER_ERROR = "Internal server error";

    public YandexErrorRestResponse toRest(
        final YandexRestApiContext context,
        final ServerWebExchange serverWebExchange,
        final YandexRestApiErrorCode errorCode,
        final Exception ex
    ) {
        return new YandexErrorRestResponse()
            .setUniq(ifNotNull(context, YandexRestApiContext::getUniq))
            .setResponse(toRest(context))
            .setRequestState(toRest(ifNotNull(serverWebExchange, ServerWebExchange::getRequest), errorCode, ex));
    }

    protected YandexErrorRestResponse.Response toRest(YandexRestApiContext context) {
        var response = new YandexErrorRestResponse.Response();
        response.setType(context.getType());
        response.setOrderId(context.getOrderId());
        return response;
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
        //TODO agrinenko to localize this message
        return INTERNAL_SERVER_ERROR;
    }

}
