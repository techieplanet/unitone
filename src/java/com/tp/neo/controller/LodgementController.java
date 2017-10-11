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
import com.tp.neo.controller.helpers.LodgementManager;
import com.tp.neo.controller.helpers.MorgageList;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.CompanyAccount;
import com.tp.neo.model.Customer;
import com.tp.neo.model.CustomerAgent;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.Notification;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.plugins.LoyaltyHistory;
import com.tp.neo.service.CustomerService;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.PropertyException;

/**
 *
 * @author John
 */
@WebServlet(name = "LodgementController", urlPatterns = {"/Lodgement","/lodgement"})
public class LodgementController extends AppController {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LODGEMENT_ADMIN = "/views/lodgement/admin.jsp"; 
    private static String LODGEMENT_NEW_AGENT = "/views/lodgement/customer_orders.jsp";
    private static String LODGEMENT_NEW = "/views/lodgement/lodge.jsp";
    private static String LODGEMENT_APPROVAL = "/views/lodgement/approval.jsp";
    private static String LODGEMENT_SUCCESS = "/views/lodgement/success.jsp";
    private final static Logger LOGGER = 
            Logger.getLogger(Lodgement.class.getCanonicalName());
    
     private HashMap<String, String> errorMessages = new HashMap<String, String>();
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
            out.println("<title>Servlet LodgmentController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LodgmentController at " + request.getContextPath() + "</h1>");
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
        
