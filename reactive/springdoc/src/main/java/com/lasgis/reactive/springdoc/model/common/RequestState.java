/*
 *  @(#)RequestState.java  last: 05.11.2023
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
public class RequestState {

    @XmlElement(name = "isError")
    protected Boolean isError;
    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
    protected List<String> errors;
    @XmlElementWrapper(name = "errorCodes")
    @XmlElement(name = "errorCode")
    protected List<ErrorCode> errorCodes;

    @Data
    @Accessors(chain = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement
    public static class ErrorCode {
        @XmlElement(name = "code")
        private Integer code;
        @XmlElement(name = "message")
        private String message;
        @XmlElement(name = "description")
        private String description;
        @XmlElementWrapper(name = "params")
        @XmlElement(name = "param")
        private List<Param> params;
    }
}
