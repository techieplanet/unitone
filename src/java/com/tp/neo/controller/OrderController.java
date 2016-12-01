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
import com.tp.neo.controller.helpers.CompanyAccountHelper;
import com.tp.neo.controller.helpers.NotificationsManager;
import com.tp.neo.controller.helpers.OrderManager;
import com.tp.neo.controller.helpers.OrderObjectWrapper;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.controller.helpers.SaleItemObject;
import com.tp.neo.controller.helpers.SaleItemObjectsList;
import com.tp.neo.model.CompanyAccount;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.utils.MailSender;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
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
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.swing.text.DateFormatter;
import javax.xml.bind.PropertyException;
/**
 *
 * @author John
 */
@MultipartConfig
@WebServlet(name = "OrderController", urlPatterns = {"/Order","/order"})
public class OrderController extends AppController {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String ORDER_ADMIN = "/views/order/admin.jsp"; 
    private static String ORDER_NEW = "/views/order/add.jsp";
    private final  String ORDER_APPROVAL = "/views/order/approval.jsp";
    private final String ORDER_NOTIFICATION_ROUTE = "/Order?action=notification&id=";
    private final String CART_CHECKOUT = "/views/order/ecommerce_order.jsp";
    
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
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String loggedIn =request.getParameter("loggedin") != null ? request.getParameter("loggedin") : "";
                
