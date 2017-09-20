/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.tp.neo.model.Agent;
import com.tp.neo.model.utils.AuthManager;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class testChangePassword {
    
    public static void main(String... args) throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Agent user = em.find(Agent.class, 50L);
        String newPassWD = "mandela";
        
        user.setPassword(AuthManager.getSaltedHash(newPassWD));
        
        //System.out.println(user.getFirstname() + " " + user.getEmail());
        em.persist(user);
        em.flush();
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
    
}
