package com.lasgis.hibernate.check.repository;


public class UmUser {

  private long umusrUserId;
  private String umusrLogin;
  private String umusrName;
  private String umusrPassword;
  private String umusrArchived;


  public long getUmusrUserId() {
    return umusrUserId;
  }

  public void setUmusrUserId(long umusrUserId) {
    this.umusrUserId = umusrUserId;
  }


  public String getUmusrLogin() {
    return umusrLogin;
  }

  public void setUmusrLogin(String umusrLogin) {
    this.umusrLogin = umusrLogin;
  }


  public String getUmusrName() {
    return umusrName;
  }

  public void setUmusrName(String umusrName) {
    this.umusrName = umusrName;
  }


  public String getUmusrPassword() {
    return umusrPassword;
  }

  public void setUmusrPassword(String umusrPassword) {
    this.umusrPassword = umusrPassword;
  }


  public String getUmusrArchived() {
    return umusrArchived;
  }

  public void setUmusrArchived(String umusrArchived) {
    this.umusrArchived = umusrArchived;
  }

}
