/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Auditlog;
import com.tp.neo.model.Customer;
import com.tp.neo.model.User;
import com.tp.neo.model.utils.AuthManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class LoginController extends HttpServlet {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    HashMap<String, String> userTypes = new HashMap<String, String>();
    String rootUrl;
    
    enum userTypesEnum {
        ADMIN, AGENT, CUSTOMER
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            //processRequest(request, response);
       String action = request.getParameter("action") != null ? request.getParameter("action") : "";
       
       if(action.equals("logout")){
           processLogoutRequest(request, response);
       }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //set up the user types in the system into an instance Map variable 
        setUpUserTypes(userTypes);
        
        processLoginRequest(request, response);
    }
    
    private void setUpUserTypes(HashMap<String, String> userTypes){
        userTypes.put("AGENT", "AGENT");
        userTypes.put("ADMIN", "ADMIN");
        userTypes.put("CUSTOMER", "CUSTOMER");
    }
    
    public void processLoginRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email") != null ? request.getParameter("email") : "";
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";//[B@3c9a818d
        String userType = request.getParameter("usertype") != null ? request.getParameter("usertype") : "";
        
        System.out.println("Password: " + password);
        
        try{
            em = emf.createEntityManager();           
            
            SystemUser user = getUserTypeObject(userType, email);
            String storedPassword = user.getPassword();
            System.out.println("storedPassword: " + storedPassword);
            
            if(AuthManager.check(password, storedPassword)){
                log("logged in");
                System.out.println("This is the stored password "+ storedPassword);
                //start the session
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(900); //15 minutes
                session.setAttribute("user", user);
                session.setAttribute("userType", userType);
                session.setAttribute("userTypes", userTypes);
                
                String basePath = "/NeoForce/Agent";
                String referrerURI = new URI(request.getHeader("referer")).toString();
                String referrerPath = new URI(request.getHeader("referer")).getPath();
                System.out.println("Context: " + URI.create(request.getRequestURL().toString()).resolve(request.getContextPath()).getPath());
                
                //do logging here
                em.getTransaction().begin();
                Auditlog auditlog = new Auditlog();
                auditlog.setActionName("User Login");
                
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));                
                
                auditlog.setLogDate(calendar.getTime());
                auditlog.setNote(String.format("User %s %s logged in as %s user at %s.",user.getFirstname(),user.getLastname(), userType, calendar.getTime()));
                auditlog.setUsertype(user.getSystemUserTypeId());
                auditlog.setUserId(user.getSystemUserId());
                
                em.persist(auditlog);
                em.getTransaction().commit();
               
                if(session.getAttribute("loginCallback") == null){
                     System.out.println("THis is the login call back of the session before redirect null"+ referrerURI );
                    response.sendRedirect(referrerURI + "Dashboard");
                    ////AuthManager.ucfirst(userType
                    return;
                }
                else{ 
                     System.out.println("THis is the login call back of the session before redirect not null"+ referrerURI );
                    response.sendRedirect(session.getAttribute("loginCallback").toString());
                    return;
                }
                
            }else{
                System.out.println("Failed!");
            }
                
            
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally{
            em.close();
        }
    }

    private SystemUser getUserTypeObject(String userType, String email){        
        String queryArg = "";
        SystemUser systemUser = new User();
        Agent agentUser = new Agent();
        Customer customerUser = new Customer();
        String returnName = "";
        if(userType.equals(userTypesEnum.ADMIN.toString().trim())) {
            queryArg = "User.findByEmail";
            Query query = em.createNamedQuery(queryArg).setParameter("email", email);
            systemUser = (User)query.getSingleResult();
            
        }
        else if(userType.equals(userTypesEnum.AGENT.toString().trim())) {
            queryArg = "Agent.findByEmail";
            Query query = em.createNamedQuery(queryArg).setParameter("email", email);
             agentUser = (Agent)query.getSingleResult();
             systemUser = (SystemUser) agentUser;
        }
        else if(userType.equals(userTypesEnum.CUSTOMER.toString().trim())) {
            queryArg = "Customer.findByEmail";
            Query query = em.createNamedQuery(queryArg).setParameter("email", email);
            customerUser = (Customer)query.getSingleResult();
            systemUser = (SystemUser) customerUser;
        }
        
        System.out.println(queryArg+" This is the query arguments");
        System.out.println(" hello "+email);
       
        return systemUser;
    }
    
    public void processLogoutRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            em = emf.createEntityManager();
            
            HttpSession session = request.getSession();
            SystemUser user = (SystemUser)session.getAttribute("user");
            String userType = session.getAttribute("userType").toString();
                                
            //do logging here
            em.getTransaction().begin();
            Auditlog auditlog = new Auditlog();
            auditlog.setActionName("User Logout");
                
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));                

            auditlog.setLogDate(calendar.getTime());
            auditlog.setNote(String.format("User %s %s logged out as %s user at %s.",user.getFirstname(),user.getLastname(), userType, calendar.getTime()));
            auditlog.setUsertype(user.getSystemUserTypeId());
            auditlog.setUserId(user.getSystemUserId());

            em.persist(auditlog);
            em.getTransaction().commit();
                
            String scheme = request.isSecure() ? "https" : "http";
            String context = URI.create(request.getRequestURL().toString()).resolve(request.getContextPath()).getPath();
            String host = new URI(request.getHeader("host")).toString();
            rootUrl = scheme + "://" + host + context + "/";
            
            session.invalidate();
            response.sendRedirect(rootUrl);
                
        } catch(IllegalStateException e){
            response.sendRedirect(rootUrl);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally {
            em.close();
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}