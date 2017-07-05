/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Account;
import com.tp.neo.model.AccountType;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.ProjectUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.xml.bind.PropertyException;

/**
 *
 * @author swedge-mac
 */
public class AccountManager {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    /**
     * Creates an account for an already persisted customerß
     * @param customer the already persisted customer to create an account for.
     * @throws PropertyException
     * @throws RollbackException 
     */
    public Account createCustomerAccount(Customer customer) throws PropertyException, RollbackException{
        Account account = new Account();
        em.getTransaction().begin();
        
        account.setAccountCode("CU" + this.createAccountCode(customer.getCustomerId()) );
        account.setAccountTypeId(new AccountType(3));  //customer account type id is 3
        account.setRemoteId(customer.getCustomerId());
        account.setActive((short)1);
        em.persist(account);
        em.getTransaction().commit();
        
        return account;
    }
    
    
    private int getCustomersCount(){
        //count all custommers
        int customersCount = ((Number)em.createNamedQuery("Customer.findAllCount").getSingleResult()).intValue();
        return customersCount;
    }
    
    
    
    /*************************  AGENT   **************************/
    /**
     * Creates an account for an already persisted agent
     * @param agent the already persisted agent to create an account for.
     * @throws PropertyException
     * @throws RollbackException 
     */
    public Account createAgentAccount(Agent agent) throws PropertyException, RollbackException{
        Account account = new Account();
        em.getTransaction().begin();
        
        account.setAccountCode("AG" + this.createAccountCode(agent.getAgentId()));
        account.setAccountTypeId(new AccountType(2));  //agent account type id is 2
        account.setRemoteId(agent.getAgentId());
        account.setActive((short)1);
        
        em.persist(account);
        em.getTransaction().commit();
        
        return account;
    }
    
    
    /*************************  UNIT   **************************/
    /**
     * Creates an account for an already persisted unit
     * @param unit the already persisted unit to create an account for.
     * @throws PropertyException
     * @throws RollbackException 
     */
    public Account createUnitAccount(ProjectUnit unit) throws PropertyException, RollbackException{
        Account account = new Account();
        em.getTransaction().begin();
        
        account.setAccountCode("UN" + this.createAccountCode(unit.getId()) );
        account.setAccountTypeId(new AccountType(1));  //unit account type id is 1
        account.setRemoteId(unit.getId());
        account.setActive((short)1);
        
        em.persist(account);
        em.getTransaction().commit();
        
        return account;
    }
    
    
    
    /*************************  UTILS   **************************/
    
    private String createAccountCode(long objectId){
        String code = String.valueOf(objectId);
        while(code.length() < 5){
            code = "0" + code;
        }
        return code;
    }
    
    public void deactivateAccount(Account account) throws PropertyException, RollbackException{
        System.out.println("Inside deactivateAccount: " + account.getId());
        System.out.println("Active status: " + account.getActive());
        em.getTransaction().begin();
        Account myAccount = em.find(Account.class, account.getId());
        myAccount.setActive((short)0);
        em.getTransaction().commit();
    }
    
}
