package com.lasgis.hibernate.check.repository;

import lombok.Data;

@Data
public class UmUser {

    private long umusrUserId;
    private String umusrLogin;
    private String umusrName;
    private String umusrPassword;
    private String umusrArchived;

}
