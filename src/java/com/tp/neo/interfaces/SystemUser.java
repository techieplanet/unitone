/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.interfaces;

import java.util.Date;

/**
 *
 * @author Swedge
 */
public interface SystemUser {
    
    default String getFirstname(){return "";};
    default void setFirstname(String firstname){};

    default String getMiddlename(){return "";};
    default void setMiddlename(String middlename){};

    default String getLastname(){return "";};
    default void setLastname(String lastname){};

    default String getUsername(){return "";};
    default void setUsername(String username){};

    default String getPassword(){return "";};
    default void setPassword(String password){};

    default String getEmail(){return "";};
    default void setEmail(String email){};

    default String getPhone(){return "";};
    default void setPhone(String phone){};

    default String getPermissions(){return "";}
    default void setPermissions(String permissions){};
    
    public Long getSystemUserId();
    public Integer getSystemUserTypeId();
    
}
