/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.DashboardHelper;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Agent;
import com.tp.utils.DateFunctions;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;
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
 * @author Swedge
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class DashboardController extends AppController {
    public final String pageTitle = "Dashboard";
    
    private final static Logger LOGGER = Logger.getLogger(Agent.class.getCanonicalName());
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    //private SystemUser sessionUser;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    
    private String action = "";
    private String viewFile = "";
    
    
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
         log("action in do get: " + action);
         System.out.println("Inside do get");
         
         if(super.hasActiveUserSession(request, response)){
//            if(super.hasActionPermission(new Agent().getPermissionName(action), request, response)){
                processGetRequest(request, response);
//            }
//            else{
//                super.errorPageHandler("forbidden", request, response);
//            }
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
        //processRequest(request, response);
    }

    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        viewFile = "/views/dashboard/home.jsp";
        
        em = emf.createEntityManager();
        
        //get total due
        DashboardHelper helper = new DashboardHelper();
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";

        try{
            if(action.equalsIgnoreCase("ordersummary")){
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                int groupId = Integer.parseInt(request.getParameter("grouping"));

                String jsonResponse = this.fetchOrderSummary(startDate, endDate, groupId);

                response.getWriter().write(jsonResponse);
                response.getWriter().flush(); 
                response.getWriter().close();
            }
            if(action.equalsIgnoreCase("lodgementsummary")){
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                int groupId = Integer.parseInt(request.getParameter("grouping"));

                String jsonResponse = this.fetchLodgementSummary(startDate, endDate, groupId);
                System.out.println("jsonResponse: " + jsonResponse );

                response.getWriter().write(jsonResponse);
                response.getWriter().flush(); 
                response.getWriter().close();
            }
            else{
                //DecimalFormat formatter = new DecimalFormat("0,000.00"); //this will do same as String.format("%,.2f",XXXXXX)

                long customerCount = em.createNamedQuery("Customer.findAll").getResultList().size();
                long agentCount = em.createNamedQuery("Agent.findByApprovalStatus").setParameter("approvalStatus", 1).getResultList().size();


                request.setAttribute("totalDue", String.format("%,.2f",helper.getTotalDuePayments()));  //total due payments 
                request.setAttribute("totalOutstanding", String.format("%,.2f",helper.getTotalOutstandingPayments()));  //total outstanding
                request.setAttribute("commissionsPayable", String.format("%,.2f",helper.getTotalCommissionsPayable()));  //total outstanding
                request.setAttribute("totalStockValue", String.format("%,.2f",helper.getTotalStockValue()));  //total value of remaining stock

                //orders will go here
                request.setAttribute("projectPerformance", helper.getProjectsPerformance());  //the amount sold for each project unit
                request.setAttribute("orderSummary", helper.getOrderSummary());  //the default version to get orders in the last 7 days
                request.setAttribute("lodgementSummary", helper.getLodgementSummary());  //the default version to get lodgements in the last 7 days

                request.setAttribute("agentCount", agentCount);  
                request.setAttribute("customerCount", customerCount);  
                request.setAttribute("completedOrders", em.createNamedQuery("ProductOrder.findByApprovalStatus").setParameter("approvalStatus", 2).getResultList().size());  
                request.setAttribute("processingOrders", em.createNamedQuery("ProductOrder.findByApprovalStatus").setParameter("approvalStatus", 1).getResultList().size());  
                request.setAttribute("customersPerAgent", String.format("%.2f", (float)customerCount/agentCount));  

                //request.setAttribute("topFiveAgentLocations", helper.getTopFiveAgentLocations());  //not needed anymore
                //System.out.println("top: " + em.createNamedQuery("Agent.findByTopSellingLocations").getResultList().size());
                
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
                dispatcher.forward(request, response);
            }
        } catch (Exception e){
            String inputValues = request.getQueryString() != null ? request.getQueryString() : "";
            SystemLogger.logSystemIssue(APP_NAME, inputValues, e.getMessage());
            e.getMessage();
            e.printStackTrace();
        }


            
    }
    
    
    private String fetchOrderSummary(String start, String end, int groupId){
        String orderSummaryJSON = "";
        
        try{
            String[] startArray = start.split("-");
            //note that months start at 0
            Date startDate = DateFunctions.getDateFromValues(Integer.parseInt(startArray[2]), Integer.parseInt(startArray[1])-1, Integer.parseInt(startArray[0]));

            String[] endArray = end.split("-");
            Date endDate = DateFunctions.getDateFromValues(Integer.parseInt(endArray[2]), Integer.parseInt(endArray[1])-1, Integer.parseInt(endArray[0]));

            String truncatedBy = "", truncationResultFormat = "";

            switch(groupId){
                case 1:
                    truncatedBy = "day";
                    truncationResultFormat = "d-Mon-YYYY";
                    break;
                case 2:
                    truncatedBy = "Month";
                    truncationResultFormat = "Mon-YYYY";
                    break;
                case 3:
                    truncatedBy = "Year";
                    truncationResultFormat = "YYYY";
                    break;
            }

            DashboardHelper helper = new DashboardHelper();
            orderSummaryJSON = helper.getOrderSummary(truncatedBy, truncationResultFormat, startDate, endDate);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orderSummaryJSON;
    }
    
    
    public String fetchLodgementSummary(String start, String end, int groupId){
        String lodgementSummaryJSON = "";
        
        try{
            String[] startArray = start.split("-");
            //note that months start at 0
            Date startDate = DateFunctions.getDateFromValues(Integer.parseInt(startArray[2]), Integer.parseInt(startArray[1])-1, Integer.parseInt(startArray[0]));

            String[] endArray = end.split("-");
            Date endDate = DateFunctions.getDateFromValues(Integer.parseInt(endArray[2]), Integer.parseInt(endArray[1])-1, Integer.parseInt(endArray[0]));

            String truncatedBy = "", truncationResultFormat = "";

            switch(groupId){
                case 1:
                    truncatedBy = "day";
                    truncationResultFormat = "d-Mon-YYYY";
                    break;
                case 2:
                    truncatedBy = "Month";
                    truncationResultFormat = "Mon-YYYY";
                    break;
                case 3:
                    truncatedBy = "Year";
                    truncationResultFormat = "YYYY";
                    break;
            }

            DashboardHelper helper = new DashboardHelper();
            lodgementSummaryJSON = helper.getLodgementSummary(truncatedBy, truncationResultFormat, startDate, endDate);
            System.out.println("orderSummaryJSON: " + lodgementSummaryJSON);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lodgementSummaryJSON;
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


//        String q = "SELECT o.agent.firstname FROM ProductOrder o WHERE o.id = :id";
//        TypedQuery<String> orders =  em.createQuery(q, String.class).setParameter("id", 5);
//        List<String> oStrings = orders.getResultList();
//        System.out.println("List.size: " + oStrings.get(0));
//        System.out.println("Single String: " + orders.getSingleResult());
        
        //multiple
//        q = "SELECT o.agent.firstname, o.agent.lastname FROM ProductOrder o WHERE o.id = :id";
//        TypedQuery<Object[]> agents =  em.createQuery(q, Object[].class).setParameter("id", 5);
//        List<Object[]> agentStringList = agents.getResultList();
//        for(Object[] thisAgent : agentStringList ){
//            System.out.println("First name" + thisAgent[0] + " Last name: " + thisAgent[1]);
//        }
        
//        String q = "SELECT p FROM ProductOrder p " 
//                    + "JOIN p.orderItemCollection q "
//                    + "JOIN q.lodgementItemCollection r "
//                    + "WHERE r.approvalStatus = :aps "
//                    + "GROUP BY p.id "
//                    + "ORDER  BY p.id";
//        TypedQuery<ProductOrder> orders =  em.createQuery(q, ProductOrder.class).setParameter("aps", 1);
//        List<ProductOrder> ordersList = orders.getResultList();
//        for(ProductOrder order : ordersList)
//            System.out.println("Order Id: " + order.getId());
//        
//        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
//        dispatcher.forward(request, response);


//          String q = "SELECT p, r FROM ProductOrder p " 
//                    + "JOIN p.orderItemCollection q "
//                    + "JOIN q.lodgementItemCollection r "
//                    + "WHERE r.approvalStatus = :aps "
//                    + "GROUP BY p.id, r.id "
//                    + "ORDER  BY p.id";
//        TypedQuery<Object[]> ordersAndL =  em.createQuery(q, Object[].class).setParameter("aps", 1);
//        List<Object[]> ordersAndLList = ordersAndL.getResultList();
//        for(Object[] orderAndL : ordersAndLList)
//            System.out.println("Order Id: " + ((ProductOrder)orderAndL[0]).getId() + ", Lodgement Id: " + ((LodgementItem)orderAndL[1]).getId());