/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model;

/**
 * Money type enum.
 * @author MKitchenko
 */
public enum MoneyType {
    /**
     * Сумма наложенного платежа
     */
    PAYMENT(1),
    /**
     * Сумма объявленной ценности
     */
    VALUE(2),
    /**
     * Общая сумма платы за пересылку наземным и воздушным транспортом
     */
    MASS_RATE(3),
    /**
     * Сумма платы за объявленную ценность
     */
    INSR_RATE(4),
    /**
     * Выделенная сумма платы за пересылку воздушным транспортом из общей суммы платы за пересылку
     */
    AIR_RATE(5),
    /**
     * Сумма налога на добавленную стоимость
     */
    AD_VAL_TAX(6),
    /**
     * Дополнительный тарифный сбор
     */
    RATE(7),
    /**
     * Сумма таможенных платежей
     */
    CUSTOM_DUTY(8),
    /**
     * Содержит сумму обязательного платежа
     */
    COMPULSORY_PAYMENT(9),
    /**
     * Стоимость вложений
     */
    PRICE(10),
    /**
     * Таможенные платежи, пошлины, налоги в отношении товаров
     */
    CUSTOMS_PAYMENTS(11),
    /**
     * Стоимость доставки с НДС, взимаемая с получателя (для отправлений ЕКОМ)
     */
    COMMON_RATE(12),
    /**
     * Общая стоимость заказа с НДС (для отправлений ЕКОМ)
     */
    TOTAL_COST(13),
    /**
     * Сумма платы за пересылку (без допуслуг, без НДС)
     */
    SHIPMENT_PAYMENT(14),
    /**
     * Сумма платы за допуслуги (без НДС)
     */
    SERVICES_PAYMENT(15);

    private final int type;

    MoneyType(final int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
