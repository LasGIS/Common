/*
 *  @(#)Location.java  last: 05.11.2023
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
public class Location {
    @XmlElement(name = "country")
    private String country;
    @XmlElement(name = "locality")
    private String locality;
    @XmlElement(name = "region")
    private String region;
    @XmlElement(name = "federalDistrict")
    private String federalDistrict;
    @XmlElement(name = "subRegion")
    private String subRegion;
    @XmlElement(name = "settlement")
    private String settlement;
    @XmlElement(name = "street")
    private String street;
    @XmlElement(name = "house")
    private String house;
    @XmlElement(name = "building")
    private String building;
    @XmlElement(name = "housing")
    private String housing;
    @XmlElement(name = "room")
    private String room;
    @XmlElement(name = "zipCode")
    private String zipCode;
    @XmlElement(name = "porch")
    private String porch;
    @XmlElement(name = "floor")
    private Integer floor;
    @XmlElement(name = "metro")
    private String metro;
    @XmlElement(name = "lat")
    private BigDecimal lat;
    @XmlElement(name = "lng")
    private BigDecimal lng;
    @XmlElement(name = "locationId")
    private Integer locationId;
    @XmlElement(name = "intercom")
    private String intercom;
}
