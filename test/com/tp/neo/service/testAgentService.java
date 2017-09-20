/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Withdrawal;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class testAgentService {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    static EntityManager em = emf.createEntityManager();
    
    public static void main(String... args){
       makePayOut();
       }
    
    private static Map<Integer , Agent> getAgentUplineTree(Agent agent , int depth){
        return AgentService.getAgentUplineTree(agent, depth);
    }
    
    private static double getTotalAgentNetworkCommission(Agent agent , Map<Integer , Agent> uplines,  Double agentInitialCommission , Double mlmUplineCommission){
        return AgentService.getTotalAgentNetworkCommission(agent, uplines, agentInitialCommission, mlmUplineCommission);
    }
    
    public static void removeUplineCommissionsFromAgentAccount(Agent agent , Map<Integer , Agent> uplines, Double agentInitialCommission ,Double mlmUplineCommission){
        AgentService.removeUplineCommissionsFromAgentAccount(agent , agent, uplines, agentInitialCommission, mlmUplineCommission);
    }
    
   public static void makePayOut(){
       
       //Lets first get the list of all approved withdrawals
       List<Withdrawal> withdrawals = em.createNamedQuery("Withdrawal.findByApproved")
                                        .setParameter("approved",1 )
                                        .getResultList();
       List<Map<String,String>>  payOut = new ArrayList<>();
       
       String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM/d/uuuu"));
       
       Account agentCommission = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "AGENT_COMMISSION").getSingleResult();
       
       em.getTransaction().begin();
       for(Withdrawal w : withdrawals)
       {
           Map<String,String> agentRequest = new HashMap<>();
           agentRequest.put("PaymentAmount", String.format("%,.2f",w.getAmount()));
           agentRequest.put("PaymentDate",date);
           agentRequest.put("Reference", "''");
           agentRequest.put("Remark", "AGENCY COMMISSION PAYMENT");
           agentRequest.put("VendorCode", w.getAgent().getBankAcctName());
           agentRequest.put("VendorName", w.getAgent().getBankAcctName());
           agentRequest.put("VendorAccountNumber", w.getAgent().getBankAcctNumber());
           agentRequest.put("Bank Sort Code", w.getAgent().getBank().getSortCode());
           payOut.add(agentRequest);
           //We have to debit the agent account with the amount 
           //and debit the agent commision account
          //new TransactionManager(sessionUser).doDoubleEntry(w.getAgent().getAccount(), agentCommission, w.getAmount());
          w.setApproved((short)3);
          em.persist(w);
        }
       
       //Lets us now write our CSV File 
       //comma delimited
       StringBuilder csv = new StringBuilder();
       csv.append("PaymentAmount,PaymentDate,Reference,Remark,VendorCode,VendorName,VendorAccountNumber,Bank Sort Code,\n");
       
       for(Map<String,String> agentRequest : payOut)
       {
         csv.append(agentRequest.get("PaymentAmount") +"," 
                 + agentRequest.get("PaymentDate") +","
                 + agentRequest.get("Reference") +","
                 + agentRequest.get("Remark") +","
                 + agentRequest.get("VendorCode") +","
                 + agentRequest.get("VendorName") +","
                 + agentRequest.get("VendorAccountNumber") +","
                 + agentRequest.get("Bank Sort Code") +",\n");
       }
       
       //System.out.print(csv);
       
       //response.setContentType("application/csv");
       //response.addHeader("Content-Type", "application/pdf");
       //response.addHeader("Content-Disposition", "inline; filename=Withdrawal" + LocalDate.now().toString() + ".csv");
       //response.setContentLength( csv.length());
       //PrintWriter writer = response.getOutputStream();
       //writer.append(csv);
       //writer.flush();
       //writer.close();
   }

}
