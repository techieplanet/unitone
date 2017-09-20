/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Company;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class CompanyService {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    static    EntityManager em = emf.createEntityManager();
    public static Company getCompany(){
        return em.find(Company.class, 1);
    }
    
    public static  void saveCompany(Company company){
        //make sure the id is still 1
        company.setId(1);
        em.getTransaction().begin();
        em.persist(company);
        em.getTransaction().commit();
    }
            
}
