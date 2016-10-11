/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.components;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Auditlog;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.User;
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
    SystemUser sessionUser;
    
    public AuditLogger(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    private void logAction(String actionName, String logMessage, int userTypeId, Long userId){
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
    
    public void logAgentApprovalAction(Agent agent){
        String actionName = "Agent Approval";
        String logMessage = String.format("Agent %s %s, Agent ID: %s was approved as an agent.", agent.getFirstname(),agent.getLastname(), agent.getAccount().getAccountCode());
        int userType = sessionUser.getSystemUserTypeId();
        Long userId = sessionUser.getSystemUserId();
        logAction(actionName, logMessage, userType, userId);
    }
    
    public void logOrderApprovalAction(ProductOrder order){
        String actionName = "Order Approval";
        String logMessage = String.format("Order ID: %s approved for customer: %s %s (%s). "
                            + "Agent: %s %s (%s).",
                            order.getId(),
                            order.getCustomer().getFirstname(), order.getCustomer().getLastname(), order.getCustomer().getAccount().getAccountCode(),
                            order.getAgent().getFirstname(), order.getAgent().getLastname(), order.getAgent().getAccount().getAccountCode());
        
        int userType = sessionUser.getSystemUserTypeId();
        Long userId = sessionUser.getSystemUserId();
        logAction(actionName, logMessage, userType, userId);
    }
    
    
}