        //AjaxRequests
        if(action.equalsIgnoreCase("lodgmentItems")){
            
            getLodgmentItemCollection(request, response);
            return;
        }
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new Lodgement().getPermissionName(action), request, response)){
                processGetRequest(request, response);
            }else{
                super.errorPageHandler("forbidden", request, response);
            }
            
        }
        
    }

      protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        SystemUser user = sessionUser;
        //System.out.println("SessionUser in Lodgment Get method : " + sessionUser);
        
        String action = request.getParameter("action") != null?request.getParameter("action"):"";
        
        long userTypeId = user.getSystemUserTypeId();
        
        boolean isRedirect = false;
         
         String viewFile = LODGEMENT_ADMIN;
         
         if(action.equals("new")){
             
             viewFile = LODGEMENT_NEW;
             
             request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
             
             if(userTypeId == 1)
                 request.setAttribute("customers", listCustomers());
             else if(userTypeId == 2){
                 request.setAttribute("customers", listAgentCustomers(user.getSystemUserId()));
             }
             else if(userTypeId == 3){
                 
                 //Sending Customer List containing single customer, so as not to break the View flow;
                 List<Customer> customerList = new ArrayList();
                 Customer cust = (Customer)user;
                 customerList.add(cust);
                 request.setAttribute("customers",customerList);
                         
             }
            
         }
         else if(action.equals("getOrders")) {
             
             listCustomerOrders(request, response);
             action = "";
         }
         else if(action.equalsIgnoreCase("lodgmentItems")){
             
             getLodgmentItemCollection(request, response);
             return;
         }
         else if(action.equalsIgnoreCase("lodgmentItem")){
             
             getLodgmentItem(request, response);
             return;
         }
         else if(action.equalsIgnoreCase("list_approved")){
             
             getApprovedLodgement(request,sessionUser.getSystemUserTypeId());
             request.setAttribute("table_title", "Approved Lodgments");
             
         }
         else if(action.equalsIgnoreCase("list_unapproved")){
             
             getUnapprovedLodgement(request,sessionUser.getSystemUserTypeId());
             request.setAttribute("table_title", "Declined Lodgments");
         }
         else if(action.equalsIgnoreCase("list_pending")){
             
             getPendingLodgement(request,sessionUser.getSystemUserTypeId());
             request.setAttribute("table_title", "Pending Lodgments");
         }
         else if(action.equalsIgnoreCase("approval")){//shows the list of Lodgements pending approval
             viewFile = LODGEMENT_APPROVAL;
             request.setAttribute("notificationLodgementId", 0);
             getUnapprovedLodgement(request);
         }
         else if(action.equalsIgnoreCase("approve")){
             approveLodgement(request);
             AppController.doRedirection(request, response, "/Lodgement?action=approval");
             isRedirect = true;
             return;
         }
         else if(action.equalsIgnoreCase("decline")){
             declineLodgement(request);
             AppController.doRedirection(request, response, "/Lodgement?action=approval");
             return;
         }
         else if(action.equalsIgnoreCase("notification")){
             viewFile = LODGEMENT_APPROVAL;
             getUnapprovedLodgement(request);
             request.setAttribute("notificationLodgementId", Long.parseLong(request.getParameter("id")));
         }
         else if(action.equals("success")){
             viewFile = LODGEMENT_SUCCESS;
         }
         else{
            viewFile = LODGEMENT_ADMIN;
            
            request.setAttribute("lodgements",listLodgements(user.getSystemUserTypeId()));
            request.setAttribute("table_title", "All Lodgments");
         }
        
            //Keep track of the sideBar
            request.setAttribute("sideNav", "Lodgement"); 
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
        
        String action = request.getParameter("action");
        
        if(super.hasActiveUserSession(request, response)){
            //if(super.hasActionPermission(new Lodgement().getPermissionName(action), request, response)){
              
             if(action.equalsIgnoreCase("mortgage")){
             
             String viewFile = LODGEMENT_NEW;
             StringBuilder errMsg = new StringBuilder();
             boolean success = payMorgage(request , errMsg);
             if(success)
             {
             AppController.doRedirection(request, response, "/Lodgement?action=success");
             }
             else
             {
                response.sendError(403, errMsg + "---Go back <a href='" + request.getHeader("referer") + "'>Previous Page</a>");
             }
               }
           else{
               processInsertRequest(request, response);
              }
            // }else{
            //    super.errorPageHandler("forbidden", request, response);
            //}
            
        }
    }

    
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{
             
            
    }
    
    
    
    /*TP: Validation is done here*/
    private void validate(HttpServletRequest request, HttpServletResponse response) throws PropertyException, ServletException, IOException{
         
         errorMessages.clear();
         if(request.getParameter("productAmountToPay").isEmpty()){
         errorMessages.put("1","Amount to pay field is required");
         
         }
        if(request.getParameter("paymentMethod").isEmpty()){
         errorMessages.put("2","Select Payment Method");
         }
         else if(!request.getParameter("paymentMethod").isEmpty()){
         if(Integer.parseInt(request.getParameter("paymentMethod"))==1){
          if(request.getParameter("bankName").isEmpty()){
         errorMessages.put("3","For the payment method selected, Bank Name is required");
         }
          if(request.getParameter("depositorsName").isEmpty()){
         errorMessages.put("4","For the payment method selected, Depositor's Name is required");
         }
          
          if(request.getParameter("tellerNumber").isEmpty()){
         errorMessages.put("5","For the payment method selected, Teller Number is required");
         }
          
          if(request.getParameter("tellerAmount").isEmpty()){
         errorMessages.put("6","For the payment method selected, Amount paid is required");
         }
               
         }
         else if(Integer.parseInt(request.getParameter("paymentMethod"))==3){
         if(request.getParameter("cashAmount").isEmpty()){
            errorMessages.put("7","For the payment method selected, Amount paid is required");
         }
             
         }
         }
     }
    
    /*TP: Listing of customers that exists in the database and are not deleted*/
    public List<Lodgement> listLodgements(int userType){
        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
      
        Query jpqlQuery = em.createNamedQuery("Lodgement.findAll");
        
        if(userType == 2){
            jpqlQuery = em.createNamedQuery("Lodgement.findByAgent");
            jpqlQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(userType == 3) {
            jpqlQuery = em.createNamedQuery("Lodgement.findByCustomer");
            jpqlQuery.setParameter("customer", (Customer)sessionUser);
        }
        
        List<Lodgement> lodgementList = jpqlQuery.getResultList();
        
        return lodgementList;
    }
    
    public List<Customer> listCustomers(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Customer.findAll");
        
        List<Customer> custResultList = jplQuery.getResultList();
        
        //Lets set the loyalty amount of each customer
        for(Customer c : custResultList)
        {
            c.setRewardPoints(CustomerService.getCustomerRewardPointBalance(c));
        }
        return custResultList;
    }
    
    public List<Customer> listAgentCustomers(long agentId) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
         Agent agent = em.find(Agent.class, agentId);
        
        Query jplQuery = em.createNamedQuery("CustomerAgent.findByAgentId");
        jplQuery.setParameter("agentId", agent);
        
        List<CustomerAgent> customerAgentResultList = jplQuery.getResultList();
        
        List<Customer> customerList = new ArrayList<Customer>();
        
        for(CustomerAgent custAgent : customerAgentResultList){
            
            customerList.add(custAgent.getCustomer());
        }
        
        //Lets set the loyalty amount of each customer
        for(Customer c : customerList)
        {
            c.setRewardPoints(CustomerService.getCustomerRewardPointBalance(c));
        }
        
        return customerList;
        
    }
    
    public void listCustomerOrders(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Gson gson = new GsonBuilder().create();
        
        long customerId = Long.parseLong(request.getParameter("customerId"));
        
        Customer customer = em.find(Customer.class, customerId);
        
        Query jplQuery = em.createNamedQuery("ProductOrder.findByCustomer");
        
        jplQuery.setParameter("customerId", customer);
        
        //System.out.println("Query : " + jplQuery.toString());
        List<ProductOrder> orderResultSet = jplQuery.getResultList();
        
        List<Map> mapList = new ArrayList<Map>();
        
        for(ProductOrder order : orderResultSet)
        {
            Map<String, String> map = new HashMap<String, String>();
            
            map.put("id", order.getId().toString());
            map.put("customerName", order.getCustomer().getLastname() + " " + order.getCustomer().getFirstname());
            map.put("agentName", order.getAgent().getLastname() + " " + order.getAgent().getFirstname());
            map.put("sales",gson.toJson(getSalesByOrder(order)));
            
            mapList.add(map);
        }
        
        String jsonResponse = gson.toJson(mapList);
        
       
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush(); 
        response.getWriter().close();
        //System.out.println("jsonResponse: " + jsonResponse);
       
        
    }
    
    private List<Map> getSalesByOrder(ProductOrder order) {
        List<Map>OrderItemsList = new ArrayList();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        
        Query jplQuery = em.createNamedQuery("OrderItem.findByOrder");
        jplQuery.setParameter("order", order);
        
        List<OrderItem> resultSet = jplQuery.getResultList();
        
        for(OrderItem orderItem : resultSet) {
            
            short status = orderItem.getApprovalStatus();
            
            Map orderItemDetail = isOrderItemPaymentCompleted(orderItem);
            //If order is declined skip
            if(status == 2){
                continue;    
            }
            
            if((boolean)orderItemDetail.get("isComplete")){
                continue;
            }
            
            //Check if the OrderItem payment has been completed
            
            
            Map<String, String> map = new HashMap();
            Double remainingAmt = orderItem.getAmountPayable() - ((Double)orderItemDetail.get("totalPaid"));
            map.put("saleId",orderItem.getId().toString());
            map.put("project", orderItem.getUnit().getProject().getName().replaceAll("'", "").replaceAll("\"", ""));
            map.put("unitName", orderItem.getUnit().getTitle().replaceAll("'", "").replaceAll("\"", ""));
            map.put("unitQty",orderItem.getQuantity().toString());
            map.put("initialDeposit",orderItem.getInitialDep().toString());
            map.put("amountPayable", remainingAmt.toString());
            map.put("amountPaid", ((Double)orderItemDetail.get("totalPaid")).toString());
            map.put("monthlyPay", ((Double)orderItem.getMontlyPayment()).toString());
            
            OrderItemsList.add(map);
        }
        
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
        
        return OrderItemsList;
    }
    
    private Map isOrderItemPaymentCompleted(OrderItem orderItem){
        
        Map<String, Object> map = new HashMap();
        
        List<LodgementItem> lodgementItemList = (List)orderItem.getLodgementItemCollection();
        
        boolean isComplete = false;
        
        double totalPaid = 0;
        
        for(LodgementItem lodgementItem : lodgementItemList){
            
            totalPaid += lodgementItem.getAmount()+lodgementItem.getRewardAmount();
        }
        
        
        double unitAmount = orderItem.getAmountPayable();
        
        if(unitAmount <= totalPaid){
            isComplete = true;
        }
        
        map.put("isComplete", isComplete);
        map.put("totalPaid", totalPaid);
        
        return map;
    }
    
    private void getUnapprovedLodgement1(HttpServletRequest request){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Lodgement.findByApprovalStatus");
        jplQuery.setParameter("approvalStatus", (short)0);
        List<Lodgement> lodgementList = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        
        request.setAttribute("notificationLodgementId",0);
        request.setAttribute("lodgements", lodgementList);
    }
    
    
    /**
     * The list of unapproved lodgements by the customers of an agent
     * @param request 
     */
    private void getUnapprovedLodgement(HttpServletRequest request){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        List<Lodgement> lodgementList = new ArrayList<Lodgement>();
        
        switch(sessionUser.getSystemUserTypeId()){
            case 1:
                lodgementList = em.createNamedQuery("Lodgement.findByApprovalStatus")
                                    .setParameter("approvalStatus", 0)
                                    .getResultList();
                break;
            case 2:
                lodgementList = em.createNamedQuery("Lodgement.findByApprovalStatusForAgent")
                                    .setParameter("approvalStatus", 0)
                                    .setParameter("agentId", sessionUser.getSystemUserId())
                                    .getResultList();
                break;
        }
        
                                                
        
        emf.getCache().evictAll();
        em.close();
        
        request.setAttribute("notificationLodgementId",0);
        request.setAttribute("lodgements", lodgementList);
    }
            
            
    private void approveLodgement(HttpServletRequest request){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("NeoForcePU");
            em = emf.createEntityManager();
            
            Long id = Long.parseLong(request.getParameter("id"));
            Lodgement lodgement = em.find(Lodgement.class,id);
            
            
            Query jpQl = em.createNamedQuery("Notification.findByRemoteId");
            jpQl.setParameter("remoteId", lodgement.getId());
            jpQl.setParameter("typeTitle","Lodgement");
            
            Notification notification = (Notification)jpQl.getSingleResult();
            
            LodgementManager manager = new LodgementManager(sessionUser);
            
            HttpSession session = request.getSession();
            HashMap<String, Plugin> plugins = (HashMap)session.getAttribute("availablePlugins");
            
            if(plugins.containsKey("loyalty")){
                
                manager = new LodgementManager(sessionUser,plugins);
            }
            
            manager.approveLodgement(lodgement, notification, request.getContextPath());
            
            
            
            request.getSession().setAttribute("success", true);
            
        } catch (PropertyException ex) {
            Logger.getLogger(LodgementController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(LodgementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(em != null){
              em.close();
            }
        }
    }
    
    private void declineLodgement(HttpServletRequest request){
       
//        try {
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
//            EntityManager em = emf.createEntityManager();
//            
//            Long id = Long.parseLong(request.getParameter("id"));
//            Lodgement lodgement = em.find(Lodgement.class,id);
//            
//            Query jpQl = em.createNamedQuery("Notification.findByRemoteId");
//            jpQl.setParameter("remoteId", lodgement.getId());
//            jpQl.setParameter("typeTitle","Lodgement");
//            
//            Notification notification = (Notification)jpQl.getSingleResult();
//            
//            LodgementManager manager = new LodgementManager(sessionUser);
//            manager.approveLodgement(lodgement, notification, request.getContextPath());
//            
//            em.close();
//            emf.close();
//        } catch (PropertyException ex) {
//            Logger.getLogger(LodgementController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (RollbackException ex) {
//            Logger.getLogger(LodgementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    private boolean payMorgage(HttpServletRequest request , StringBuilder errMsg) {
        
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            
            String morgageItemJson = request.getParameter("orderItemsJson");
            List<MorgageList> morgageList = processJson(morgageItemJson);
            
            SystemUser user = sessionUser;
            long type = userType;
            
            Customer customer = null;
            ProductOrder order = null;
            
            Long userId = user.getSystemUserId();
            Lodgement lodgement = prepareLodgement(getRequestParameters(request), userId);
            
            HttpSession session = request.getSession();
            Map<String, Plugin> plugins = (Map)session.getAttribute("availablePlugins");
            double amountPerRewardPoint = 0;
            
            if(plugins.containsKey("loyalty")){
                Gson gson = new Gson();
                Type mapType = new TypeToken<Map<String, String>>(){}.getType();
                Map<String, String> loyaltySettingsMap = gson.fromJson(plugins.get("loyalty").getSettings(), mapType);
                amountPerRewardPoint =  Double.parseDouble(loyaltySettingsMap.get("reward_value"));
            }
            double lodgementAmount = 0;
            double lodgementRewardAmount = 0;
            double totalRoyaltyUsed = 0;
            List<LodgementItem> lodgementItemList = new ArrayList();
            
            for(MorgageList morgageItem : morgageList){
                
                OrderItem orderItem = em.find(OrderItem.class, (long) morgageItem.getOrderItemId());
                LodgementItem lodgementItem = new LodgementItem();
                
                lodgementItem.setCreatedDate(getDateTime().getTime());
                lodgementItem.setCreatedBy(userId);
                lodgementItem.setItem(orderItem);
                if(morgageItem.getAmount()<= 0)
                {
                    errMsg.append("One or More Lodgment Amount is Invalid");
                    return false;
                }
                
                lodgementItem.setAmount(morgageItem.getAmount());
                lodgementAmount += morgageItem.getAmount();
                
                if(plugins.containsKey("loyalty")){
                    totalRoyaltyUsed += morgageItem.getRewardPoint();
                    lodgementItem.setRewardAmount(morgageItem.getRewardPoint() * amountPerRewardPoint);
                    lodgementRewardAmount += (morgageItem.getRewardPoint() * amountPerRewardPoint);
                }
                else{
                    double d = 0;
                    lodgementItem.setRewardAmount(d);
                }
                
                lodgementItemList.add(lodgementItem);
                
                if(customer == null){
                    customer = orderItem.getOrder().getCustomer();
                    order = orderItem.getOrder();
                }
               
            }
            //lets now validate the royalty point;
            double customerBalanceRewardPoint = CustomerService.getCustomerRewardPointBalance(customer);
            if(lodgement.getAmount() < lodgementAmount)
            {
                errMsg.append("Invalid Lodgement Amount");
                return false;
            }
            else if(plugins.containsKey("loyalty") && (totalRoyaltyUsed > customerBalanceRewardPoint))
            {
               errMsg.append("Total Royalty Being used is more than available");
                return false; 
            }
            
            if(plugins.containsKey("loyalty")){
                lodgement.setAmount(lodgementAmount);
                lodgement.setRewardAmount(lodgementRewardAmount);
            }
            else{
                lodgement.setRewardAmount(new Double(0));
            }
            
            lodgement.setCustomer(customer);
            LodgementManager lodgementManager = new LodgementManager(user);
            lodgementManager.amountPerRewardPoint = amountPerRewardPoint;
            lodgementManager.availablePlugins = plugins;
            lodgement = lodgementManager.processLodgement(customer, lodgement, lodgementItemList, request.getContextPath(), order);
            
            Map map = prepareMorgageInvoice(lodgement, lodgementItemList);
            
            //HttpSession session = request.getSession(false);
            session.setAttribute("invoice",map);
            
            em.close();
            emf.close();
            
        } catch (PropertyException ex) {
            Logger.getLogger(LodgementController.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (RollbackException ex) {
            Logger.getLogger(LodgementController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        return true;
    }
    
    
    private Map prepareMorgageInvoice(Lodgement lodgement, List<LodgementItem> lodgementItems){
        Map<String,Object> map = new HashMap();
        
        List<Map> itemList = new ArrayList();
        
        for(LodgementItem item : lodgementItems){
            
            Map<String, String> itemMap = new HashMap();
            
            String title = item.getItem().getUnit().getTitle();
            Double amount = item.getAmount();
            
            itemMap.put("title",title);
            itemMap.put("amount", amount.toString());
            
            itemList.add(itemMap);
        }
       
        map.put("total",lodgement.getAmount());
        map.put("items",itemList);
        
        return map;
    }
    
    private List<MorgageList> processJson(String jsonString){
        
        Gson gson = new GsonBuilder().create();
        Type listType = new TypeToken<ArrayList<MorgageList>>(){}.getType();
        List<MorgageList> morgageItems = gson.fromJson(jsonString, listType);
        
        return morgageItems;
    }
    
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
    
    private Lodgement prepareLodgement(Map request, Long userId) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Lodgement lodgement = new Lodgement();
        
        Short paymentMethod = Short.parseShort(request.get("paymentMethod").toString());
        
        CompanyAccount companyAccount = em.find(CompanyAccount.class, Integer.parseInt(request.get("companyAccount").toString()));
        
        lodgement.setPaymentMode(paymentMethod);
        lodgement.setCreatedDate(this.getDateTime().getTime());
        lodgement.setCreatedBy(userId);
        lodgement.setCompanyAccountId(companyAccount);
        lodgement.setApprovalStatus((short)0);
        lodgement.setCreatedByUserType(Short.parseShort(sessionUser.getSystemUserTypeId().toString()));
        
        if(paymentMethod == 1) {
            lodgement.setTransactionId(request.get("tellerNumber").toString());
            lodgement.setAmount(Double.parseDouble(request.get("tellerAmount").toString()));
            lodgement.setDepositorName(request.get("depositorsName").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
        }
        else if(paymentMethod == 3){
            
            lodgement.setAmount(Double.parseDouble(request.get("cashAmount").toString()));
            lodgement.setLodgmentDate(getDateTime().getTime());
        }
        else if(paymentMethod == 4) {
            lodgement.setDepositorBankName(request.get("transfer_bankName").toString());
            lodgement.setAmount(Double.parseDouble(request.get("transfer_amount").toString()));
            lodgement.setOriginAccountName(request.get("transfer_accountName").toString());
            lodgement.setOriginAccountNumber(request.get("transfer_accountNo").toString());
            lodgement.setLodgmentDate(getDateTime().getTime());
            
        }
        
        em.close();
        emf.close();
        
        return lodgement;
    }
    
    private void getLodgmentItemCollection(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        long lodgment_id = Long.parseLong(request.getParameter("lodgement_id"));
        
        Lodgement lodgement = em.find(Lodgement.class, lodgment_id);
        
        Query query = em.createNamedQuery("LodgementItem.findByLodgment");
        query.setParameter("lodgement", lodgement);
        
        List<LodgementItem> lodgementItems = query.getResultList();
        
        List<Map> mapList = new ArrayList();
        
        for(LodgementItem item : lodgementItems){
            
            Map<String,String> map = new HashMap();
            
            map.put("project", item.getItem().getUnit().getProject().getName());
            map.put("unit", item.getItem().getUnit().getTitle());
            map.put("amount", String.format("%.2f",item.getAmount()));
            
            mapList.add(map);
        }
        
        Gson gson = new GsonBuilder().create();
        
        String jsonString = gson.toJson(mapList);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    private void getLodgmentItem(HttpServletRequest request, HttpServletResponse response) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        long lodgmentitem_id = Long.parseLong(request.getParameter("lodgementitem_id"));
        
        LodgementItem l = em.find(LodgementItem.class, lodgmentitem_id);
        
       
            Map<String,String> map = new HashMap();
            
            map.put("project", l.getItem().getUnit().getProject().getName());
            map.put("unit", l.getItem().getUnit().getTitle());
            map.put("amount", String.format("%.2f",l.getAmount()));
            map.put("date", new SimpleDateFormat("EEE, d MMM yyyy").format(l.getCreatedDate()));
      
        Gson gson = new GsonBuilder().create();
        
        String jsonString = gson.toJson(map);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonString);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    private void getUnapprovedLodgement(HttpServletRequest request, Integer userType) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Lodgement.findByApprovalStatus");
        
        if(userType == 2){
            jplQuery = em.createNamedQuery("Lodgement.findByAgentApproval");
            jplQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(userType == 3){
            jplQuery = em.createNamedQuery("Lodgement.findByCustomerApproval");
            jplQuery.setParameter("customer", (Customer)sessionUser);
        }
        
        jplQuery.setParameter("approvalStatus", (short)2);
        List<Lodgement> lodgementList = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        
        request.setAttribute("lodgements", lodgementList);
        
    }

    private void getApprovedLodgement(HttpServletRequest request, Integer userType) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Lodgement.findByApprovalStatus");
        
        if(userType == 2){
            jplQuery = em.createNamedQuery("Lodgement.findByAgentApproval");
            jplQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(userType == 3){
            jplQuery = em.createNamedQuery("Lodgement.findByCustomerApproval");
            jplQuery.setParameter("customer", (Customer)sessionUser);
        }
        
        jplQuery.setParameter("approvalStatus", (short)1);
        List<Lodgement> lodgementList = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        
        request.setAttribute("lodgements", lodgementList);
        
    }
    
    private void getPendingLodgement(HttpServletRequest request, Integer userType) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Lodgement.findByApprovalStatus");
        
        if(userType == 2){
            jplQuery = em.createNamedQuery("Lodgement.findByAgentApproval");
            jplQuery.setParameter("agent", (Agent)sessionUser);
        }
        else if(userType == 3){
            jplQuery = em.createNamedQuery("Lodgement.findByCustomerApproval");
            jplQuery.setParameter("customer", (Customer)sessionUser);
        }
        
        jplQuery.setParameter("approvalStatus", (short)0);
        List<Lodgement> lodgementList = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        
        request.setAttribute("lodgements", lodgementList);
        
    }
    
    
    private Calendar getDateTime(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        return calendar;
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