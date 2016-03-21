/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Role;
import com.tp.neo.model.utils.TrailableManager;
import com.tp.neo.model.User;
import com.tp.neo.model.utils.TPController;
import com.tp.neo.model.utils.AuthManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;
import com.tp.neo.model.utils.MailSender;
import java.net.URI;
import java.util.Enumeration;
import javax.persistence.RollbackException;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "User", urlPatterns = {"/User"})
public class UserController extends TPController {
    public final String pageTitle = "User";
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static final String ENTITY_LIST = "/views/user/admin.jsp"; 
    private static final String NEW_ENTITY = "/views/user/add.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    
    List<Role> rolesList;
    
    String newEmailSubject = "Your NeoForce login details";
    String newRegEmail = "Dear %s,<br/>" +
                        "You have been registered as a new user on NeoForce Sales Force Solution. You may login with your email and password.<br/>" +
                        "Your newly created password is %s.<br/><br/>" +
                        "To login, visit this <a href=\"localhost:8080/NeoForce\">link</a>. If the link does not work, copy the following URL and paste in directly in your address bar.<br/><br/>" +
                        "<p style=\"text-align: center;\">localhost:8080/NeoForce</p>";
            
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        em = emf.createEntityManager();
        Query query = em.createNamedQuery("Role.findByActive").setParameter("active", 1);
        rolesList = query.getResultList();
        em.close();
        
      }
    
//    public UserController(){
//        
//    }
    
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
            out.println("<title>Servlet UserController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
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
        if(super.hasActiveUserSession(request, response, request.getRequestURL().toString()))
            processGetRequest(request, response);
    }

    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManager em = emf.createEntityManager();
        String viewFile = ENTITY_LIST; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String stringId = request.getParameter("id") != null ? request.getParameter("id") : "";
        
        if (action.equalsIgnoreCase("new")){
               viewFile = NEW_ENTITY;
               request.setAttribute("userId", "");
               
               request.setAttribute("rolesList", rolesList);
               request.setAttribute("action", "new");
        }
        else if(action.equalsIgnoreCase("delete")){
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("edit") && !(stringId.equals(""))){
            viewFile = NEW_ENTITY;
            
            //find by ID
            int id = Integer.parseInt(stringId);
            
            User user = em.find(User.class, id);
            request.setAttribute("user", user);
            request.setAttribute("action", "edit");
            request.setAttribute("rolesList", rolesList);
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listusers")){
            viewFile = ENTITY_LIST;
            request.setAttribute("users", listUsers());
                
        }
        else{
            viewFile = ENTITY_LIST;
            request.setAttribute("users", listUsers());
        }
        
