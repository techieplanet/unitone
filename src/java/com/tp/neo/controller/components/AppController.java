/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.components;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.AgentProspect;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Notification;
import com.tp.neo.model.Permission;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.tp.neo.model.utils.MailSender;
/**
 *
 * @author Swedge
 */
public class AppController extends HttpServlet{
    public static final String APP_NAME = "NeoForce";
    public static  String defaultEmail = "noreply@techieplanet.net.com";
    
    
    public Calendar calendar;
    protected int userType;
    protected SystemUser sessionUser;
    protected String callbackUrRL = "";
    protected boolean isAjaxRequest;
    public static  String companyName = "TechiePlanet Ltd.";
    protected String companyAddress = "214 Allen Avenue, Ikeja, Lagos.";
    protected static String companyPhone = "(+234) 816-4334-657";
    protected static String companyEmail = "info@techieplanetltd.com ";
    
    //to improve page loading
    static protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    static {
        Company company = emf.createEntityManager().find(Company.class, 1);
        defaultEmail = company.getEmail();
        companyName = company.getName();
        companyPhone = company.getPhone();
        companyEmail = company.getEmail();
        MailSender.companyName = companyName;
    }
    
    public AppController(){
        //System.out.println("Inside TPController");
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
    }
    
    
    public void guestService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        
        super.service(req, res);
        
    }
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, java.io.IOException {
     
        //System.out.println("Inside Service Method");
        //hasActiveUserSession(req, res);
        
        sessionUser = (SystemUser)req.getSession().getAttribute("user");
        req.setAttribute("dateFmt", new SimpleDateFormat("EEE, d MMM yyyy"));
        
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
        
        //System.out.println("Session User : " +  session.getAttribute("user"));
        
        if(session.getAttribute("user") == null){
            //System.out.println("User not logged in");
            session.setAttribute("loginCallback", callbackURL);
            
            if(isAjaxRequest){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
                response.getWriter().write("SESSION_EXPIRED");
                response.getWriter().flush();
                response.getWriter().close();
                return false;
            }
            
            AppController.doRedirection(request, response, "/");
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
        //System.out.println("Session User in HasPermission  : " + sessionUser);
        //System.out.println("Session User Permissions : " + sessionUser.getPermissions() );
        //System.out.println("Action : " + action);
        String[] permissions = sessionUser.getPermissions().split(",");
        //log("action: " + action); log("cleanedPermissions: " + sessionUser.getPermissions());
        
        //First get The System user 
        SystemUser user = (SystemUser) request.getSession().getAttribute("user");
        
        //If the user has not Log into the sysytem
        if(user == null)
        {
            return false;
        }
        
        //Get all ID related parameters 
        Long id  = request.getParameter("id") != null && !((String)(request.getParameter("id"))).isEmpty() ? 
                Long.parseLong(request.getParameter("id")) : 0;
        
        Long customerId  = request.getParameter("customerId") != null && !((String)(request.getParameter("customerId"))).isEmpty() ? 
                Long.parseLong(request.getParameter("customerId")) : 0;
        
        Long agentId  = request.getParameter("agent_id") != null && !((String)(request.getParameter("agent_id"))).isEmpty()? 
                Long.parseLong(request.getParameter("agent_id")) :
                (request.getParameter("agentId") != null && !((String)(request.getParameter("agentId"))).isEmpty() ?
                Long.parseLong(request.getParameter("agentId")) : 0);
        
        //Check is the system user is an Instance of User Class
        {
        if(user instanceof User)
        {
            //Then we known that it is Either an Admin user or whatever
            //We proceed to check if the Admin has permission 
           
             //if User is trying to print an invoice
            String temp = (String) request.getParameter("action");
            if(( temp!=null ) && temp.contains("lodgement_invoice"))
            {
                return true;
            }
            
            //if User   wants the project Unit list
            temp = request.getMethod();
            if(temp.equals("GET") && action.equals("edit_project"))
            {
                return true;
            }
            
            //if User Tries to create Order
            if(action.equals("create_order"))
            {
                return true;
            }
            
             for(String p : permissions)
             {
              //   p =  p.replaceAll("\"", "");
            //log("p: " + p); log("action: " + action);
            if(p.contains(action))
                return true;
            }
        //No permission so we return false
        return false;    
        }
        
     }
        
    //Check is system user is an instance of the Agent Class
        if(user instanceof Agent)
        {
            
            //Now we can cast the SystemUser Object to an Agent   reference 
            
            //This solve the issue of Agent Attempt to Edit or create Project
            if (action.contains("view_project")) return true ;
            
            Agent agent = (Agent)user;
            
            String temp = (String) request.getParameter("action");
            
             //Let check if Agent is trying to see it propective customers
            if(temp!=null && temp.equalsIgnoreCase("edit_prospect"))
             {
                 AgentProspect prospect = emf.createEntityManager().find(AgentProspect.class, id);
                 
                 if(prospect == null)
                     return false;
                 
                return agent.getAgentId().equals(prospect.getAgent().getAgentId());
             }
                 
            //if User is trying to print an invoice
            if(( temp!=null ) && temp.contains("lodgement_invoice"))
            {
                return true;
            }
            
            //if user tries to share referral code
            if(action.contains("referer")){
                    return true;
            }
            
            //if User   wants the project Unit list
            temp = request.getMethod();
            if(temp.equals("GET") && action.equals("edit_project"))
            {
                return true;
            }
            
            //if User Tries to create Order
            if(action.equals("create_order"))
            {
                return true;
            }
            
            //lets first check if the agent Id belong to the Agent
            if(agentId != 0)
            {
                if(agentId.equals(agent.getAgentId())) return true ;
            }
            
            //Now lets check the Id parameter may be it belong to the agent 
            if(id != 0)
            {
                if(id.equals(agent.getAgentId())) return true ; 
                
            /** Now we known the id doesn't belong to the agent 
            *May be it belong to it customer but the Id may still belong 
            *to another agent . The only way out is to check if the agent is trying
            *to make any agent related calls
            */
                if(action.contains("agent") 
                        || action.contains("history")
                        || action.contains("withdrawal")
                        || action.contains("statement"))
                {
                    //Then we now know that it is an agent trying to beat the system 
                    return false;
                }
            }
            
                
            /**
            *Now we Know that the Agent is trying to view it Customer Profile
            *Let proceed to get the Customer with that Id; 
            *and check if the customer belong to that agent
            */
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            Customer customer = null;
             
            if(id != 0) 
            {
            Query jpqlQuery  = em.createNamedQuery("Customer.findByCustomerId");
            jpqlQuery.setParameter("customerId", id);
            jpqlQuery.setParameter("deleted", (short)0);
            
            customer = (Customer) jpqlQuery.getSingleResult();
            if(customer != null)
            {
                //The customer exit and lets check if the customer belong to the agent
                return agent.getAgentId().equals(customer.getAgent().getAgentId());
            }
            else
            {
                //The customer with that Id doesn't exit
                return false ;
            }
            }
            
            if(customerId != 0) 
            {
            Query jpqlQuery  = em.createNamedQuery("Customer.findByCustomerId");
            jpqlQuery.setParameter("customerId", customerId);
            jpqlQuery.setParameter("deleted", (short)0);
            
            customer = (Customer) jpqlQuery.getSingleResult();
            
            if(customer != null)
            {
                //The customer exit and lets check if the customer belong to the agent
                return agent.getAgentId().equals(customer.getAgent().getAgentId());
            }
            else
            {
                //The customer with that Id doesn't exit
                return false ;
            }
            
            }
            
            /**
            * We need to solve a issue where by the agent needs to see all it customers.
            * then we need to return true if the agentId and the customerId or the Id is not set here
            */
            
            if(id == 0 && agentId == 0 )
            {
                if((action.contains("view_user")
                        ||action.contains("view_agent"))) return false; 
            }      
            
            
            return true;
        }
        
      
        
//check if system User is an instance of the Customer Class
        if(user instanceof Customer)
        {
            //Now we can cast the SystemUser Object to a customer reference
            
            Customer customer = (Customer)user;
           
            //if User is trying to print an invoice
            String temp = (String) request.getParameter("action");
            if(( temp!=null ) && temp.contains("lodgement_invoice"))
            {
                return true;
            }
            
            //if User   wants the project Unit list
            temp = request.getMethod();
            if(temp.equals("GET") && action.equals("edit_project"))
            {
                return true;
            }
            
            //if User Tries to create Order
            if(action.equals("create_order"))
            {
                return true;
            }
            
            //Check if any of the Id Parameter belong to the customer itself
           if(id != 0 )
           {
               return id.equals(customer.getCustomerId());
           }
           else if (customerId != 0)
           {
               return customerId.equals(customer.getCustomerId());
           }
           
           if(id == 0 && customerId == 0 )
            {
                if(action.contains("view_user")
                        ||action.contains("view_agent")
                        ||action.contains("view_customer")) return false;
            }
           
          //This solve the issue of Customer  attemp to Edit or create Project
            return (!action.contains("project") || action.contains("view_project"))  ;
        }
        
      return false;
    }
    
    
    public void errorPageHandler(String action, HttpServletRequest request, HttpServletResponse response) throws IOException{
       doRedirection(request , response ,  "/Error?action="+action);
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
    
    public void log(String str){ //System.out.println(str); 
        
    }
    
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

    public static void doRedirection(HttpServletRequest request , HttpServletResponse response , String redirect) throws IOException{
       String context = request.getContextPath();
       context += redirect;
       context = context.replaceAll("//", "/");
       response.sendRedirect(context);
    }
    
    public static String doRedirection(HttpServletRequest request , String redirect) throws IOException{
       String context = request.getContextPath();
       context += redirect;
       context = context.replaceAll("//", "/");
       return request.getServerName() + context;
    }
}
