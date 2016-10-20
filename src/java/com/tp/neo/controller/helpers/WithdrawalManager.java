/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.User;
import com.tp.neo.model.Withdrawal;
import com.tp.neo.model.utils.TrailableManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author swedge-mac
 */
public class WithdrawalManager {
    
    SystemUser sessionUser;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    public WithdrawalManager(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    public void processWithdrawalRequest(Withdrawal w, String applicationContext){
        em.getTransaction().begin();
        
        new TrailableManager(w).registerInsertTrailInfo(sessionUser.getSystemUserId());
        em.persist(w);
        em.flush();
        
        //send alerts
        //now send alerts on the lodgement to customer, agent and admin
        //email alert will be sent to all Admins with approve_order permisison
        List<User> recipientsList = em.createNamedQuery("User.findAll").getResultList();
        for(int i=0; i < recipientsList.size(); i++){
            if( !(recipientsList.get(i).hasActionPermission("approve_withdrawal")) )
                recipientsList.remove(i);
        }
        String withdrawalPageLink = applicationContext + "Withdrawal?action=approval";
        new AlertManager().sendNewWithdrawalRequestAlerts(w, recipientsList, withdrawalPageLink);
        em.getTransaction().commit();
        
    }
    
    
    public void processWithdrawalApproval(Withdrawal w, String applicationContext){
        em.getTransaction().begin();
        w.setApproved((short)1);
        new TrailableManager(w).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        
        //double entry: debit agent, credit cash
        //Account cashAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "CASH").getSingleResult();
        //new TransactionManager(sessionUser).doDoubleEntry(w.getAgent().getAccount(), cashAccount, w.getAmount());
        
        
        em.getTransaction().commit();
    }
    
    /**
     * This is a batch process that will pick each withdrawal that is in approved state
     * and add it to a batch excel file then set the approval status to piad (3)
     * @param w 
     */
    public void disburseWithdrawals(){
        em.getTransaction().begin();
        
        List<Withdrawal> wList = em.createNamedQuery("Withdrawal.findByApproved").setParameter("approved", 3).getResultList();
        
        for(int i = 0; i < wList.size(); i++){
            Withdrawal w;
            w = wList.get(i);
            //send to excel list
            w.setApproved((short)3);
            new TrailableManager(w).registerUpdateTrailInfo(sessionUser.getSystemUserId());
            em.flush();
        }
        
        em.getTransaction().commit();
    }
    
}
