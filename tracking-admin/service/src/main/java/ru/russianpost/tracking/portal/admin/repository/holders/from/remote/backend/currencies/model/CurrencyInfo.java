/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.currencies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Currency info class.
 * @author MKitchenko
 * @version 2.0 23.01.2019
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyInfo {
    private String codeCurrency;
    private Integer currencyDecimalPlaces;

    /**
     * Default constructor
     */
    public CurrencyInfo() {
    }

    /**
     * Constructor for CurrencyInfo class
     * @param codeCurrency          codeCurrency
     * @param currencyDecimalPlaces currencyDecimalPlaces
     */
    public CurrencyInfo(String codeCurrency, Integer currencyDecimalPlaces) {
        this.codeCurrency = codeCurrency;
        this.currencyDecimalPlaces = currencyDecimalPlaces;
    }

    public String getCodeCurrency() {
        return codeCurrency;
    }

    public Integer getCurrencyDecimalPlaces() {
        return currencyDecimalPlaces;
    }

    public void setCodeCurrency(String codeCurrency) {
        this.codeCurrency = codeCurrency;
    }

    public void setCurrencyDecimalPlaces(Integer currencyDecimalPlaces) {
        this.currencyDecimalPlaces = currencyDecimalPlaces;
    }
}