        if (action.equalsIgnoreCase("checkOut") && loggedIn.equalsIgnoreCase("no")){
               
               String viewFile = "/views/index/checkout.jsp";
               request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
               request.getRequestDispatcher(viewFile).forward(request, response);
               return;
        }
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new ProductOrder().getPermissionName(action), request, response)){
                    processGetRequest(request, response);
            }else{
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
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new ProductOrder().getPermissionName(action), request, response)){
                processPostRequest(request, response);
            }else{
                super.errorPageHandler("forbidden", request, response);
            }
        }
    }

     protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = ORDER_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        Long customerId = request.getParameter("customer") != null ? Long.parseLong(request.getParameter("customer")) : null;
        
        
        ProjectController project = new ProjectController();
        AgentController agent = new AgentController();
        
        
        //HttpSession session = request.getSession();
        SystemUser user = sessionUser;
        System.out.println("System User : " + user);
        
        request.setAttribute("userType", sessionUser.getSystemUserTypeId());
        request.setAttribute("agents",agent.listAgents());
       
        
        
        //Project listprojects = project.listProjects();
        if (action.equalsIgnoreCase("new")){
               viewFile = ORDER_NEW;
               request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
        }
        else if (action.equalsIgnoreCase("checkOut")){
               
               viewFile = CART_CHECKOUT;
               request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
               
        }
        else if(action.equalsIgnoreCase("approval")) {
            viewFile = ORDER_APPROVAL; 
            request.setAttribute("pendingOrders", listPendingOrders());
            request.setAttribute("singleOrderId",0);
            request.setAttribute("title","Order Approval");
        }
        else if(action.equals("notification")) {
            viewFile = ORDER_APPROVAL; 
            approveSingleOrder(request,response);
            request.setAttribute("title","Order Notification");
            
        }
        else if(action.equalsIgnoreCase("getOrder")){
            
            getOrder(request, response);
            return;
        }
        else if(action.equalsIgnoreCase("approved")){
            
            request.setAttribute("orders", listApprovedOrders());
            request.setAttribute("title","Approved Orders");
        }
        else if(action.equalsIgnoreCase("declined")){
            
            request.setAttribute("orders", listDeclinedOrders());
            request.setAttribute("title","Declined Orders");
        }
        else if(action.equalsIgnoreCase("processing")){
            
            request.setAttribute("orders", listProcessingOrders());
            request.setAttribute("title","Processing Orders");
        }
        else if(action.equalsIgnoreCase("current")){
            
            request.setAttribute("orders", listCurrentOrders());
            request.setAttribute("title","Current Paying Orders");
        }
        else if(action.equalsIgnoreCase("completed")){
            
            request.setAttribute("orders", listCompletedOrders());
            request.setAttribute("title","Completed Payment Orders");
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("list_orders")){
            viewFile = ORDER_ADMIN;
            request.setAttribute("orders", listOrders());
            request.setAttribute("title","Orders");
        }
        request.setAttribute("projects", project.listProjects());
        request.setAttribute("customers",listCustomers());
        
        if(sessionUser.getSystemUserTypeId() == 3){
            request.setAttribute("customerId",sessionUser.getSystemUserId());
        }else{
            request.setAttribute("customerId",customerId);
        }
        
        //Keep track of the sideBar
        request.setAttribute("sideNav", "Order");
        
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

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if(action.equals("approveOrder")){
            
            approveOrder(request,response,ORDER_APPROVAL);
            
        }else{
        
            initOrderProcess(request,response);
        }
    }
    
    public SaleItemObjectsList getCartData(HttpServletRequest request) {
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
            System.out.println("Product Qty : " + s.productQuantity);
            System.out.println("Product Amount Per Unit " + s.amountUnit);
            System.out.println("Product Cost" + s.amountTotalUnit);
        }
        
        return salesObj;
    }
    
    public void initOrderProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            
            Agent agent = null;
            Customer customer = null;
            
            
            Long customerId = null;
            
            if(request.getParameter("customer_id") != null){
                
                customerId = Long.parseLong(request.getParameter("customer_id"));
                
            }else{
                customerId = sessionUser.getSystemUserId();
            }
            
            customer  = em.find(Customer.class, customerId);
            
            if(sessionUser.getSystemUserTypeId() == 1){
                long agentId = Long.parseLong(request.getParameter("agent_id"));
                agent = em.find(Agent.class, agentId);
            }
            else if(sessionUser.getSystemUserTypeId() == 2)
            {
                agent = em.find(Agent.class, sessionUser.getSystemUserId());
            }
            else{
                
                agent = customer.getAgent();
            }
            
            System.out.println("Customer id :" + customer.getCustomerId() + ", Agent id: " + agent.getAgentId());
            
            SaleItemObjectsList saleItemObject = getCartData(request);
            
            //Get Session User
            SystemUser user  = sessionUser;
            
            List<OrderItem> orderItems = prepareOrderItem(saleItemObject, agent);
            Lodgement lodgement = prepareLodgement(getRequestParameters(request), agent);
            lodgement.setCustomer(customer);
            
            OrderManager orderManager = new OrderManager(user);
            ProductOrder productOrder = orderManager.processOrder(customer, lodgement, orderItems, request.getContextPath());
            
            if(productOrder != null){
                    if(productOrder.getId() != null){
                        
                    }
             }
            
            request.setAttribute("success", "Saved successfully");
            
            String viewFile = "/views/customer/success.jsp";
            
            if(sessionUser.getSystemUserTypeId() == 3){
                    if(lodgement.getPaymentMode() == 2){
                        
                        Date date = lodgement.getCreatedDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
                        String dateString = sdf.format(date);
                        
                        double vat = 0.00;
                        double gateWayCharge = 0.00;
                        Map map = getInvoicData(orderItems, vat, gateWayCharge);
                        
                        viewFile = "/views/customer/gateway.jsp";
                        request.getSession().setAttribute("productOrderInvoice", productOrder);
                        request.getSession().setAttribute("orderItemInvoice", orderItems);
                        request.getSession().setAttribute("transactionDate", dateString);
                        request.getSession().setAttribute("customerInvoice", customer);
                        request.getSession().setAttribute("totalInvoice", (Double)map.get("total"));
                        request.getSession().setAttribute("grandTotalInvoice", (Double)map.get("grandTotal"));
                        request.getSession().setAttribute("vatInvoice", vat);
                        request.getSession().setAttribute("gatewayChargeInvoice", gateWayCharge);
                        
                    }
                    else{
                        viewFile = "/views/customer/success.jsp";
                        request.setAttribute("customer",customer);
                    }
                }
            else{
                        viewFile = "/views/customer/success.jsp";
                        request.setAttribute("customer",customer);
            }
            
            em.close();
            emf.close();
            
            request.getRequestDispatcher(viewFile).forward(request, response);
            
        } catch (PropertyException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            
        }

    }
    
    /**
     * @param HttpServletRequest 
     * @return  Map 
     * This method returns the parameters of an HTTP request as a Map object 
     */
    public Map getRequestParameters(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        Enumeration params = request.getParameterNames();
        while(params.hasMoreElements())
        {
            String elem = params.nextElement().toString();
            map.put(elem, request.getParameter(elem));
        }
        
        return map;
    }
    
    public List<OrderItem> prepareOrderItem(SaleItemObjectsList salesItemObject, Agent agent){
        List<OrderItem> orderItemList = new ArrayList();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<SaleItemObject> salesItem = salesItemObject.sales;
        for(SaleItemObject saleItem : salesItem) {
            
            OrderItem orderItem = new OrderItem();
            
            short approval = 0;
            long unitId = saleItem.productUnitId;
            ProjectUnit projectUnit = em.find(ProjectUnit.class, unitId);
            
            orderItem.setQuantity(saleItem.productQuantity);
            orderItem.setInitialDep((double)(saleItem.productMinimumInitialAmount));
            orderItem.setDiscountAmt(projectUnit.getDiscount());
            orderItem.setDiscountPercentage(projectUnit.getDiscount());
            orderItem.setCreatedDate(getDateTime().getTime());
            if(sessionUser != null){
                orderItem.setCreatedBy(sessionUser.getSystemUserId()); 
            }
            orderItem.setApprovalStatus(approval);
            orderItem.setUnit(projectUnit);
            
            orderItemList.add(orderItem);
        }
        
        return orderItemList;
    }
    
    public Lodgement prepareLodgement(Map request, Agent agent) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Lodgement lodgement = new Lodgement();
        
        Short paymentMethod = Short.parseShort(request.get("paymentMethod").toString());
        
        CompanyAccount companyAccount = em.find(CompanyAccount.class, Integer.parseInt(request.get("companyAccount").toString()));
        
        lodgement.setPaymentMode(paymentMethod);
        lodgement.setLodgmentDate(this.getDateTime().getTime());
        lodgement.setCreatedDate(this.getDateTime().getTime());
        if(sessionUser != null) {
            lodgement.setCreatedBy(sessionUser.getSystemUserId());
            lodgement.setCreatedByUserType(Short.parseShort((sessionUser.getSystemUserTypeId()).toString()));
        }
        lodgement.setCompanyAccountId(companyAccount);
        lodgement.setApprovalStatus((short)0);
        
        if(paymentMethod == 1) {
            lodgement.setTransactionId(request.get("tellerNumber").toString());
            lodgement.setAmount(Double.parseDouble(request.get("tellerAmount").toString()));
            lodgement.setDepositorName(request.get("depositorsName").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
        }
        else if(paymentMethod == 2){
            lodgement.setAmount(Double.parseDouble(request.get("cardAmount").toString()));
        }
        else if(paymentMethod == 3){
            
            lodgement.setAmount(Double.parseDouble(request.get("cashAmount").toString()));
            
        }
        else if(paymentMethod == 4) {
            
            lodgement.setAmount(Double.parseDouble(request.get("transfer_amount").toString()));
            lodgement.setOriginAccountName(request.get("transfer_accountName").toString());
            lodgement.setOriginAccountNumber(request.get("transfer_accountNo").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
            
        }
        
        return lodgement;
    }
    
    private long getAgentId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SystemUser user = (SystemUser)session.getAttribute("user");
        Long agentId = user.getSystemUserId();
        
        return agentId;
    }
    
    private double getTotalOrderAmountPaid(HttpServletRequest request){
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
    }//    
    
    private Calendar getDateTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        return calendar;
    }
    
    public List<ProductOrder> listOrders() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("ProductOrder.findAll");
        
        //Check if user is an Agent, fetch orders made by the user
        
        if(sessionUser.getSystemUserTypeId() == 2){
            jplQuery = em.createNamedQuery("ProductOrder.findByAgent");
            jplQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            jplQuery = em.createNamedQuery("ProductOrder.findByCustomer");
            jplQuery.setParameter("customerId", (Customer)sessionUser);
        }
        
        
        List<ProductOrder> orderList = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        
        return orderList;
    }
    
    private List<ProductOrder> listApprovedOrders(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<ProductOrder> orders = null;
        
        Query query = em.createNamedQuery("ProductOrder.findByApprovalStatus");
        
        //check if user is an agent, so you can only show orders related to that agent
        if(sessionUser.getSystemUserTypeId() == 2){
            
            Agent agent = em.find(Agent.class, sessionUser.getSystemUserId());
            query = em.createNamedQuery("ProductOrder.findByApprovalStatusAgent");
            query.setParameter("agent", agent);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            query = em.createNamedQuery("ProductOrder.findByApprovalStatusCustomer");
            query.setParameter("customer", (Customer)sessionUser);
        }
        
        query.setParameter("approvalStatus", 2);
        
        orders = query.getResultList();
        
        return orders;
    }
    
     private List<ProductOrder> listDeclinedOrders(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<ProductOrder> orders = null;
        
        Query query = em.createNamedQuery("ProductOrder.findByApprovalStatus");
        
        //check if user is an agent, so you can only show orders related to that agent
        if(sessionUser.getSystemUserTypeId() == 2){
            
            Agent agent = em.find(Agent.class, sessionUser.getSystemUserId());
            query = em.createNamedQuery("ProductOrder.findByApprovalStatusAgent");
            query.setParameter("agent", agent);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            query = em.createNamedQuery("ProductOrder.findByApprovalStatusCustomer");
            query.setParameter("customer", (Customer)sessionUser);
        }
        
        query.setParameter("approvalStatus", 3);
        
        orders = query.getResultList();
        
        return orders;
    }
     
     private List<ProductOrder> listProcessingOrders(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        List<ProductOrder> orders = null;
        
        Query query = em.createNamedQuery("ProductOrder.findByProcessing");
        
        //check if user is an agent, so you can only show orders related to that agent
        if(sessionUser.getSystemUserTypeId() == 2){
            
            Agent agent = em.find(Agent.class, sessionUser.getSystemUserId());
            query = em.createNamedQuery("ProductOrder.findByProcessingAgent");
            query.setParameter("agent", agent);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            query = em.createNamedQuery("ProductOrder.findByProcessingCustomer");
            query.setParameter("customer", (Customer)sessionUser);
        }
        
        
        orders = query.getResultList();
        
        return orders;
    } 
    
    public List<ProductOrder> listCurrentOrders() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("ProductOrder.findByCurrentPaying");
        
        
        if(sessionUser.getSystemUserTypeId() == 2){
            
            jplQuery = em.createNamedQuery("ProductOrder.findByAgentCurrentPaying");
            jplQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            jplQuery = em.createNamedQuery("ProductOrder.findByCustomerCurrentPaying");
            jplQuery.setParameter("customer", (Customer)sessionUser);
        }
        
        
        List<ProductOrder> orderList = jplQuery.getResultList();
        
        
        
        return orderList;
    }
    
    
    public List<ProductOrder> listCompletedOrders() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("ProductOrder.findByCompleted");
        
        //Check if user is an Agent, fetch orders made by the user
        
        if(sessionUser.getSystemUserTypeId() == 2){
            
            jplQuery = em.createNamedQuery("ProductOrder.findByAgentCompleted");
            jplQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            jplQuery = em.createNamedQuery("ProductOrder.findByCustomerCompleted");
            jplQuery.setParameter("customer", (Customer)sessionUser);
        }
        
        
        List<ProductOrder> orderList = jplQuery.getResultList();
        
        
        
        return orderList;
    }
    
     public List<OrderObjectWrapper> listPendingOrders() throws IOException {
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
//        Query jplQuery = em.createNamedQuery("ProductOrder.findByNotApprovalStatus");
//        Short s = 3; // Get Orders that are not declined approved
//        jplQuery.setParameter("approvalStatus", s);
//        
//        System.out.println("Query : " + jplQuery.toString());
//        List<ProductOrder> orderResultSet = jplQuery.getResultList();

        String q = "SELECT p FROM ProductOrder p "

                    + "JOIN p.orderItemCollection q "

                    + "JOIN q.lodgementItemCollection r "

                    + "WHERE r.approvalStatus = :aps "

                    + "GROUP BY p.id "

                    + "ORDER  BY p.id";

        TypedQuery<ProductOrder> orders =  em.createQuery(q, ProductOrder.class).setParameter("aps", 1);

        List<ProductOrder> ordersList = orders.getResultList();
        
         System.out.println("Product Count : " + ordersList.size());
        
        List<OrderObjectWrapper> orderWrapperList = new ArrayList();
        
        for(ProductOrder order : ordersList)
        {
            List<OrderItem> orderItems = getSalesByOrder(order);
            if(orderItems.size() < 1){
                continue;
            }
            OrderObjectWrapper ordersWrapper = new OrderObjectWrapper(order, orderItems);
            orderWrapperList.add(ordersWrapper);
        }
        
        
       return orderWrapperList;
        
    }
    
    private List<OrderItem> getSalesByOrder(ProductOrder order) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("OrderItem.findByOrderAndUattendedItem");
        
        short unattended = 0;
        
        jplQuery.setParameter("orderId", order);
        jplQuery.setParameter("approvalStatus", unattended);
        
        List<OrderItem> resultSet = jplQuery.getResultList();
        
       
        return resultSet;
    }
    
   
    
    private void approveOrder(HttpServletRequest request,HttpServletResponse response, String viewFile){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        try{
        
        em.getTransaction().begin();
        
        String[] approveOrders = request.getParameterValues("order-item-approve");
        String[] declineOrders = request.getParameterValues("order-item-decline");
        
        List<OrderItem> orderItemList = new ArrayList();
        Customer customer = null;
        ProductOrder productOrder = null;
        
        if(approveOrders != null) {
            for(int i=0; i < approveOrders.length;i++){
                
                long id = Long.parseLong(approveOrders[i]);
                OrderItem orderItem = em.find(OrderItem.class, id);
                short s = 1;
                orderItem.setApprovalStatus(s);
                em.merge(orderItem);
                em.persist(orderItem);
                orderItemList.add(orderItem);
              
            }
        }
        
        if(declineOrders != null) {
            for(int i=0; i < declineOrders.length; i++){
                
                long id = Long.parseLong(declineOrders[i]);
                OrderItem orderItem = em.find(OrderItem.class, id);
                short s = 2;
                orderItem.setApprovalStatus(s);
                em.merge(orderItem);
                orderItemList.add(orderItem);
            }
        }
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        
        if(orderItemList.size() > 0){
            
            OrderItem item = orderItemList.get(0);
            customer = item.getOrder().getCustomer();
            productOrder = item.getOrder();
            
            /***********************************************************************************************/
            Notification notification = new Notification(); //this object is plain. Values should go into it
            /***********************************************************************************************/
            
            OrderManager orderManager = new OrderManager(sessionUser);
            orderManager.processOrderApproval(productOrder, orderItemList, customer, notification);
            System.out.println("Successful");
            
        }
        
            redirectToPendingOrder(request, response, viewFile);
    }
    catch(PropertyException pe){
        pe.printStackTrace();
    }
    catch(RollbackException rollBack){
        em.getTransaction().rollback();
    }
   }
    
    private void approveSingleOrder(HttpServletRequest request,HttpServletResponse response){
        
        try {
            EntityManagerFactory emf =  Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            
            long productOrderId = Long.parseLong(request.getParameter("id"));
            ProductOrder productOrder = em.find(ProductOrder.class, productOrderId);
            
            request.setAttribute("pendingOrders", listPendingOrders());
            request.setAttribute("singleOrderId",productOrder.getId());
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   private void redirectToPendingOrder(HttpServletRequest request,HttpServletResponse response, String viewFile){
       
        try {
            
            request.setAttribute("pendingOrders", listPendingOrders());
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
            
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   private void getOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
       
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
       EntityManager em = emf.createEntityManager();
       
       Gson gson = new GsonBuilder().create();
       
       Long orderId = Long.parseLong(request.getParameter("order_id"));
       
       ProductOrder productOrder = em.find(ProductOrder.class, orderId);
       List<OrderItem> orderItemList = (List)productOrder.getOrderItemCollection();
       
       List<Map> orderItemMap = new ArrayList();
      
       
       for(OrderItem item : orderItemList){
           Map<String,String> map = new HashMap(); 
           Double total_paid = getTotalItemPaidAmount((List)item.getLodgementItemCollection());
           
           map.put("id", item.getId().toString());
           map.put("quantity", item.getQuantity().toString());
           map.put("initialDeposit", String.format("%.2f",item.getInitialDep()));
           map.put("cpu", String.format("%.2f",item.getUnit().getCpu()));
           map.put("title", item.getUnit().getTitle());
           map.put("discount", getOrderItemDiscount(item.getUnit().getDiscount(), item.getUnit().getCpu(), item.getQuantity()));
           map.put("total_paid", String.format("%.2f",total_paid));
           map.put("project_name", item.getUnit().getProject().getName());
           map.put("balance",getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), total_paid));
           map.put("completionDate",getCompletionDate(item, total_paid));
           
           orderItemMap.add(map);
       }
       
       String payLoad = gson.toJson(orderItemMap);
       
       System.out.println("Order : " + payLoad);
       
       response.setContentType("text/plain");
       response.setCharacterEncoding("UTF-8");
       response.getWriter().write(payLoad);
       response.getWriter().flush();
       response.getWriter().close();
   }
   
   /**
    * 
    * @param List<lodgementItem>
    * @return Double Total amount paid for an OrderItem
    * This method returns the total amount paid for an item,
    * By looping through a collection of LodgementItem
    */
   private Double getTotalItemPaidAmount(List<LodgementItem> lodgementItem){
       
       double totalAmount = 0.00;
       
       for(LodgementItem item : lodgementItem){
           totalAmount += item.getAmount();
       }
       
       return totalAmount;
   }
   
   private String getOrderItemBalance(double amtPayable, int qty, double amountPaid){
       
       String amt = String.format("%.2f", (Double)((amtPayable * qty) - amountPaid));
       return amt;
   }
   
   private String getOrderItemDiscount(double discountPercent, double cpu, int qty){
       
       String amt = String.format("%.2f", (Double)((discountPercent / 100 ) * (cpu * qty)));
       return amt;
   }
    
   private String getCompletionDate(OrderItem item, double total_paid){
       
       //Get monthly Pay
       double mortgage = item.getUnit().getMonthlyPay();
       double balance = Double.parseDouble(getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), total_paid));
       int qty = item.getQuantity();
       
       double months = Math.ceil(balance / (mortgage * qty));
       Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
       
       if(months <= 0){
           
           Date d = item.getOrder().getModifiedDate();
          cal.setTime(d);
       }
       else{
           
         cal.add(Calendar.MONTH, (int)months);
       }
       
       System.out.println("Month = " + (int)months);
       
       SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
       
       String date = sdf.format(cal.getTime());
       
       return date;
   }
   
   private List<Customer> listCustomers(){
        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        Query jpqlQuery  = em.createNamedQuery("Customer.findByDeleted");
        
        if(sessionUser.getSystemUserTypeId() == 2){
            
            jpqlQuery  = em.createNamedQuery("Customer.findByAgent");
            jpqlQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(sessionUser.getSystemUserTypeId() == 3){
            
            jpqlQuery  = em.createNamedQuery("Customer.findByCustomerId");
            jpqlQuery.setParameter("customerId", sessionUser.getSystemUserId());
        }
        
        jpqlQuery.setParameter("deleted", 0);
        List<Customer> customerList = jpqlQuery.getResultList();

        return customerList;
    }
   
   
   private Map getInvoicData(List<OrderItem> items, double vat, double gateWayCharge){
        
        double total = 0.00;
        double grandTotal = 0.00;
        
        for(OrderItem item : items){
            total += item.getInitialDep();
        }
        
        grandTotal = total + vat + gateWayCharge;
        
        Map<String, Double> map = new HashMap();
        map.put("total", total);
        map.put("grandTotal", grandTotal);
        
        return map;
    }
   
    
    
   
}
