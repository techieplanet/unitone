/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.utils.AuthManager;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Agent;
import com.tp.neo.model.CustomerAgent;
import com.tp.neo.controller.ProjectController;
import com.tp.neo.model.utils.TrailableManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.PropertyException;


/**
 *
 * @author John
 */
@WebServlet(name = "Customer", urlPatterns = {"/Customer"})
@MultipartConfig
public class CustomerController extends AppController  {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String CUSTOMER_ADMIN = "/views/customer/admin.jsp"; 
    private static String CUSTOMER_NEW = "/views/customer/add.jsp";
    private Customer customer = new Customer();
    private final static Logger LOGGER = 
            Logger.getLogger(Customer.class.getCanonicalName());
    
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
            out.println("<title>Servlet CustomerController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerController at " + request.getContextPath() + "</h1>");
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
     /*TP: Processes the post requests in general*/
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                Gson gson = new GsonBuilder().create();
              //  String viewFile = AGENTS_ADMIN;
              Customer customer = new Customer();
            try{                                
                if(!(request.getParameter("customer_id").equals(""))) { //edit mode
                this.processUpdateRequest(request,response);
                }else{
                this.processInsertRequest(request, response);
                }
               
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Customer", gson.toJson(customer), e.getMessage());
            }
        
       }
    
    /*TP: Processes every insert request of request type POST*/
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        errorMessages.clear();
        String root = getServletContext().getRealPath("/");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = CUSTOMER_NEW;
//        Customer customer = new Customer();
        String customerFileName = null;
        String customerKinFileName = null;
        
        root = root.replace("\\", "/");
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
        
            try{                                

                em.getTransaction().begin();
                
               
                if(!(request.getParameter("customer_id").equals(""))) { //edit mode
                    customer = em.find(Customer.class, new Long(Integer.parseInt(request.getParameter("customer_id"))));
                }
               
//               Date date = new Date();
//               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
//               String formattedDate = sdf.format(date);
               
               
                String path = root+"/images/uploads/customers/";
                long unixTime = System.currentTimeMillis() / 1000L;
                
                 validate(customer,request);
                 customerFileName = uploadCustomerPicture(customer,request,customerFileName);
                 customerKinFileName = uploadCustomerKinPicture(customer,request,customerKinFileName);
               
                Integer i = 1; 
                Long l = new Long(i);
                Long id = new Long(4);
                Agent agent = em.find(Agent.class, id);
                customer.setFirstname(request.getParameter("customerFirstname"));
                customer.setLastname(request.getParameter("customerLastname"));               
                customer.setEmail(request.getParameter("customerEmail"));
                //customer.setPassword(request.getParameter("customerPassword"));
                customer.setPassword(AuthManager.getSaltedHash(request.getParameter("customerPassword")));
                customer.setStreet(request.getParameter("customerStreet"));
                customer.setCity(request.getParameter("customerCity"));
                customer.setState(request.getParameter("customerState"));
                customer.setPhone(request.getParameter("customerPhone"));
                customer.setKinName(request.getParameter("customerKinName"));
                customer.setKinPhone(request.getParameter("customerKinPhone"));
                customer.setKinAddress(request.getParameter("customerKinAddress"));
                
                
                customer.setCustomerId(l);
               
//                if(true) throw new Exception("User triggered exception");
                
                new TrailableManager(customer).registerInsertTrailInfo((long)1);
                customer.setDeleted((short)0);
                if(customerFileName!=null){
                customer.setPhotoPath(customerFileName);
                }
                if(customerKinFileName != null){
                customer.setKinPhotoPath(customerKinFileName);
                }
                
              
                
                
                
               //persist only on save mode

                
                    em.persist(customer);
                   // em.getTransaction().commit();
//                
//         
                CustomerAgent customerAgent =  new CustomerAgent();
                customerAgent.setAgentId(agent);
                customerAgent.setCustomerId(customer);
                
             //em.getTransaction().begin();
//                
                em.persist(customerAgent);
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                viewFile = CUSTOMER_NEW;
                request.setAttribute("customerKinPhotoHidden",customerKinFileName);
                request.setAttribute("customerPhotoHidden",customerFileName);
                request.setAttribute("customers", listCustomers());
                request.setAttribute("success",true);
                request.setAttribute("customer", customer);
                
            }
            catch (PropertyException err){
            err.printStackTrace();
                System.out.println("inside catch area: " + err.getMessage());
                viewFile = CUSTOMER_NEW;
                request.setAttribute("customerKinPhotoHidden",customerKinFileName);
                request.setAttribute("customerPhotoHidden",customerFileName);
                request.setAttribute("customer", customer);
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Customer", gson.toJson(customer), err.getMessage());
            }
            catch(Exception e){
                
                
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Customer", gson.toJson(customer), e.getMessage());
            }
            
           
        
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    /*TP: Processes every update request of request type POST*/
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         errorMessages.clear();
        String root = getServletContext().getRealPath("/");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = CUSTOMER_NEW;
