/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.User;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.xml.bind.PropertyException;
import org.apache.commons.lang3.text.StrSubstitutor;
        
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
        
        //persist the lodgement and notify admin about it
        em.persist(lodgement);
        em.flush();
        
        //log the notification in the database
        String route = "Lodgement?action=notification&id=" + lodgement.getId();
        Notification notification = new AlertManager().getNotificationsManager(route).setupLodgementNotification(customer,lodgement);
        em.persist(notification);
        
        //send the alert to customer, agent and admin
        AlertManager alertManager = new AlertManager();
        List<User> recipientsList = em.createNamedQuery("User.findAll").getResultList();
        for(int i=0; i < recipientsList.size(); i++){
            if( !(recipientsList.get(i).hasActionPermission("approve_lodgement")) )
                recipientsList.remove(i);
        }
        String waitingLodgementsPageLink = applicationContext + "Lodgement?action=approval";
        alertManager.sendNewLodgementAlerts(lodgement, customer, recipientsList, waitingLodgementsPageLink);
        
        
        //create the order 
        ProductOrder order = createOrder(customer.getAgent(), customer);
        
        //process the order items
        for(int i=0; i < orderItems.size(); i++){
            OrderItem orderItem = createOrderItem(orderItems.get(i), order);    //insert sale item
            createLodgementItem(lodgement, orderItem);                          //insert the lodgment items
        }
        
        em.getTransaction().commit();
        
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
        order.setMortgageStatus((short)0);
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
    public void processOrderApproval(ProductOrder order, List<OrderItem> orderItemsList, Customer customer, Notification notification) throws PropertyException, RollbackException{
        
        em.getTransaction().begin();
        
        AlertManager alertManager = new AlertManager();
        
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
                
                //double entry: debit customer, credit unit
                TransactionManager transactionManager = new TransactionManager(sessionUser);
                System.out.println("Customer Account = " + customer.getAccount());
                System.out.println("Unit Account = " + thisItem.getUnit().getAccount());
                System.out.println("Initial Deposit = " + thisItem.getInitialDep() );
                
                transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getUnit().getAccount(), thisItem.getInitialDep());
                
                //send approval alerts (email and SMS) to agent and customer
                alertManager.sendOrderApprovalAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
                //double entry (credit agent wallet): credit agent, debit unit
                transactionManager.doDoubleEntry(thisItem.getUnit().getAccount(), customer.getAgent().getAccount(), thisItem.getCommissionAmount());
                
                //send wallet credit alert
                alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
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
            
            //set the notification status here
            notification.setStatus((short)2);
            em.merge(notification);
        }
        else if(declinedItems.size() == allItems.size()){
            setOrderStatus(order, (short)3); //decline order
            //List<LodgementItem> lodgementItems = (List)orderItemsList.get(0).getLodgementItemCollection();
            //setLodgementStatus(lodgementItems.get(0).getLodgement(), (short)0); //decline lodgement
        }
        else if(approvedItems.size() + declinedItems.size() < allItems.size()){
            setOrderStatus(order, (short)1); //processing status
            //no need to treat lodgement items
            
        }
        em.merge(order);
        em.getTransaction().commit();
    }
    
    /**
     * This is the approval method for the first lodgement when the order is initially made
     * @param lodgement the lodgement class
     * @param order the order class
     * @param customer the customer making the purchase
     * @param applicationContext  the application context path
     */
    public void processOrderLevelLodgementApproval(Lodgement lodgement, ProductOrder order, Customer customer, String applicationContext){
        em.getTransaction().begin();
        
        //credit the customer account to the tune of the lodgment 
        //Account cashAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "CASH").getSingleResult();
        //new TransactionManager(sessionUser).doDoubleEntry(cashAccount, customer.getAccount(), lodgement.getAmount());
        
        //create new order system notification
        String route =  applicationContext + "/Order?action=notification&id=" + order.getId();
        Notification notification = new AlertManager().getNotificationsManager(route).createNewOrderNotification(customer,order);
        em.persist(notification);
        
        //send email alert to all Admins with approve_order permisison
        List<User> recipientsList = em.createNamedQuery("User.findAll").getResultList();
        for(int i=0; i < recipientsList.size(); i++){
            if( !(recipientsList.get(i).hasActionPermission("approve_order")) )
                recipientsList.remove(i);
        }
        String thisOrderPageLink = applicationContext + "/Order?action=notification&id=" + order.getId();
        new AlertManager().sendNewOrderAlerts(order, lodgement, customer, recipientsList, thisOrderPageLink);
        
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
    
    private static String getInvoiceFileContent(String invoiceFilePath) throws IOException{
        if(invoiceFilePath.equals(""))
            invoiceFilePath = "/Users/mac/dev/javaee/NeoForce/build/web/views/customer/invoice3.jsp";
        
        Path path = Paths.get(invoiceFilePath);
        String content = new String(Files.readAllBytes(path));
        
        //System.out.println("Shout: " + content);
        //URL location = OrderManager.class.getProtectionDomain().getCodeSource().getLocation();
        //System.out.println("location: " + location.getFile());
        return content;
    }
    
    
    private static String resolveInvoiceValues(Customer customer, Lodgement lodgement, Company company, String invoiceContent){
        Map<String, String> invoiceValuesMap = new HashMap<String, String>();
        
        invoiceValuesMap.put("companyName", company.getName()); //2 placeholders
        invoiceValuesMap.put("addressLine1", company.getAddressLine1());
        invoiceValuesMap.put("addressLine2", company.getAddressLine2());
        invoiceValuesMap.put("phone", company.getPhone());
        invoiceValuesMap.put("email", company.getEmail());
        
        invoiceValuesMap.put("customerName", customer.getFirstname() + " " + customer.getLastname());
        invoiceValuesMap.put("customerStreet", customer.getStreet());
        invoiceValuesMap.put("customerCity", customer.getCity());
        invoiceValuesMap.put("customerState", customer.getState());
        invoiceValuesMap.put("customerPhone", customer.getPhone());
        invoiceValuesMap.put("customerEmail", customer.getEmail());
        
        invoiceValuesMap.put("orderId", lodgement.getId().toString());
        
        List<LodgementItem> lodgementItems = (List)lodgement.getLodgementItemCollection();
        String salesDataHTML = ""; int count = 1;
        for(LodgementItem lodgementItem : lodgementItems ){
            Map<String, String> salesValuesMap = new HashMap<String, String>();
            salesValuesMap.put("SN", String.valueOf(count));
            salesValuesMap.put("projectName", lodgementItem.getItem().getUnit().getProject().getName());
            salesValuesMap.put("unitName", lodgementItem.getItem().getUnit().getTitle());
            salesValuesMap.put("qty", lodgementItem.getItem().getQuantity().toString());
            salesValuesMap.put("subTotal", String.format("%,.2f",lodgementItem.getAmount()));
            
            StrSubstitutor substitutor = new StrSubstitutor(salesValuesMap);
            String resolvedString = substitutor.replace(getSaleLineHTML());
        
            salesDataHTML += resolvedString;
            
            count++;
        }
        
        double gatewayCharge = 0.00;
        double vat = 0.00;
        
        invoiceValuesMap.put("salesData", salesDataHTML);
        invoiceValuesMap.put("total", String.format("%,.2f",lodgement.getAmount()));
        invoiceValuesMap.put("vat", String.format("%,.2f", vat));
        invoiceValuesMap.put("gatewayCharge", String.format("%,.2f", gatewayCharge));
        invoiceValuesMap.put("grandTotal", String.format("%,.2f",(lodgement.getAmount() + vat + gatewayCharge)));
        
        StrSubstitutor substitutor = new StrSubstitutor(invoiceValuesMap);
        String resolvedString = substitutor.replace(invoiceContent);
        
        return resolvedString;
    }
    
    private static String getSaleLineHTML(){
        return "<tr style=\"padding-top: 20px; font: normal normal 13px/20px Arial;margin: 0;\">" 
               + "<td style=\"width:10%;border:1px solid #eee;border-right: 0;padding:10px;text-align: center;\">${SN}</td>" 
               + "<td style=\"width:30%;border:1px solid #eee;border-right: 0;padding:10px;text-align: left;\">${projectName}</td>"
               + "<td style=\"width:30%;border:1px solid #eee;border-right: 0;padding:10px;text-align: left;\">${unitName}</td>"
               + "<td style=\"width:10%;border:1px solid #eee;border-right: 0;padding:10px;text-align: center;\">${qty}</td>"
               + "<td style=\"width:20%;border:1px solid #eee;padding:10px;text-align: right;\">${subTotal}</td>"
               + "</tr>";
    }
    
    public static String createInvoice(Customer customer, Lodgement lodgement, Company company, String invoiceMarkupFilePath){
        String resolvedString = "";
        
        try{
            
            String invoiceContent = getInvoiceFileContent(invoiceMarkupFilePath);
            
            resolvedString = resolveInvoiceValues(customer, lodgement, company, invoiceContent);
            System.out.println("resolvedString: " + resolvedString);
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resolvedString;
    }
    
    
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            
            Company company = em.find(Company.class, 1);
            Customer customer = em.find(Customer.class, 15L);
            Lodgement lodgement = em.find(Lodgement.class, 30L);
            
            createInvoice(customer, lodgement, company, "");
        
    }
    
}