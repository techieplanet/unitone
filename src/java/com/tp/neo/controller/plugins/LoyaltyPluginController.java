/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.controller.PluginsController;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;

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
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        Plugin plugin = new Plugin();
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(plugin.getPermissionName(action), request, response)){
                    processUpdateRequest(request, response);
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
        
        String viewFile = "/views/plugins/loyalty/admin.jsp"; 
      
        if (action.isEmpty()){     
            //get plugins list
            List<Plugin> pluginsList = super.listPlugins();
            
            //find by name
            Plugin loyaltyPlugin = (Plugin) em.createNamedQuery("Plugin.findByPluginName").setParameter("pluginName", "Loyalty").getSingleResult();            
            
            request.setAttribute("loyaltyPlugin", loyaltyPlugin); 
            request.setAttribute("plugins", pluginsList); 
            request.setAttribute("currentpluginName", "loyalty"); 
            request.setAttribute("settingsMap", decodeJsonToMap(loyaltyPlugin.getSettings())); 

                
        }
        
        //Keep track of the sideBar
        if(request.getAttribute("sideNav") == null)
            request.setAttribute("sideNav", "Plugin");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
            
    }
    
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        em = emf.createEntityManager();
        String viewFile = "/views/plugins/loyalty/admin.jsp"; 
        
        HashMap settingsMap = new HashMap();
        Plugin loyaltyPlugin = new Plugin();
        Gson gson = new GsonBuilder().create();
        boolean success = false;
        
        try{                 
                em.getTransaction().begin();
                
                loyaltyPlugin = (Plugin) em.createNamedQuery("Plugin.findByPluginName").setParameter("pluginName", "Loyalty").getSingleResult();
                int installationValue = request.getParameter("install_status").equalsIgnoreCase("on") ? 1 : 0;
                loyaltyPlugin.setInstallationStatus((short)installationValue);
                
                int activeValue = request.getParameter("active").equalsIgnoreCase("on") ? 1 : 0;
                loyaltyPlugin.setActive((short)activeValue);
                
                loyaltyPlugin.setSettings(request.getParameter("reward_value"));
                
                double rewardValue = 0;
                
                validate(loyaltyPlugin);
                
                rewardValue = Double.parseDouble(request.getParameter("reward_value"));
                
                settingsMap.put("reward_value", rewardValue);
                loyaltyPlugin.setSettings(gson.toJson(settingsMap)); //replace the value with a map
                                
                new TrailableManager(loyaltyPlugin).registerUpdateTrailInfo(sessionUser.getSystemUserId());   
                
                em.getTransaction().commit();
                
                success=true;
                request.setAttribute("loyaltyPlugin", loyaltyPlugin);
                request.setAttribute("success", success);
                request.setAttribute("plugins", super.listPlugins()); 
                request.setAttribute("currentpluginName", "loyalty"); 
                request.setAttribute("settingsMap", decodeJsonToMap(loyaltyPlugin.getSettings())); 
                em.close();
                
            }
            catch(PropertyException e){
                e.printStackTrace();
                //System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("loyaltyPlugin", loyaltyPlugin);
                request.setAttribute("errors", errorMessages);
                request.setAttribute("plugins", super.listPlugins()); 
                request.setAttribute("currentpluginName", "loyalty");
                settingsMap.put("reward_value", loyaltyPlugin.getSettings());
                request.setAttribute("settingsMap", settingsMap); 
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
//            if(success){

//              AppController.doRedirection(request, response, "/Plugins/LoyaltyPlugin");
//            }
//            else {
//                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
//                dispatcher.forward(request, response);
//            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    private Map<String, String> decodeJsonToMap(String jsonElement){
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> settingsMap = gson.fromJson(jsonElement, type);
        
//        for (Map.Entry<String, String> entry : settingsMap.entrySet())
//        {
//           //System.out.println(entry.getKey() + "/" + entry.getValue());
//        }

        return settingsMap;
    }
    
    private void validate(Plugin plugin) throws PropertyException{
        errorMessages.clear();
        
        if(!plugin.getSettings().matches("\\d+(\\.\\d{1,2})?")) {
            errorMessages.put("reward_value", "Enter valid currency value for reward value");
            throw new PropertyException("");
        }
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
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
