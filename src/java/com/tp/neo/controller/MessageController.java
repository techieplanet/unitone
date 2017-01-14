/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.OrderItemHelper;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.OrderItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
@WebServlet(name = "Message", urlPatterns = {"/Message"})
public class MessageController extends AppController {

   

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
                processGetRequest(request, response);
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
                processPostRequest(request, response);
        }
        else{
            super.errorPageHandler("forbidden", request, response);
        }
    }

    

    private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        String viewFile = "";
        
        if(action.equalsIgnoreCase("new")){
            
            viewFile = "/views/message/bcc_email.jsp";
            request.setAttribute("customers", getAgentCustomers());
        }else if(action.equalsIgnoreCase("filter_customer")){
            
            getFilteredCustomer(request,response);
            return;
        }
        
        request.setAttribute("sideNav", "Message");
        request.setAttribute("sideNavAction",action);
        
        request.getRequestDispatcher(viewFile).forward(request, response);
        
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        String viewFile = "";
        if(action.equalsIgnoreCase("email")){
            
            sendEmail(request);
            viewFile = "/views/message/bcc_email.jsp";
            request.setAttribute("customers", getAgentCustomers());
        }
        else if(action.equalsIgnoreCase("sms")){
            sendSMS(request);
            viewFile = "/views/message/bcc_email.jsp";
            request.setAttribute("customers", getAgentCustomers());
        }
        
        request.setAttribute("sideNav", "Message");
        request.setAttribute("sideNavAction","new");
        
        request.getRequestDispatcher(viewFile).forward(request, response);
    }
    
    
    private List<Map> getAgentCustomers(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Customer.findByAgent")
                .setParameter("agent", (Agent)sessionUser)
                .setParameter("deleted", 0);
        
        List<Customer> customers = query.getResultList();
        
        List<Map> customersMap = new ArrayList();        
        
        OrderItemHelper itemHelper = new OrderItemHelper();
        
        for(Customer customer : customers){
            
            Map<String, String> map = new HashMap();
            
            map.put("photoPath", customer.getPhotoPath());
            map.put("firstname", customer.getFirstname());
            map.put("middlename", customer.getMiddlename());
            map.put("lastname", customer.getLastname());
            map.put("phone", customer.getPhone());
            map.put("email", customer.getEmail());
            map.put("street", customer.getStreet());
            map.put("city", customer.getCity());
            map.put("state", customer.getState());
            map.put("id", customer.getCustomerId().toString());
            map.put("defaulter", (itemHelper.isCustomerDefaulter(customer)) ? "defaulter" : "");
            
            customersMap.add(map); 
        }
        
        return customersMap;
        
    }
    
    private void getFilteredCustomer(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            Gson gson = new GsonBuilder().create();
            String jsonResponse = gson.toJson(getAgentCustomers());
            
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    

    private void sendEmail(HttpServletRequest request) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String msg = request.getParameter("email_body");
        
        Gson gson = new GsonBuilder().create();
        Type token = new TypeToken<List<Long>>(){}.getType();
        List<Long> ids = gson.fromJson(request.getParameter("customersId"), token);
        
        List<Customer> customerList = new ArrayList();
        
        for(Long id : ids){
            
            Customer customer  = em.find(Customer.class, id);
            customerList.add(customer);
        }
        
        
        request.setAttribute("success", customerList.size() + " Email was sent successfully");
    }

    private void sendSMS(HttpServletRequest request) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String msg = request.getParameter("sms_body");
        
        Gson gson = new GsonBuilder().create();
        
        Type token = new TypeToken<List<Long>>(){}.getType();
        List<Long> ids = gson.fromJson(request.getParameter("customersId"), token);
        
        List<Customer> customerList = new ArrayList();
        
        for(Long id : ids){
            
            Customer customer  = em.find(Customer.class, id);
            customerList.add(customer);
        }
        
        request.setAttribute("success", customerList.size() + " SMS was sent successfully");
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
