/*
 *  @(#)Order.java  last: 05.11.2023
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

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Order {
    @XmlElement(name = "orderId")
    private ResourceId orderId;
    @XmlElement(name = "parcelId")
    private ResourceId parcelId;
    @XmlElement(name = "locationTo")
    private Location locationTo;
    @XmlElement(name = "locationFrom")
    private Location locationFrom;
    @XmlElement(name = "korobyte")
    private Korobyte korobyte;
    @XmlElement(name = "cargoType")
    private Integer cargoType;
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items;
    @XmlElement(name = "cargoCost")
    private BigDecimal cargoCost;
    @XmlElement(name = "tariff")
    private String tariff;
    @XmlElement(name = "shipmentDate")
    private String shipmentDate;
    @XmlElement(name = "assessedCost")
    private BigDecimal assessedCost;
    @XmlElement(name = "paymentMethod")
    private Integer paymentMethod;
    @XmlElement(name = "deliveryType")
    private Integer deliveryType;
    @XmlElement(name = "deliveryCost")
    private BigDecimal deliveryCost;
    @XmlElement(name = "documentData")
    private DocData documentData;
    @XmlElement(name = "recipient")
    private Recipient recipient;
    @XmlElement(name = "total")
    private BigDecimal total;
    @XmlElement(name = "amountPrepaid")
    private BigDecimal amountPrepaid;
    @XmlElement(name = "sender")
    private Sender sender;
    @XmlElement(name = "pickupPointCode")
    private String pickupPointCode;
    @XmlElement(name = "pickupPointId")
    private ResourceId pickupPointId;
    @XmlElement(name = "deliveryDate")
    private String deliveryDate;
    @XmlElement(name = "deliveryTime")
    private String deliveryTime;
    @XmlElement(name = "deliveryInterval")
    private String deliveryInterval;
    @XmlElementWrapper(name = "services")
    @XmlElement(name = "service")
    private List<Service> services;
    @XmlElement(name = "comment")
    private String comment;
    @XmlElement(name = "warehouse")
    private Warehouse warehouse;
    @XmlElement(name = "shipmentPointCode")
    private String shipmentPointCode;
    @XmlElementWrapper(name = "places")
    @XmlElement(name = "place")
    private List<Place> places;
    @XmlElement(name = "cashService")
    private BigDecimal cashService;
    @XmlElement(name = "weight")
    private BigDecimal weight;
    @XmlElement(name = "length")
    private Integer length;
    @XmlElement(name = "width")
    private Integer width;
    @XmlElement(name = "height")
    private Integer height;
}
