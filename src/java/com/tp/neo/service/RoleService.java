/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Role;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class RoleService {
        private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        private static EntityManager em = emf.createEntityManager();
    public static List<Role> findAllRolesLowerThan(int roleid){
        try
        {
        Role role = em.find(Role.class, roleid);
        List<Role> roles = em.createNamedQuery("Role.findRolesLowerThan").setParameter("tier", role.getTier()).getResultList();
        return roles;
        }catch( Exception e)
        {
            return new ArrayList<Role> (); // Prevent Null pointer
        }
    }
    
    public static List<Role> findAllRolesLowerThanOrEqual(int roleid){
        try
        {
        Role role = em.find(Role.class, roleid);
        List<Role> roles = em.createNamedQuery("Role.findRolesLowerThanOrEqual").setParameter("tier", role.getTier()).getResultList();
        return roles;
        }catch( Exception e)
        {
            return new ArrayList<Role> (); // Prevent Null pointer
        }
    }
    
    
    public static List<Role> findAllRolesHigherThan(int roleid){
        try
        {
        Role role = em.find(Role.class, roleid);
        List<Role> roles = em.createNamedQuery("Role.findRolesHigherThan").setParameter("tier", role.getTier()).getResultList();
        return roles;
        }catch( Exception e)
        {
            return new ArrayList<Role> (); // Prevent Null pointer
        }
    }
    
    public static List<Role> findAllRolesTeirsLowerThan(int tier){
        try
        {
        List<Role> roles = em.createNamedQuery("Role.findRolesLowerThan").setParameter("tier", tier).getResultList();
        return roles;
        }catch( Exception e)
        {
            return new ArrayList<Role> (); // Prevent Null pointer
        }
    }
    
    public static List<Role> findAllRolesTeirsHigherThan(int tier){
        try
        {
        List<Role> roles = em.createNamedQuery("Role.findRolesHigherThan").setParameter("tier", tier).getResultList();
        return roles;
        }catch( Exception e)
        {
            return new ArrayList<Role> (); // Prevent Null pointer
        }
    }
    
    public static List<Role> findAllRolesWithTiers(int tier){
        try
        {
        List<Role> roles = em.createNamedQuery("Role.findRolesWithTier").setParameter("tier", tier).getResultList();
        return roles;
        }catch( Exception e)
        {
            return new ArrayList<Role> (); // Prevent Null pointer
        }
    }

    public static Role findRoleWithAlias(String alais){
        Role role; 
        try
        {
         role = (Role)em.createNamedQuery("Role.findRolesWithAlias").setParameter("alias", alais).getSingleResult();
        return role;
        }catch( Exception e)
        {
            role = new Role(); // Prevent Null pointer
            role.setTitle("");
            return role;
        }
    }
}
