/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.AgentDashboardHelper;
import com.tp.neo.controller.helpers.DashboardHelper;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.Transaction;
import com.tp.utils.DateFunctions;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
         ////System.out.println("Inside do get");
         
         if(super.hasActiveUserSession(request, response)){
//            if(super.hasActionPermission(new Agent().getPermissionName(action), request, response)){
                if(sessionUser.getSystemUserTypeId() == 1) //admin
                    processAdminDashboard(request, response);
                if(sessionUser.getSystemUserTypeId() == 2) //agent
                    processAgentDashboard(request, response);
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

    
    protected void processAdminDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println("Inside admin dashboard");
        response.setContentType("text/html;charset=UTF-8");
        
        viewFile = "/views/dashboard/home.jsp";
        
        em = emf.createEntityManager();
        
        //get total due
        DashboardHelper helper = new DashboardHelper((HashMap<String, Plugin>)request.getSession().getAttribute("availablePlugins"));
        
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
            else if(action.equalsIgnoreCase("lodgementsummary")){
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                int groupId = Integer.parseInt(request.getParameter("grouping"));

                String jsonResponse = this.fetchLodgementSummary(startDate, endDate, groupId);
                ////System.out.println("jsonResponse: " + jsonResponse );

                response.getWriter().write(jsonResponse);
                response.getWriter().flush(); 
                response.getWriter().close();
            }
            else{
                //DecimalFormat formatter = new DecimalFormat("0,000.00"); //this will do same as String.format("%,.2f",XXXXXX)

                long customerCount = em.createNamedQuery("Customer.findAll").getResultList().size();
                long agentCount = em.createNamedQuery("Agent.findByApprovalStatus").setParameter("approvalStatus", 1).getResultList().size();

                double totalDebt = helper.getTotalDuePayments();
                request.setAttribute("totalDue", String.format("%,.2f",totalDebt));  //total due payments 
                request.setAttribute("totalOutstanding", String.format("%,.2f", helper.getTotalOutstandingPayments()));  //total outstanding
                request.setAttribute("commissionsPayable", String.format("%,.2f",helper.getTotalCommissionsPayable()));  //total outstanding
                request.setAttribute("totalStockValue", String.format("%,.2f",helper.getTotalStockValue()));  //total value of remaining stock

                //orders will go here
                request.setAttribute("projectPerformanceBySalesQuota", helper.getProjectsPerformanceBySalesQuota());  //the quota of this project or unit compared to other items in same category
                request.setAttribute("projectPerformanceByStockSold", helper.getProjectsPerformanceByStockSold());  //the srock sold for each project and its units
                request.setAttribute("orderSummary", helper.getOrderSummary());  //the default version to get orders in the last 7 days
                request.setAttribute("lodgementSummary", helper.getLodgementSummary());  //the default version to get lodgements in the last 7 days
                        
                request.setAttribute("agentCount", agentCount);  
                request.setAttribute("customerCount", customerCount);  
                request.setAttribute("completedOrders", em.createNamedQuery("ProductOrder.findByApprovalStatus").setParameter("approvalStatus", 2).getResultList().size());  
                request.setAttribute("processingOrders", em.createNamedQuery("ProductOrder.findByApprovalStatus").setParameter("approvalStatus", 1).getResultList().size());  
                request.setAttribute("customersPerAgent", String.format("%.2f", agentCount == 0 ? 0 : (float)customerCount/agentCount));  
                
                //Lets get the total income 
                request.setAttribute("income",String.format("%,.2f",helper.getTotalIncome()));
                
                //Lets get the total annual Maintenance 
                request.setAttribute("annualMaintenance",String.format("%,.2f",helper.getTotalAnnualMaintenance()));
                
                //Lets get the total with Holding Tax 
                request.setAttribute("withHoldingTax",String.format("%,.2f",helper.getTotalWithHoldingTax()));
                
                 //Lets get the total VAT 
                request.setAttribute("VAT",String.format("%,.2f",helper.getTotalVat()));
                
                //request.setAttribute("topFiveAgentLocations", helper.getTopFiveAgentLocations());  //not needed anymore
               ////System.out.println("top: " + em.createNamedQuery("Agent.findByTopSellingLocations").getResultList().size());
                
                //Keep track of the sideBar
                request.setAttribute("sideNav", "Dashboard");
                
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
                dispatcher.forward(request, response);
            }
        } catch (Exception e){
            String inputValuesString = request.getQueryString() != null ? request.getQueryString() : "";
            SystemLogger.logSystemIssue(APP_NAME, inputValuesString, e.getMessage());
            e.getMessage();
            e.printStackTrace();
        }


            
    }
    
    
    
    
    protected void processAgentDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println("Inside agent dashboard");
        response.setContentType("text/html;charset=UTF-8");
        
        viewFile = "/views/dashboard/home.jsp";
        
        em = emf.createEntityManager();
        
        //get total due
        AgentDashboardHelper helper = new AgentDashboardHelper((HashMap<String, Plugin>)request.getSession().getAttribute("availablePlugins"));
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        Long agentId = sessionUser.getSystemUserId();
        
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
            else if(action.equalsIgnoreCase("lodgementsummary")){
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                int groupId = Integer.parseInt(request.getParameter("grouping"));

                String jsonResponse = this.fetchLodgementSummary(startDate, endDate, groupId);
               ////System.out.println("jsonResponse: " + jsonResponse );

                response.getWriter().write(jsonResponse);
                response.getWriter().flush(); 
                response.getWriter().close();
            }
            else{
                //DecimalFormat formatter = new DecimalFormat("0,000.00"); //this will do same as String.format("%,.2f",XXXXXX)
                
                //long customerCount = em.createNamedQuery("Customer.findAll").getResultList().size();
                //long agentCount = em.createNamedQuery("Agent.findByApprovalStatus").setParameter("approvalStatus", 1).getResultList().size();
                
                //debtors
                HashMap<String, Number> debtorsMap = helper.getTotalAgentDebts(agentId);
                request.setAttribute("totalDebt", String.format("%,.2f", debtorsMap.get("totalDebt").doubleValue()));  //total due payments 
                request.setAttribute("debtorsCount", debtorsMap.get("customerCount").intValue());  // debtors count
                
                //outstanding
                HashMap<String, Number> totalOutstandingPayments = helper.getTotalOutstandingPayments(agentId);
                request.setAttribute("totalOutstanding", String.format("%,.2f", totalOutstandingPayments.get("totalOustandingPayments").doubleValue()));  //total outstanding
                request.setAttribute("outstandersCount", totalOutstandingPayments.get("customerCount").intValue());  //total 
                
                //commissions
                request.setAttribute("commissionsPayable", String.format("%,.2f",helper.getTotalCommissionsPayable(agentId)));  //total outstanding
                
                //unapproved lodgements
                HashMap<String, Number> totalUnapproved = helper.getTotalUnapprovedLodgments(agentId);
                request.setAttribute("totalUapprovedLodgements", String.format("%,.2f",totalUnapproved.get("totalUnapproved")));  //total value of unapproved lodgements
                request.setAttribute("unapprovedCount", totalUnapproved.get("customerCount"));  //total value of unapproved lodgements

                //orders will go here
                request.setAttribute("projectPerformance", helper.getProjectsPerformance(agentId));  //the amount sold for each project unit
                request.setAttribute("projectPerformanceBySalesQuota", helper.getProjectsPerformanceBySalesQuota(agentId));  //the amount sold for each project unit
                request.setAttribute("orderSummary", helper.getOrderSummary(agentId));  //the default version to get orders in the last 7 days
                request.setAttribute("lodgementSummary", helper.getLodgementSummary(agentId));  //the default version to get lodgements in the last 7 days
                
               
               
                //request.setAttribute("agentCount", agentCount);  
                List<Customer> myCustomers = helper.getMyCustomers(agentId);
                request.setAttribute("customerCount", myCustomers.size());  
                
                List<ProductOrder> myOrders = helper.getMyOrders(agentId);
                request.setAttribute("orderCount", myOrders.size());
                
                double paymentValue = helper.getMyPaymentValue(agentId);
                request.setAttribute("paymentValue", String.format("%,.2f",paymentValue));  
                //request.setAttribute("processingOrders", em.createNamedQuery("ProductOrder.findByApprovalStatus").setParameter("approvalStatus", 1).getResultList().size());  
                //request.setAttribute("customersPerAgent", String.format("%.2f", agentCount == 0 ? 0 : (float)customerCount/agentCount));  
                //total order value = totalpayment + totalOutstanding + totalReceivable;
                request.setAttribute("totalOrderValue" , String.format("%,.2f", helper.getTotalOrderValue(agentId)));
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
                dispatcher.forward(request, response);
            }
        } catch (Exception e){
            String inputValuesString = request.getQueryString() != null ? request.getQueryString() : "";
            SystemLogger.logSystemIssue(APP_NAME, inputValuesString, e.getMessage());
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
                    truncationResultFormat = "dd-Mon-YYYY";
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
            ////System.out.println("Error: " + e.getMessage());
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
                    truncationResultFormat = "dd-Mon-YYYY";
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

            DashboardHelper helper = new DashboardHelper(super.getAvailableplugins());
            lodgementSummaryJSON = helper.getLodgementSummary(truncatedBy, truncationResultFormat, startDate, endDate);
            ////System.out.println("orderSummaryJSON: " + lodgementSummaryJSON);
        } catch(Exception e){
            ////System.out.println("Error: " + e.getMessage());
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