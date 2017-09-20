/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static com.tp.neo.controller.AgentController.gson;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.Settings;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author SWEDGE
 */
@WebServlet(name="SettingsController" , urlPatterns={"/Settings"})
public class SettingsController extends AppController {
    
    private static String AppName = "/views/settings/add.jsp";
    private static EntityManager em = AppController.emf.createEntityManager();
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
        
        if(super.hasActiveUserSession(request, response)){
           // if(super.hasActionPermission(new Agent().getPermissionName(action), request, response)){
                processRequest(request , response);
           // }
           // else{
                
           //     super.errorPageHandler("forbidden", request, response);
           // }
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
         if(super.hasActiveUserSession(request, response)){
           // if(super.hasActionPermission(new Agent().getPermissionName(action), request, response)){
                processRequest(request , response);
           // }
           // else{
                
           //     super.errorPageHandler("forbidden", request, response);
           // }
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

    
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if(action.equalsIgnoreCase("appname"))
        {
            appNameSetting(request , response);
        }
        
    }
    
    public void appNameSetting(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        String method = request.getMethod();
        
        Settings appName = (Settings) em.createNamedQuery("Settings.findByTitle")
                .setParameter("title", "AppName")
                .getSingleResult();
        
        //Get Request 
        if(method.equalsIgnoreCase("GET"))
        {
         
         Type type = new TypeToken<Map<String, String>>(){}.getType();
         Map<String, String> value = gson.fromJson(appName.getValue(), type);
         
         request.setAttribute("appName", value.get("AppName"));
         request.getRequestDispatcher(AppName).forward(request, response);
         return;
        }
        else
        {
            String name = request.getParameter("appName");
            Map<String , String> value = new HashMap<>();
            value.put("AppName", name);
            
           appName.setValue(gson.toJson(value));
           em.getTransaction().begin();
           em.persist(appName);
           em.getTransaction().commit();
           
         request.setAttribute("appName", name);
         request.setAttribute("success" , true);
         request.getRequestDispatcher(AppName).forward(request, response);
         return;
        }
    }
}
