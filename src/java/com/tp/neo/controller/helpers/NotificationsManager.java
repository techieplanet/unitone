/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Customer;
import com.tp.neo.model.Notification;
import com.tp.neo.model.NotificationType;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author swedge-mac
 */
public class NotificationsManager {
 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager(); 
    
    public String route = ""; 
    
    public NotificationsManager(String route){
        this.route = route;
    }

    protected Notification createNewOrderNotification(Customer customer){
        String title = String.format("New order for %s %s (%s)", customer.getFirstname(), customer.getLastname(), customer.getAccount().getAccountCode());
        
        Notification notification = new Notification();
        
        notification.setTitle(title);
        notification.setRoute(route);
        notification.setStatus((short)0);
        notification.setCreatedDate(new Date());
        notification.setClearOnClick((short)0);
        notification.setTypeId((NotificationType)em.createNamedQuery("NotificationType.findByAlias").setParameter("alias", "ALERT_NEW_ORDER").getSingleResult());
        
        return notification;
    }
    
    protected Notification createNewLodgementNotification(Customer customer){
        String title = String.format("New Lodgement for %s %s (%s)", customer.getFirstname(), customer.getLastname(), customer.getAccount().getAccountCode());
        
        Notification notification = new Notification();
        
        notification.setTitle(title);
        notification.setRoute(route);
        notification.setStatus((short)0);
        notification.setCreatedDate(new Date());
        notification.setClearOnClick((short)0);
        notification.setTypeId((NotificationType)em.createNamedQuery("NotificationType.findByAlias").setParameter("alias", "ALERT_NEW_LODGE").getSingleResult());
        
        return notification;
    }
    
   
}
