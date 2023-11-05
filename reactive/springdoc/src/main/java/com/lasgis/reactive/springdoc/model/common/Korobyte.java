/*
 *  @(#)Korobyte.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.springdoc.model.common;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Korobyte {
    @XmlElement(name = "width")
    private Integer width;
    @XmlElement(name = "height")
    private Integer height;
    @XmlElement(name = "length")
    private Integer length;
    @XmlElement(name = "weightGross")
    private BigDecimal weightGross;
    @XmlElement(name = "weightNet")
    private BigDecimal weightNet;
    @XmlElement(name = "weightTare")
    private BigDecimal weightTare;
}
