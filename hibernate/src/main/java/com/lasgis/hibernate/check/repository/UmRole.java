package com.lasgis.hibernate.check.repository;

import lombok.Data;

@Data
public class UmRole {

    private String umrleRoleId;
    private String umrleDescription;


    public String getUmrleRoleId() {
        return umrleRoleId;
    }

    public void setUmrleRoleId(String umrleRoleId) {
        this.umrleRoleId = umrleRoleId;
    }


    public String getUmrleDescription() {
        return umrleDescription;
    }

    public void setUmrleDescription(String umrleDescription) {
        this.umrleDescription = umrleDescription;
    }

}
