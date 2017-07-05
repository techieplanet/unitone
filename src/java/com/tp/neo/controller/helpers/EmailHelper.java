/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import static com.tp.neo.controller.components.AppController.APP_NAME;
import static com.tp.neo.controller.components.AppController.companyName;
import static com.tp.neo.controller.components.AppController.defaultEmail;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.User;
import com.tp.neo.model.Withdrawal;
import com.tp.neo.model.utils.MailSender;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Swedge
 */
public class EmailHelper {
    
    /*********************************** AGENT ***********************************/
    /*
    This method sends approval or decline email
    */
    protected void sendAgentApprovalEmail(Agent agent, int status) {
        //send email 
        String approvalMessage = "Dear %s %s,<br/>" +
                "Your registration as an agent on %s has been approved.<br/>" +
                "You may now login with your email and password used during registration.<br/><br/>" +
                "Thank you for choosing to work with us.";
        String declineMessage = "Dear %s %s,<br/>" +
                "Unfortunately your registration as an agent on %s could not be approved.<br/>";
        String emailSubject = APP_NAME + " Agent Registration Request";
        String message = "";
        
        if(status == 1)
            message = String.format(approvalMessage, agent.getFirstname(), agent.getLastname(), APP_NAME);
        else if (status == 0)
            message = String.format(declineMessage, agent.getFirstname(), agent.getLastname(), APP_NAME);
        
        //System.err.println("email " + agent.getEmail() + " email: " + defaultEmail + " subject: " + emailSubject + " message: " + message);
       new MailSender().sendHtmlEmail(agent.getEmail(), defaultEmail, emailSubject, message);

    }
    
