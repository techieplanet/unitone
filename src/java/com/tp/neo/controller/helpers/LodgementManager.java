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
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.User;
import com.tp.neo.model.plugins.LoyaltyHistory;
import com.tp.neo.model.utils.TrailableManager;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.xml.bind.PropertyException;

/**
 *
 * @author swedge-mac
 */
public class LodgementManager {
    
    SystemUser sessionUser;
    HashMap<String, Plugin> availablePlugins = new HashMap<String, Plugin>();
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    Gson gson = new GsonBuilder().create();
    
    public LodgementManager(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    public LodgementManager(SystemUser sessionUser, HashMap<String, Plugin> availablePlugins){
        this.sessionUser = sessionUser;
        this.availablePlugins = availablePlugins;
    }
    
    /**
     * This method will set up the lodgement, the sale items and the lodgement records.
     * 
     * @param agent the agent that owns the customer
     * @param customer the customer that is ordering the items
     * @param orderItems List of items being purchased
     * @return 1 - if all the operations are successful, 0 otherwise
     */
    public Lodgement processLodgement(Customer customer, Lodgement lodgement, List<LodgementItem> lodgementItems, String applicationContext, ProductOrder order)
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
        Notification notification = new AlertManager().getNotificationsManager(route).setupLodgementNotification(customer,lodgement);
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
            System.out.println("Inside approveLodgement order side");
            //process lodgement approval for a fresh  new order
            processLodgementApproval(lodgement, lodgement.getCustomer(), notification);
            
            //now this method will create the alerts for the order 
            new OrderManager(sessionUser).createNewOrderAlerts(lodgement, order, lodgement.getCustomer(), applicationContext);
            //Don't Fret: processApprovedLodgementItems will be called in order manager when the order is finally approved
        }
        else{//mortgage lodgement
            System.out.println("Inside make lodgement route");
            //processLodgementApproval(lodgement, lodgementItems, lodgement.getCustomer(), notification, order);
            processLodgementApproval(lodgement, lodgement.getCustomer(), notification);
            processApprovedLodgementItems(lodgementItems, lodgement.getCustomer(), order);
        }
    }
   
    
    /**
     * 1. This method is used to approve the lodgment made for a new order i.e. it is run when approve lodgement is clicked
     * 2. It also clears the lodgment notification
     * 3. It credits the customer account since the lodgement has been approved
     * 4. Commits changes to database
     * @param lodgement
     * @param customer
     * @param notification
     * @throws PropertyException
     * @throws RollbackException 
     */
    private void processLodgementApproval(Lodgement lodgement, Customer customer, Notification notification) throws PropertyException, RollbackException{
        em.getTransaction().begin();
        
        System.out.println("Order Level Lodgement Approval");
        
        //approve the lodgement
        lodgement.setApprovalStatus((short)1);
        new TrailableManager(lodgement).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        em.merge(lodgement);
        
        //set the lodgement items as approved to ensure the order can be traced for fresh order process flow
        List<LodgementItem> lodgmeItems = (List)lodgement.getLodgementItemCollection();
        for(LodgementItem thisItem : lodgmeItems){
            thisItem.setApprovalStatus((short)1);
            new TrailableManager(thisItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
            em.merge(thisItem);
        }
        
        notification.setStatus((short)2);
        em.merge(notification);
        
        //double entry: debit cash, credit customer account to the tune of the lodgment
        Account cashAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "CASH").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(cashAccount, customer.getAccount(), lodgement.getAmount() + lodgement.getRewardAmount());
        
        em.getTransaction().commit();
        
        
    }
    
    
    /**
     * This will process approved (a new order may not have all order items approved and so not all lodgment items  ) lodgement items from an approved lodgement
     * @param lodgementItems
     * @param customer
     * @param order
     * @throws PropertyException
     * @throws RollbackException 
     */
    protected void processApprovedLodgementItems(List<LodgementItem> lodgementItems, Customer customer, ProductOrder order) throws PropertyException, RollbackException{
        em.getTransaction().begin();
        System.out.println("processApprovedLodgementItems LITEMS SIZE: " + lodgementItems.size());
        
        for(int i=0; i < lodgementItems.size(); i++){
            LodgementItem thisItem = lodgementItems.get(i);
            
            //set the item as approved
            //thisItem.setApprovalStatus((short)1);
            //new TrailableManager(thisItem).registerUpdateTrailInfo(sessionUser.getSystemUserId());
            
            //double entry - take the money out of the customer account and fund the project unit
            TransactionManager transactionManager = new TransactionManager(sessionUser);
            transactionManager.doDoubleEntry(customer.getAccount(), thisItem.getItem().getUnit().getAccount(), thisItem.getAmount());
            
            //split funds into respective accounts - buidling, service, income. Others can be added later too.
            this.splitUnitFunds(customer, thisItem.getItem().getUnit(), thisItem);
            
            
            
            //send approval alerts (email and SMS) to agent and customer
            AlertManager alertManager = new AlertManager();
            alertManager.sendLodgementApprovalAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());

            //send wallet credit alert
            alertManager.sendAgentWalletCreditAlerts(customer, thisItem.getItem().getUnit(), thisItem.getAmount());
            em.merge(thisItem);
        }//end for       
        
        em.getTransaction().commit();
        
        //set the mortgage status for the order based on the resultant of the order items
        updateMortgageStatus(order);
        
        //make sure to process loyalty after order mortgage status has been set
        if(this.availablePlugins.containsKey("loyalty")){
            em.getTransaction().begin();
            for(LodgementItem lodgementItem : lodgementItems){
                System.out.println("Customers point : " + customer.getRewardPoints());
                this.processLoyaltyDetails(lodgementItem, customer, order);
            }
            em.merge(customer);
            em.getTransaction().commit();
            em.close();
            emf.close();
        }
    }
    
    
    
    
    public void declineLodgementApproval(Lodgement lodgement, List<LodgementItem> lodgementItems, Customer customer, Notification notification, ProductOrder order) throws PropertyException, RollbackException{
//        em.getTransaction().begin();
//        
//        //decline the lodgement
//        lodgement.setApprovalStatus((short)2);
//        new TrailableManager(lodgement).registerUpdateTrailInfo(sessionUser.getSystemUserId());
//        em.merge(lodgement);
//        
//        notification.setStatus((short)2);
//        em.merge(notification);   
//
//        //decline the whole lodgement
//        AlertManager alertManager = new AlertManager();
//        alertManager.sendLodgementDeclineAlerts(customer, lodgement, lodgement.getAmount());
//
//        em.merge(lodgement);
//        em.getTransaction().commit();
//        updateMortgageStatus(order);
    }
    
    private void splitUnitFunds(Customer customer, ProjectUnit unit, LodgementItem lodgementItem){
        Account unitAccount = lodgementItem.getItem().getUnit().getAccount();
        
        //Split Vat amount into vat account
        double vatAmount = lodgementItem.getItem().calculateVatAmount(lodgementItem.getAmount());
        Account vatAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "VAT").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(unitAccount, vatAccount, vatAmount);
        
        double incomeAfterTax = lodgementItem.getAmount() - vatAmount;
        
        //Split commissions - credit agent wallet - double entry
        System.out.println("Lodgement order item: " + lodgementItem.getItem().getId());
        double commissionAmount = lodgementItem.getItem().getCommissionAmount(incomeAfterTax);
        new TransactionManager(sessionUser).doDoubleEntry(unitAccount, customer.getAgent().getAccount(), commissionAmount);
        
        //calculate annual maintenance amount = ama = comm - ( comm * am percentage)/100
        //double entry - debit: agent , credit : annual maintenace
        double annualMaintenance = lodgementItem.getItem().calculateAnnualMaintenanceAmount(commissionAmount);
        Account annualMaintenanceAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "AGENT_ANNUAL_MAINTENANCE").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(customer.getAgent().getAccount(), annualMaintenanceAccount, annualMaintenance);
        
        commissionAmount -= annualMaintenance;
        
        //calculate the commissions for the upline if MLM is enabled.
        processUplineCommissions(customer.getAgent(), commissionAmount);
        
        //Split property dev cost - debit unit, credit property dev  - double entry
        Account propertyDevelopmentAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "PROPERTY_DEV").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(unitAccount, propertyDevelopmentAccount, lodgementItem.getItem().getUnit().getBuildingCost());
        
        //Split services cost - debit unit, credit property dev  - double entry
        Account servicesAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "SERVICES").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(unitAccount, servicesAccount, lodgementItem.getItem().getUnit().getServiceValue());
        
        
        //Split reward cost - debit unit, credit loyalty  - double entry