//        Customer customer = new Customer();
        String customerFileName = null;
        String customerKinFileName = null;
        
        root = root.replace("\\", "/");
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
            try{                                

                em.getTransaction().begin();
                
               
                if(!(request.getParameter("customer_id").equals(""))) { //edit mode
                    customer = em.find(Customer.class, new Long(Integer.parseInt(request.getParameter("customer_id"))));
                }
               
              Long id = new Long(3);
               Agent agent = em.find(Agent.class, id);
               Date date = new Date();
//               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
//               String formattedDate = sdf.format(date);
              
               
                String path = root+"/images/uploads/customers/";
                long unixTime = System.currentTimeMillis() / 1000L;
                
                 validate(customer,request);
                 customerFileName = uploadCustomerPicture(customer,request,customerFileName);
                 customerKinFileName = uploadCustomerKinPicture(customer,request,customerKinFileName);
               
                Integer i = 2; 
                Long l = new Long(i);
               new TrailableManager(customer).registerUpdateTrailInfo((long)1);
               
                customer.setFirstname(request.getParameter("customerFirstname"));
                customer.setLastname(request.getParameter("customerLastname"));               
                customer.setEmail(request.getParameter("customerEmail"));
                //customer.setPassword(request.getParameter("customerPassword"));
                if(!request.getParameter("customerPassword").isEmpty()){
                customer.setPassword(AuthManager.getSaltedHash(request.getParameter("customerPassword")));
                }
                customer.setStreet(request.getParameter("customerStreet"));
                customer.setCity(request.getParameter("customerCity"));
                customer.setState(request.getParameter("customerState"));
                customer.setPhone(request.getParameter("customerPhone"));
                customer.setKinName(request.getParameter("customerKinName"));
                customer.setKinPhone(request.getParameter("customerKinPhone"));
                customer.setKinAddress(request.getParameter("customerKinAddress"));
                
                if(customerFileName!=null){
                customer.setPhotoPath(customerFileName);
                }
                if(customerKinFileName != null){
                customer.setKinPhotoPath(customerKinFileName);
                }
               
                
               //persist only on save mode

                if(!em.contains(customer)){
                    em.persist(customer);
                   
                }
               
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                viewFile = CUSTOMER_NEW;
                request.setAttribute("customerKinPhotoHidden",customerKinFileName);
                request.setAttribute("customerPhotoHidden",customerFileName);
                request.setAttribute("customers", listCustomers());
                request.setAttribute("success",true);
                request.setAttribute("customer", customer);
                
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = CUSTOMER_NEW;
                request.setAttribute("customerKinPhotoHidden",customerKinFileName);
                request.setAttribute("customerPhotoHidden",customerFileName);
                request.setAttribute("customer", customer);
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Customer", gson.toJson(customer), e.getMessage());
            }
           
        
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }

     protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = CUSTOMER_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        ProjectController project = new ProjectController();
        //Project listprojects = project.listProjects();
        if (action.equalsIgnoreCase("new")){
               viewFile = CUSTOMER_NEW;
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
        }
