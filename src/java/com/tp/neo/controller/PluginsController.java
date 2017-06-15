/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;
import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author swedge-mac
 */
@WebServlet(name = "PluginsController", urlPatterns = {"/Plugins"})
public class PluginsController extends AppController {

    private static final String ENTITY_LIST = "/views/plugins/admin.jsp"; 
    private static final String NEW_ENTITY = "/views/plugins/add.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    
    private String action = "";
    private String viewFile = "";
    
    
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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PluginsController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PluginsController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        
        Plugin plugin = new Plugin();
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(plugin.getPermissionName(action), request, response)){
                processGetRequest(request, response);
            }
            else{
                super.errorPageHandler("forbidden", request, response);
            }
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
        processRequest(request, response);
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        log("Inside get request");
    
        EntityManager em = emf.createEntityManager();
        
        viewFile = ENTITY_LIST; 
        
        if (action.equalsIgnoreCase("new")){
               log("Inside new action");
                
               viewFile = NEW_ENTITY;
               request.setAttribute("action", "new");
        }
        else if(action.equalsIgnoreCase("delete")){
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("edit")){
            viewFile = NEW_ENTITY;
            log("Inside editaction");
            
            //find by ID
            int id = Integer.parseInt(request.getParameter("id"));
            
            Plugin plugin = em.find(Plugin.class, id);
            request.setAttribute("plugin", plugin); //different from session user
            request.setAttribute("action", "edit");
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listusers")){
            viewFile = ENTITY_LIST;
            request.setAttribute("plugins", listPlugins());
        }
        
        //Keep track of the sideBar
        if(request.getAttribute("sideNav") == null)
            request.setAttribute("sideNav", "Plugin");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
            
    }
    
    
    
    public void delete(long id){
        em = emf.createEntityManager();
        
        Plugin plugin = em.find(Plugin.class, id);
        em.getTransaction().begin();
        plugin.setDeleted((short)1);
        
        new TrailableManager(plugin).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        
        em.getTransaction().commit();

        em.close();
    }
    
    
    private void validate(Plugin plugin) throws PropertyException{
        errorMessages.clear();
        
        if(plugin.getPluginName().equalsIgnoreCase("")){
            errorMessages.put("pluginname", "Plugin name cannot be empty");
        }
        
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    
     public List<Plugin> listPlugins(){
        
        em = emf.createEntityManager();
       
        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Plugin.findAll");
        List<Plugin> pluginsList = jpqlQuery.getResultList();

        return pluginsList;
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
