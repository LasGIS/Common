/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotEmpty;
import ru.russianpost.tracking.portal.admin.validation.constraints.EachPattern;

import java.util.List;
import java.util.Objects;

/**
 * Barcode provider user DTO.
 *
 * @author KKiryakov
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarcodeProviderUser {
    private Long id;
    private String username;
    private String password;
    @NotEmpty
    @EachPattern("^[1-9][0-9]{5}$")
    private List<String> postalCodes;
    private String companyName;
    private String inn;
    private String datDog;
    private String depCode;
    private boolean internal;
    private boolean international;

    /**
     * Constructor.
     *
     * @param id user id
     * @param username username
     * @param password password
     * @param postalCodes postal codes
     * @param companyName company name
     * @param inn inn
     * @param datDog contract date
     * @param depCode department code
     * @param internal internal
     * @param international international
     */
    @JsonCreator
    public BarcodeProviderUser(
        @JsonProperty("id") Long id,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("postalCodes") List<String> postalCodes,
        @JsonProperty("companyName") String companyName,
        @JsonProperty("inn") String inn,
        @JsonProperty("datDog") String datDog,
        @JsonProperty("depCode") String depCode,
        @JsonProperty("internal") boolean internal,
        @JsonProperty("international") boolean international
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.postalCodes = postalCodes;
        this.companyName = companyName;
        this.inn = inn;
        this.datDog = datDog;
        this.depCode = depCode;
        this.internal = internal;
        this.international = international;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getPostalCodes() {
        return postalCodes;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getInn() {
        return inn;
    }

    public String getDatDog() {
        return datDog;
    }

    public String getDepCode() {
        return depCode;
    }

    public boolean isInternal() {
        return internal;
    }

    public boolean isInternational() {
        return international;
    }

    @Override
    public String toString() {
        return "BarcodeProviderUser{id="
            + id
            + ", username='"
            + username
            + "', password='"
            + password
            + "', postalCodes="
            + postalCodes
            + ", companyName='"
            + companyName
            + "', inn='"
            + inn
            + "', datDog='"
            + datDog
            + "', depCode='"
            + depCode
            + "', internal="
            + internal
            + ", international="
            + international
            + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BarcodeProviderUser)) {
            return false;
        }
        BarcodeProviderUser that = (BarcodeProviderUser) o;
        return Objects.equals(id, that.id)
            && internal == that.internal
            && international == that.international
            && Objects.equals(username, that.username)
            && Objects.equals(password, that.password)
            && Objects.equals(postalCodes, that.postalCodes)
            && Objects.equals(companyName, that.companyName)
            && Objects.equals(inn, that.inn)
            && Objects.equals(datDog, that.datDog)
            && Objects.equals(depCode, that.depCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, postalCodes, companyName, inn, datDog, depCode, internal, international);
    }
}
