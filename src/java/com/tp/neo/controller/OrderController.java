/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tp.neo.controller.components.MailSender;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.Order1;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.SaleItem;
import com.tp.neo.controller.helpers.SaleItemObject;
import com.tp.neo.controller.helpers.SaleItemObjectsList;
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
import javax.xml.bind.PropertyException;
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
    
    private double totalAmountDeposited = 0;
    
    
    
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
        SystemUser user = null;
        
        if(session.getAttribute("user") != null && !session.getAttribute("user").equals(""))
        {
           user = (SystemUser)session.getAttribute("user");
        }
        
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
            request.setAttribute("orders", listOrders());
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
       
        initOrderProcess(request);
    }
    
    public SaleItemObjectsList getCartData(HttpServletRequest request)
    {
        SaleItemObjectsList saleObj = this.processJsonData(request.getParameter("cartDataJson").toString());
        return saleObj;
    }
    
    private SaleItemObjectsList processJsonData(String json) {
        Gson gson = new GsonBuilder().create();
        System.out.println(json);
        //String str = "{sales:[{\"productName\":\"Kali Homes\",productId:1,productUnitName:\"2 Bedroom Duplex\",productUnitId:5,productQuanity:1,productAmount:1500000,amountUnit:1500000,amountTotalUnit:1500000,initialAmountPerUnit:250000,minInitialAmountSpan:250000,productMinimumInitialAmount:250000,amountLeft:1250000,payDurationPerUnit:\"24 months\",payDurationPerQuantity:\"24 months\",productMaximumDuration:5,monthlyPayPerUnit:250000,monthlyPayPerQuantity:250000,productMinimumMonthlyPayment:250000}]}";
        //Type collectionType = new TypeToken<ArrayList<SalesObject>>(){}.getType();
        SaleItemObjectsList salesObj = gson.fromJson(json,SaleItemObjectsList.class);
        
        ArrayList<SaleItemObject> sales = salesObj.sales;
        
        for(SaleItemObject s : sales) {
            System.out.println("Product Name : " + s.productName);
            System.out.println("Product Qty : " + s.productQuanity);
            System.out.println("Product Amount Per Unit " + s.amountUnit);
            System.out.println("Product Cost" + s.amountTotalUnit);
        }
        
        return salesObj;
    }
    
    public void initOrderProcess(HttpServletRequest request) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Agent agent = null;
        Customer customer = null;
        
        Long customerId = Long.parseLong(request.getParameter("customer_id"));
        customer  = em.find(Customer.class, customerId);
        
        long agentId = Long.parseLong(request.getParameter("agent_id"));
        agent = em.find(Agent.class, agentId);
        
        SaleItemObjectsList saleItemObject = getCartData(request);
        
        createOrder(customer,agent,saleItemObject,getRequestParameters(request));
        
    }
    
    /**
     * @param HttpServletRequest 
     * @return  Map 
     * This method returns the parameters of an HTTP request as a Map object 
     */
    public Map getRequestParameters(HttpServletRequest request)
    {
        Map<String, String> map = new HashMap();
        Enumeration params = request.getParameterNames();
        while(params.hasMoreElements())
        {
            String elem = params.nextElement().toString();
            map.put(elem, request.getParameter(elem));
        }
        
        return map;
    }
    
    public void createOrder(Customer customer, Agent agent, SaleItemObjectsList saleItemObject,Map request)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        try {
                
                Order1 order = new Order1();

                //Prepare Order Entity
                order.setAgentId(agent);
                order.setCustomerId(customer);
               
                em.getTransaction().begin();
              
                em.persist(order);
                em.flush(); 
                
                createSaleItems(request, saleItemObject, em, order, agent);

                em.getTransaction().commit();

                creditCustomerAccount(customer, totalAmountDeposited);
                CreateNewOrderAlert(order);
        }
        catch(Exception ex){
            
            em.getTransaction().rollback();
            
            System.out.println("Exception NeoForce: " + ex.getMessage());
            ex.printStackTrace();
        }
    }   
    
    
    private void createSaleItems(Map request, SaleItemObjectsList salesObject, EntityManager em, Order1 order, Agent agent)
    {
        /**
         * Loop through each of the sales item
         * create a sale record, and get the sale id
         * create a new lodgement for the sale.
         **/
        
        ArrayList<SaleItemObject> saleItems = salesObject.sales;
        Date date = this.getDateTime();
        
        double totalAmountPaid = 0;
        for(SaleItemObject saleItem : saleItems) 
        {
            
            SaleItem saleItemObject = new SaleItem();
            
            long unitId = saleItem.productUnitId;
            ProjectUnit projectUnit = em.find(ProjectUnit.class, unitId);
            
            
            saleItemObject.setOrderId(order);
            saleItemObject.setUnitId(projectUnit);
            saleItemObject.setQuantity(saleItem.productQuanity);
            saleItemObject.setInitialDep(saleItem.productMinimumInitialAmount);
            saleItemObject.setDiscountAmt(projectUnit.getDiscount());
            saleItemObject.setDiscountPercentage(projectUnit.getCommissionPercentage());
            saleItemObject.setCreatedBy(agent.getAgentId());
            saleItemObject.setCreatedDate(date);
            
            
            
            em.persist(saleItemObject);
            em.flush();
            System.out.println("Sale Id : " + saleItemObject.getSaleId());
            
            lodgePayment(request, em, saleItemObject, agent, saleItem.productMinimumInitialAmount);
        }
    }
    
     private void lodgePayment(Map request, EntityManager em, SaleItem saleItemObj, Agent agent, double amtPaid)
    {
        
        Lodgement lodgement = new Lodgement();
        System.out.println("From Lodgement, Sale ID : " + saleItemObj.getSaleId());
        SaleItem saleItem = em.find(SaleItem.class, saleItemObj.getSaleId());
        System.out.println("Payment method : " + request.get("paymentMethod").toString());
        Short paymentMethod = Short.parseShort(request.get("paymentMethod").toString());
       
        lodgement.setPaymentMode(paymentMethod);
        lodgement.setCreatedDate(this.getDateTime());
        lodgement.setCreatedBy(agent.getAgentId());
        lodgement.setSale(saleItem); 
        lodgement.setAmount(amtPaid);
        
        if(paymentMethod == 1)
        {
            lodgement.setBankName(request.get("bankName").toString());
            lodgement.setDepositorsName(request.get("depositorsName").toString());
            lodgement.setTellerNo(request.get("tellerNumber").toString());
//            lodgement.setAmount(Double.parseDouble(request.getParameter("tellerAmount")));
            
        }
        else if(paymentMethod == 3)
        {
//            lodgement.setAmount(Double.parseDouble(request.getParameter("cashAmount")));
            
        }
        else if(paymentMethod == 4)
        {
            lodgement.setBankName(request.get("transfer_bankName").toString());
            lodgement.setDepositorsName(request.get("transfer_depositiorsName").toString());
            lodgement.setTellerNo(request.get("transfer_transactionId").toString());
//            lodgement.setAmount(Double.parseDouble(request.getParameter("transfer_amount")));
        }
        
        em.persist(lodgement);
        em.flush();
    }
    
    
    private void creditCustomerAccount(Customer customer, double amount) {
        
    }
    
    private void CreateNewOrderAlert(Order1 order) {
        
    }
    
    private void sendNewOrderEmail(Order1 order, Customer customer) {
        
        
        MailSender mail = new MailSender();
        
        Agent agent = customer.getAgentId();
        
        String msgSubject = "New Order Request";
        String msgBody = "";
        
        String customerEmail = customer.getEmail();
        String agentEmail = agent.getEmail();
        String adminEmail = "admin@tplocalhost.com";
        
        mail.sendHtmlEmail(customerEmail, adminEmail, msgSubject, msgBody);
        mail.sendHtmlEmail(agentEmail, adminEmail, msgSubject, msgBody);
        mail.sendHtmlEmail(adminEmail, adminEmail, msgSubject, msgBody);
        
    }
    
    private long getAgentId(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        SystemUser user = (SystemUser)session.getAttribute("user");
        Long agentId = user.getSystemUserId();
        
        return agentId;
    }
    
    private double getTotalOrderAmountPaid(HttpServletRequest request)
    {
        double totalOrderAmountPaid = 0;
        
        if(request.getParameter("tellerAmount") != null & request.getParameter("tellerAmount") != "")
        {
            totalOrderAmountPaid =  Double.parseDouble(request.getParameter("tellerAmount"));
        }
        else if(request.getParameter("cashAmount") != null && !request.getParameter("cashAmount").equals("")) {
            totalOrderAmountPaid =  Double.parseDouble(request.getParameter("cashAmount"));
        }
        else if(request.getParameter("transfer_amount") != null && !request.getParameter("transfer_amount").equals("")) {
            totalOrderAmountPaid =  Double.parseDouble(request.getParameter("transfer_amount"));
        }
        
        return totalOrderAmountPaid;
    }
//    
    
    private Date getDateTime()
    {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Date date = calendar.getTime();
        return date;
    }
    
    public List<Order1> listOrders()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Order1.findAll");
        List<Order1> orderList = jplQuery.getResultList();
        
        
        
        return orderList;
    }

}
