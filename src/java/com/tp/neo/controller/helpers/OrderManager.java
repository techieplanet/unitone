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
import java.util.ArrayList;
import java.util.Collection;
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
public class OrderManager {
    
    SystemUser sessionUser;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    public OrderManager(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    /**
     * This method will set up the order, the sale items and the lodgement records.
     * 
     * @param agent the agent that owns the customer
     * @param customer the customer that is ordering the items
     * @param orderItems List of items being purchased
     * @return 1 - if all the operations are successful, 0 otherwise
     */
    public ProductOrder processOrder(Customer customer, Lodgement lodgement, List<OrderItem> orderItems, String applicationContext)
    throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        //persist the lodgement
        em.persist(lodgement);
        em.flush();
        
        //credit the customer account to the tune of the lodgment 
        Account cashAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "CASH").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(cashAccount, customer.getAccount(), lodgement.getAmount());
        
        //create the order 
        ProductOrder order = createOrder(customer.getAgent(), customer);
        
        //process the order items
        for(int i=0; i < orderItems.size(); i++){
            OrderItem orderItem = createOrderItem(orderItems.get(i), order);    //insert sale item
            createLodgementItem(lodgement, orderItem);                          //insert the lodgment items
        }
        
        //create new order system notification
        String route = applicationContext + "/order?action=notification&id=" + order.getId();
        Notification notification = new AlertManager().getNotificationsManager(route).createNewOrderNotification(customer);
        em.persist(notification);
        
        em.getTransaction().commit();
        
        //send email alert to all Admins with approve_order permisison
        List<User> recipientsList = em.createNamedQuery("User.findAll").getResultList();
        for(int i=0; i < recipientsList.size(); i++){
            
            if(recipientsList.get(i).getPermissions() == null){
                continue;
            }
            
            if(recipientsList.get(i).getPermissions().equals("")){
                continue;
            }
            
            if( !(recipientsList.get(i).hasActionPermission("approve_order")) )
                recipientsList.remove(i);
        }
        
        em.close();
        emf.close();
        
        new AlertManager().sendNewOrderAlerts(order, lodgement, customer, recipientsList, applicationContext);
                
