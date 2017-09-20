/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Transaction;
import com.tp.neo.model.User;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author swedge-mac
 */
public class TransactionManagerTest {
    SystemUser sessionUser;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    public TransactionManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doDoubleEntry method, of class TransactionManager.
     */
    @Test
    public void testDoDoubleEntry() {
        //debit - 1, crdit - 3
        //System.out.println("doDoubleEntry");
        Account debitAccount = em.find(Account.class, (long)1);
        Account creditAccount = em.find(Account.class, (long)3);
        double amount = (double)3000000;
        
        sessionUser = (SystemUser) em.find(User.class, (long)18);
        TransactionManager instance = new TransactionManager(sessionUser);
        
        Transaction expResult = new Transaction();
        expResult.setAmount(amount);
        expResult.setDebitAccount(debitAccount);
        expResult.setCreditAccount(creditAccount);
        expResult.setCreatedBy((long)18);
        expResult.setTransactionDate(new Date());
        
        Transaction result = instance.doDoubleEntry(debitAccount, creditAccount, amount);
     
        //assertJ
        assertThat(expResult).isEqualToIgnoringGivenFields(result, "id", "createdBy", "transactionDate");
        
        em.getTransaction().begin();
        Transaction toBeRemoved = em.merge(result);
        em.remove(toBeRemoved);
        em.getTransaction().commit();
        
    }
    
}
