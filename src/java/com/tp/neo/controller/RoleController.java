/*
 * To change this license header, choose License Headers in Role Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Role;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "Role", urlPatterns = {"/Role"})
public class RoleController extends AppController {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String ROLE_ADMIN = "/views/role/admin.jsp"; 
    private static String ROLE_NEW = "/views/role/add.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    
    public final String pageTitle = "Role";
    
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
            out.println("<title>Servlet RoleController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoleController at " + request.getContextPath() + "</h1>");
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
        String viewFile = ROLE_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String stringId = request.getParameter("id") != null ? request.getParameter("id") : "";
        
        if (action.equalsIgnoreCase("new")){
               viewFile = ROLE_NEW;
               request.setAttribute("permissionsList", new ArrayList<String>());
               request.setAttribute("permissionsList", getSystemPermissionsEntitiesMap());
               request.setAttribute("roleId", "");
        }
        else if(action.equalsIgnoreCase("delete")){
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("edit") && !(stringId.equals(""))){
            viewFile = ROLE_NEW;
            
            //find by ID
            int id = Integer.parseInt(stringId);
            cleanRequest(request);
            Role role = em.find(Role.class, id);
            log("Logging permissions: " + role.getPermissions());
            request.setAttribute("selectedPermissions", getSelectedPermissionsCollection(role.getPermissions()));
            setRequestAttributes(request, role, "edit"); //set others
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listroles")){
            viewFile = ROLE_ADMIN;
            request.setAttribute("roles", listRoles());
        }
        else{
            viewFile = ROLE_ADMIN;
            request.setAttribute("roles", listRoles());
        }

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
        
        em = emf.createEntityManager();
        String viewFile = ROLE_NEW;
        request.setAttribute("success", false);
        
        Role role = new Role();
        
        
            try{                                
                em.getTransaction().begin();
                
                role.setTitle(request.getParameter("title"));
                role.setDescription(request.getParameter("desc"));
                role.setPermissions(gson.toJson(request.getParameterValues("permissions")));
                role.setActive((short)1);   
                
                validate(role);
                
                new TrailableManager(role).registerInsertTrailInfo((long)1);                
                
                em.persist(role);
                
                em.getTransaction().commit();
                
                em.refresh(role);
                
                setRequestAttributes(request, role, "edit");
                request.setAttribute("success", true);
                request.setAttribute("selectedPermissions", new ArrayList<String>());
                request.setAttribute("selectedPermissions", getSelectedPermissionsCollection(role.getPermissions()));
               
                em.close();
            }
            catch(PropertyException e){
                e.printStackTrace();
                //System.out.println("inside catch area: " + e.getMessage());
                viewFile = ROLE_NEW;
                String[] array = request.getParameterValues("permissions");
                ArrayList<String> selectedPermissions = array != null && array.length > 0 ? new ArrayList<String>(Arrays.asList(array)) : new ArrayList<String>();
                
                request.setAttribute("selectedPermissions", selectedPermissions);
                setRequestAttributes(request, role, "new");
                request.setAttribute("errors", errorMessages);
            }
            catch(Exception e){
                e.printStackTrace();
                SystemLogger.logSystemIssue("Role", gson.toJson(role), e.getMessage());
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        em = emf.createEntityManager();
        String viewFile = ROLE_NEW;
        request.setAttribute("success", false);
        
        Role role = new Role();
        Gson gson = new GsonBuilder().create();
        
        try{                                
                em.getTransaction().begin();
                
                role = em.find(Role.class, Integer.parseInt(request.getParameter("id")));
                role.setTitle(request.getParameter("title"));
                role.setDescription(request.getParameter("desc"));
                role.setPermissions(gson.toJson(request.getParameterValues("permissions")));
                role.setActive((short)1);   
                
                validate(role);
                
                new TrailableManager(role).registerUpdateTrailInfo((long)1);
                                
                em.getTransaction().commit();
            
                setRequestAttributes(request, role, "edit");
                request.setAttribute("success", true);
                request.setAttribute("selectedPermissions", new ArrayList<String>());
                request.setAttribute("selectedPermissions", getSelectedPermissionsCollection(role.getPermissions()));
               
                em.close();
                
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = ROLE_NEW;
                String[] array = request.getParameterValues("permissions");
                ArrayList<String> selectedPermissions = array != null && array.length > 0 ? new ArrayList<String>(Arrays.asList(array)) : new ArrayList<String>();
                
                request.setAttribute("selectedPermissions", selectedPermissions);
                setRequestAttributes(request, role, "edit");   
                request.setAttribute("errors", errorMessages);
            }
            catch(Exception e){
                e.printStackTrace();
                SystemLogger.logSystemIssue("Role", gson.toJson(role), e.getMessage());
            }        
            
            //new URI(request.getHeader("referer")).getPath();
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    private void setRequestAttributes(HttpServletRequest request, Role role, String action){            
        request.setAttribute("role", role);
        request.setAttribute("permissionsList", "");
        request.setAttribute("permissionsList", getSystemPermissionsEntitiesMap()); //all system permissions
        request.setAttribute("action", action);
        request.setAttribute("id", role.getRoleId());
    }
    
    private ArrayList<String> getSelectedPermissionsCollection(String permissions){
        System.out.println("getSelectedPermissionsCollection: " + permissions);
        Type str = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> selectedPermissions = gson.fromJson(permissions, str);
        
        return selectedPermissions;
    }
    
    
    public void delete(int id){
        em = emf.createEntityManager();
        
        Role role = em.find(Role.class, id);
        em.getTransaction().begin();
        em.remove(role);
        em.getTransaction().commit();

        em.close();
    }
    
    
    private void validate(Role role) throws PropertyException{
        errorMessages.clear();
        
        if(role.getTitle().isEmpty()){
            errorMessages.put("title", "Please enter a title ");
        }
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    
     public List<Role> listRoles(){
        
        em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Role.findAll");
        List<Role> roleList = jpqlQuery.getResultList();

        return roleList;
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