        return order;
    }
    
    /**
     * Insert the order record 
     * 
     * @param agentId
     * @param customerId
     * @return
     * @throws PropertyException
     * @throws RollbackException 
     */
    private ProductOrder createOrder(Agent agentId, Customer customerId) throws PropertyException, RollbackException{
        
        ProductOrder order = new ProductOrder();
        
        order.setAgent(agentId);
        order.setCustomer(customerId);
        order.setCreatorUserType(sessionUser.getSystemUserTypeId());
        order.setApprovalStatus((short)0);
        new TrailableManager(order).registerInsertTrailInfo(sessionUser.getSystemUserId());
        
        em.persist(order);
        
        em.flush();
                
        //get the last element in the database table. This will be the one you just inserted
        //em.refresh(order);
  
        return order;
        
    }
    
    /**
     * Create an order item.Usually when processing a new order
     * @param orderItem the item to process
     * @param order the order that the item is mapped to
     * @return OrderItem
     */
    private OrderItem createOrderItem(OrderItem orderItem, ProductOrder order) throws PropertyException, RollbackException{
            orderItem.setOrder(order);
            orderItem.setApprovalStatus((short)0);
            new TrailableManager(orderItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
            
            em.persist(orderItem);
            em.flush();
            return orderItem;
    }
    
    
    /**
     * Record lodgment items for new orders (NOT mortgage lodgements)
     * @param lodgement lodgment of the whole sale
     * @param orderItem the item in the order. Maps to one sale unit
     * 
     */
    private void createLodgementItem(Lodgement lodgement, OrderItem orderItem) throws PropertyException, RollbackException{
        LodgementItem lodgementItem = new LodgementItem();
        
        lodgementItem.setAmount(orderItem.getInitialDep());
        lodgementItem.setItem(orderItem);
        lodgementItem.setLodgement(lodgement);
        lodgementItem.setApprovalStatus((short)0);
        new TrailableManager(lodgementItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
        
        em.persist(lodgementItem);
        em.flush();
    }
    
    
    
    /********************* APPROVAL **********************/
    public void processOrderApproval(ProductOrder order, List<OrderItem> orderItemsList, Customer customer) throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        //get the unapproved order items for this order
        List<OrderItem> allItems = (List)order.getOrderItemCollection();
        List<OrderItem> approvedItems = new ArrayList<OrderItem>();
        List<OrderItem> waitingItems = new ArrayList<OrderItem>();
        List<OrderItem> declinedItems = new ArrayList<OrderItem>();
        
        for(int i=0; i < allItems.size(); i++){
            if(allItems.get(i).getApprovalStatus().intValue() > 0) waitingItems.add(allItems.get(i));
            if(allItems.get(i).getApprovalStatus().intValue() > 1) approvedItems.add(allItems.get(i));
            if(allItems.get(i).getApprovalStatus().intValue() > 2) declinedItems.add(allItems.get(i));
        }
        
        for(int i=0; i < orderItemsList.size(); i++){
            OrderItem thisItem = orderItemsList.get(i);
            
            if(thisItem.getApprovalStatus() == 1){//approve
                approvedItems.add(thisItem);
                
                //if(order.getApprovalStatus() != 2) approveOrder(order);
                setOrderItemStatus(thisItem);
                
                
                //get/set corresponding lodgment item

                List list = (List)thisItem.getLodgementItemCollection();
                LodgementItem lodgementItem = (LodgementItem) list.get(0);

                setLodgementItemStatus(lodgementItem, thisItem.getApprovalStatus());
                
                //double entry
                TransactionManager transactionManager = new TransactionManager(sessionUser);
                transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getUnit().getAccount(), thisItem.getInitialDep());
                
                //send approval alerts (email and SMS) to agent and customer
                AlertManager alertManager = new AlertManager();
                //alertManager.sendOrderApprovalAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
                //credit agent wallet - double entry
                transactionManager.doDoubleEntry(thisItem.getUnit().getAccount(), customer.getAgent().getAccount(), thisItem.getCommissionAmount());
                
                //send wallet credit alert
                //alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
            }
            
            if(thisItem.getApprovalStatus() == 2){//decline
                declinedItems.add(thisItem);
                
                setOrderItemStatus(thisItem);
                
                //get/set corresponding lodgment item
                LodgementItem lodgementItem = ((List<LodgementItem>)thisItem.getLodgementItemCollection()).get(0);
                setLodgementItemStatus(lodgementItem, thisItem.getApprovalStatus());
            }
            
            
            
        }//end for
        
        //set the resultant status of the order based on the statuses of the items in it

        
        
        if(approvedItems.size() + declinedItems.size()  == allItems.size()){ //each item has either approved or declined status
            setOrderStatus(order, (short)2); //complete the order
            //List<LodgementItem> lodgementItems = (List)orderItemsList.get(0).getLodgementItemCollection();
            //setLodgementStatus(lodgementItems.get(0).getLodgement(), (short)1); //approve lodgement
        }
        else if(declinedItems.size() == allItems.size()){
            setOrderStatus(order, (short)3); //decline order
            //List<LodgementItem> lodgementItems = (List)orderItemsList.get(0).getLodgementItemCollection();
            //setLodgementStatus(lodgementItems.get(0).getLodgement(), (short)0); //decline lodgement
        }
        else if(approvedItems.size() + declinedItems.size() < allItems.size()){
            setOrderStatus(order, (short)1); //processing status
            //no nee to treat lodgement items
            
        }
        em.merge(order);
        em.getTransaction().commit();
    }
    
    
    private void setOrderStatus(ProductOrder order, short status) throws PropertyException, RollbackException{
        order.setApprovalStatus(status);
        new TrailableManager(order).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
    }
    
    private void setOrderItemStatus(OrderItem orderItem) throws PropertyException, RollbackException{
        new TrailableManager(orderItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
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