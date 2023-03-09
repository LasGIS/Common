/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.opm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * OnlinePaymentMarkStatus.
 * @author MKitchenko
 */
public enum OnlinePaymentMarkStatus {
    /**
     * Статус "Аннулировано"
     */
    ANNULLED("Аннулировано"),
    /**
     * Статус "Проконтролирован"
     */
    CONTROLLED("Проконтролирован"),
    /**
     * Статус "Завершён"
     */
    COMPLETED("Завершён"),
    /**
     * Статус "Выпущен"
     */
    ISSUED("Выпущен"),
    /**
     * Статус "Средства захолдированы"
     */
    HOLD("Средства захолдированы"),
    /**
     * Статус "Средства списаны"
     */
    PAID("Средства списаны");

    private final String description;

    /**
     * Status description constructor
     * @param description status description
     */
    OnlinePaymentMarkStatus(String description) {
        this.description = description;
    }

    /**
     * Status code getter
     * @return status code
     */
    public Integer getCode() {
        return this.ordinal();
    }

    /**
     * Status description getter
     * @return status description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns OnlinePaymentMarkStatus from string value
     * @param value the value
     * @return status OnlinePaymentMarkStatus
     */
    @JsonCreator
    public static OnlinePaymentMarkStatus fromValue(String value) {
        if (value != null) {
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Returns the name of this enum constant
     * @return the name of this enum constant
     */
    @JsonValue
    public String toJson() {
        return name();
    }

    /**
     * All possible statuses getter
     * @return string the statuses of this enum type, in the order they're declared separated by a comma
     */
    public static String possibleValues() {
        return Arrays.stream(values()).map(OnlinePaymentMarkStatus::toString).collect(Collectors.joining(", "));
    }
}
