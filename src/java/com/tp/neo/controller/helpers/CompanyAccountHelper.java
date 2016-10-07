/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Prestige
 */
public class CompanyAccountHelper {
    
    
    public CompanyAccountHelper() {
        
    }
    
    public static List<com.tp.neo.model.CompanyAccount> getCompanyAccounts() {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("CompanyAccount.findAll");
        List<com.tp.neo.model.CompanyAccount> resultSet = jplQuery.getResultList();
        
        return resultSet;
    }
    
}
