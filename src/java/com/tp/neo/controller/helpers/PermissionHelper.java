/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.model.Permission;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author swedge-mac
 */
public class PermissionHelper {
    
    public Map<String, List<Permission>> getSystemPermissionsEntitiesMap(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        HashMap<String, List<Permission>> entitiesMap  = new LinkedHashMap<>();
        
        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Permission.findAllOrderedByEntityAndWeight");
        List<Permission> permissionsList = jpqlQuery.getResultList();
        
        //String currentEntity = permissionsList.get(0).getEntity();
        
        for(Permission p : permissionsList)
        {
            String entity = p.getEntity();
            if(entitiesMap.containsKey(entity))
            {
                entitiesMap.get(entity).add(p);
            }
            else
            {
                entitiesMap.put(entity, new ArrayList<>());
                entitiesMap.get(entity).add(p);
            }
        }
        
        em.close();
        emf.close();

        ////System.out.println("Size of map: " + entitiesMap.size());
        return entitiesMap;
    }
    
    public ArrayList<String> getSelectedPermissionsCollection(String permissions){
        //System.out.println("getSelectedPermissionsCollection: " + permissions);
        Gson gson = new GsonBuilder().create();
        Type str = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> selectedPermissions = gson.fromJson(permissions, str);
        
        return selectedPermissions;
    }
}
