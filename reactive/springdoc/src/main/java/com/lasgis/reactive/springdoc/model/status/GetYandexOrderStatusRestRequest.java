/*
 *  @(#)GetYandexOrderStatusRestRequest.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.model.status;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import com.lasgis.reactive.springdoc.model.common.ResourceId;

import java.util.List;

@Data
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetYandexOrderStatusRestRequest {
    @XmlElement(name = "uniq")
    private String uniq;
    @XmlElement(name = "request")
    private Request request;

    @Data
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Request {
        @XmlAttribute(name = "type")
        private String type;
        @XmlElementWrapper(name = "ordersId")
        @XmlElement(name = "orderId")
        private List<ResourceId> ordersId;
    }
}