//        else {
//             if(super.hasActiveUserSession(request, response, request.getRequestURL().toString())){
        else if(action.equalsIgnoreCase("delete")){
           
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("edit")){
            viewFile = CUSTOMER_NEW;
//            
//            //find by ID
            int id = Integer.parseInt(request.getParameter("customerId"));
            Query jpqlQuery  = em.createNamedQuery("Customer.findByCustomerId");
            jpqlQuery.setParameter("customerId", id);
            List<Customer> customerList = jpqlQuery.getResultList();
//            
            request.setAttribute("customer", customerList.get(0));
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listcustomers")){
            viewFile = CUSTOMER_ADMIN;
            request.setAttribute("customers", listCustomers());
        }
        request.setAttribute("projects", project.listProjects());

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
//        }
//        }
    }
    
    
     /*TP: Listing of customers that exists in the database and are not deleted*/
    public List<Customer> listCustomers(){
        
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Customer.findByDeleted");
        jpqlQuery.setParameter("deleted", 0);
        List<Customer> customerList = jpqlQuery.getResultList();

        return customerList;
    }
    
    /*TP: Upload of the Customer's picture is done here*/
    private String uploadCustomerPicture(Customer customer,HttpServletRequest request,String customerFileName)throws PropertyException,ServletException,IOException{
          String root = getServletContext().getRealPath("/");
          String path = root+"/images/uploads/customers/";
          long unixTime = System.currentTimeMillis() / 1000L;
     if(( request.getPart("customerPhoto") != null ) || (!request.getParameter("customerPhotoHidden").isEmpty() && request.getParameter("customerPhotoHidden") != null )){
                   if (request.getPart("customerPhoto")!=null){
                /*TP: Customer personal file upload*/
                        Part customerPartPhoto = request.getPart("customerPhoto"); 
                        String myName = getFileName(customerPartPhoto);
                         
                         if(myName.isEmpty() && !request.getParameter("customerPhotoHidden").isEmpty() ){
                            
                              customerFileName = request.getParameter("customerPhotoHidden");
                              customer.setPhotoPath(request.getParameter("customerPhotoHidden"));
                         }else {
                            
                        int fnameLength = myName.length();
                        int startingPoint = fnameLength - 4;
                        myName = myName.substring(startingPoint,fnameLength);
                        customerFileName = "customer_"+unixTime+myName;
                        
                        this.photoImageUpload(customerFileName,path,customerPartPhoto);
                       }
                   }
                   else if(!request.getParameter("customerPhotoHidden").isEmpty()){
                       customerFileName = request.getParameter("customerPhotoHidden");
                       customer.setPhotoPath(request.getParameter("customerPhotoHidden"));
                   }
               
         }
     return customerFileName;
     
     }
    
    /*TP: Upload of the Customer's next of kin picture is done here*/
    private String uploadCustomerKinPicture(Customer customer,HttpServletRequest request, String customerKinFileName)throws PropertyException, ServletException, IOException{
         String root = getServletContext().getRealPath("/");
         String path = root+"/images/uploads/customers/";
          long unixTime = System.currentTimeMillis() / 1000L;
     if((request.getPart("customerKinPhoto")!= null) || (!request.getParameter("customerKinPhotoHidden").isEmpty() && request.getParameter("customerKinPhotoHidden") != null)){
              
                       
                if( request.getPart("customerKinPhoto") !=null ){
                       /*TP: Customer Kin personal file upload*/
                               Part customerKinPartPhoto = request.getPart("customerKinPhoto");
                               String myNameKin = "";
                               myNameKin = getFileName(customerKinPartPhoto);
                               System.out.println("This is the my name of the next of kin we are testing for "+request.getParameter("customerKinPhotoHidden"));
                             if( myNameKin.isEmpty()&& (!request.getParameter("customerKinPhotoHidden").isEmpty())){
                               customerKinFileName = request.getParameter("customerKinPhotoHidden");
                               customer.setPhotoPath(request.getParameter("customerKinPhotoHidden"));
                              }else {
                               int fnameLengthK = myNameKin.length();
                               int startingPointK = fnameLengthK - 4;
                               myNameKin = myNameKin.substring(startingPointK,fnameLengthK);
                               customerKinFileName = "customerKin_"+unixTime+myNameKin;
                               photoImageUpload(customerKinFileName,path,customerKinPartPhoto);
                }
                          }
                    else if(!request.getParameter("customerKinPhotoHidden").isEmpty()){

                               customerKinFileName = request.getParameter("customerKinPhotoHidden");
                               customer.setPhotoPath(request.getParameter("customerKinPhotoHidden"));
                              }
                    }
     return customerKinFileName;
     
     }
    
    /*TP: This is a generic method for image upload*/
    private void photoImageUpload(String customerFileName,String path, Part customerPartPhoto){
    OutputStream fout = null;
    InputStream filecontent = null;
   
      String type = customerPartPhoto.getHeader("content-type");
   if(type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg") || type.equals("image/gif") || type.equals("image/bmp") )
    {
          try {
            fout = new FileOutputStream(new File(path  + customerFileName));
          
            filecontent = customerPartPhoto.getInputStream();
            
            int read = 0;
            final byte[] bytes = new byte[32*1024];
// 
            while ((read =filecontent.read(bytes)) != -1) {
                fout.write(bytes, 0, read);
            }
            fout.flush();
            fout.close();
            } catch (Exception e) { 
          errorMessages.put("error7","Invalid File Format");
        }  
    } else { 
        errorMessages.put("error7","Invalid File Format");
        } 
    }
    
    
    /*Validate the customers details*/
    /*TP: Validation is done here*/
    private void validate(Customer customer, HttpServletRequest request) throws PropertyException, ServletException, IOException{
         errorMessages.clear();
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
         EntityManager em = emf.createEntityManager();
         Query jpqlQuery  = em.createNamedQuery("Customer.findByEmail");
         jpqlQuery.setParameter("email",request.getParameter("customerEmail"));
         List<Customer> customerDetails = jpqlQuery.getResultList();
         System.out.println(customerDetails);
        if(request.getParameter("customerFirstname").isEmpty()){
            errorMessages.put("errors1", "Please enter First Name");
        } 
        
        if(request.getParameter("customerLastname").isEmpty()){
            errorMessages.put("errors2", "Please enter Last Name");
        }
        if(request.getParameter("customerEmail").isEmpty()){
            errorMessages.put("errors3", "Please enter Email");
        }
        
        if((request.getParameter("customer_id").equals(""))) { //edit mode
                if(request.getParameter("customerPassword").isEmpty()){
            errorMessages.put("errors4", "Please enter Password");
        }  
                if(!customerDetails.isEmpty()){
        errorMessages.put("errors6","Email exists in the database");
        }
                }
       
       if(request.getParameter("customerStreet").isEmpty()){
        errorMessages.put("errors7", "Please enter Street");
       }
       if(request.getParameter("customerCity").isEmpty()){
        errorMessages.put("errors8", "Please enter City");
        }
       if(request.getParameter("customerState").isEmpty()){
        errorMessages.put("errors9", "Please select a State");
       }
       if(request.getParameter("customerPhone").isEmpty()){
        errorMessages.put("errors10", "Please enter Phone Number");
       }
       if(request.getParameter("customerKinName").isEmpty()){
        errorMessages.put("errors14", "Please enter Kin Name");
       }
       if(request.getParameter("customerKinPhone").isEmpty()){
        errorMessages.put("errors15", "Please enter Kin Phone Number");
       }
       if(request.getParameter("customerKinAddress").isEmpty()){
        errorMessages.put("errors16", "Please enter Kin Address");
       }
   
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    /*TP: Getting the file name of an uploaded image*/
    private String getFileName(final Part part) {
            final String partHeader = part.getHeader("content-disposition");
            LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
         for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
                    }
                }
            return null;
        }
    
     /*TP: Deletion of a customer is done here*/
    public void delete(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
         
        customer = em.find(Customer.class, new Long(id));
        int i = 1;
        short y = (short) i;
        em.getTransaction().begin();
        customer.setDeleted(y);
        em.persist(customer);
        em.getTransaction().commit();

        em.close();
        emf.close();
        
    }
    
      /*TP: Getting the customer Id for public use*/
    public Long getSystemUserId(){
    return customer.getCustomerId();
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
