/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Agent;
import com.tp.neo.controller.components.MailSender;
import com.tp.neo.controller.components.AppController;
import static com.tp.neo.controller.components.AppController.defaultEmail;
import static com.tp.neo.controller.components.AppController.APP_NAME;

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
    
    
}
