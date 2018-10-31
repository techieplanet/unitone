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
import static com.tp.neo.model.OrderItem_.unit;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.User;
import com.tp.neo.model.Withdrawal;
import java.util.List;

/**
 *
 * @author swedge-mac
 */
public class AlertManager {
    
    public EmailHelper getEmailManager(){
        return new EmailHelper();
    }
    
    public SMSHelper getSMSManager(){
        return new SMSHelper();
    }
    
    public NotificationsManager getNotificationsManager(String route){
        return new NotificationsManager(route);
    }
    
    public NotificationsManager getNotificationsManager(){
        return new NotificationsManager();
    }
    
    public void sendAgentApprovalAlerts(Agent agent){
        new EmailHelper().sendAgentApprovalEmail(agent, 1);
        new SMSHelper().sendAgentApprovalSMS(agent, 1);
    }
    
    public void sendAgentWalletCreditAlerts(Customer customer, OrderItem item, double amount){
        new EmailHelper().sendAgentWalletCreditAlert(customer, item, amount);
        //new SMSHelper().sendAgentWalletCreditAlert(customer, item, amount);
    }
    
    
    
    
    
    
    public void sendNewOrderAlerts(ProductOrder order, Lodgement lodgement, Customer customer, List<User> recipientsList, String thisOrderPageLink){
        //emails
        new EmailHelper().sendNewOrderEmailToAdmins(order, customer, recipientsList, thisOrderPageLink);
        new EmailHelper().sendNewOrderEmailToCustomer(lodgement, customer);
        new EmailHelper().sendNewOrderEmailToAgent(lodgement, customer);
        
        //SMS
        new SMSHelper().sendNewOrderSMSToCustomer(lodgement, customer);
        new SMSHelper().sendNewOrderSMSToAgent(lodgement, customer);
    }
    
    
    
    public void sendOrderApprovalAlerts(Customer customer, ProjectUnit unit, double amount){
        //emails
        new EmailHelper().sendOrderApprovalEmailToCustomer(customer, unit, amount);
        new EmailHelper().sendOrderApprovalEmailToAgent(customer, unit, amount);
        
        //sms
        new SMSHelper().sendOrderApprovalSMSToCustomer(customer, unit, amount);
        new SMSHelper().sendOrderApprovalSMSToAgent(customer, unit, amount);
    }
    
    public void sendNewLodgementAlerts(Lodgement lodgement, Customer customer, List<User> recipientsList, String waitingLodgementsPageLink){
        //emails
        new EmailHelper().sendNewLodgementEmailToAdmins(lodgement, customer, recipientsList, waitingLodgementsPageLink);
        new EmailHelper().sendNewLodgementEmailToCustomer(lodgement, customer);
        new EmailHelper().sendNewLodgementEmailToAgent(lodgement, customer);
        
        //SMS
        new SMSHelper().sendNewLodgementSMSToCustomer(lodgement, customer);
        new SMSHelper().sendNewLodgementEmailToAgent(lodgement, customer);
    }
    
    
    public void sendLodgementApprovalAlerts(Customer customer, OrderItem item, double amount){
        //emails
        new EmailHelper().sendLodgementApprovalEmailToCustomer(customer, item, amount);
        new EmailHelper().sendLodgementApprovalEmailToAgent(customer, item, amount);
        
        //sms
        //new SMSHelper().sendLodgementApprovalSMSToCustomer(customer, unit, amount);
        //new SMSHelper().sendLodgementApprovalSMSToAgent(customer, unit, amount);
    }
    
    
    public void sendLodgementDeclineAlerts(Customer customer, Lodgement lodgement, double amount){
        //emails
        new EmailHelper().sendLodgementDeclineEmailToCustomer(customer, lodgement, amount);
        new EmailHelper().sendLodgementDeclineEmailToAgent(customer, lodgement, amount);
        
        //sms
        new SMSHelper().sendLodgementDeclineSMSToCustomer(customer, lodgement, amount);
        new SMSHelper().sendLodgementDeclineSMSToAgent(customer, lodgement, amount);
    }
       
    
    /************************************  WITHDRAWAL ********************************/
    public void sendNewWithdrawalRequestAlerts(Withdrawal w, List<User> recipientsList, String withdrawalPageLink){
        //email to admin
        new EmailHelper().sendWithdrawalRequestEmailToAdmin(w, recipientsList, withdrawalPageLink);
        
        //agent
        new EmailHelper().sendWithdrawalRequestEmailToAgent(w);
        
        new SMSHelper().sendWithdrawalRequestEmailToAgent(w);
    }
    
    
    /************************************  WITHDRAWAL ********************************/
    public void sendRemiderAlerts(List customerAndItemsList, int dueDays){
        //email
        new EmailHelper().sendReminderAlert(customerAndItemsList, dueDays);
        
        //sms
        new SMSHelper().sendReminderAlert(customerAndItemsList, dueDays);
    }
    
    
    
    /************************************  REFERRAL CODE SHARING ********************************/
    public void sendReferralCodeEmail(String recipientEmail, Agent agent, String reflink){
        new EmailHelper().sendReferralCodeEmail(recipientEmail, agent, reflink);
    }
    
    public void sendReferralCodeSMS(String recipientEmail, Agent agent, String reflink){
        new EmailHelper().sendReferralCodeEmail(recipientEmail, agent, reflink);
    }
    
    
}