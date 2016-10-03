/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

/**
 *
 * @author swedge-mac
 */
public class AlertManager {
    
    public EmailHelper getEmailManager(){
        return new EmailHelper();
    }
    
    public SMSHelper getSMSManager(){
        return new SMSHelper();
    }
    
    
    
}
