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
import com.tp.neo.model.Notification;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.utils.MailSender;
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
import javax.persistence.RollbackException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
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
               request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
        }
        
        else if(action.equalsIgnoreCase("approval")) {
            viewFile = ORDER_APPROVAL; 
            request.setAttribute("pendingOrders", listPendingOrders());
            request.setAttribute("singleOrderId",0);
        }
        else if(action.equals("notification")) {
            viewFile = ORDER_APPROVAL; 
            approveSingleOrder(request,response);
            
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
        
        String action = request.getParameter("action");
        
        if(action.equals("approveOrder")){
            
            approveOrder(request,response,ORDER_APPROVAL);
            
        }else{
        
            initOrderProcess(request);
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
            System.out.println("Product Qty : " + s.productQuanity);
            System.out.println("Product Amount Per Unit " + s.amountUnit);
            System.out.println("Product Cost" + s.amountTotalUnit);
        }
        
        return salesObj;
    }
    
    public void initOrderProcess(HttpServletRequest request)  {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            
            Agent agent = null;
            Customer customer = null;
            
            Long customerId = Long.parseLong(request.getParameter("customer_id"));
            customer  = em.find(Customer.class, customerId);
            
            long agentId = Long.parseLong(request.getParameter("agent_id"));
            agent = em.find(Agent.class, agentId);
            
            System.out.println("Customer id :" + customer.getCustomerId() + ", Agent id: " + agent.getAgentId());
            
            SaleItemObjectsList saleItemObject = getCartData(request);
            
            //Get Session User
            HttpSession session = request.getSession();
            SystemUser user  = (SystemUser) session.getAttribute("user");
            
            List<OrderItem> orderItems = prepareOrderItem(saleItemObject, agent);
            Lodgement lodgement = prepareLodgement(getRequestParameters(request), agent);
            
            OrderManager orderManager = new OrderManager(user);
            ProductOrder productOrder = orderManager.processOrder(customer, lodgement, orderItems, request.getContextPath());
            
            if(productOrder != null){
                    if(productOrder.getId() != null){
                        
                    }
             }
            
        } catch (PropertyException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
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
            
            orderItem.setQuantity(saleItem.productQuanity);
            orderItem.setInitialDep((double)(saleItem.productMinimumInitialAmount));
            orderItem.setDiscountAmt(projectUnit.getDiscount());
            orderItem.setDiscountPercentage(projectUnit.getDiscount());
            orderItem.setCreatedDate(getDateTime().getTime());
            orderItem.setCreatedBy(agent.getAgentId());
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
        lodgement.setCreatedDate(this.getDateTime().getTime());
        lodgement.setCreatedBy(agent.getAgentId());
        lodgement.setCompanyAccountId(companyAccount);
        lodgement.setApprovalStatus((short)0);
        
        if(paymentMethod == 1) {
            lodgement.setTransactionId(request.get("tellerNumber").toString());
            lodgement.setAmount(Double.parseDouble(request.get("tellerAmount").toString()));
            lodgement.setDepositorName(request.get("depositorsName").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
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
        List<ProductOrder> orderList = jplQuery.getResultList();
        
        
        
        return orderList;
    }
    
     public List<OrderObjectWrapper> listPendingOrders() throws IOException {
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("ProductOrder.findByNotApprovalStatus");
        Short s = 3; // Get Orders that are not declined approved
        jplQuery.setParameter("approvalStatus", s);
        
        System.out.println("Query : " + jplQuery.toString());
        List<ProductOrder> orderResultSet = jplQuery.getResultList();
        
        List<OrderObjectWrapper> orderWrapperList = new ArrayList();
        
        for(ProductOrder order : orderResultSet)
        {
            List<OrderItem> orderItems = getSalesByOrder(order);
            if(orderItems.size() < 1){
                continue;
            }
            OrderObjectWrapper orders = new OrderObjectWrapper(order, orderItems);
            orderWrapperList.add(orders);
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
    

}
