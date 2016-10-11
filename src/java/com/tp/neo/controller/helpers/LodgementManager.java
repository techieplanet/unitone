/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.OrderItem;
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
    public ProductOrder processLodgement(ProductOrder order, Customer customer, Lodgement lodgement, List<LodgementItem> lodgementItems)
    throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        //save the lodgement
        em.persist(lodgement);
        em.flush();
        
        //process the lodgenent items
        for(int i=0; i < lodgementItems.size(); i++){
            LodgementItem lodgementItem = createLodgementItem(lodgement, lodgementItems.get(i).getItem());    //insert sale item
        }
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        
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
            orderItem.setOrderId(order);
            orderItem.setApprovalStatus((short)0);
            new TrailableManager(orderItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
            
            em.persist(orderItem);
            em.flush();
            return orderItem;            
    }
    
    /**
     * Record lodgment items for mortgage lodgements
     * @param lodgement lodgment of the whole sale
     * @param orderItem the order item for the lodgment item that is being created
     * 
     */
    private LodgementItem createLodgementItem(Lodgement lodgement, OrderItem orderItem) throws PropertyException, RollbackException{
        LodgementItem lodgementItem = new LodgementItem();
        
        lodgementItem.setAmount(orderItem.getInitialDep());
        lodgementItem.setItem(orderItem);
        lodgementItem.setLodgementId(lodgement);
        lodgementItem.setApprovalStatus((short)0);
        new TrailableManager(lodgementItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
        
        em.persist(lodgementItem);
        em.flush();
        
        return lodgementItem;
        
    }
    
    
    
    /********************* APPROVAL **********************/
    public void processOrderApproval(ProductOrder order, List<OrderItem> orderItemsList, Customer customer) throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        int allItemsApproveDeclineFlag = 0;
        for(int i=0; i < orderItemsList.size(); i++){
            OrderItem thisItem = orderItemsList.get(i);
            System.out.println("Item status: " + thisItem.getApprovalStatus());
            
            if(thisItem.getApprovalStatus() == 1){//approve
                allItemsApproveDeclineFlag = 1;
                
                //if(order.getApprovalStatus() != 2) approveOrder(order);
                setOrderItemStatus(thisItem);
                
                //get/set corresponding lodgment item
                LodgementItem lodgementItem = ((List<LodgementItem>)thisItem.getLodgementItemCollection()).get(0);
                setLodgementItemStatus(lodgementItem, thisItem.getApprovalStatus());
                
                //double entry
                TransactionManager transactionManager = new TransactionManager(sessionUser);
                transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getUnit().getAccount(), thisItem.getInitialDep());
                
                //send approval alerts (email and SMS) to agent and customer
                AlertManager alertManager = new AlertManager();
                alertManager.sendOrderApprovalAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
                //credit agent wallet - double entry
                transactionManager.doDoubleEntry(thisItem.getUnit().getAccount(), customer.getAgent().getAccount(), thisItem.getCommissionAmount());
                
                //send wallet credit alert
                alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
            }
            if(thisItem.getApprovalStatus() == 2){//decline
                if(allItemsApproveDeclineFlag  == 0) allItemsApproveDeclineFlag = 2;
                setOrderItemStatus(thisItem);
                
                //get/set corresponding lodgment item
                LodgementItem lodgementItem = ((List<LodgementItem>)thisItem.getLodgementItemCollection()).get(0);
                setLodgementItemStatus(lodgementItem, thisItem.getApprovalStatus());
            }
            
            //set the resultant status of the order based on the statuses of the items in it
            if(allItemsApproveDeclineFlag == 1) //at least one item was approved
                setOrderStatus(order, (short)2); //approved order
            else
                setOrderStatus(order, (short)3); //decline order
            
        }//end for       
        
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
    
    private void setLodgementItemStatus(LodgementItem lodgementItem, short status) throws PropertyException, RollbackException{
        lodgementItem.setApprovalStatus(status);
        new TrailableManager(lodgementItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
    }
    
    
    
    
}