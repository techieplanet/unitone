/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.exception;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Swedge
 */
public class SystemLogger {
    
    public static void logSystemIssue(String entityName, String inputValues, String errorMsg){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        try{
            em.getTransaction().begin();
            
            Date date = new Date();
            ExceptionLog exLog = new ExceptionLog();
            exLog.setEntity(entityName);
            exLog.setInputvalues(inputValues);
            exLog.setErrorMessage(errorMsg);
            exLog.setDate(date);
            
            em.persist(exLog);
            em.flush();
            em.getTransaction().commit();
            
            em.close();
            emf.close();
        }   
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
