/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Company;
import com.tp.neo.model.CompanyAccount;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.User;
import com.tp.neo.model.plugins.LoyaltyHistory;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.xml.bind.PropertyException;
import org.apache.commons.lang3.text.StrSubstitutor;
        
/**
 
 * @author swedge-mac
 */
public class OrderManager {
    
    SystemUser sessionUser;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    Map<String, Plugin> plugins = new HashMap();
    
    public OrderManager(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    public OrderManager(SystemUser sessionUser, Map map){
        this.sessionUser = sessionUser;
        this.plugins = map;
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
        //persist the lodgement and notify admin about it
        em.getTransaction().begin();
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
        lodgementItem.setRewardAmount(orderItem.getRewardAmount());
        
        new TrailableManager(lodgementItem).registerInsertTrailInfo(sessionUser.getSystemUserId());
        
        em.persist(lodgementItem);
        em.flush();
    }
    
    
    
    /********************* APPROVAL **********************/
    public void processOrderApproval(ProductOrder order, List<OrderItem> orderItemsList, Customer customer, Notification notification) throws PropertyException, RollbackException{
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        //AlertManager alertManager = new AlertManager();
        
        //this list will be used to collect all approved lodgmentItems
        List<LodgementItem> approvedLodgementItems = new ArrayList<LodgementItem>();
        
        //get the unapproved order items for this order
        List<OrderItem> allItems = (List)order.getOrderItemCollection();
        List<OrderItem> approvedItems = new ArrayList<OrderItem>();
        List<OrderItem> waitingItems = new ArrayList<OrderItem>();
        List<OrderItem> declinedItems = new ArrayList<OrderItem>();
        
//        for(int i=0; i < allItems.size(); i++){
//            if(allItems.get(i).getApprovalStatus().intValue() > 0) waitingItems.add(allItems.get(i));
//            if(allItems.get(i).getApprovalStatus().intValue() > 1) approvedItems.add(allItems.get(i));
//            if(allItems.get(i).getApprovalStatus().intValue() > 2) declinedItems.add(allItems.get(i));
//        }
        
        for(int i=0; i < orderItemsList.size(); i++){
            OrderItem thisItem = orderItemsList.get(i);
            
            if(thisItem.getApprovalStatus() == 1){//approve
                approvedItems.add(thisItem);
                
                //note that 1 has been set for the item approval status already
                //this will now set the audit dates and approval date
                setOrderItemDates(thisItem); 
                //em.persist(thisItem);
                
                //get/set corresponding lodgment item

                List lodgementItems = (List)thisItem.getLodgementItemCollection();
                LodgementItem lodgementItem = (LodgementItem) lodgementItems.get(0);

                setLodgementItemStatus(lodgementItem, thisItem.getApprovalStatus());
                //em.persist(lodgementItem);
                
                approvedLodgementItems.add(lodgementItem);
                
                //add the credit points to the customer
                customer.setRewardPoints(customer.getRewardPoints() + thisItem.getRewardPoints());
                
                //the lodgement has been approved before we can get here. 
                //Now process the lodgment item, which is a part of the whole lodgement 
                //double entry: debit customer, credit unit
                //TransactionManager transactionManager = new TransactionManager(sessionUser);
                ////System.out.println("Customer Account = " + customer.getAccount());
                ////System.out.println("Unit Account = " + thisItem.getUnit().getAccount());
                ////System.out.println("Initial Deposit = " + thisItem.getInitialDep() );
                
                //transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getUnit().getAccount(), thisItem.getInitialDep());
                
                //send approval alerts (email and SMS) to agent and customer
                //alertManager.sendOrderApprovalAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
                //double entry (credit agent wallet): credit agent, debit unit                
                //double commissionAmount = thisItem.getCommissionAmount(lodgementItem.getAmount());
                ////System.out.println("LODGEMENT AMOUNT: " + lodgementItem.getAmount());
                ////System.out.println("COMMISSION PCENT: " + thisItem.getCommissionPercentage());
                ////System.out.println("COMMISSION AMOUNT: " + commissionAmount);
                //transactionManager.doDoubleEntry(thisItem.getUnit().getAccount(), customer.getAgent().getAccount(), commissionAmount);
                
                //send wallet credit alert
                //alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getUnit(), thisItem.getInitialDep());
                
            }
            
            if(thisItem.getApprovalStatus() == 2){//decline
                declinedItems.add(thisItem);
                
                setOrderItemDates(thisItem);
                
                //get/set corresponding lodgment item
                LodgementItem lodgementItem = ((List<LodgementItem>)thisItem.getLodgementItemCollection()).get(0);
                setLodgementItemStatus(lodgementItem, thisItem.getApprovalStatus());
            }
            
            
            
        }//end for
        
        if(plugins.containsKey("loyalty")){
            //send the list of approved lodgmentitems to the LMngr's processApprovedLodgementItems method 
            new LodgementManager(sessionUser,(HashMap)plugins).processApprovedLodgementItems(approvedLodgementItems, customer, order);
        }
        else{
            
            //send the list of approved lodgmentitems to the LMngr's processApprovedLodgementItems method 
            new LodgementManager(sessionUser).processApprovedLodgementItems(approvedLodgementItems, customer, order);
        }
        /*******************************************************************************************************/
        //System.out.println("Approved Items : " + approvedItems.size() + ", All Items : " + allItems.size());
        //set the resultant status of the order based on the statuses of the items in it
        if(approvedItems.size() + declinedItems.size()  == allItems.size()){ //each item has either approved or declined status
            setOrderStatus(order, (short)2); //complete the order
            //set the notification status here
            notification.setStatus((short)2);
           // em.persist(notification);
            
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
        
        //em.persist(order);
        //em.flush();
        em.getTransaction().commit();
        em.close();
    }
    
    /**
     * This was the approval method for the first lodgement when the order is initially made
     * It has been stripped down in functionality to just do alerts for the new order
     * @param lodgement the lodgement class
     * @param order the order class
     * @param customer the customer making the purchase
     * @param applicationContext  the application context path
     */
    //public void processOrderLevelLodgementApproval(Lodgement lodgement, ProductOrder order, Customer customer, String applicationContext){
    public void createNewOrderAlerts(Lodgement lodgement, ProductOrder order, Customer customer, String applicationContext){
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
        //em.flush();
    }
    
    private void setOrderItemDates(OrderItem orderItem) throws PropertyException, RollbackException{
        //set approval date first
        orderItem.setApprovalDate(new Date());
        new TrailableManager(orderItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        //em.flush();
    }
    
    private void setLodgementStatus(Lodgement lodgement, short status) throws PropertyException, RollbackException{
        lodgement.setApprovalStatus(status);
        new TrailableManager(lodgement).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.flush();
    }
    
    private void setLodgementItemStatus(LodgementItem lodgementItem, short status) throws PropertyException, RollbackException{
        lodgementItem.setApprovalStatus(status);
        new TrailableManager(lodgementItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        //em.flush();
    }
    
    private static String getInvoiceFileContent(String invoiceFilePath) throws IOException{
        if(invoiceFilePath.equals(""))
            invoiceFilePath = "/Users/mac/dev/javaee/NeoForce/build/web/views/customer/invoice3.jsp";
        
        Path path = Paths.get(invoiceFilePath);
        String content = new String(Files.readAllBytes(path));
        
        ////System.out.println("Shout: " + content);
        //URL location = OrderManager.class.getProtectionDomain().getCodeSource().getLocation();
        ////System.out.println("location: " + location.getFile());
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
            //System.out.println("resolvedString: " + resolvedString);
        }
        catch(IOException e){
            //System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return resolvedString;
    }
    
    public List<OrderItem> prepareOrderItem(OrderItemObjectsList salesItemObject, Agent agent){
     return prepareOrderItem(salesItemObject , agent , false);
    }
    
    public List<OrderItem> prepareOrderItem(OrderItemObjectsList salesItemObject, Agent agent , boolean isAdmin){
        List<OrderItem> orderItemList = new ArrayList();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<OrderItemObject> salesItem = salesItemObject.sales;
        
        Gson gson = new Gson();
        double amountPerRewardPoint = 0;
        
        if(plugins.containsKey("loyalty")){
               
               Type mapType = new TypeToken<Map<String, String>>(){}.getType();
               Map<String, String> loyaltySettingsMap = gson.fromJson(plugins.get("loyalty").getSettings(), mapType);
               amountPerRewardPoint =  Double.parseDouble(loyaltySettingsMap.get("reward_value"));
               
               
        }
        
        for(OrderItemObject saleItem : salesItem) {
            
            OrderItem orderItem = new OrderItem();
            
            short approval = 0;
            long unitId = saleItem.productUnitId;
            ProjectUnit projectUnit = em.find(ProjectUnit.class, unitId);
            int qty = saleItem.productQuantity;
            
            orderItem.setQuantity(qty);
            orderItem.setInitialDep((double)(saleItem.productMinimumInitialAmount));
            
            orderItem.setCreatedDate(getDateTime().getTime());
            orderItem.setMonthlyPayDay(saleItem.dayOfNotification);
            
            if(plugins.containsKey("loyalty")){
               
               orderItem.setRewardAmount(saleItem.rewardPoint * amountPerRewardPoint);
               orderItem.setRewardPoints((int)saleItem.rewardPoint);
               orderItem.setRewardPoint(projectUnit.getRewardPoints() * qty);
            }
            else{
                orderItem.setRewardAmount(new Double(0));
                orderItem.setRewardPoints(0);
                orderItem.setRewardPoint(0.0);
            }
            
            //if(sessionUser.getSystemUserTypeId() == 1){
              //  //System.out.println("Sale Item Commp: " + saleItem.commp);
            //}
            if(sessionUser != null){
                orderItem.setCreatedBy(sessionUser.getSystemUserId()); 
            }
                
            orderItem.setApprovalStatus(approval);
            orderItem.setUnit(projectUnit);
            orderItem.setCommissionPercentage(projectUnit.getCommissionPercentage());
            orderItem.setVatPercentage(projectUnit.getVatPercentage());
            orderItem.setAnnualMaintenancePercentage(projectUnit.getAnnualMaintenancePercentage());
            
            if(isAdmin)
            orderItem.setAmountPayable((double)saleItem.productAmount);
            else
            orderItem.setAmountPayable(projectUnit.getAmountPayable() * qty);
            
            orderItem.setCostPrice(projectUnit.getCpu() * qty );
            
            orderItem.setMontlyPayment((double)saleItem.monthlyPayPerQuantity); // hey we need This for some calculations
            orderItem.setServiceValue(projectUnit.getServiceValue() * qty);
            //int payDuration = saleItem.productMaximumDuration.indexOf(" ");
            orderItem.setMaxPaymentDuration(Integer.parseInt(saleItem.productMaximumDuration));
            
            if(isAdmin)
            {
            orderItem.setDiscountPercentage(saleItem.discountPercent);
            orderItem.setDiscountAmt(calculatediscountAmount(saleItem.discountPercent , projectUnit)* qty);
            }
            else
            {
            orderItem.setDiscountPercentage(projectUnit.getDiscount());
            orderItem.setDiscountAmt(calculatediscountAmount(projectUnit)* qty);
            }
            
            orderItem.setProjectUnitDiscountPercentage(projectUnit.getDiscount());
            //orderItem.setIncome(projectUnit.getIncome() * qty);
            orderItemList.add(orderItem);
            
            ////System.out.println("Notification ID : " + saleItem.dayOfNotification);
        }
        
        return orderItemList;
    }
    
    public List<LoyaltyHistory> prepareLoyaltyHistory(OrderItemObjectsList orderItemObject){
        
        List<LoyaltyHistory> loyaltyHistoryList = new ArrayList();
        
        for(OrderItemObject itemObj : orderItemObject.sales){
            
            LoyaltyHistory history = new LoyaltyHistory();
            
        }
        
        return loyaltyHistoryList;
    }
    
    public Lodgement prepareLodgement(Map request, Agent agent) {
        
        Lodgement lodgement = new Lodgement();
        
        Short paymentMethod = Short.parseShort(request.get("paymentMethod").toString());
        
        CompanyAccount companyAccount = em.find(CompanyAccount.class, Integer.parseInt(request.get("companyAccount").toString()));
        
        lodgement.setPaymentMode(paymentMethod);
        lodgement.setLodgmentDate(this.getDateTime().getTime());
        lodgement.setCreatedDate(this.getDateTime().getTime());
        if(sessionUser != null) {
            lodgement.setCreatedBy(sessionUser.getSystemUserId());
            lodgement.setCreatedByUserType(Short.parseShort((sessionUser.getSystemUserTypeId()).toString()));
        }
        lodgement.setCompanyAccountId(companyAccount);
        lodgement.setApprovalStatus((short)0);
        
        if(paymentMethod == 1) {
            lodgement.setTransactionId(request.get("tellerNumber").toString());
            lodgement.setAmount(Double.parseDouble(request.get("tellerAmount").toString()));
            lodgement.setDepositorName(request.get("depositorsName").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
        }
        else if(paymentMethod == 2){
            lodgement.setAmount(Double.parseDouble(request.get("cardAmount").toString()));
        }
        else if(paymentMethod == 3){
            
            lodgement.setAmount(Double.parseDouble(request.get("cashAmount").toString()));
            
        }
        else if(paymentMethod == 4) {
            lodgement.setDepositorBankName(request.get("transfer_bankName").toString());
            lodgement.setAmount(Double.parseDouble(request.get("transfer_amount").toString()));
            lodgement.setOriginAccountName(request.get("transfer_accountName").toString());
            lodgement.setOriginAccountNumber(request.get("transfer_accountNo").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
            
        }
        
        return lodgement;
    }
    
    public OrderItemObjectsList getCartData(String orderItemsJsonString) {
        OrderItemObjectsList saleObj = this.processJsonData(orderItemsJsonString);
        return saleObj;
    }
    
    //This method processes the new order items, sent as json data via request attribute from new order form
    private OrderItemObjectsList processJsonData(String json) {
        Gson gson = new GsonBuilder().create();
        ////System.out.println(json);
        
        OrderItemObjectsList salesObj = gson.fromJson(json,OrderItemObjectsList.class);
        
        ArrayList<OrderItemObject> sales = salesObj.sales;
        
       /* for(OrderItemObject s : sales) {
            //System.out.println("Product Name : " + s.productName);
            //System.out.println("Product Qty : " + s.productQuantity);
            //System.out.println("Product Amount Per Unit " + s.amountUnit);
            //System.out.println("Product Cost " + s.amountTotalUnit);
            //System.out.println("Commision Payable : " + s.commp);
        }*/
        
        return salesObj;
    }
    
    private Calendar getDateTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        return calendar;
    }
    
    public Lodgement setLodgementRewardPoint(Lodgement lodgement,List<OrderItem> items){
        
        double pointUsed = 0;
        double amountPaid = 0;
        
        for(OrderItem item : items){
            
            pointUsed += item.getRewardAmount();
            amountPaid += item.getInitialDep();
        }
        
        lodgement.setAmount(amountPaid);
        lodgement.setRewardAmount(pointUsed);
        
        return lodgement;
    }
    
    private double calculatediscountAmount( double discountPercent  , ProjectUnit unit){
        double cpuMinusServiceValue = unit.getCpu() - unit.getServiceValue();
        double discountAmount = (cpuMinusServiceValue * discountPercent)/100;
        return discountAmount;
    }
   private double calculatediscountAmount(ProjectUnit unit){
        double cpuMinusServiceValue = unit.getCpu() - unit.getServiceValue();
        double discountAmount = (cpuMinusServiceValue * unit.getDiscount())/100;
        return discountAmount;
    }
   
   public boolean validateOrder(List<OrderItem> orderItems , Lodgement lodgement, StringBuilder errorMSG){
      
       //Validating if the initial payment specified by user is lesser that what is required
       Double customerTotalInitialPayment = 0.0;
      
       for(OrderItem item : orderItems)
       {
         //Here we calculate the actual value of initial deposit that we are expecting
         double expectedIntialDep = item.getQuantity() * item.getUnit().getLeastInitDep();
         //Lets compare it with the value we are receiving from the front end
         if(item.getInitialDep() < expectedIntialDep )
         {
             //Here we know that the customer have reduced the amount he/she oghth to pay 
             errorMSG.append("One or more Order have an Invalid Initial deposit amount");
             return false;
         }
         
         //for each order item lets validate the montly payment amount and the maximum payment duration
         //lets first get the maximum payment duration specified from front end
         int payDuration = item.getMaxPaymentDuration();
         if(payDuration <= item.getUnit().getMaxPaymentDuration())
         {
             //Here we Know The Customer wants to pay for a lesser time
             //Now we need to judge his montly payment according to his/her 
             //Demand
             boolean valid = validateMontlyPayment(item);
             if(!valid)
             {
             errorMSG.append("The Montly Payment value You entered Is Invalid for one or more orders");
             return false;
             }
         }
         else
         {
             //Here We definately know That the customer wants to beat the system
             errorMSG.append("The Maximum Payment Duration You entered Is Invalid for one or more orders");
             return false;
         }
         //If we get here that mean the customer is paying the expected value or is paying over
         customerTotalInitialPayment += item.getInitialDep();
         
       }
       
       if(lodgement.getAmount() < customerTotalInitialPayment)
       {
           errorMSG.append("The Lodgement Amount is invalid");
           return false;
       }
       
       
      return true;
  }
   
   private  boolean validateMontlyPayment(OrderItem item){
       double amount = item.getAmountPayable();
       double maxPayduration = item.getMaxPaymentDuration();
       double initialPayment = item.getInitialDep();
       double montlyPayment = Math.round(item.getMontlyPayment());
       
       double balance = amount - initialPayment;
       
       double validMontlyPay = Math.round(balance / maxPayduration);
       //Double.validMontlyPay
       return validMontlyPay == montlyPayment;
   }
   
    public static void main(String[] args){
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
//            EntityManager em = emf.createEntityManager();
//            
//            Company company = em.find(Company.class, 1);
//            Customer customer = em.find(Customer.class, 15L);
//            Lodgement lodgement = em.find(Lodgement.class, 30L);
//            
//            createInvoice(customer, lodgement, company, "");
        
    }
    
}