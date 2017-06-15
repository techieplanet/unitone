/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.components;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Notification;
import com.tp.neo.model.Permission;
import com.tp.neo.model.Plugin;
import java.io.IOException;
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
public class AppController extends HttpServlet{
    public static final String APP_NAME = "NeoForce";
    public static final String defaultEmail = "noreply@techieplanet.net.com";
    
    public Calendar calendar;
    protected long userType;
    protected SystemUser sessionUser;
    protected String callbackUrRL = "";
    protected boolean isAjaxRequest;
    protected String companyName = "TechiePlanet, Ltd.";
    protected String companyAddress = "214 Allen Avenue, Ikeja, Lagos.";
    protected String companyPhone = "(+234) 816-4334-657";
    protected String companyEmail = "info@techieplanetltd.com ";
    
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
        
        isAjaxRequest = isXMLHttpRequest(req);
        
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
            
            if(isAjaxRequest){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
                response.getWriter().write("SESSION_EXPIRED");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
            
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
        System.out.println("Action : " + action);
        String[] permissions = sessionUser.getPermissions().split(",");
        //log("action: " + action); log("cleanedPermissions: " + sessionUser.getPermissions());
        
        for(String p : permissions){
            //p.replaceAll("\"", "");
            //log("p: " + p); log("action: " + action);
            if(p.equalsIgnoreCase(action))
                return true;
        }
        return true;
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
    
    
    
    public void cleanRequest(HttpServletRequest request){
        Enumeration<String> attributeNames = request.getParameterNames();
        while(attributeNames.hasMoreElements())
            request.removeAttribute(attributeNames.nextElement());
    }
    
    
    
    public void log(String str){ System.out.println(str); }
    
    
    private List<Notification> getNotifications(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        emf.getCache().evictAll();
        
        short status = 0;
        
        Query jplQuery = em.createNamedQuery("Notification.findByStatus");
        jplQuery.setParameter("status", status);
        
        List<Notification> notificationList = jplQuery.getResultList();
        
        em.close();
        emf.close();
        
        return notificationList;
    }
    
    
    public HashMap<String, Plugin> getAvailableplugins(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<Plugin> pluginslist = em.createNamedQuery("Plugin.findAvailable")
                                    .setParameter("installationStatus", 1)
                                    .setParameter("active",1)
                                    .setParameter("deleted", 0)
                                    .getResultList();
        
        HashMap<String, Plugin> pluginsMap = new HashMap<String, Plugin>();
        for(Plugin plugin : pluginslist){
            pluginsMap.put(plugin.getPluginName().toLowerCase(), plugin);
        }
        
        return pluginsMap;
    }
    
    private boolean isXMLHttpRequest(HttpServletRequest request){
        
        boolean bool = false;
        
        String requestType = request.getHeader("X-Requested-With") != null ? request.getHeader("X-Requested-With") : "";
        
        if(requestType.equalsIgnoreCase("XMLHttpRequest")){
            bool = true;
        }
        
        return bool;
                    
    }
}
