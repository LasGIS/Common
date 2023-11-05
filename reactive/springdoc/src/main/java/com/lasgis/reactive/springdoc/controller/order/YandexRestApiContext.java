/*
 *  @(#)YandexRestApiContext.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.controller.order;


import com.lasgis.reactive.springdoc.model.common.ResourceId;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YandexRestApiContext {
    private final String uniq;
    private final String type;
    private final ResourceId orderId;
}
