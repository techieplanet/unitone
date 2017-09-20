/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Customer;
import com.tp.neo.model.plugins.LoyaltyHistory;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class CustomerService {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    static EntityManager em = emf.createEntityManager();
    
    
    public static Double getCustomerRewardPointBalance(Customer customer){
        Collection<LoyaltyHistory> loyaltyPoints = customer.getLoyaltyHistoryCollection();
        double loyaltyPoint = 0;
        
        for(LoyaltyHistory l  : loyaltyPoints)
        {
            if(l.getType() == 1)
                loyaltyPoint += l.getRewardPoints();
            else  
                loyaltyPoint -= l.getRewardPoints();
        }
        
        return loyaltyPoint;
    }
}
