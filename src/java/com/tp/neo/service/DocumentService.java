/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Document;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class DocumentService {
    public static List<Document> getDocuments(long ownerId , int ownerTypeId){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<Document> documents = (List<Document>)em.createNamedQuery("Document.findByOwnerIdAndOwnerTypeId")
               .setParameter("ownerId", ownerId)
               .setParameter("ownerTypeId", ownerTypeId)
               .getResultList();
        em.close();
        emf.close();
        
       return documents;
    }
    
    public static List<Document> getCustomerDocuments(long customerId){
        return getDocuments(customerId,3);
    }
    
    public static List<Document> getAgentDocuments(long agentId){
        return getDocuments(agentId,2);
    }
}
