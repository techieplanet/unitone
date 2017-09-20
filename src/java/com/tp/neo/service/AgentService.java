/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.controller.helpers.TransactionManager;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class AgentService {
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    static EntityManager em = emf.createEntityManager();
    
    static boolean debug = true;
    public static Map<Integer , Agent> getAgentUplineTree(Agent agent , int depth){
        Map<Integer , Agent> upline = new HashMap<>();
        
        //Temp Atempt
        Agent tAgent = agent;
        for(int i = 1 ; i <= depth ; i++)
        {
            if(tAgent.getReferrerId() != 0)
            {
             tAgent = em.find(Agent.class, tAgent.getReferrerId()); //With This We get the lower Agent Refferer in Our temp Agent Refencence
             upline.put(i, tAgent);
            }
            else
            {
                break;
            }
        }
        return upline;
    }
    
    /**
     * This method calculate the total value that should be deducted from the principal.
     * This Calculation Include the agent Commission and the commisssion accrue to it upline.
     * @param agent
     * @param uplines
     * @param agentInitialCommission
     * @param mlmUplineCommission
     * @return 
     */
    public static double getTotalAgentNetworkCommission(Agent agent , Map<Integer , Agent> uplines,  Double agentInitialCommission , Double mlmUplineCommission){
        
        //Lets Get the Key Set in asccending order
        List<Integer> list = new ArrayList<>(uplines.keySet());
        Collections.sort(list);
        
        double uplineComm = agentInitialCommission;
        double totalNetWorkComm = agentInitialCommission;
        double mlmPercentage = mlmUplineCommission / 100;
        
        for(Integer id : list)
        {
            double temp = mlmPercentage * uplineComm;
            totalNetWorkComm += temp;
            uplineComm = temp;
        }
        
        return totalNetWorkComm;
    }

    /**
     * What this method does is to remove the amount for each upline from the agent account
     * NOTE: The Total upline amount ougth to have been calculated and credited into the agent account account
     * from the project Unit before calling This Method.
     * @param sessionUser
     * @param agent
     * @param uplines
     * @param agentCommissionAfterTax
     * @param mlmUplineCommission 
     */
    public static void removeUplineCommissionsFromAgentAccount(SystemUser sessionUser , Agent agent , Map<Integer , Agent> uplines, Double agentCommissionAfterTax ,Double mlmUplineCommission){
        //Lets Get the Key Set in asccending order
        List<Integer> list = new ArrayList<>(uplines.keySet());
        Collections.sort(list);
        
        double uplineComm = agentCommissionAfterTax;
        double mlmPercentage = mlmUplineCommission / 100;
        
        for(Integer id : list)
        {
            double temp = mlmPercentage * uplineComm;
            //totalNetWorkComm += temp;
            uplineComm = temp;
            
            if(debug)
            //System.out.println("Upline: " + id  + "  With AgentId: " + uplines.get(id).getAgentId() + " And Account code: " + uplines.get(id).getAccount().getAccountCode() +  " Gets " + uplineComm);
            
            //Remove the value from Agent Account and place it inside the Upline accounts
           new TransactionManager(sessionUser).doDoubleEntry(agent.getAccount(), uplines.get(id).getAccount(), temp);
        }
    }
    
    
}
