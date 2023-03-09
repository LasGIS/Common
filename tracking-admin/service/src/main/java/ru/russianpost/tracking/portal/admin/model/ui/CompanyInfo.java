/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

import ru.russianpost.tracking.web.model.core.Company;

import javax.validation.constraints.Size;

/**
 * CompanyInfo
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class CompanyInfo {

    private Integer id;
    private String tin;
    @Size(max = 255)
    private String name;
    @Size(max = 20)
    private String region;
    @Size(max = 512)
    private String contractNumber;
    @Size(max = 50)
    private String contractDate;

    /**
     * Default
     */
    public CompanyInfo() {
    }

    /**
     * Constructs from a {@link Company} object.
     *
     * @param company {@link Company} object
     * @return company info
     */
    public static CompanyInfo from(Company company) {
        if (company == null) {
            return null;
        }
        return new CompanyInfo(
            company.getId(),
            company.getInn(),
            company.getName(),
            company.getUfps(),
            company.getContractNumber(),
            company.getContractDate()
        );
    }

    private CompanyInfo(Integer id, String tin, String name, String region, String contractNumber, String contractDate) {
        this.id = id;
        this.tin = tin;
        this.name = name;
        this.region = region;
        this.contractNumber = contractNumber;
        this.contractDate = contractDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
    }

    /**
     * Converts to {@link Company} object.
     * @return {@link Company} object
     */
    public Company toCompany() {
        return new Company(id, null, name, tin, contractNumber, contractDate, region);
    }
}
