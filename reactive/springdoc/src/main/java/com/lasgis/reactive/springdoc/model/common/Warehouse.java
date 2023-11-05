/*
 *  @(#)Warehouse.java  last: 05.11.2023
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

/**
 * The Class Warehouse in XML response
 * <pre>
 * <a href="https://yandex.ru/dev/market/delivery-service/doc/dg/concepts/types.html#types__Warehouse">Данные о складе.</a>
 * <table><tr><th>Название</th><th>Тип</th><th>Описание</th><th>Обязательный</th></tr>
 * <tr><td>id</td><td>{@link ResourceId}</td><td>Идентификатор склада в системе СД.</td><td>Да</td></tr>
 * <tr><td>address</td><td>{@link Location}</td><td>Адрес склада.</td><td>Да</td></tr>
 * <tr><td>schedule</td><td>{@link WorkTime}[]</td><td><p>Режим работы склада.</p>
 * <p>Должен содержать хотя бы один вложенный элемент workTime.</p></td><td>Да</td></tr>
 * <tr><td>contact</td><td>{@link Person}</td><td>Фамилия, имя и отчество контактного лица.</td><td>Нет</td></tr>
 * <tr><td>phones</td><td>{@link Phone}[]</td><td><p>Контактные телефоны.</p>
 * <p>Должен содержать хотя бы один вложенный элемент phone.</p></td><td>Нет</td></tr>
 * <tr><td>instruction</td><td>String</td><td>Данные о том, как добраться до склада.</td><td>Нет</td></tr>
 * </table></pre>
 */
@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Warehouse {
    @XmlElement(name = "id")
    private ResourceId id;
    @XmlElement(name = "address")
    private Location address;
    @XmlElement(name = "instruction")
    private String instruction;
    @XmlElementWrapper(name = "schedule")
    @XmlElement(name = "workTime")
    private List<WorkTime> schedule;
    @XmlElement(name = "contact")
    private Person contact;
    @XmlElementWrapper(name = "phones")
    @XmlElement(name = "phone")
    private List<Phone> phones;
}