//        try{
//            log("Initial AuthManager: " + AuthManager.getInitialPassword());
//        } catch(Exception e){
//            
//        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
            
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
        if(super.hasActiveUserSession(request, response, request.getRequestURL().toString())){
            if(request.getParameter("id").equalsIgnoreCase(""))
                processInsertRequest(request, response);
            else
                processUpdateRequest(request, response);
        }
    }

    
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("Insert mode");
               
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        em = emf.createEntityManager();
        String viewFile = NEW_ENTITY;
        request.setAttribute("success", false);
        
        
        User user = new User();
        
            try{                                
                em.getTransaction().begin();
                
                user.setFirstname(request.getParameter("firstname"));
                user.setMiddlename(request.getParameter("middlename"));
                user.setLastname(request.getParameter("lastname"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setRole(em.find(Role.class, Integer.parseInt(request.getParameter("role_id"))));
                user.setPermissions("");
                user.setDeleted((short)0);   
                user.setActive((short)1);   
                
                validate(user);
                
                //password handling
                String initPass = AuthManager.generateInitialPassword();  //randomly generated password
                user.setPassword(AuthManager.getSaltedHash(initPass));  //hash the inital password
                user.setUsername(user.getEmail());
                
                new TrailableManager(user).registerInsertTrailInfo(1);                
                
                //log(gson.toJson(user));
                
                em.persist(user);
                
                em.getTransaction().commit();
                
                em.refresh(user);
                
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("user", user);
                request.setAttribute("action", "edit");
                request.setAttribute("success", true);
               
                em.close();
                
                
                //send email to user on new registration 
                String message = String.format(newRegEmail, user.getFirstname(), initPass);
                new MailSender().sendHtmlEmail(user.getEmail(), defaultEmail, newEmailSubject, message);
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage() + "ACTION: " + action);
                viewFile = NEW_ENTITY;
                request.setAttribute("user", user);
                request.setAttribute("action", action);
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("errors", errorMessages);
            }
            catch(RollbackException e){
                e.printStackTrace();
                System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                viewFile = NEW_ENTITY;
                request.setAttribute("user", user);
                request.setAttribute("action", action);
                request.setAttribute("rolesList", rolesList);
                errorMessages.put("mysqlviiolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
            }
            catch(Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                if(message == null) message = "An error occured";
                setExceptionAttribbutes(user);
                SystemLogger.logSystemIssue("User", gson.toJson(errorMessages), message);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        em = emf.createEntityManager();
        String viewFile = NEW_ENTITY;
        request.setAttribute("success", false);
        
        User user = new User();
        Gson gson = new GsonBuilder().create();
        
        try{                                
                em.getTransaction().begin();
                
                user = em.find(User.class, Integer.parseInt(request.getParameter("id")));
                user.setFirstname(request.getParameter("firstname"));
                user.setMiddlename(request.getParameter("middlename"));
                user.setLastname(request.getParameter("lastname"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setRole(em.find(Role.class, Integer.parseInt(request.getParameter("role_id"))));
                user.setPermissions("");
                user.setDeleted((short)0);   
                user.setActive((short)1); 
                
                validate(user);
                
                new TrailableManager(user).registerUpdateTrailInfo(1);
                
                em.getTransaction().commit();
            
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("user", user);
                request.setAttribute("action", "edit");
                request.setAttribute("success", true);
               
                em.close();
                
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = NEW_ENTITY;
                request.setAttribute("user", user);
                request.setAttribute("action", action);
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("errors", errorMessages);
            }
            catch(RollbackException e){
                    e.printStackTrace();
                    System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                    viewFile = NEW_ENTITY;
                    request.setAttribute("user", user);
                    request.setAttribute("action", action);
                    request.setAttribute("rolesList", rolesList);
                    errorMessages.put("mysqlviiolation", e.getMessage());
                    request.setAttribute("errors", errorMessages);
                }
            catch(Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                if(message == null) message = "An error occured";
                setExceptionAttribbutes(user);
                SystemLogger.logSystemIssue("User", gson.toJson(errorMessages), message);
            }        
            
            //new URI(request.getHeader("referer")).getPath();
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    public void delete(int id){
        em = emf.createEntityManager();
        
        User user = em.find(User.class, id);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();

        em.close();
    }
    
    
    private void validate(User user) throws PropertyException{
        errorMessages.clear();
        
        if(user.getFirstname().isEmpty()){
            errorMessages.put("firstname", "First name cannot be empty");
        }
        
        if(user.getLastname().isEmpty()){
            errorMessages.put("lastname", "Last name cannot be empty");
        }
        
//        if(user.getUsername().isEmpty()){
//            errorMessages.put("username", "User name cannot be empty");
//        }
        
//        if(user.getPassword().isEmpty()){
//            errorMessages.put("password", "AuthManager cannot be empty");
//        }
        
//        if(!(request.getParameter("password").equals(request.getParameter("confirm"))) ){
//            errorMessages.put("password2", "AuthManager mismatch. Please confirm your password");
//        }
        
        if(!EmailValidator.getInstance().isValid(user.getEmail())){
            errorMessages.put("email", "Please enter a valid email");
        }
        
        if(!user.getPhone().isEmpty() && !user.getPhone().matches("^[0-9]{8,11}$")){
            errorMessages.put("phone", "Phone number can only be 8-11 digits");
        }
        
        //if(!(Integer.parseInt(request.getParameter("role_id")) > 0)){
        if(!(user.getRole().getRoleId() > 0)){
            errorMessages.put("role", "You must select a role");
        }
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    
     public List<User> listUsers(){
        
        em = emf.createEntityManager();
       
        //find by ID
        Query jpqlQuery  = em.createNamedQuery("User.findAllOperable");
        List<User> userList = jpqlQuery.getResultList();

        return userList;
    }
     
     
     private void setExceptionAttribbutes(User user){
        errorMessages.clear();
        errorMessages.put("firstname", user.getFirstname());
        errorMessages.put("middlename", user.getMiddlename());
        errorMessages.put("lastname", user.getLastname());
        errorMessages.put("email", user.getEmail());
        errorMessages.put("phone", user.getPhone());
        if(user.getRole() != null)
            errorMessages.put("role", user.getRole().getRoleId().toString());
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
