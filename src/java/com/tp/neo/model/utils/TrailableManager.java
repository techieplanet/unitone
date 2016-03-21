/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model.utils;

import com.tp.neo.interfaces.ITrailable;

import java.util.Date;


/**
 *
 * @author Swedge
 */
public class TrailableManager {
    private ITrailable trailable;
    
    public TrailableManager(ITrailable t){
        trailable = t;
    }
    
    private void setCreatedBy(Integer userId){
        trailable.setCreatedBy(userId);
    }
    
    private void setCreatedDate(Date date){
        trailable.setCreatedDate(date);
    }
    
    private void setModifiedBy(Integer userId){
        trailable.setModifiedBy(userId);
    }
    
    private void setModifiedDate(Date date){
        trailable.setModifiedDate(date);
    }
    
    public void registerInsertTrailInfo(Integer userId){
        Date date = new Date();
        setCreatedBy(userId);
        setCreatedDate(date);
    }
    
    public void registerUpdateTrailInfo(Integer userId){
        Date date = new Date();
        setModifiedBy(userId);
        setModifiedDate(date);
    }
}
