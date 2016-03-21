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

    public Date getCreatedDate();

    public void setCreatedDate(Date createdDate);

    public Integer getCreatedBy();

    public void setCreatedBy(Integer createdBy);

    public Date getModifiedDate();

    public void setModifiedDate(Date modifiedDate);

    public Integer getModifiedBy();

    public void setModifiedBy(Integer modifiedBy);
    
}
