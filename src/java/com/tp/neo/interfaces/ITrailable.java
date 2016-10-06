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
public interface ITrailable {

    default Date getCreatedDate(){return new Date();};
    default void setCreatedDate(Date createdDate){};

    default Long getCreatedBy(){return new Long(0);};
    default void setCreatedBy(Long createdBy){};

    default Date getModifiedDate(){return new Date();};
    default void setModifiedDate(Date modifiedDate){};

    default Long getModifiedBy(){return new Long(0);};
    default void setModifiedBy(Long modifiedBy){};
    
}
