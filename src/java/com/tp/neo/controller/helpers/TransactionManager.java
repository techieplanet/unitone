/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Transaction;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author swedge-mac
 */
public class TransactionManager {
 
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
    SystemUser sessionUser;
                
    public TransactionManager(SystemUser sessionUser){
        this.sessionUser = sessionUser;
    }
    
    public Transaction doDoubleEntry(Account debitAccount, Account creditAccount, double amount){            
        Transaction transaction = new Transaction();
        
        em.getTransaction().begin();
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(calendar.getTime());
        transaction.setCreatedBy(sessionUser.getSystemUserId());
        transaction.setAmount(amount);
        
        em.persist(transaction);
        System.out.println("after  perist: " + transaction.getId());
        
        em.getTransaction().commit();
        
        em.refresh(transaction);
        
        return transaction;
    }
    
    
}
