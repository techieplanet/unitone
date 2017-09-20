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
import com.tp.neo.controller.helpers.OrderItemHelper;
import com.tp.neo.controller.helpers.OrderItemObjectsList;
import com.tp.neo.controller.helpers.OrderManager;
import com.tp.neo.controller.helpers.OrderObjectWrapper;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.plugins.LoyaltyHistory;
import com.tp.neo.model.utils.FileUploader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        //System.out.println("System User : " + user);
        
        request.setAttribute("userType", sessionUser.getSystemUserTypeId());
        request.setAttribute("agents",agent.listAgents());
       
        String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
        request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
        
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
            action = "";
        }
        
        request.setAttribute("projects", project.listProjects());
        request.setAttribute("customers",listCustomers());
        
        if(sessionUser.getSystemUserTypeId() == 3){
            request.setAttribute("customerId",sessionUser.getSystemUserId());
        }else{
            request.setAttribute("customerId",customerId);
        }
        
        
        String imageAccessDirPathCustomer = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
        request.setAttribute("customerImageAccessDir", imageAccessDirPathCustomer + "/customers");
        request.setAttribute("customerKinImageAccessDir", imageAccessDirPathCustomer + "/customers/customerkins");
        
        //Keep track of the sideBar
        request.setAttribute("sideNav", "Order");
        request.setAttribute("sideNavAction",action);
        
        //Get Available Plugins
        HttpSession session = request.getSession();
        Map<String, Plugin> plugins = (Map)session.getAttribute("availablePlugins");
        request.setAttribute("plugins",plugins);
        
        double pointToCurrency = 0;
        
        if(plugins.containsKey("loyalty")){
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, String>>(){}.getType();
            Map<String, String> loyaltySettingsMap = gson.fromJson(plugins.get("loyalty").getSettings(), mapType);
            pointToCurrency =  Double.parseDouble(loyaltySettingsMap.get("reward_value"));
        }
        request.setAttribute("pointToCurrency", pointToCurrency);
        
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

    
    
    /**
     * This method processes New Orders
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
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
                long agentId = !request.getParameter("agent_id").equalsIgnoreCase("") ? Long.parseLong(request.getParameter("agent_id")) : customer.getAgent().getAgentId();
                agent = em.find(Agent.class, agentId);
            }
            else if(sessionUser.getSystemUserTypeId() == 2)
            {
                agent = em.find(Agent.class, sessionUser.getSystemUserId());
            }
            else{
                
                agent = customer.getAgent();
            }
            
            //Get Available Plugins
            HttpSession session = request.getSession();
            Map<String, Plugin> plugins = (Map)session.getAttribute("availablePlugins");
            
            OrderManager orderManager = new OrderManager(sessionUser);
            
            if(plugins.containsKey("loyalty")){
                orderManager = new OrderManager(sessionUser,plugins);
            }
            
            OrderItemObjectsList orderItemObject = orderManager.getCartData(request.getParameter("cartDataJson").toString());
           Map<String , String> requestParams = getRequestParameters(request);
            
            
            List<OrderItem> orderItems = orderManager.prepareOrderItem(orderItemObject, agent);
            Lodgement lodgement = orderManager.prepareLodgement(requestParams, agent);
            
            StringBuilder eMsg = new StringBuilder();
            boolean valid = validateOrder(orderItems , lodgement , eMsg);
            if(!valid)
            {
                sendErrorMessage(request, response , eMsg.toString());
                return;
                
            }
            
            if(plugins.containsKey("loyalty")){
                lodgement = orderManager.setLodgementRewardPoint(lodgement, orderItems);
            }
            else{
                lodgement.setRewardAmount(new Double(0));
            }
            
            lodgement.setCustomer(customer);
            
            ProductOrder productOrder = orderManager.processOrder(customer, lodgement, orderItems, request.getContextPath());
            
            if(productOrder != null){
                    if(productOrder.getId() != null){
                        
                    }
             }
            
            request.setAttribute("success", "Saved successfully");
            
            String viewFile = "/views/customer/success.jsp";
            
            if(sessionUser.getSystemUserTypeId() == 3){
                    if(lodgement.getPaymentMode() == 2){
                        
                        List<LodgementItem> LItems = (List)lodgement.getLodgementItemCollection();
                        Date date = lodgement.getCreatedDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
                        String dateString = sdf.format(date);
                        
                        double vat = 0.00;
                        double gateWayCharge = 0.00;
                        Map map = getInvoicData(LItems, vat, gateWayCharge);
                        
                        viewFile = "/views/customer/gateway.jsp";
                        request.getSession().setAttribute("productOrderInvoice", productOrder);
                        request.getSession().setAttribute("orderItemInvoice",   LItems);
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
//        //System.out.println("Query : " + jplQuery.toString());
//        List<ProductOrder> orderResultSet = jplQuery.getResultList();

        String q = "SELECT p FROM ProductOrder p "

                    + "JOIN p.orderItemCollection q "

                    + "JOIN q.lodgementItemCollection r "

                    + "WHERE r.approvalStatus = :aps "

                    + "GROUP BY p.id "

                    + "ORDER  BY p.id";

        TypedQuery<ProductOrder> orders =  em.createQuery(q, ProductOrder.class).setParameter("aps", 1);
        

        List<ProductOrder> ordersList = orders.getResultList();
        
        
        
        List<OrderObjectWrapper> orderWrapperList = new ArrayList();
        
        for(ProductOrder order : ordersList)
        {
            //Checking for orders that has unapproved/undeclined orderitem in them
            List<OrderItem> orderItems = getOrderItemByOrder(order);
            if(orderItems.size() < 1){
                continue;
            }
            OrderObjectWrapper ordersWrapper = new OrderObjectWrapper(order, orderItems);
            orderWrapperList.add(ordersWrapper);
        }
        
         //System.out.println("ObjectWrappper : " + orderWrapperList.size()); 
        
         
         em.close();
         emf.close();
         
       return orderWrapperList;
        
    }
    
    private List<OrderItem> getOrderItemByOrder(ProductOrder order) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("OrderItem.findByOrderAndUattendedItem");
        
        short unattended = 0;
        
        jplQuery.setParameter("orderId", order);
        jplQuery.setParameter("approvalStatus", unattended);
        
        List<OrderItem> resultSet = jplQuery.getResultList();
        
        //System.out.println("Order items count " + resultSet.size() + ", for order " + order.getId());
       
        return resultSet;
    }
    
   
    
    private void approveOrder(HttpServletRequest request,HttpServletResponse response, String viewFile){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        HttpSession session = request.getSession();
        HashMap<String,Plugin> plugins = (HashMap)session.getAttribute("availablePlugins");
        
        try{
        
        em.getTransaction().begin();
        
        String[] approveOrders = request.getParameterValues("order-item-approve");
        String[] declineOrders = request.getParameterValues("order-item-decline");
        
        List<OrderItem> orderItemList = new ArrayList();
        Customer customer = null;
        ProductOrder productOrder = null;
        Date date = new Date(System.currentTimeMillis());
        
        if(approveOrders != null) {
            for(int i=0; i < approveOrders.length;i++){
                
                long id = Long.parseLong(approveOrders[i]);
                OrderItem orderItem = em.find(OrderItem.class, id);
                short s = 1;
                orderItem.setApprovalStatus(s);
                //em.persist(orderItem);
                orderItemList.add(orderItem);
                
                //Lets make a Loyalty history entry 
                //This entry will have the number of loyalty that the customer 
                //order
               if(plugins != null)
                if(plugins.containsKey("loyalty")){
                    customer = orderItem.getOrder().getCustomer();
                    
                    int quantity = orderItem.getQuantity();
                    
                    for(int x = 0 ; x < quantity ; x ++ )
                    {
                    LoyaltyHistory lH = new LoyaltyHistory();
                    lH.setCustomerId(customer);
                    lH.setRewardPoints(orderItem.getUnit().getRewardPoints());
                    lH.setType((short)1);
                    lH.setCreatedDate(date);
                    em.persist(lH);
                    }
                }
              
            }
        }
        
        if(declineOrders != null) {
            for(int i=0; i < declineOrders.length; i++){
                
                long id = Long.parseLong(declineOrders[i]);
                OrderItem orderItem = em.find(OrderItem.class, id);
                short s = 2;
                orderItem.setApprovalStatus(s);
                //em.persist(orderItem);
                orderItemList.add(orderItem);
            }
        }
        
        
        if(orderItemList.size() > 0){
            
            OrderItem item = orderItemList.get(0);
            customer = item.getOrder().getCustomer();
            productOrder = item.getOrder();
            
            Query jpQl = em.createNamedQuery("Notification.findByRemoteId");
            jpQl.setParameter("remoteId", productOrder.getId());
            jpQl.setParameter("typeTitle","Order");

            Notification notification = (Notification)jpQl.getResultList().get(0);
            
            OrderManager orderManager = new OrderManager(sessionUser);
            
            if(plugins.containsKey("loyalty")){
                orderManager = new OrderManager(sessionUser, plugins);
            }
            orderManager.processOrderApproval(productOrder, orderItemList, customer, notification);
            //System.out.println("Successful");
            
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
        
        redirectToPendingOrder(request, response, viewFile);
    }
    catch(PropertyException pe){
        pe.printStackTrace();
    }
    catch(RollbackException rollBack){
        if(em.getTransaction().isActive())
        em.getTransaction().rollback();
        em.close();
        emf.close();
    }
    catch(Exception e){
        em.getTransaction().rollback();
        em.close();
        emf.close();
    }
   }
    
    private void approveSingleOrder(HttpServletRequest request,HttpServletResponse response){
        
        try {
            EntityManagerFactory emf =  Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            emf.getCache().evictAll();
            
            long productOrderId = Long.parseLong(request.getParameter("id"));
            ProductOrder productOrder = em.find(ProductOrder.class, productOrderId);
            
            request.setAttribute("pendingOrders", listPendingOrders());
            if(productOrder != null)
            request.setAttribute("singleOrderId",productOrder.getId());
            
            
            em.close();
            emf.close();
            
        } catch (IOException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   private void redirectToPendingOrder(HttpServletRequest request,HttpServletResponse response, String viewFile){
       
        try {
            
//            request.setAttribute("pendingOrders", listPendingOrders());
//            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
//            dispatcher.forward(request, response);
            AppController.doRedirection(request, response, "/Order?action=approval");
            
        } catch (IOException ex) {
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
       
       OrderItemHelper itemHelper = new OrderItemHelper();
       
       for(OrderItem item : orderItemList){
           Map<String,String> map = new HashMap(); 
           Double total_paid = itemHelper.getTotalItemPaidAmount((List)item.getLodgementItemCollection());
           
           map.put("id", item.getId().toString());
           map.put("quantity", item.getQuantity().toString());
           map.put("initialDeposit", String.format("%.2f",item.getInitialDep()));
           map.put("cpu", String.format("%.2f",item.getUnit().getCpu()));
           map.put("title", item.getUnit().getTitle());
           map.put("discount", itemHelper.getOrderItemDiscount(item.getUnit().getDiscount(), item.getUnit().getCpu(), item.getQuantity()));
           map.put("total_paid", String.format("%.2f",total_paid));
           map.put("project_name", item.getUnit().getProject().getName());
           map.put("balance",itemHelper.getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), total_paid));
           map.put("completionDate",itemHelper.getCompletionDate(item, total_paid));
           
           orderItemMap.add(map);
       }
       
       String payLoad = gson.toJson(orderItemMap);
       
       //System.out.println("Order : " + payLoad);
       
       response.setContentType("text/plain");
       response.setCharacterEncoding("UTF-8");
       response.getWriter().write(payLoad);
       response.getWriter().flush();
       response.getWriter().close();
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
   
   
   private Map getInvoicData(List<LodgementItem> items, double vat, double gateWayCharge){
        
        double total = 0.00;
        double grandTotal = 0.00;
        
        for(LodgementItem LI : items){
            double rewardAmount = LI.getRewardAmount() != null ? LI.getRewardAmount() : 0;
            total += LI.getAmount() + rewardAmount;
        }
        
        grandTotal = total + vat + gateWayCharge;
        
        Map<String, Double> map = new HashMap();
        map.put("total", total);
        map.put("grandTotal", grandTotal);
        
        return map;
    }
   
   private boolean validateOrder(List<OrderItem> orderItems , Lodgement lodgement, StringBuilder errorMSG){
      
       //Validating if the initial payment specified by user is lesser that what is required
       Double customerTotalInitialPayment = 0.0;
       
       for(OrderItem item : orderItems)
       {
          customerTotalInitialPayment += item.getInitialDep();
          if(item.getInitialDep() < item.getUnit().getLeastInitDep())
          {
              errorMSG.append("One or more Order have an Invalid Initial deposit amount");
              return false;
          }
       }
       
       if(lodgement.getAmount() < customerTotalInitialPayment)
       {
           errorMSG.append("The Lodgement Amount is invalid");
           return false;
       }
       
       
      return true;
  }
   
   private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response , String msg) throws IOException{
       response.sendError(403, msg+ "---Go back <a href='" + request.getHeader("referer") + "'>Previous Page</a>");
   }
}