    protected void sendAgentWalletCreditAlert(Customer customer, ProjectUnit unit, double amount){
        String messageBody =   "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                      + "<br/>" + "Your wallet has been credited " + String.format("%.2f", amount)
                      + "Item: " + unit.getTitle() + " in " + unit.getProject().getName() + "."
                      + "Customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ","                       
                      + "Number of Units: " + unit.getQuantity();
                      
        String emailSubject = APP_NAME + ": New Wallet Credit";
        
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    
    
    
    
    
   /*********************************** ORDERS ***********************************/
    /**
     * This email goes to the admin when a new order is created in the system
     * @param order the order that was just created
     * @param customer the customer that owns the order 
     * @param recipientsList the list of admins that can receive that email. One of them can now process the order
     */
    protected void sendNewOrderEmailToAdmins(ProductOrder order, Customer customer, List<User> recipientsList, String thisOrderPageLink){
        
        
        String messageBody =   "Dear Admin,"
                      + "<br/>" + "A new order, ID: <b>" + order.getId() + "</b> for customer: <b>" + customer.getFirstname() + " " + customer.getLastname() 
                      + " has been created and needs approval from you."
                      + "<br/>" 
                      + "<br/>"  
                      + "<br/>" + "Please follow the link below to take necessary action."
                      + "<br/>"  + "<a href=" + thisOrderPageLink + ">" + thisOrderPageLink + "</a>"
                      + "<br/>"
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Order Awaiting Approval";
        
        for(int i=0; i < recipientsList.size(); i++){
            new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, messageBody);
        }
        
    }
    
    
    protected void sendNewOrderEmailToCustomer(Lodgement lodgement, Customer customer){
            String messageBody =   "Dear " + customer.getFirstname() + " " + customer.getLastname() + ","
                          + "<br/>" + "Your order of value " + lodgement.getAmount() + " has been received and is being processed." 
                          + "<br/>"  
                          + "<br/>"  
                          + "<br/>" + APP_NAME;

            String emailSubject = APP_NAME + ": New Order";

            new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    protected void sendNewOrderEmailToAgent(Lodgement lodgement, Customer customer){
            String messageBody =   "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + ")," 
                          + "<br/>" + "Your new customer order has been received and is being processed."
                          + "<br/>" + "Customer: " + customer.getFirstname() + " " + customer.getLastname() 
                          + "<br/>" + "Order Amount: " + lodgement.getAmount() 
                          + "<br/>"  
                          + "<br/>"  
                          + "<br/>" + APP_NAME;

            String emailSubject = APP_NAME + ": New Order";

            new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    
    
    
    /*********************************** ORDER APPROVALS ***********************************/
    protected void sendOrderApprovalEmailToCustomer(Customer customer, ProjectUnit unit, double amount){
        String messageBody =   "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                      + "<br/>" + "Your order for " + unit.getTitle() + " in " + unit.getProject().getName() + " has been approved " 
                      + "and has been advanced by the sum of " + String.format("%.2f", amount) + "."
                      + "<br/>"  
                      + "<br/>" + "Congratulations " 
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Order Approval";
        
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    protected void sendOrderApprovalEmailToAgent(Customer customer, ProjectUnit unit, double amount){
        String messageBody =   "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                      + "<br/>" + "An order has been approved for your customer - " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "," 
                      + "Item: " + unit.getTitle() + " in " + unit.getProject().getName() + "."
                      + "Number of Units: " + unit.getQuantity()
                      + "This order has been advanced by the sum of " + String.format("%.2f", amount) + "."
                      + "<br/>"  
                      + "<br/>" + "Congratulations " 
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Order Approval";
        
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    
    
    
    
    
    /*********************************** LODGEMENT ***********************************/
    protected void sendNewLodgementEmailToAdmins(Lodgement lodgement, Customer customer, List<User> recipientsList, String waitingLodgementsPageLink){
        String messageBody =   "Dear Admin,"
                      + "<br/>" + "A new lodgement, ID: <b>" + lodgement.getId() + "</b> for customer: <b>" + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ")"
                      + " has been created and needs approval."
                      + "<br/>" 
                      + "<br/>"  
                      + "<br/>" + "Please follow the link below to take necessary action."
                      + "<br/>" +  waitingLodgementsPageLink
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Lodgement Awaiting Approval";
        
        for(int i=0; i < recipientsList.size(); i++){
            new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, messageBody);
        }
        
    }
    
    
    protected void sendNewLodgementEmailToCustomer(Lodgement lodgement, Customer customer){
            String messageBody =   "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                          + "<br/>" + "Your lodgement of value " + lodgement.getAmount() + " has been received and is being processed." 
                          + "<br/>"  
                          + "<br/>"  
                          + "<br/>" + APP_NAME;

            String emailSubject = APP_NAME + ": New Lodgement";

            new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    protected void sendNewLodgementEmailToAgent(Lodgement lodgement, Customer customer){
            String messageBody =   "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + ")," 
                          + "<br/>" + "Your customer's lodgement has been received and is being processed."
                          + "<br/>" + "Customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                          + "<br/>" + "Amount: " + lodgement.getAmount() 
                          + "<br/>"  
                          + "<br/>"  
                          + "<br/>" + APP_NAME;

            String emailSubject = APP_NAME + ": New Lodgement";

            new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    
    
    
    /*********************************** LODGEMENT APPROVALS ***********************************/
    protected void sendLodgementApprovalEmailToCustomer(Customer customer, ProjectUnit unit, double amount){
        String messageBody =   "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                      + "<br/>" + "Your lodgement for " + unit.getTitle() + " in " + unit.getProject().getName() + " has been approved " 
                      + "and your purchase has been advanced by the sum of " + String.format("%.2f", amount) + "."
                      + "<br/>"  
                      + "<br/>" + "Congratulations " 
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Lodgement Approval";
        
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    protected void sendLodgementApprovalEmailToAgent(Customer customer, ProjectUnit unit, double amount){
        String messageBody =   "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                      + "<br/>" + "A lodgement has been approved for your customer - " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ")" 
                      + "Item: " + unit.getTitle() + " in " + unit.getProject().getName() + "."
                      + "Number of Units: " + unit.getQuantity()
                      + "This sale has been advanced by the sum of " + String.format("%.2f", amount) + "."
                      + "<br/>"  
                      + "<br/>" + "Congratulations " 
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Lodgement Approval";
        
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    
    /*********************************** LODGEMENT DECLINE ***********************************/
    protected void sendLodgementDeclineEmailToCustomer(Customer customer, Lodgement lodgement, double amount){
        String messageBody =   "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                      + "<br/>" + "Your lodgement of " + lodgement.getAmount() + " on " + lodgement.getCreatedDate() + " has been declined. " 
                      + "<br/>"  
                      + "<br/>"  
                      + "Please contact your agent or our customer care for further details."
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": Lodgement Decline";
        
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    protected void sendLodgementDeclineEmailToAgent(Customer customer, Lodgement lodgement, double amount){
        String messageBody =   "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                      + "<br/>" + "A lodgement of your customer -  " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ") has been declined." 
                      + "<br/>"  
                      + "<br/>"  
                      + "Please contact your agent or our customer care for further details."
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Lodgement Approval";
        
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    
    /************************************  WITHDRAWAL ********************************/
    protected void sendWithdrawalRequestEmailToAgent(Withdrawal w){
        String messageBody =   "Dear " + w.getAgent().getFirstname() + " " + w.getAgent().getLastname() + " (" + w.getAgent().getAccount().getAccountCode() + "),"
                      + "<br/>" + "Your withdrawal request has been received and is being processed."
                      + "<br/>Amount: " + w.getAmount()
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Withdrawal Request";
        
        new MailSender().sendHtmlEmail(w.getAgent().getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    protected void sendWithdrawalRequestEmailToAdmin(Withdrawal w, List<User> recipientsList, String withdrawalPageLink){
        String messageBody =   "New withdrawal request waiting for approval." 
                      + "<br/>" + "Agent: " + w.getAgent().getFirstname() + " " + w.getAgent().getLastname() + " (" + w.getAgent().getAccount().getAccountCode() + "),"
                      + "<br/>Amount: " + w.getAmount()
                      + "<br/>" + "Please follow the link below to take necessary action."
                      + "<br/>"  + "<a href=" + withdrawalPageLink + ">" + withdrawalPageLink + "</a>"
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Withdrawal Request";
        
       for(int i=0; i < recipientsList.size(); i++)            
           new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, messageBody);
    }
    
    
    
    /************************** REMINDER ALERT ****************************************/
    protected void sendReminderAlert(List customerAndItemsList, int dueDays){
        String orderItemsString = "<ul>";
        for(Object customerAndItems : customerAndItemsList){
            HashMap customerAndItemsMap = (HashMap) customerAndItems;
            Customer customer = (Customer)customerAndItemsMap.get("customer");
            List<OrderItem> orderItemsList = (List)customerAndItemsMap.get("order_items");
            
            for(Object itemListElement: orderItemsList){
                HashMap itemMap = (HashMap)itemListElement;
                OrderItem item = (OrderItem)itemMap.get("order_item");
                double balance = (Double)itemMap.get("balance");
                orderItemsString += "<li>" + item.getUnit().getTitle() + " (" + item.getUnit().getProject().getName() + "): " + String.format("%.2f",balance) + "</li>";
            }
            
            orderItemsString += "</ul>";
        
        
            String messageBody =   "Dear Customer (" + customer.getFirstname() + " " + customer.getLastname() + "(" + customer.getAccount().getAccountCode() + ")" 
                            + "<br/>Reminder on your orders with payment due in " + dueDays + "."
                            + "<br/>" + orderItemsString
                            + "<br/><br/>" + "Please take necessary actions."
                            + "<br/>"  
                            + "<br/>"  
                            + "<br/>" + APP_NAME;

            String emailSubject = APP_NAME + ": Payment Due in " + dueDays;

            new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, messageBody);
            
            
        }
    }
   
    
    
    
    /************************************  REFERRAL CODE SHARING ********************************/
    protected void sendReferralCodeEmail(String recipientEmail, Agent agent, String refLink){
        String messageBody =   "Good day,"  
                            + "<br/>" + "Please join me as an agent with " + companyName + "."
                            + "<br/>" + "You stand a chance to make a lot of money selling properties."
                            + "<br/><br/>" + "Simply follow the link below to register with my referral code."
                            + "<br/>"  
                            + "<br/>"  
                            + "<a href=" + refLink + ">" + "Go to registration" + "</a>" 
                            + "<br/>"
                            + "<br/>" 
                            + "Thank you";

            String emailSubject = "Agent Referral Code - " + agent.getAccount().getAccountCode();
            System.out.println("recipientEmail: " + recipientEmail);
            new MailSender().sendHtmlEmail(recipientEmail, agent.getEmail(), emailSubject, messageBody);
    }
    
    
}