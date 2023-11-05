/*
 *  @(#)OrderStatusHistory.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.model.common;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * The Class OrderStatusHistory in XML response
 *
 * @author VLaskin
 * @since 26.09.2023 : 12:57
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class OrderStatusHistory {
    @XmlElement(name = "orderId")
    private ResourceId orderId;
    @XmlElementWrapper(name = "history")
    @XmlElement(name = "orderStatus")
    private List<OrderStatus> history;
}
