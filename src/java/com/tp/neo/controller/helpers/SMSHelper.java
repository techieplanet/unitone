/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.Withdrawal;
import com.tp.neo.model.utils.SMSSender;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Swedge
 */
public class SMSHelper {
    
    /*********************************** AGENT ***********************************/
    /*
    This method sends approval or decline SMS for agents
    */
    protected void sendAgentApprovalSMS(Agent agent, int status) {
        //send email 
        String approvalMessage = "Dear %s, your registration as an agent has been approved. You may now login on our site. Thank you";
        String declineMessage = "Dear %s, unfortunately your registration as an agent could not be approved.";
        String message = "";
        String phone = "";
        
        if(status == 1)
            message = String.format(approvalMessage, agent.getFirstname());
        else if (status == 0)
            message = String.format(declineMessage, agent.getFirstname());
        
        if(agent.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + agent.getPhone().substring(1);
        
        new SMSSender(phone,message).start();
    }
    
    protected void sendAgentWalletCreditAlert(Customer customer, ProjectUnit unit, double amount){
        String phone ="";
        String message =   "Acct: " + customer.getAgent().getAccount().getAccountCode() 
                      + "Wallet credit " + String.format("%.2f", amount)
                      + "Item: " + unit.getTitle() + " (" + unit.getProject().getName() + ") "
                      + "Customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ","                       
                      + "Qty: " + unit.getQuantity();
                      
        if(customer.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getPhone().substring(1);
        
        new SMSSender(phone,message).start(); 
    }
    
    
    
    
    
    
    /************** ORDER *******************/


    protected void sendNewOrderSMSToCustomer(Lodgement lodgement, Customer customer){
        String phone ="";
        String message =   "New Order of value " + lodgement.getAmount() + " has been received and is being processed."
                            + "Customer: " + customer.getFirstname() + " " + customer.getLastname();
                          
        if(customer.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getPhone().substring(1);
        
        new SMSSender(phone,message).start();  
    }
    
    protected void sendNewOrderSMSToAgent(Lodgement lodgement, Customer customer){
            String phone ="";
            String message =   "Acct: " + customer.getAgent().getAccount().getAccountCode()
                               + "New Order of value " + lodgement.getAmount() + " has been received and is being processed."
                               + "Customer: " + customer.getFirstname() + " " + customer.getLastname();

            if(customer.getAgent().getPhone().matches("^[0-9]{8,11}$"))
                phone = "234" + customer.getAgent().getPhone().substring(1);
        
            new SMSSender(phone,message).start();  
    }
    
    
    
    
    
    
    
    /************** ORDER APPROVALS *******************/
    protected void sendOrderApprovalSMSToCustomer(Customer customer, ProjectUnit unit, double amount){
        String phone = "";
        String message =   "Acct: " + customer.getAccount().getAccountCode() + ","
                      + "Order approval: " + unit.getTitle() + " (" + unit.getProject().getName() + ") " 
                      + "Amount advanced: " + String.format("%.2f", amount) + "."
                      + "Congratulations";
              
        
        if(customer.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getPhone().substring(1);
        
        new SMSSender(phone,message).start();        
    }
    
    protected void sendOrderApprovalSMSToAgent(Customer customer, ProjectUnit unit, double amount){
        String phone ="";
        String message =   "Acct: " + customer.getAccount().getAccountCode() + ","
                      + "Order approval: " + unit.getTitle() + " (" + unit.getProject().getName() + ") " 
                      + "Customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ")"
                      + "Amount advanced: " + String.format("%.2f", amount) + "."
                      + "<br/>" + "Congratulations"; 
        
        if(customer.getAgent().getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getAgent().getPhone().substring(1);
        
        new SMSSender(phone,message).start();      
    }

  
    
    
    
    
    
    
    /*********************************** LODGEMENT ***********************************/
    protected void sendNewLodgementSMSToCustomer(Lodgement lodgement, Customer customer){
        String phone ="";
        String message =   "Acct: " + customer.getAccount().getAccountCode()
                                    + "Lodgement of " + lodgement.getAmount() + " has been received and is awaiting processing.";
                          
        if(customer.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getPhone().substring(1);
        
        new SMSSender(phone,message).start();  
    }
    
    protected void sendNewLodgementEmailToAgent(Lodgement lodgement, Customer customer){
            String phone ="";
            String message =   "Acct: " + customer.getAgent().getAccount().getAccountCode()
                          + "Lodgement received for customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ")"
                          + "Amount: " + lodgement.getAmount();

            if(customer.getAgent().getPhone().matches("^[0-9]{8,11}$"))
                phone = "234" + customer.getAgent().getPhone().substring(1);
        
            new SMSSender(phone,message).start();  
    }
    
    
    
    
    
    
    /************** LODGEMENT APPROVALS *******************/
    protected void sendLodgementApprovalSMSToCustomer(Customer customer, ProjectUnit unit, double amount){
        String phone = "";
        String message =   "Acct: " + customer.getAccount().getAccountCode() + ","
                      + "Lodgement approval: " + unit.getTitle() + " (" + unit.getProject().getName() + ") " 
                      + "Amount advanced: " + String.format("%.2f", amount) + "."
                      + "Congratulations";
              
        
        if(customer.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getPhone().substring(1);
        
        new SMSSender(phone,message).start();        
    }
    
    protected void sendLodgementApprovalSMSToAgent(Customer customer, ProjectUnit unit, double amount){
        String phone ="";
        String message =   "Customer: " + customer.getFirstname() + " " + customer.getLastname() + customer.getAccount().getAccountCode() + ","
                      + "Lodgement approval: " + unit.getTitle() + " (" + unit.getProject().getName() + ") " 
                      + "Amount advanced: " + String.format("%.2f", amount) + "."
                      + "Congratulations"; 
        
        if(customer.getAgent().getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getAgent().getPhone().substring(1);
        
        new SMSSender(phone,message).start();      
    }
    
    
    
    
    /************** LODGEMENT DECLINES *******************/
    protected void sendLodgementDeclineSMSToCustomer(Customer customer, Lodgement lodgement, double amount){
        String phone = "";
        String message =   "Acct: " + customer.getAccount().getAccountCode() + ","
                      + "Lodgement decline: " + lodgement.getAmount() + " on " + lodgement.getCreatedDate() + ". " 
                      + "Please contact your agent or customer care for details.";
              
        
        if(customer.getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getPhone().substring(1);
        
        new SMSSender(phone,message).start();        
    }
    
    protected void sendLodgementDeclineSMSToAgent(Customer customer, Lodgement lodgement, double amount){
        String phone ="";
        String message =   "Customer: " + customer.getFirstname() + " " + customer.getLastname() + customer.getAccount().getAccountCode() + ","
                      + "Lodgement decline: " + lodgement.getAmount() + " on " + lodgement.getCreatedDate() + ". " 
                      + "Please contact your agent or customer care for details.";
        
        if(customer.getAgent().getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + customer.getAgent().getPhone().substring(1);
        
        new SMSSender(phone,message).start();      
    }
    
    
    
    
    
    /************************************  WITHDRAWAL ********************************/
    protected void sendWithdrawalRequestEmailToAgent(Withdrawal w){
        String phone = "";
        String message =   "Acct: " + w.getAgent().getFirstname() + " " + w.getAgent().getLastname() + " (" + w.getAgent().getAccount().getAccountCode() + "),"
                      + "Withdrawal request received and processing"
                      + "Amount: " + w.getAmount();
        
         if(w.getAgent().getPhone().matches("^[0-9]{8,11}$"))
            phone = "234" + w.getAgent().getPhone().substring(1);
        
        new SMSSender(phone,message).start(); 
    }
    
    
    
    
    /************************** REMINDER ALERT ****************************************/
    protected void sendReminderAlert(List customerAndItemsList, int dueDays){
        String orderItemsString = ""; String phone="";
        for(Object customerAndItems : customerAndItemsList){
            HashMap customerAndItemsMap = (HashMap) customerAndItems;
            Customer customer = (Customer)customerAndItemsMap.get("customer");
            List<OrderItem> orderItemsList = (List)customerAndItemsMap.get("order_items");
            
            for(Object itemListElement: orderItemsList){
                HashMap itemMap = (HashMap)itemListElement;
                OrderItem item = (OrderItem)itemMap.get("order_item");
                double balance = (Double)itemMap.get("balance");
                orderItemsString += item.getUnit().getTitle() + " (" + item.getUnit().getProject().getName() + "): " + balance + "; ";
            }
            
            orderItemsString = orderItemsString.substring(0, orderItemsString.length()-1);
        
        
            String message =   "Dear Customer (" + customer.getFirstname() + " " + customer.getLastname() + "(" + customer.getAccount().getAccountCode() + ")" 
                            + "<br/>Reminder on your orders with payment due in " + dueDays + "."
                            + "<br/>" + orderItemsString
                            + "<br/><br/>" + "Please take necessary actions.";

            if(customer.getPhone().matches("^[0-9]{8,11}$"))
                phone = "234" + customer.getPhone().substring(1);
        
            new SMSSender(phone,message).start(); 
        }
    }
}