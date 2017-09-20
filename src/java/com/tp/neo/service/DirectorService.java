/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;


import com.tp.neo.model.Agent;
import com.tp.neo.model.Director;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class DirectorService {
    
    public static List<Director> getDirectors(Agent agent){
        if((agent != null) && (!agent.isCorporate()))
        {
            return null;
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<Director> directors = em.createNamedQuery("Director.findByAgent").setParameter("agent", agent).getResultList();
        em.close();
        emf.close();
        return directors;
    }
    
     public static List<Director> getDirectors(Long agentId){
        if((agentId != null) && (agentId == 0))
        {
            return null;
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<Director> directors = em.createNamedQuery("Director.findByAgentId").setParameter("agentId", agentId).getResultList();
        em.close();
        emf.close();
        return directors;
    }
}
