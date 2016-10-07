/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.components;

import com.tp.neo.model.Auditlog;
import java.util.Calendar;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Swedge
 */
public class AuditLogger {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    private EntityManager em;
    
    public void logAction(String actionName, String logMessage, int userTypeId, Long userId){
        em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Auditlog auditlog = new Auditlog();
        auditlog.setActionName(actionName);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));                

        auditlog.setLogDate(calendar.getTime());
        auditlog.setNote(logMessage);
        auditlog.setUsertype(userTypeId);
        auditlog.setUserId(userId);

        em.persist(auditlog);
        em.getTransaction().commit();
    }
}
