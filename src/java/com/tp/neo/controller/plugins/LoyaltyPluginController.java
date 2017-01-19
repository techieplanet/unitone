/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.PluginsController;
import com.tp.neo.model.Plugin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author swedge-mac
 */
@WebServlet(name = "LoyaltyPluginController", urlPatterns = {"/Plugins/LoyaltyPlugin"})
public class LoyaltyPluginController extends PluginsController {

    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    
    private String action = "";
    
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
            out.println("<title>Servlet LoyaltyPluginController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoyaltyPluginController at " + request.getContextPath() + "</h1>");
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
        
        String viewFile = "/views/plugins/loyalty/admin.jsp"; 
      
        if (action.isEmpty()){     
            //get plugins list
            List<Plugin> pluginsList = super.listPlugins();
            
            //find by name
            Plugin loyaltyPlugin = (Plugin) em.createNamedQuery("Plugin.findByPluginName").setParameter("pluginName", "Loyalty").getSingleResult();
            
            request.setAttribute("loyaltyPlugin", loyaltyPlugin); 
            request.setAttribute("plugins", pluginsList); 
            request.setAttribute("currentpluginName", "loyalty"); 
        }
        
        //Keep track of the sideBar
        if(request.getAttribute("sideNav") == null)
            request.setAttribute("sideNav", "Plugin");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
            
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
