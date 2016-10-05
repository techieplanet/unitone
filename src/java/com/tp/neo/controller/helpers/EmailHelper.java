/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Agent;
import com.tp.neo.model.utils.MailSender;
import static com.tp.neo.controller.components.AppController.defaultEmail;
import static com.tp.neo.controller.components.AppController.APP_NAME;
import com.tp.neo.model.Customer;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.User;
import java.util.List;

/**
 *
 * @author Swedge
 */
public class EmailHelper {
    
    /*
    This method sends approval or decline email
    */
    public static void sendAgentApprovalEmail(Agent agent, int status) {
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
    
    
   /*********************************** ORDERS ***********************************/
    public void sendNewOrderEmail(ProductOrder order, Customer customer, List<User> recipientsList){
        String waitingOrdersPageLink = "xyz";
        String messageBody =   "Dear Admin,"
                      + "<br/>" + "A new order, ID: <b>" + order.getId() + "</b> for customer: <b>" + customer.getFirstname() + " " + customer.getLastname() 
                      + " has been created and needs approval from you."
                      + "<br/>" + "Customer: " 
                      + "<br/>" 
                      + "<br/>"  
                      + "<br/>" + "Please follow the link below to take necessary action."
                      + "<br/>" +  waitingOrdersPageLink
                      + "<br/>"  
                      + "<br/>"  
                      + "<br/>" + APP_NAME;
        
        String emailSubject = APP_NAME + ": New Order Waiting for Approval";
        
        for(int i=0; i < recipientsList.size(); i++){
            new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, messageBody);
        }
        
    }
    
}
