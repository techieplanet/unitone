/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.model.Permission;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Swedge
 */
public class TPController extends HttpServlet{
    public static final String APP_NAME = "NeoForce";
    public static final String defaultEmail = "no-reply@tplocalhost.com";
    
    public Calendar calendar;
    public String userType;
    
    public TPController(){
        System.out.println("Inside TPController");
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
    }
    
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);       
    }
    
    public boolean hasActiveUserSession(HttpServletRequest request, HttpServletResponse response, String callbackURL)
            throws IOException{
        HttpSession session = request.getSession(true);
        log("Login Callback before setting: " + session.getAttribute("loginCallback"));
        //log("user before check: " + session.getAttribute("user").toString());
        if(session.getAttribute("user") == null){
            session.setAttribute("loginCallback", callbackURL);
            String loginPage = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/";
            log("loginPage: " + loginPage);
            response.sendRedirect(loginPage);
            return false;
        }
        
        return true;
    }
    
    public List<Permission> getSystemPermissions(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Permission.findAllOrderedByEntityAndWeight");
        List<Permission> permissionsList = jpqlQuery.getResultList();

        return permissionsList;
    }
    
    public HashMap<String, List<Permission>> getSystemPermissionsEntitiesMap(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        HashMap<String, List<Permission>> entitiesMap  = new HashMap<String, List<Permission>>();
        List<Permission> entityPermissionsList = new ArrayList<Permission>();
        
        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Permission.findAllOrderedByEntityAndWeight");
        List<Permission> permissionsList = jpqlQuery.getResultList();
        
        String currentEntity = permissionsList.get(0).getEntity();
        
        for(int i=0; i<permissionsList.size(); i++){                       
            if(currentEntity.equalsIgnoreCase(permissionsList.get(i).getEntity())){
                entityPermissionsList.add(permissionsList.get(i));
            }
            else {
                entitiesMap.put(currentEntity, entityPermissionsList);
                
                //set up for the new entity found
                currentEntity = permissionsList.get(i).getEntity();
                entityPermissionsList = new ArrayList<Permission>();
                entityPermissionsList.add(permissionsList.get(i));
            }
        }
        
        //add the last treated entity and its permissionst to the map
        entitiesMap.put(currentEntity, entityPermissionsList);
        
        em.close();
        emf.close();

        //System.out.println("Size of map: " + entitiesMap.size());
        return entitiesMap;
    }
    
    public void cleanRequest(HttpServletRequest request){
        Enumeration<String> attributeNames = request.getParameterNames();
        while(attributeNames.hasMoreElements())
            request.removeAttribute(attributeNames.nextElement());
    }
    
    public void log(String str){ System.out.println(str); }
}
