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
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.PermissionHelper;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.RollbackException;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "User", urlPatterns = {"/User"})
public class UserController extends AppController {
    public final String pageTitle = "User";
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static final String ENTITY_LIST = "/views/user/admin.jsp"; 
    private static final String NEW_ENTITY = "/views/user/add.jsp";
    private static final String USER_PROFILE = "/views/user/profile.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    PermissionHelper permissionHelper = new PermissionHelper();
    
    List<Role> rolesList;
    private String action = "";
    private String viewFile = "";
    
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
        
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        System.out.println("referrer: " + request.getRequestURL().toString());
        
        User user = new User();
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(user.getPermissionName(action), request, response)){
                processGetRequest(request, response);
            }
            else{
                super.errorPageHandler("forbidden", request, response);
            }
        }
    }

    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        log("Inside get request");
    
        EntityManager em = emf.createEntityManager();
        
        viewFile = ENTITY_LIST; 
        int addstat = request.getParameter("addstat") != null   ? Integer.parseInt(request.getParameter("addstat")) : 0;
        String stringId = request.getParameter("id") != null ? request.getParameter("id") : "";
        
        if (action.equalsIgnoreCase("new")){
               log("Inside new action");
                
               viewFile = NEW_ENTITY;
               request.setAttribute("userId", "");
               request.setAttribute("rolesList", rolesList);
               request.setAttribute("action", "new");
               request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
        }
        else if(action.equalsIgnoreCase("delete")){
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("edit") && !(stringId.equals(""))){
            viewFile = NEW_ENTITY;
            log("Inside editaction");
            
            //find by ID
            long id = Long.parseLong(stringId);
            
            User user = em.find(User.class, id);
            request.setAttribute("reqUser", user); //different from session user
            request.setAttribute("action", "edit");
            request.setAttribute("rolesList", rolesList);
            request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
            request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
            if(addstat == 1) request.setAttribute("success", true);
        }
        else if(action.equalsIgnoreCase("profile")){
            
            viewFile = USER_PROFILE;
            
            request.setAttribute("user", getUser(request));
            request.setAttribute("sideNav", "Profile");
            
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listusers")){
            viewFile = ENTITY_LIST;
            request.setAttribute("users", listUsers());
        }
        else{
            viewFile = ENTITY_LIST;
            request.setAttribute("users", listUsers());
        }
        
        //Keep track of the sideBar
        if(request.getAttribute("sideNav") == null)
            request.setAttribute("sideNav", "User");
        
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
        
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        User user = new User();
        
        if(action.equalsIgnoreCase("edit_profile")){
            
            editProfile(request, response);
            return;
        }
        
        if(action.equalsIgnoreCase("password_change")){
            
            changeUserPassword(request, response);
            return;
        }
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(user.getPermissionName(action), request, response)){
                if(request.getParameter("id").equalsIgnoreCase(""))
                    processInsertRequest(request, response);
                else
                    processUpdateRequest(request, response);
            }
            else{
                super.errorPageHandler("forbidden", request, response);
            }
        }
    }

    
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        em = emf.createEntityManager();
        String viewFile = NEW_ENTITY;
        boolean insertStatus = false;
        request.setAttribute("success", false);
        
        System.out.println("Inside processInsertRequest");
        
        User user = new User();
        
            try{                                
                em.getTransaction().begin();
                
                user.setFirstname(request.getParameter("firstname"));
                user.setMiddlename(request.getParameter("middlename"));
                user.setLastname(request.getParameter("lastname"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setRole(em.find(Role.class, Integer.parseInt(request.getParameter("role_id"))));
                user.setDeleted((short)0);   
                user.setActive((short)1);   
                user.setPermissions(gson.toJson(request.getParameterValues("permissions")));
                
                validate(user);
                
                //password handling
                String initPass = AuthManager.generateInitialPassword();  //randomly generated password
                user.setPassword(AuthManager.getSaltedHash(initPass));  //hash the inital password
                user.setUsername(user.getEmail());
                
                new TrailableManager(user).registerInsertTrailInfo(sessionUser.getSystemUserId());   
                
                //log(gson.toJson(user));
                
                em.persist(user);
                
                em.getTransaction().commit();
                
                insertStatus = true;
                
                em.refresh(user);
                
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
               
                em.close();
               
                //send email to user on new registration 
                String message = String.format(newRegEmail, user.getFirstname(), initPass);
                new MailSender().sendHtmlEmail(user.getEmail(), defaultEmail, newEmailSubject, message);
                
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage() + "ACTION: " + action);
                viewFile = NEW_ENTITY;
                request.setAttribute("reqUser", user);
                request.setAttribute("action", action);
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("errors", errorMessages);
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
            }
            catch(RollbackException e){
                e.printStackTrace();
                System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                viewFile = NEW_ENTITY;
                request.setAttribute("reqUser", user);
                request.setAttribute("action", action);
                request.setAttribute("rolesList", rolesList);
                errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
            }
            catch(Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                if(message == null) message = "An error occured";
                setExceptionAttribbutes(user);
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
                SystemLogger.logSystemIssue("User", gson.toJson(errorMessages), message);
            }
            
            if(insertStatus){
                String page = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/User?action=edit&id=" + user.getUserId() + "&addstat=1";
                response.sendRedirect(page);
            }
            else{
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
                dispatcher.forward(request, response);
            }
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
                
                user = em.find(User.class, Long.parseLong(request.getParameter("id")));
                user.setFirstname(request.getParameter("firstname"));
                user.setMiddlename(request.getParameter("middlename"));
                user.setLastname(request.getParameter("lastname"));
                user.setEmail(request.getParameter("email"));
                user.setPhone(request.getParameter("phone"));
                user.setRole(em.find(Role.class, Integer.parseInt(request.getParameter("role_id"))));
                user.setDeleted((short)0);   
                user.setActive((short)1);
                user.setPermissions(gson.toJson(request.getParameterValues("permissions")));
                
                validate(user);
                
                new TrailableManager(user).registerUpdateTrailInfo(sessionUser.getSystemUserId());   
                
                em.getTransaction().commit();
    
                
                //if the updated user is the same as logged in, i.e. user updating their own records
                //replace the session user object with the new updated user object
                if((long)sessionUser.getSystemUserId() == (long)user.getUserId()) 
                    request.getSession().setAttribute("user", user);
                
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("reqUser", user);
                request.setAttribute("action", "edit");
                request.setAttribute("success", true);
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
               
                log("User Permissions; " + user.getPermissions());
                em.close();
                
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = NEW_ENTITY;
                request.setAttribute("reqUser", user);
                request.setAttribute("action", action);
                request.setAttribute("rolesList", rolesList);
                request.setAttribute("errors", errorMessages);
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
            }
            catch(RollbackException e){
                    e.printStackTrace();
                    System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                    viewFile = NEW_ENTITY;
                    request.setAttribute("reqUser", user);
                    request.setAttribute("action", action);
                    request.setAttribute("rolesList", rolesList);
                    errorMessages.put("mysqlviiolation", e.getMessage());
                    request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                    request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
                    request.setAttribute("errors", errorMessages);
            }
            catch(Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                if(message == null) message = "An error occured";
                setExceptionAttribbutes(user);
                SystemLogger.logSystemIssue("User", gson.toJson(errorMessages), message);
                request.setAttribute("permissionsList", permissionHelper.getSystemPermissionsEntitiesMap());
                request.setAttribute("selectedPermissions", permissionHelper.getSelectedPermissionsCollection(user.getPermissions()));
            }
            
            //new URI(request.getHeader("referer")).getPath();
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    public void delete(long id){
        em = emf.createEntityManager();
        
        User user = em.find(User.class, id);
        em.getTransaction().begin();
        user.setDeleted((short)1);
        
        new TrailableManager(user).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        
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
        if(user.getRole() == null || (user.getRole().getRoleId() <= 0)){
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
     
     private User getUser(HttpServletRequest request) {
         
         em = emf.createEntityManager();
         User user = em.find(User.class, Long.parseLong(request.getParameter("id")));
         
         em.close();
         return user;
    }
    
    private void editProfile(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        em = emf.createEntityManager();
        
        long userId = Long.parseLong(request.getParameter("id"));
        
        em.getTransaction().begin();
        User user = em.find(User.class, userId);
        
        Map<String,String> resMap = new HashMap();
        
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String mname = request.getParameter("mname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        
        if(fname.equalsIgnoreCase(""))
            resMap.put("fname_error", "firstname is required");
        if(lname.equalsIgnoreCase(""))
            resMap.put("lname_error", "lastname is required");
        if(phone.equalsIgnoreCase(""))
            resMap.put("phone_error", "mobile no is required");
        if(email.equalsIgnoreCase(""))
            resMap.put("email_error", "email is required");
        
        if(!user.getEmail().equals(email) && !email.equals("") && resMap.size() < 1){
            
          //Check if the email is unique 
          Query query = em.createNamedQuery("User.findByEmail");
          query.setParameter("email", email);
          List<User> userList = query.getResultList();
          
          if(userList.size() > 0){
              resMap.put("email_error", "Email already exist");
          }
          else{
              
              user.setFirstname(fname);
              user.setLastname(lname);
              user.setMiddlename(mname);
              user.setEmail(email);
              user.setPhone(phone);
              user.setUsername(email);
              
              resMap.put("success", "Profile changed successfully");
          }
          
        }
        else if(resMap.size() < 1){
            
              user.setFirstname(fname);
              user.setLastname(lname);
              user.setMiddlename(mname);
              user.setEmail(email);
              user.setPhone(phone);
              user.setUsername(email);
              
              resMap.put("success", "Profile changed successfully");
        }
        
        em.merge(user);
        em.getTransaction().commit();
        
        Gson gson = new GsonBuilder().create();
        
        String json = gson.toJson(resMap);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
        
    }
    
    private void changeUserPassword(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            em = emf.createEntityManager();
            
            
            
            Map<String, String> resMap = new HashMap();
            
            long id = Long.parseLong(request.getParameter("id"));
            
            String oldPassword = request.getParameter("old_password");
            String pwd1 = request.getParameter("pwd1");
            String pwd2 = request.getParameter("pwd2");
            
            System.out.println("Old Password : " + oldPassword + " Id : " + id);
            em.getTransaction().begin();
            
            User user = em.find(User.class, id);
            
            
            
            if(AuthManager.check(oldPassword, user.getPassword())){
                
                if(pwd1.equals(pwd2) && !pwd1.equals("")){
                    user.setPassword(AuthManager.getSaltedHash(pwd1));
                    resMap.put("success", "Password changed successfully");
                }else{
                    resMap.put("error", "Password and Re-enter password do not match");
                }
                 
            }else{
                resMap.put("error", "Invalid old password");
            }
            
            if(user != null){
                em.merge(user);
                
            }
            
            em.getTransaction().commit();
            em.close();
            
            Gson gson = new GsonBuilder().create();
            
            String json = gson.toJson(resMap);
            
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
            
            
        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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