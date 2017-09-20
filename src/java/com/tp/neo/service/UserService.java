/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Role;
import com.tp.neo.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class UserService {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    private static EntityManager em = emf.createEntityManager();
    public static List<User> getUsersUnderUser(User userObj){
       try
        {
            
        List<User> users ;
        
        if(userObj.getRole().getTier()!= 0)
        {
        Role role = userObj.getRole();
        users = em.createNamedQuery("User.findAll").getResultList();
        List<User> tempuser = new ArrayList<>();
        for(User user : users)
        {
            Role userRole = user.getRole();
            if(role.getAlias().equalsIgnoreCase(userRole.getSupervisor()))
            {
                tempuser.add(user);
            }
        }
        tempuser.add(userObj);
        users = tempuser;
        }
        else
        {
            users = em.createNamedQuery("User.findAll").getResultList();
        }
        return users;
        }catch( Exception e)
        {
            return new ArrayList<> (); // Prevent Null pointer
        }
    }
    
    public static List<User> getUsersUnderUser(Long userid){
       User user = em.find(User.class,userid );
       return getUsersUnderUser(user);
    }

    public static List<User> getUsersBelowAndEqualsToMe(User userObj){
        try
        {
            
        List<User> users ;
        
        if(userObj.getRole().getTier()!= 0)
        {
        Role role = userObj.getRole();
        users = em.createNamedQuery("User.findAll").getResultList();
        List<User> tempuser = new ArrayList<>();
        for(User user : users)
        {
            em.refresh(user);
            Role userRole = user.getRole();
            if(userRole.getTier() >= role.getTier())
            {
                tempuser.add(user);
            }
        }
        users = tempuser;
        }
        else
        {
            users = em.createNamedQuery("User.findAll").getResultList();
        }
        return users;
        }catch( Exception e)
        {
            return new ArrayList<> (); // Prevent Null pointer
        }
    }
}

