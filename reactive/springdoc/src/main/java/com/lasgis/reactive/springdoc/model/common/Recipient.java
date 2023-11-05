/*
 *  @(#)Recipient.java  last: 05.11.2023
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
public class Recipient {
    @XmlElement(name = "fio")
    private Person fio;
    @XmlElementWrapper(name = "phones")
    @XmlElement(name = "phone")
    private List<Phone> phones;
    @XmlElement(name = "email")
    private String email;
    @XmlElement(name = "recipientData")
    private RecipientData recipientData;
    @XmlElement(name = "personalDataStatus")
    private Integer personalDataStatus;
    @XmlElement(name = "uid")
    private Integer uid;
}