/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.Order1;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.utils.Sales;
import com.tp.neo.model.utils.SalesObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
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
        AgentController agent = new AgentController();
        
        
        HttpSession session = request.getSession();
        SystemUser user = (SystemUser)session.getAttribute("user");
        
        if(user != null)
        {
            int userType = user.getSystemUserTypeId();
            request.setAttribute("userType", userType);
            request.setAttribute("agents",agent.listAgents());
        }
        
        
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
       
        SalesObject salesObj = this.processJsonData(request.getParameter("cartDataJson").toString());
        //this.createOrder(request, salesObj);
    }
    
    private SalesObject processJsonData(String json)
    {
        Gson gson = new GsonBuilder().create();
        System.out.println(json);
        //String str = "{sales:[{\"productName\":\"Kali Homes\",productId:1,productUnitName:\"2 Bedroom Duplex\",productUnitId:5,productQuanity:1,productAmount:1500000,amountUnit:1500000,amountTotalUnit:1500000,initialAmountPerUnit:250000,minInitialAmountSpan:250000,productMinimumInitialAmount:250000,amountLeft:1250000,payDurationPerUnit:\"24 months\",payDurationPerQuantity:\"24 months\",productMaximumDuration:5,monthlyPayPerUnit:250000,monthlyPayPerQuantity:250000,productMinimumMonthlyPayment:250000}]}";
        //Type collectionType = new TypeToken<ArrayList<SalesObject>>(){}.getType();
        SalesObject salesObj = gson.fromJson(json,SalesObject.class);
        
        ArrayList<Sales> sales = salesObj.sales;
        
        for(Sales s : sales) {
            System.out.println("Product Name : " + s.productName);
            System.out.println("Product Qty : " + s.productQuanity);
            System.out.println("Product Amount Per Unit " + s.amountUnit);
            System.out.println("Product Cost" + s.amountTotalUnit);
        }
        
        return salesObj;
    }
    
    
    private void createOrder(HttpServletRequest request, SalesObject sales)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Order1 order = new Order1();
    }   
    
    
    private void createSales(HttpServletRequest request, SalesObject salesObject, EntityManager em, Order1 order, Agent agent)
    {
        /**
         * Loop through each of the sales item
         * create a sale record, and get the sale id
         * create a new lodgement for the sale.
         **/
    }
    
    private void lodgePayment(HttpServletRequest request, EntityManager em, OrderItem saleItem, Agent agent)
    {
        
    }
    
//    private void createOrder(HttpServletRequest request, SalesObject sales)
//    {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
//        EntityManager em = emf.createEntityManager();
//        
//        HttpSession session = request.getSession();
//        SystemUser user = (SystemUser)session.getAttribute("user");
//        
//        Long customerId = Long.parseLong(request.getParameter("customer_id"));
//        Long agentId = user.getSystemUserId();
//        //int userType = user.getSystemUserTypeId();
//        
//        //Check if user is an Agent
//        
//        Order1 order = new Order1();
//        
//        Agent agent = em.find(Agent.class, new Long(1));
//        Customer customer  = em.find(Customer.class, customerId);
//        
//        //Prepare Order Entity
//        order.setAgentId(agent);
//        order.setCustomerId(customer);
//        
//        
//        
//        // Begin Transaction
//        em.getTransaction().begin();
//        
//        System.out.println("Transaction Begined");
//        
//        em.persist(order);
//        em.flush();
//        System.out.println("Transaction flushed");
//        this.insertSales(request, sales, em, order, agent);
//        System.out.println("About to commit");
//        em.getTransaction().commit();
//        
//    }
//    
//    private void insertSales(HttpServletRequest request, SalesObject salesObject, EntityManager em, Order1 order, Agent agent)
//    {
//        ArrayList<Sales> sales = salesObject.sales;
//        Date date = this.getDateTime();
//        
//        for(Sales sale : sales) {
//            
//            SaleItem saleItem = new SaleItem();
//            
//            long unitId = sale.productUnitId;
//            ProjectUnit projectUnit = em.find(ProjectUnit.class, unitId);
//            
//            
//            saleItem.setOrderId(order);
//            saleItem.setUnitId(projectUnit);
//            saleItem.setQuantity(sale.productQuanity);
//            saleItem.setInitialDep(sale.productMinimumInitialAmount);
//            saleItem.setDiscountAmt(projectUnit.getDiscount());
//            saleItem.setDiscountPercentage(projectUnit.getCommissionPercentage());
//            saleItem.setCreatedBy(agent.getAgentId());
//            saleItem.setCreatedDate(date);
//            
//            em.persist(saleItem);
//            em.flush();
//            this.lodgePayment(request, em, saleItem, agent);
//        }
//        
//    }
//    
//    
//    private void lodgePayment(HttpServletRequest request, EntityManager em, SaleItem saleItem, Agent agent)
//    {
//        Lodgement lodgement = new Lodgement();
//        Short paymentMethod = Short.parseShort(request.getParameter("paymentMethod"));
//        
//        System.out.println("Sale Id : " + saleItem.getSaleId());
//       
//        lodgement.setPaymentMode(paymentMethod);
//        lodgement.setCreatedDate(this.getDateTime());
//        lodgement.setCreatedBy(agent.getAgentId());
//        lodgement.setSale(saleItem);
//        
//        if(paymentMethod == 1)
//        {
//            lodgement.setBankName(request.getParameter("bankName"));
//            lodgement.setDepositorsName(request.getParameter("depositiorsName"));
//            lodgement.setTellerNo(request.getParameter("tellerNumber"));
//            lodgement.setAmount(Double.parseDouble(request.getParameter("tellerAmount")));
//            
//        }
//        else if(paymentMethod == 2)
//        {
//            lodgement.setAmount(Double.parseDouble(request.getParameter("cashAmount")));
//            
//        }
//        else
//        {
//            
//        }
//        
//        em.persist(lodgement);
//    }
    
    private Date getDateTime()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Date date = calendar.getTime();
        return date;
    }

}