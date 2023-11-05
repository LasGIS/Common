/*
 *  @(#)OrderStatus.java  last: 05.11.2023
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

/**
 * The Class OrderStatus in XML response
 * <pre>
 * <a href="https://yandex.ru/dev/market/delivery-service/doc/dg/concepts/types.html#types__OrderStatus">Данные о статусе заказа.</a>
 * <table><tr><th>Название</th><th>Тип</th><th>Описание</th><th>Обязательный</th></tr>
 * <tr><td>statusCode</td><td>Int32</td><td>Код статуса заказа.</td><td>Да</td></tr>
 * <tr><td>setDate</td><td>String</td><td>Дата и время установки статуса.<p>
 * Формат: YYYY-MM-DDThh:mm:ss+hh:mm. Часовой пояс может быть не указан (по умолчанию 03:00 — UTC+03). Например, 2012-12-21T11:59:00.
 * </p></td><td>Да</td></tr>
 * <tr><td>message</td><td>String</td><td>Комментарий.</td><td>Нет</td></tr>
 * <tr><td>warehouse</td><td>{@link Warehouse}</td><td>Данные о складе СД, на котором был установлен статус.</td><td>Нет</td></tr>
 * <tr><td>weight</td><td>Float</td><td>Вес заказа в момент установки статуса.</td><td>Нет</td></tr>
 * </table></pre>
 *
 * @author VLaskin
 * @since 26.09.2023 : 12:10
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class OrderStatus {
    @XmlElement(name = "statusCode")
    private Integer statusCode;
    @XmlElement(name = "setDate")
    private String setDate;
    @XmlElement(name = "message")
    private String message;
    @XmlElement(name = "warehouse")
    private Warehouse warehouse;
    @XmlElement(name = "weight")
    private BigDecimal weight;
}
