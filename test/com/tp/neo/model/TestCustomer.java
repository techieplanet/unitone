/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import com.tp.neo.controller.helpers.EmailHelper;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class TestCustomer {
    
    public static void main(String... args){
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        Agent agent = em.find(Agent.class,77L);
        
        //System.out.println("Name: " + agent.getFullName() + " Total Balance: " + agent.getTotalBalance() + 
        //                                                    " Total Pending: " + agent.getTotalPendingWithdrawal()
        //                                                 +  " Total Approved: " + agent.getTotalApprovedWithdrawal() 
        //                                                 +  " Total Paid: " + agent.getTotalPaidWithdrawal()
        //                                                 +  " Total Eligible Withdrawal: " + agent.getEligibleWithdrawalBalance());
        Company company = em.find(Company.class, 1);
 
  new EmailHelper().sendUserWelcomeMessageAndPassword("josepholuwaseunpeter1@gmail.com", company.getEmail(), "Fuck You bitch" , agent , company , "google.com");
    }
    
}
