/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.controller.components.SMSSender;
import com.tp.neo.model.Agent;

/**
 *
 * @author Swedge
 */
public class SMSHelper {
    
    /*
    This method sends approval or decline SMS for agents
    */
    public static void sendAgentApprovalSMS(Agent agent, int status) {
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
    
}
