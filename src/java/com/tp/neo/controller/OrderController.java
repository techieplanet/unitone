/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.model.Customer;
import com.tp.neo.model.utils.Sales;
import com.tp.neo.model.utils.SalesItemJson;
import com.tp.neo.model.utils.SalesObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
/**
 *
 * @author John
 */
@MultipartConfig
@WebServlet(name = "OrderController", urlPatterns = {"/Order"})
public class OrderController extends HttpServlet {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String ORDER_ADMIN = "/views/order/admin.jsp"; 
    private static String ORDER_NEW = "/views/order/add.jsp";
    private final static Logger LOGGER = 
            Logger.getLogger(Customer.class.getCanonicalName());
    

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
            out.println("<title>Servlet OrderController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderController at " + request.getContextPath() + "</h1>");
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
        processGetRequest(request, response);
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
        processPostRequest(request, response);
    }

     protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = ORDER_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String customerId = request.getParameter("customer") != null ? request.getParameter("customer") : "";
        customerId = customerId.trim();
        ProjectController project = new ProjectController();
        CustomerController customer = new CustomerController();
        
        //Project listprojects = project.listProjects();
        if (action.equalsIgnoreCase("new")){
               viewFile = ORDER_NEW;
        }
        
     
        else if (action.isEmpty() || action.equalsIgnoreCase("listcustomers")){
            viewFile = ORDER_ADMIN;
           // request.setAttribute("customers", listCustomers());
        }
        request.setAttribute("projects", project.listProjects());
        request.setAttribute("customers",customer.listCustomers());
        request.setAttribute("customerId",customerId);

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

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) {
        
        Enumeration<String> formData = request.getParameterNames();
        Enumeration<String> formAtrr = request.getAttributeNames();
        
        this.processJsonData(request.getParameter("cartDataJson").toString());
        
        String customerId = request.getParameter("Pay") != null?request.getParameter("Pay") : "No Pay";
        System.out.println("Pay : " + customerId);
        System.out.println("Form Elements");
        System.out.println("****************************");
        
        
        while(formData.hasMoreElements())
        {
            String data = formData.nextElement();
            System.out.println(data + " : " + request.getParameter(data));
        }
        System.out.println("Form Attributes");
        System.out.println("****************************");
        while(formAtrr.hasMoreElements())
        {
            String attr = formAtrr.nextElement();
            System.out.println(attr);
            System.out.println("_____________________________________________________________");
        }
    }
    
    private void processJsonData(String json)
    {
        Gson gson = new GsonBuilder().create();
        System.out.println(json);
        String str = "{sales:[{\"productName\":\"Kali Homes\",productId:1,productUnitName:\"2 Bedroom Duplex\",productUnitId:5,productQuanity:1,productAmount:1500000,amountUnit:1500000,amountTotalUnit:1500000,initialAmountPerUnit:250000,minInitialAmountSpan:250000,productMinimumInitialAmount:250000,amountLeft:1250000,payDurationPerUnit:\"24 months\",payDurationPerQuantity:\"24 months\",productMaximumDuration:5,monthlyPayPerUnit:250000,monthlyPayPerQuantity:250000,productMinimumMonthlyPayment:250000}]}";
        Type collectionType = new TypeToken<ArrayList<SalesObject>>(){}.getType();
        SalesObject salesObj = gson.fromJson(json,SalesObject.class);
        
        ArrayList<Sales> sales = salesObj.sales;
        
        for(Sales s : sales) {
            System.out.println("Product Name : " + s.productName);
            System.out.println("Product Qty : " + s.productQuanity);
            System.out.println("Product Amount Per Unit " + s.amountUnit);
            System.out.println("Product Cost" + s.amountTotalUnit);
        }
    }

}
