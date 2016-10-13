/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.User;
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
    
    public void sendAgentApprovalAlerts(Agent agent){
        new EmailHelper().sendAgentApprovalEmail(agent, 1);
        new SMSHelper().sendAgentApprovalSMS(agent, 1);
    }
    
    public void sendAgentWalletCreditAlerts(Customer customer, ProjectUnit unit, double amount){
        new EmailHelper().sendAgentWalletCreditAlert(customer, unit, amount);
        new SMSHelper().sendAgentWalletCreditAlert(customer, unit, amount);
    }
    
    
    
    
    
    
    public void sendNewOrderAlerts(ProductOrder order, Lodgement lodgement, Customer customer, List<User> recipientsList){
        //emails
        new EmailHelper().sendNewOrderEmailToAdmins(order, customer, recipientsList);
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
    
    public void sendNewLodgementAlerts(Lodgement lodgement, Customer customer, List<User> recipientsList){
        //emails
        new EmailHelper().sendNewLodgementEmailToAdmins(lodgement, customer, recipientsList);
        new EmailHelper().sendNewLodgementEmailToCustomer(lodgement, customer);
        new EmailHelper().sendNewLodgementEmailToAgent(lodgement, customer);
        
        //SMS
        new SMSHelper().sendNewLodgementSMSToCustomer(lodgement, customer);
        new SMSHelper().sendNewLodgementEmailToAgent(lodgement, customer);
    }
    
    
    public void sendLodgementApprovalAlerts(Customer customer, ProjectUnit unit, double amount){
        //emails
        new EmailHelper().sendLodgementApprovalEmailToCustomer(customer, unit, amount);
        new EmailHelper().sendLodgementApprovalEmailToCustomer(customer, unit, amount);
        
        //sms
        new SMSHelper().sendLodgementApprovalSMSToCustomer(customer, unit, amount);
        new SMSHelper().sendLodgementApprovalSMSToAgent(customer, unit, amount);
    }
       
}