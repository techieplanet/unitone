/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.components;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Notification;
import com.tp.neo.model.Permission;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
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
public class AppController extends HttpServlet{
    public static final String APP_NAME = "NeoForce";
    public static final String defaultEmail = "no-reply@tplocalhost.com";
    
    public Calendar calendar;
    protected long userType;
    protected SystemUser sessionUser;
    protected String callbackUrRL = "";
    
    
    public AppController(){
        System.out.println("Inside TPController");
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
    }
    
    
    public void guestService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        
        super.service(req, res);
        
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
     
        System.out.println("Inside Service Method");
        //hasActiveUserSession(req, res);
        
        sessionUser = (SystemUser)req.getSession().getAttribute("user");
        
        req.setAttribute("notifications", getNotifications());
        req.setAttribute("loggedInUser",sessionUser);
        
        
        super.service(req, res);
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public boolean hasActiveUserSession(HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        HttpSession session = request.getSession(true);
        log("Login Callback before setting: " + session.getAttribute("loginCallback"));
        
        String callbackURL = getCallbackURL(request, response);
        log("callbackURL: " + callbackURL);
        session.setAttribute("loginCallback", callbackURL);
        
        System.out.println("Session User : " +  session.getAttribute("user"));
        
        if(session.getAttribute("user") == null){
            System.out.println("User not logged in");
            session.setAttribute("loginCallback", callbackURL);
            String loginPage = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/";
            log("loginPage: " + loginPage);
            response.sendRedirect(loginPage);
            return false;
        }
        
        return true;
    }
    
    private String getCallbackURL(HttpServletRequest request, HttpServletResponse response) throws IOException{
//        String uri = request.getScheme() + "://" +
//             request.getServerName() + 
//             ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort() ) +
//             request.getRequestURI() +
//            (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        String url = request.getRequestURL().toString() + 
                     (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        return url;
    }
       
    public boolean hasActionPermission(String action, HttpServletRequest request, HttpServletResponse response) throws IOException{                
        System.out.println("Session User in HasPermission  : " + sessionUser);
        System.out.println("Session User Permissions : " + sessionUser.getPermissions() );
        String[] permissions = sessionUser.getPermissions().split(",");
        log("action: " + action); log("cleanedPermissions: " + sessionUser.getPermissions());
        
        for(String p : permissions){
            //log("p: " + p); log("action: " + action);
            if(p.equalsIgnoreCase(action))
                return true;
        }
        return false;
    }
    
    
    public void errorPageHandler(String action, HttpServletRequest request, HttpServletResponse response) throws IOException{
        String errorPage = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/Error?action="+action;
        log("errorPage: " + errorPage);
        response.sendRedirect(errorPage);
    }
    
    
    public List<Permission> getSystemPermissions(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Permission.findAllOrderedByEntityAndWeight");
        List<Permission> permissionsList = jpqlQuery.getResultList();

        return permissionsList;
    }
    
    public String getUserCleanedPermissions(){
        return sessionUser.getPermissions().replaceAll("\\[|\\]|\"", "");  //Regex matches the characters: []"
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
    
    
    private List<Notification> getNotifications(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        short status = 0;
        
        Query jplQuery = em.createNamedQuery("Notification.findByStatus");
        jplQuery.setParameter("status", status);
        
        List<Notification> notificationList = jplQuery.getResultList();
        
        em.close();
        emf.close();
        
        return notificationList;
    }
}
