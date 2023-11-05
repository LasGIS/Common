/*
 *  @(#)GetYandexOrderRestResponse.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.model.order;

import com.lasgis.reactive.springdoc.model.common.Order;
import com.lasgis.reactive.springdoc.model.common.RequestState;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetYandexOrderRestResponse {

    @XmlElement(name = "uniq")
    private String uniq;
    @XmlElement(name = "response")
    private Response response;
    @XmlElement(name = "requestState")
    private RequestState requestState;

    @Data
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Response {
        @XmlAttribute(name = "type")
        private String type;
        @XmlElement(name = "order")
        private Order order;
    }
}
