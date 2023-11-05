/*
 *  @(#)Item.java  last: 05.11.2023
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
public class Item {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "count")
    private Integer count;
    @XmlElement(name = "price")
    private BigDecimal price;
    @XmlElement(name = "article")
    private String article;
    @XmlElementWrapper(name = "taxes")
    @XmlElement(name = "tax")
    private List<Tax> taxes;
    @XmlElement(name = "nameEnglish")
    private String nameEnglish;
    @XmlElement(name = "cargoType")
    private Integer cargoType;
    @XmlElementWrapper(name = "cargoTypes")
    @XmlElement(name = "cargoType")
    private List<Integer> cargoTypes;
    @XmlElement(name = "url")
    private String url;
    @XmlElement(name = "categoryName")
    private String categoryName;
    @XmlElement(name = "hsCode")
    private String hsCode;
    @XmlElement(name = "korobyte")
    private Korobyte korobyte;
    @XmlElement(name = "itemDescriptionEnglish")
    private String itemDescriptionEnglish;
    @XmlElement(name = "unitId")
    private UnitId unitId;
    @XmlElement(name = "transitData")
    private TransitData transitData;
    @XmlElement(name = "supplier")
    private Supplier supplier;
    @XmlElementWrapper(name = "instances")
    @XmlElement(name = "instance")
    private List<Instance> instances;
}
