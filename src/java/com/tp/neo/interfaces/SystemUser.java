/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.interfaces;

/**
 *
 * @author Swedge
 */
public interface SystemUser {
    
    public String getFirstname();
    public void setFirstname(String firstname);

    public String getMiddlename();
    public void setMiddlename(String middlename);

    public String getLastname();
    public void setLastname(String lastname);

    public String getUsername();
    public void setUsername(String username);

    public String getPassword();
    public void setPassword(String password);

    public String getEmail();
    public void setEmail(String email);

    public String getPhone();
    public void setPhone(String phone);

    public String getPermissions();
    public void setPermissions(String permissions);
    
    public Long getSystemUserId();
    public Integer getSystemUserTypeId();
    
}
