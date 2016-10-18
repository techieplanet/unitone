/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.User;
import com.tp.neo.model.utils.TrailableManager;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.xml.bind.PropertyException;

/**
 *
 * @author swedge-mac
 */
public class LodgementManager {
    
    SystemUser sessionUser;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    public LodgementManager(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    /**
     * This method will set up the lodgement, the sale items and the lodgement records.
     * 
     * @param agent the agent that owns the customer
     * @param customer the customer that is ordering the items
     * @param orderItems List of items being purchased
     * @return 1 - if all the operations are successful, 0 otherwise
     */
    public Lodgement processLodgement(Customer customer, Lodgement lodgement, List<LodgementItem> lodgementItems, String applicationContext)
    throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        //save the lodgement
        em.persist(lodgement);
        em.flush();
        
        //credit the customer account to the tune of the lodgment 
        Account cashAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "CASH").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(cashAccount, customer.getAccount(), lodgement.getAmount());
        
        //process the lodgenent items
        for(LodgementItem lodgementItem : lodgementItems){
            
               lodgementItem.setLodgement(lodgement);
               lodgementItem.setApprovalStatus((short)0);
                 new TrailableManager(lodgementItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
        
                em.persist(lodgementItem);
                em.flush();
        }
        
        //create system notification for the lodgement
        String route = applicationContext + "/lodgement?action=notification&id=" + lodgement.getId();
        Notification notification = new AlertManager().getNotificationsManager(route).createNewLodgementNotification(customer);
        em.persist(notification);
        
        
        em.getTransaction().commit();
        
        //now send alerts on the lodgement to customer, agent and admin
        //email alert will be sent to all Admins with approve_order permisison
        List<User> recipientsList = em.createNamedQuery("User.findAll").getResultList();
        em.close();
        emf.close();
        
        for(int i=0; i < recipientsList.size(); i++){
            if( !(recipientsList.get(i).hasActionPermission("approve_order")) )
                recipientsList.remove(i);
        }
        new AlertManager().sendNewLodgementAlerts(lodgement, customer, recipientsList);
        
        return lodgement;
    }

    
    /**
     * Record lodgment items for mortgage lodgements
     * @param lodgement lodgment of the whole sale
     * @param orderItem the order item for the lodgment item that is being created
     * 
     */
//    private LodgementItem createLodgementItem(Lodgement lodgement, OrderItem orderItem) throws PropertyException, RollbackException{
//        LodgementItem lodgementItem = new LodgementItem();
//        
//        //lodgementItem.setAmount(orderItem.getInitialDep());
//        lodgementItem.setItem(orderItem);
//        lodgementItem.setLodgement(lodgement);
//        lodgementItem.setApprovalStatus((short)0);
//        new TrailableManager(lodgementItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
//        
//        em.persist(lodgementItem);
//        em.flush();
//        
//        return lodgementItem;
//        
//    }
    
    
    
    /********************* APPROVAL **********************/
    public void processLodgementApproval(Lodgement lodgement, List<LodgementItem> lodgementItems, Customer customer) throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        int allItemsApproveDeclineFlag = 0;
        for(int i=0; i < lodgementItems.size(); i++){
            LodgementItem thisItem = lodgementItems.get(i);
            
            if(thisItem.getApprovalStatus() == 1){//approve
                allItemsApproveDeclineFlag = 1;
                
                setLodgementItemStatus(thisItem, thisItem.getApprovalStatus());
                
                //double entry - take the money out of ther customert account and fund the project unit
                TransactionManager transactionManager = new TransactionManager(sessionUser);
                transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getItem().getUnit().getAccount(), thisItem.getAmount());
                
                //send approval alerts (email and SMS) to agent and customer
                AlertManager alertManager = new AlertManager();
                alertManager.sendLodgementApprovalAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());
                
                //credit agent wallet - double entry
                transactionManager.doDoubleEntry(thisItem.getItem().getUnit().getAccount(), customer.getAgent().getAccount(), thisItem.getItem().getCommissionAmount());
                
                //send wallet credit alert
                alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());
                
            }
            else if(thisItem.getApprovalStatus() == 2){//decline
                if(allItemsApproveDeclineFlag  == 0) allItemsApproveDeclineFlag = 2;
                setLodgementItemStatus(thisItem, thisItem.getApprovalStatus());
            }
            
            //set the resultant status of the order based on the statuses of the items in it
            if(allItemsApproveDeclineFlag == 1) //at least one item was approved
                setLodgementStatus(lodgement, (short)2); //approved order
            else
                setLodgementStatus(lodgement, (short)3); //decline order
            
        }//end for       
        
        em.getTransaction().commit();
    }
    
    private void setLodgementStatus(Lodgement lodgement, short status) throws PropertyException, RollbackException{
        lodgement.setApprovalStatus(status);
        new TrailableManager(lodgement).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
    }
    
    private void setLodgementItemStatus(LodgementItem lodgementItem, short status) throws PropertyException, RollbackException{
        lodgementItem.setApprovalStatus(status);
        new TrailableManager(lodgementItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
    }
       
}