/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
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
        
        //process the lodgenent items
        for(LodgementItem lodgementItem : lodgementItems){
            
               lodgementItem.setLodgement(lodgement);
               lodgementItem.setApprovalStatus((short)0);
                 new TrailableManager(lodgementItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
        
                em.persist(lodgementItem);
                em.flush();
        }
        
        //create system notification for the lodgement
        String route =  applicationContext + "/Lodgement?action=notification&id=" + lodgement.getId();
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
        String waitingLodgementsPageLink = applicationContext + "Lodgement?action=approval";
        new AlertManager().sendNewLodgementAlerts(lodgement, customer, recipientsList, waitingLodgementsPageLink);
        
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
    /**
     * 
     * @param lodgement 
     * @param applicationContext 
     */
    public void approveLodgement(Lodgement lodgement, Notification notification, String applicationContext) throws PropertyException, RollbackException{
        List<LodgementItem> lodgementItems = (List)lodgement.getLodgementItemCollection();
        ProductOrder order = lodgementItems.get(0).getItem().getOrder();
        
        if(order.getApprovalStatus() == 0){ //unattended, create new order notification
            //this method will handle the process: credit the customer account, create order notification and send new order alerts to admins
            new OrderManager(sessionUser).processOrderLevelLodgementApproval(lodgement, order, lodgement.getCustomer(), applicationContext);
            processLodgementApproval(lodgement, lodgementItems, lodgement.getCustomer(), notification);
        }
        else{//mortgage lodgement
            processLodgementApproval(lodgement, lodgementItems, lodgement.getCustomer(), notification);
        }
    }
    
    
    /**
     * 
     * @param lodgement
     * @param lodgementItems
     * @param customer
     * @throws PropertyException
     * @throws RollbackException 
     */
    public void processLodgementApproval(Lodgement lodgement, List<LodgementItem> lodgementItems, Customer customer, Notification notification) throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        //approve the lodgement
        lodgement.setApprovalStatus((short)1);
        new TrailableManager(lodgement).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
        
        notification.setStatus((short)2);
        em.merge(notification);
        
        //double entry: debit cash, credit customer account to the tune of the lodgment
        Account cashAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "CASH").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(cashAccount, customer.getAccount(), lodgement.getAmount());
        
        
        for(int i=0; i < lodgementItems.size(); i++){
            LodgementItem thisItem = lodgementItems.get(i);
      
            //set the item as approved
            thisItem.setApprovalStatus((short)1);
            new TrailableManager(thisItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
            
            //double entry - take the money out of their customer account and fund the project unit
            TransactionManager transactionManager = new TransactionManager(sessionUser);
            transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getItem().getUnit().getAccount(), thisItem.getAmount());

            //send approval alerts (email and SMS) to agent and customer
            AlertManager alertManager = new AlertManager();
            alertManager.sendLodgementApprovalAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());

            //credit agent wallet - double entry
            transactionManager.doDoubleEntry(thisItem.getItem().getUnit().getAccount(), customer.getAgent().getAccount(), thisItem.getItem().getCommissionAmount());

            //send wallet credit alert
            alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());
            
        }//end for       
        
        em.getTransaction().commit();
    }
    
    
    public void declineLodgementApproval(Lodgement lodgement, List<LodgementItem> lodgementItems, Customer customer) throws PropertyException, RollbackException{
        
//        em.getTransaction().begin();
//        
//        //decline the lodgement
//        lodgement.setApprovalStatus((short)2);
//        new TrailableManager(lodgement).registerUpdateTrailInfo(sessionUser.getSystemUserId());
//        em.flush();
//        
//        for(int i=0; i < lodgementItems.size(); i++){
//            LodgementItem thisItem = lodgementItems.get(i);
//      
//            //set the item as approved
//            thisItem.setApprovalStatus((short)2);
//            new TrailableManager(thisItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
//            
//            //double entry - take the money out of ther customert account and fund the project unit
//            TransactionManager transactionManager = new TransactionManager(sessionUser);
//            transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getItem().getUnit().getAccount(), thisItem.getAmount());
//
//            //send approval alerts (email and SMS) to agent and customer
//            AlertManager alertManager = new AlertManager();
//            alertManager.sendLodgementApprovalAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());
//
//            //credit agent wallet - double entry
//            transactionManager.doDoubleEntry(thisItem.getItem().getUnit().getAccount(), customer.getAgent().getAccount(), thisItem.getItem().getCommissionAmount());
//
//            //send wallet credit alert
//            alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());
//            
//        }//end for       
//        
//        em.getTransaction().commit();
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