//        Account loyaltyAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "LOYALTY").getSingleResult();
//        System.out.println("Account : " + loyaltyAccount.getAccountCode() + ", LodgementItem RewardPoint : " + lodgementItem.getRewardAmount());
//        new TransactionManager(sessionUser).doDoubleEntry(unitAccount, loyaltyAccount, lodgementItem.getRewardAmount());
//        
        
        //Split income amount - debit unit, credit credit income account  - double entry
        Account incomeAccount = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "INCOME").getSingleResult();
        new TransactionManager(sessionUser).doDoubleEntry(unitAccount, incomeAccount, lodgementItem.getItem().getUnit().getIncome() - lodgementItem.getRewardAmount());
    }
    
    
    private void processLoyaltyDetails(LodgementItem lodgementItem, Customer customer, ProductOrder order){
        System.out.println("Customer Reward Point : " + customer.getRewardPoints());
        System.out.println("LodgementItem Reward Point : " + lodgementItem.getRewardAmount());
        if(lodgementItem.getRewardAmount()== 0) return;
        
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        
        
                //loyalty history
                LoyaltyHistory loyaltyHistory = new LoyaltyHistory();
                loyaltyHistory.setCustomerId(customer);
                loyaltyHistory.setItemId(lodgementItem);
                
                Type mapType = new TypeToken<Map<String, String>>(){}.getType();
                Map<String, String> loyaltySettingsMap = gson.fromJson(availablePlugins.get("loyalty").getSettings(), mapType);
                double amountPerRewardPoint =  Double.parseDouble(loyaltySettingsMap.get("reward_value"));
                
                int rewardPoints = (int)(lodgementItem.getRewardAmount() / amountPerRewardPoint);
                loyaltyHistory.setRewardPoints(rewardPoints);
                loyaltyHistory.setType((short)order.getMortgageStatus());
                em.persist(loyaltyHistory);
                
                //customer point deductions
                customer.setRewardPoints(customer.getRewardPoints() - rewardPoints);
        
        System.out.println("Customer Reward Point : " + customer.getRewardPoints());
        //em.merge(customer);
        em.getTransaction().commit();
        
    }
    
    
    
    private void processUplineCommissions(Agent agent, double initialCommissionAmount){
        if(!this.availablePlugins.containsKey("mlm") || agent.getReferrerId() == 0)
            return;
        
        Type mapType = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> mlmSettingsMap = gson.fromJson(availablePlugins.get("mlm").getSettings(), mapType);
        double mlmPercentage =  Double.parseDouble(mlmSettingsMap.get("ucp"));
        double mlmPercentageDecimalValue = mlmPercentage / 100;
        
        //get upline
        long referrerId = agent.getReferrerId();
        List<Agent> uplineList = new ArrayList<Agent>();
        while(referrerId > 0 && uplineList.size() <= 4){
            Agent parentAgent = em.find(Agent.class, referrerId);
            uplineList.add(parentAgent);
            referrerId = parentAgent.getReferrerId();
        }
        
        //loop upline and calculate the commissions. 
        //immediately debit the agent and credit the upline
        for(int i = uplineList.size()-1; i >=0; i--){
            double commissionAmount = Math.pow(mlmPercentageDecimalValue, i) * initialCommissionAmount;
            new TransactionManager(sessionUser).doDoubleEntry(agent.getAccount(), uplineList.get(i).getAccount(), commissionAmount);
        }
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
    
    public double getTotalOrderLodgementAmount(ProductOrder order){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query JPQL = em.createNamedQuery("LodgementItem.findTotalApprovedOrderSum");
        JPQL.setParameter("orderId", order.getId());
        
        double total = (Double) JPQL.getSingleResult();
        
        return total;
    }
    
    public double getOrderActualCost(ProductOrder order){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query JPQL = em.createNamedQuery("ProductOrder.totalAmount");
        JPQL.setParameter("orderId", order.getId());
        
        double total = (Double) JPQL.getSingleResult();
        
        return total;
   }
    
    public void updateMortgageStatus(ProductOrder order){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        System.out.println("UpdateMortgageStatus Called");
        
        em.getTransaction().begin();
        
        double actualCost = getOrderActualCost(order);
        double totalLodgementAmount = getTotalOrderLodgementAmount(order);
        
        if((actualCost - totalLodgementAmount) <= 0){
            
            order.setMortgageStatus((short)1);
            em.merge(order);
        }
        
        em.getTransaction().commit();
        emf.getCache().evictAll();
       
    }
       
}