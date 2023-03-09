/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

/**
 * HdpsScope enumeration.
 *
 * @author MKitchenko
 */
public enum HdpsScope {
    /**
     * Корректировки
     */
    CORRECTIONS,
    /**
     * Данные о почтовых переводах
     */
    ESPP,
    /**
     * Скрытые операции
     */
    HIDDEN,
    /**
     * Информация о связи РПО-Груз
     */
    HYPERLOCAL_DELIVERY,
    /**
     * Информация о многоместном отправлении
     */
    MULTIPLACE,
    /**
     * Заказные уведомления
     */
    NOTIFICATIONS,
    /**
     * Данные ИНН в обобщенной информации об отправлении
     */
    SUMMARY_INN,
    /**
     * Номер контракта (номер договора) в обобщенной информации об отправлении
     */
    SUMMARY_ACNT,
    /**
     * Код типа(типоразмера) упаковки и вида упаковки отправления в обобщенной информации об отправлении
     */
    PACKAGE_INFO,
    /**
     * Код способа доставки отправления в обобщенной информации об отправлении
     */
    DELIVERY,
    /**
     * Информация о свойствах операции над РПО
     */
    PROPERTIES,
    /** Инфо о возвратном РПО */
    RETURN_PARCEL,
}
