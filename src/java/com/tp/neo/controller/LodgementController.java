/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Customer;
import com.tp.neo.model.utils.TPController;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementPK;
import com.tp.neo.model.Sale;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;

/**
 *
 * @author John
 */
@WebServlet(name = "LodgementController", urlPatterns = {"/Lodgement"})
public class LodgementController extends TPController {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LODGEMENT_ADMIN = "/views/lodgement/admin.jsp"; 
    private static String LODGEMENT_NEW_AGENT = "/views/lodgement/customer_orders.jsp";
    private static String LODGEMENT_NEW_CUSTOMER = "/views/lodgement/lodge.jsp";
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
        //processRequest(request, response);
        processGetRequest(request, response);
    }

      protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
         String invoiceID = request.getParameter("invoice_id") != null ? request.getParameter("invoice_id") : "";
         String lodge = request.getParameter("lodge")!=null ? request.getParameter("lodge"): "";
         String viewFile = LODGEMENT_NEW_AGENT;
         if(!lodge.isEmpty() && !invoiceID.isEmpty()){
            viewFile = LODGEMENT_NEW_CUSTOMER; 
            Map<String, String> map = new HashMap<String, String>();
            map.put("monthlyPay", "10000");
            map.put("amountLeft", "90000");
            map.put("monthsPaid", "1");
            map.put("durationLeft", "9");
            
         request.setAttribute("invoiceId", invoiceID);
         request.setAttribute("paymentPlan",map);
//         request.setAttribute("lodgements",listLodgements());
//         request.setAttribute("resultList","hello");
         }else{
         viewFile = LODGEMENT_ADMIN;
//         request.setAttribute("lodgementList",listLodgements());
//        request.setAttribute("lodgements",listLodgements());
//        request.setAttribute("resultList","hello");
         }
        
         //System.out.println("This is the lodgement details "+listLodgements());
        request.setAttribute("lodgementList",listLodgements());
        request.setAttribute("lodgements",listLodgements());
        request.setAttribute("resultList","hello");
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
        processInsertRequest(request, response);
    }

    
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException{
             
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
             EntityManager em = emf.createEntityManager();
             String viewFile = LODGEMENT_NEW_CUSTOMER;
             Lodgement lodgement = new Lodgement();
              Gson gson = new GsonBuilder().create();
    
        try{
            em.getTransaction().begin();
            
        String invoiceID = request.getParameter("invoice_id") != null ? request.getParameter("invoice_id") : "";
        String paymentMethod = request.getParameter("paymentMethod") != null ? request.getParameter("paymentMethod") : "";
        int verificationStatus = -1;
        Double transAmount = Double.valueOf(request.getParameter("productAmountToPay"));
        if(Integer.parseInt(paymentMethod) == 2){
          verificationStatus = 1;
          transAmount = Double.valueOf(request.getParameter("productAmountToPay")); //for online transaction leave it at the amount entered 
        }else if(Integer.parseInt(paymentMethod) == 3){
           transAmount = Double.valueOf(request.getParameter("cashAmount"));
        }
        else if(Integer.parseInt(paymentMethod) == 1){
        transAmount = Double.valueOf(request.getParameter("tellerAmount"));
        }
        
        
        Sale sale = em.find(Sale.class, new Long(1));
        
        //validation is done here
        validate(request,response);
        
        //this is where the proper processing is done
        processLodgementPayment(request,response);
        
        
        
        lodgement.setAmount(Double.valueOf(request.getParameter("productAmountToPay")));
        lodgement.setPaymentMode((Short.parseShort(paymentMethod)));
        lodgement.setBankName(request.getParameter("bankName"));
        lodgement.setDepositorsName(request.getParameter("depositorsName"));
        lodgement.setTellerNo(request.getParameter("tellerNumber"));
        lodgement.setTransAmount(transAmount);
        lodgement.setVerificationStatus((short) verificationStatus );
        Date date = new Date();
        lodgement.setLodgmentDate(date);
        lodgement.setSale(sale);
        
        new TrailableManager(lodgement).registerInsertTrailInfo(1);
        LodgementPK pk = new LodgementPK();
        pk.setSaleId(new Long(1));
        pk.setLodgementId(new Long(1));
        lodgement.setLodgementPK(pk);
        
        sale.getLodgementCollection().add(lodgement);
       
        em.persist(sale);
        em.getTransaction().commit();
        em.close();
        emf.close();
        
         Map<String, String> map = new HashMap<String, String>();
            map.put("monthlyPay", "10000");
            map.put("amountLeft", "90000");
            map.put("monthsPaid", "1");
            map.put("durationLeft", "9");
            
         viewFile = LODGEMENT_ADMIN;
         request.setAttribute("paymentPlan",map);
         request.setAttribute("invoiceId",invoiceID);
         
         //System.out.println("This is the object we want to print out"+lodgement);
//         System.out.println("Lodgement object "+gson.toJson(lodgement));    
        if(Integer.parseInt(paymentMethod) == 2){ // we have to redirect to the image page from here
          response.sendRedirect("images/img/webpay.png");
        }else{
            
       
        request.setAttribute("success",true);
        request.setAttribute("lodgements",listLodgements());
        request.setAttribute("resultList","hello");
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
        }
               
        
    }catch(PropertyException err){
                err.printStackTrace();
                System.out.println("inside catch area: " + err.getMessage());
                viewFile = LODGEMENT_NEW_CUSTOMER;
             
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Lodgement", gson.toJson(lodgement), err.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
     catch(Exception e){
                e.printStackTrace();
                request.setAttribute("errors", errorMessages);
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Lodgement", gson.toJson(lodgement), e.getMessage());
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
        }
    }
    
    private void processLodgementPayment(HttpServletRequest request, HttpServletResponse response) throws PropertyException, ServletException, IOException{
        errorMessages.clear();
        int productAmountToPay = Integer.parseInt(request.getParameter("productAmountToPay"));
          int defAmount = Integer.parseInt(request.getParameter("defAmount"));
          int amountLeft = Integer.parseInt(request.getParameter("amountLeft"));
    if(productAmountToPay>defAmount && amountLeft==productAmountToPay){
        //this is the part of the last payment of the user
        
     
    }
    else if(productAmountToPay<=defAmount && amountLeft==productAmountToPay){
        //this is the system's last payment 
        
    }
    else if((productAmountToPay>defAmount) && (productAmountToPay <= amountLeft)){
//        alert("This is the product amount "+productAmountToPay);
//        alert("This is the amount left amount "+amountLeft);
        
        //this is the part where the multiples of the amount user is paying is needed
        if((productAmountToPay%defAmount) >0){
             errorMessages.put("1","You need to input an amount that is a multiple of the preset monthly payment, or you stick to the default muonthly payment plan" );
             
         
       }else{
   
        }
        
    }else if(productAmountToPay==defAmount){
     
    }
    else if(productAmountToPay<defAmount){
       // alert("The amount cant be lss than the normal point");
     errorMessages.put("2","The amount you can pay cannot be less than the preset monthly payment" );
    
   }else{
       errorMessages.put("3","The amount you want to pay has exceeded the amount you have left" );
    }   
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
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
    public List<Lodgement> listLodgements(){
        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        int i = 1;
        Long id = new Long(i);
        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Lodgement.findAll");
       
        List<Lodgement> lodgementList = jpqlQuery.getResultList();
        
        System.out.println("This is the new lodgement details "+lodgementList);
        return lodgementList;
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
