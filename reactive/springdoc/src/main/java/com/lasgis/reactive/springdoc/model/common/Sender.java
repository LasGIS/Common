/*
 *  @(#)Sender.java  last: 05.11.2023
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

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Sender {
    @XmlElement(name = "id")
    private ResourceId id;
    @XmlElement(name = "incorporation")
    private String incorporation;
    @XmlElement(name = "url")
    private String url;
    @XmlElement(name = "legalForm")
    private Integer legalForm;
    @XmlElement(name = "ogrn")
    private String ogrn;
    @XmlElement(name = "inn")
    private String inn;
    @XmlElement(name = "address")
    private Location address;
    @XmlElement(name = "taxation")
    private Integer taxation;
    @XmlElementWrapper(name = "phones")
    @XmlElement(name = "phone")
    private List<Phone> phones;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "contact")
    private Person contact;
    @XmlElement(name = "type")
    private String type;
}
