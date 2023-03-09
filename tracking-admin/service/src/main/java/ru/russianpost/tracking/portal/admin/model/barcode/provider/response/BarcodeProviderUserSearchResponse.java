/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.response;

import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderUser;

import java.util.List;

/**
 * Barcode provider user search response.
 *
 * @author KKiryakov
 */
public class BarcodeProviderUserSearchResponse extends BarcodeProviderResponse {

    private List<BarcodeProviderUser> users;

    public List<BarcodeProviderUser> getUsers() {
        return users;
    }

    public void setUsers(List<BarcodeProviderUser> users) {
        this.users = users;
    }
}
