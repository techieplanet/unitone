/*
 * To change this license header, choose License Headers in Agent Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;
import com.tp.neo.model.utils.TPController;
import com.tp.neo.model.Agent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.utils.TrailableManager;
import com.tp.neo.model.utils.AuthManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.PropertyException;

//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.FileItemFactory;
//import org.apache.tomcat.util.http.fileupload.RequestContext;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import javax.xml.bind.PropertyException;
import java.util.Map;



/**
 *
 * @author John
 */
@WebServlet(name = "Agent", urlPatterns = {"/Agent"})
@MultipartConfig
public class AgentController extends TPController {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String AGENTS_ADMIN = "/views/agent/admin.jsp"; 
    private static String AGENTS_NEW = "/views/agent/add.jsp";
    private static String AGENTS_VIEW = "/views/agent/view.jsp";
    private final String UPLOAD_DIRECTORY = "C:/Users/John/Documents/uploads";
    private Agent agent = new Agent();
   
    private final static Logger LOGGER = 
            Logger.getLogger(Agent.class.getCanonicalName());
    
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
            out.println("<title>Servlet AgentController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AgentController at " + request.getContextPath() + "</h1>");
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
//        if(super.hasActiveUserSession(request, response, request.getRequestURL().toString())){
            processGetRequest(request, response);
       // }
//         processGetRequest(request, response);
    }

    /*TP: processes the get request in general*/
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = AGENTS_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if (action.equalsIgnoreCase("new")){
               viewFile = AGENTS_NEW;
        }
        
        else if(action.equalsIgnoreCase("delete")){
           
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if (action.equalsIgnoreCase("view")){
                viewFile = AGENTS_VIEW;
                
//            
//            //find by ID
            int id = Integer.parseInt(request.getParameter("agentId"));
            Query jpqlQuery  = em.createNamedQuery("Agent.findByAgentId");
            jpqlQuery.setParameter("agentId", id);
            List<Agent> agentList = jpqlQuery.getResultList();
//            
            request.setAttribute("agent", agentList.get(0));
            }
        else if(action.equalsIgnoreCase("edit")){
            viewFile = AGENTS_NEW;
//            
//            //find by ID
            int id = Integer.parseInt(request.getParameter("agentId"));
            Query jpqlQuery  = em.createNamedQuery("Agent.findByAgentId");
            jpqlQuery.setParameter("agentId", id);
            List<Agent> agentList = jpqlQuery.getResultList();
//            
            request.setAttribute("agent", agentList.get(0));
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listagents")){
            viewFile = AGENTS_ADMIN;
            request.setAttribute("agents", listAgents());
        }

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
        processPostRequest(request, response);
    }

    /*TP: Processes the post requests in general*/
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                Gson gson = new GsonBuilder().create();
              //  String viewFile = AGENTS_ADMIN;
//              Agent agent = new Agent();
              String updateStatus = request.getParameter("updateStatus") != null ? request.getParameter("updateStatus") : "";
            try{                                
                if(!(request.getParameter("agent_id").isEmpty()) ) { //edit mode'
                    if(!(updateStatus.isEmpty())){
                    this.processAgentAccountStatus(request,response);
                    }else{
                    this.processUpdateRequest(request,response);
                    }
                
                }
               else{
                this.processInsertRequest(request, response);
                }
               
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
        
       }
    
    /*TP: Processes every insert request of request type POST*/
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        errorMessages.clear();
        String root = getServletContext().getRealPath("/");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = AGENTS_NEW;
//        Agent agent = new Agent();
        String agentFileName = null;
        String agentKinFileName = null;
        
        root = root.replace("\\", "/");
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
        
            try{                                

                em.getTransaction().begin();
                
               
                if(!(request.getParameter("agent_id").equals(""))) { //edit mode
                    agent = em.find(Agent.class, new Long(Integer.parseInt(request.getParameter("agent_id"))));
                }
               
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
               String formattedDate = sdf.format(date);
               
               
                String path = root+"/images/uploads/agents/";
                long unixTime = System.currentTimeMillis() / 1000L;
                
                 validate(agent,request);
                 agentFileName = uploadAgentPicture(agent,request,agentFileName);
                 agentKinFileName = uploadAgentKinPicture(agent,request,agentKinFileName);
               
                Integer i = 2; 
                Long l = new Long(i);
               
                agent.setFirstname(request.getParameter("agentFirstname"));
                agent.setLastname(request.getParameter("agentLastname"));               
                agent.setEmail(request.getParameter("agentEmail"));
               // String initPass = AuthManager.generateInitialPassword();  //randomly generated password
                agent.setPassword(AuthManager.getSaltedHash(request.getParameter("agentPassword")));
                //agent.setPassword(request.getParameter("agentPassword"));
                agent.setStreet(request.getParameter("agentStreet"));
                agent.setCity(request.getParameter("agentCity"));
                agent.setState(request.getParameter("agentState"));
                agent.setPhone(request.getParameter("agentPhone"));
                agent.setBankName(request.getParameter("agentBankName"));
                agent.setBankAcctNumber(request.getParameter("agentBankAccountNumber"));
                agent.setBankAcctName(request.getParameter("agentBankAccountName"));
                agent.setKinName(request.getParameter("agentKinName"));
                agent.setKinPhone(request.getParameter("agentKinPhone"));
                agent.setKinAddress(request.getParameter("agentKinAddress"));
                new TrailableManager(agent).registerInsertTrailInfo(1);
                agent.setDeleted((short)0);
                if(agentFileName!=null){
                agent.setPhotoPath(agentFileName);
                }
                if(agentKinFileName != null){
                agent.setKinPhotoPath(agentKinFileName);
                }
               
                
               //persist only on save mode

                if(!em.contains(agent)){
                    em.persist(agent);
                   
                }
               
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                viewFile = AGENTS_NEW;
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = AGENTS_NEW;
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
           
        
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
      
    protected void processAgentAccountStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, PropertyException{
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
             EntityManager em = emf.createEntityManager();
//             Agent agent = new Agent();
             Gson gson = new GsonBuilder().create();
        try{                                

                em.getTransaction().begin();
                
               
               
                    agent = em.find(Agent.class, new Long(Integer.parseInt(request.getParameter("agent_id"))));
                    int  status = Integer.parseInt(request.getParameter("updateStatus"));
                    agent.setActive((short) status);
                    em.persist(agent);
                    em.getTransaction().commit();
                    em.close();
                    emf.close();
                    Long agentId = agent.getAgentId();
//            String jsonResponse = gson.toJson(agentId);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("This is the agent id"+agentId);

//            System.out.println("jsonResponse: " + jsonResponse);
           }
//        catch(PropertyException err){
//                err.printStackTrace();
//                System.out.println("inside catch area: " + err.getMessage());
//                  
//                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), err.getMessage());
//            }
            catch(Exception e){
               
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
    }
    /*TP: Processes every update request of request type POST*/
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         errorMessages.clear();
        String root = getServletContext().getRealPath("/");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = AGENTS_NEW;
//        Agent agent = new Agent();
        String agentFileName = null;
        String agentKinFileName = null;
        
        root = root.replace("\\", "/");
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
            try{                                

                em.getTransaction().begin();
                
               
                if(!(request.getParameter("agent_id").equals(""))) { //edit mode
                    agent = em.find(Agent.class, new Long(Integer.parseInt(request.getParameter("agent_id"))));
                }
               
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
               String formattedDate = sdf.format(date);
              
               
                String path = root+"/images/uploads/agents/";
                long unixTime = System.currentTimeMillis() / 1000L;
                
                 validate(agent,request);
                 agentFileName = uploadAgentPicture(agent,request,agentFileName);
                 agentKinFileName = uploadAgentKinPicture(agent,request,agentKinFileName);
               
                Integer i = 2; 
                Long l = new Long(i);
               new TrailableManager(agent).registerUpdateTrailInfo(1);
               
                agent.setFirstname(request.getParameter("agentFirstname"));
                agent.setLastname(request.getParameter("agentLastname"));               
                agent.setEmail(request.getParameter("agentEmail"));
                //agent.setPassword(request.getParameter("agentPassword"));
                if(!request.getParameter("agentPassword").isEmpty()){
                agent.setPassword(AuthManager.getSaltedHash(request.getParameter("agentPassword")));
              
                }
                agent.setStreet(request.getParameter("agentStreet"));
                agent.setCity(request.getParameter("agentCity"));
                agent.setState(request.getParameter("agentState"));
                agent.setPhone(request.getParameter("agentPhone"));
                agent.setBankName(request.getParameter("agentBankName"));
                agent.setBankAcctNumber(request.getParameter("agentBankAccountNumber"));
                agent.setBankAcctName(request.getParameter("agentBankAccountName"));
                agent.setKinName(request.getParameter("agentKinName"));
                agent.setKinPhone(request.getParameter("agentKinPhone"));
                agent.setKinAddress(request.getParameter("agentKinAddress"));
                if(agentFileName!=null){
                agent.setPhotoPath(agentFileName);
                }
                if(agentKinFileName != null){
                agent.setKinPhotoPath(agentKinFileName);
                }
               
                
               //persist only on save mode

                if(!em.contains(agent)){
                    em.persist(agent);
                   
                }
               
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                viewFile = AGENTS_NEW;
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                
            }
            catch(PropertyException err){
                err.printStackTrace();
                System.out.println("inside catch area: " + err.getMessage());
                
                viewFile = AGENTS_NEW;
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), err.getMessage());
            }
            catch(Exception e){
                
                
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
           
        
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
             
    /*TP: Upload of the Agent's picture is done here*/
    private String uploadAgentPicture(Agent agent,HttpServletRequest request,String agentFileName)throws PropertyException,ServletException,IOException{
          String root = getServletContext().getRealPath("/");
          String path = root+"/images/uploads/agents/";
          long unixTime = System.currentTimeMillis() / 1000L;
     if(( request.getPart("agentPhoto") != null ) || (!request.getParameter("agentPhotoHidden").isEmpty() && request.getParameter("agentPhotoHidden") != null )){
                   if (request.getPart("agentPhoto")!=null){
                /*TP: Agent personal file upload*/
                        Part agentPartPhoto = request.getPart("agentPhoto"); 
                        String myName = getFileName(agentPartPhoto);
                         
                         if(myName.isEmpty() && !request.getParameter("agentPhotoHidden").isEmpty() ){
                            
                              agentFileName = request.getParameter("agentPhotoHidden");
                              agent.setPhotoPath(request.getParameter("agentPhotoHidden"));
                         }else {
                            
                        int fnameLength = myName.length();
                        int startingPoint = fnameLength - 4;
                        myName = myName.substring(startingPoint,fnameLength);
                        agentFileName = "agent_"+unixTime+myName;
                        
                        this.photoImageUpload(agentFileName,path,agentPartPhoto);
                       }
                   }
                   else if(!request.getParameter("agentPhotoHidden").isEmpty()){
                       agentFileName = request.getParameter("agentPhotoHidden");
                       agent.setPhotoPath(request.getParameter("agentPhotoHidden"));
                   }
               
         }
     return agentFileName;
     
     }
    
    /*TP: Upload of the Agent's next of kin picture is done here*/
    private String uploadAgentKinPicture(Agent agent,HttpServletRequest request, String agentKinFileName)throws PropertyException, ServletException, IOException{
         String root = getServletContext().getRealPath("/");
         String path = root+"/images/uploads/agents/";
          long unixTime = System.currentTimeMillis() / 1000L;
     if((request.getPart("agentKinPhoto")!= null) || (!request.getParameter("agentKinPhotoHidden").isEmpty() && request.getParameter("agentKinPhotoHidden") != null)){
              
                       
                if( request.getPart("agentKinPhoto") !=null ){
                       /*TP: Agent Kin personal file upload*/
                               Part agentKinPartPhoto = request.getPart("agentKinPhoto");
                               String myNameKin = "";
                               myNameKin = getFileName(agentKinPartPhoto);
                               System.out.println("This is the my name of the next of kin we are testing for "+request.getParameter("agentKinPhotoHidden"));
                             if( myNameKin.isEmpty()&& (!request.getParameter("agentKinPhotoHidden").isEmpty())){
                               agentKinFileName = request.getParameter("agentKinPhotoHidden");
                               agent.setPhotoPath(request.getParameter("agentKinPhotoHidden"));
                              }else {
                               int fnameLengthK = myNameKin.length();
                               int startingPointK = fnameLengthK - 4;
                               myNameKin = myNameKin.substring(startingPointK,fnameLengthK);
                               agentKinFileName = "agentKin_"+unixTime+myNameKin;
                               photoImageUpload(agentKinFileName,path,agentKinPartPhoto);
                }
                          }
                    else if(!request.getParameter("agentKinPhotoHidden").isEmpty()){

                               agentKinFileName = request.getParameter("agentKinPhotoHidden");
                               agent.setPhotoPath(request.getParameter("agentKinPhotoHidden"));
                              }
                    }
     return agentKinFileName;
     
     }
    
    /*TP: This is a generic method for image upload*/
    private void photoImageUpload(String agentFileName,String path, Part agentPartPhoto){
    OutputStream fout = null;
    InputStream filecontent = null;
   
      String type = agentPartPhoto.getHeader("content-type");
   if(type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg") || type.equals("image/gif") || type.equals("image/bmp") )
    {
          try {
            fout = new FileOutputStream(new File(path  + agentFileName));
          
            filecontent = agentPartPhoto.getInputStream();
            
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
    
    /*TP: Validation is done here*/
    private void validate(Agent agent, HttpServletRequest request) throws PropertyException, ServletException, IOException{
         errorMessages.clear();
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
         EntityManager em = emf.createEntityManager();
         Query jpqlQuery  = em.createNamedQuery("Agent.findByEmail");
         jpqlQuery.setParameter("email",request.getParameter("agentEmail"));
         List<Agent> agentDetails = jpqlQuery.getResultList();
         System.out.println(agentDetails);
        if(request.getParameter("agentFirstname").isEmpty()){
            errorMessages.put("errors1", "Please enter First Name");
        } 
        
        if(request.getParameter("agentLastname").isEmpty()){
            errorMessages.put("errors2", "Please enter Last Name");
        }
        if(request.getParameter("agentEmail").isEmpty()){
            errorMessages.put("errors3", "Please enter Email");
        }
        
        if((request.getParameter("agent_id").equals(""))) { //edit mode
                if(request.getParameter("agentPassword").isEmpty()){
            errorMessages.put("errors4", "Please enter Password");
        }  
                if(!agentDetails.isEmpty()){
        errorMessages.put("errors6","Email exists in the database");
        }
                }
       
       if(request.getParameter("agentStreet").isEmpty()){
        errorMessages.put("errors7", "Please enter Street");
       }
       if(request.getParameter("agentCity").isEmpty()){
        errorMessages.put("errors8", "Please enter City");
        }
       if(request.getParameter("agentState").isEmpty()){
        errorMessages.put("errors9", "Please select a State");
       }
       if(request.getParameter("agentPhone").isEmpty()){
        errorMessages.put("errors10", "Please enter Phone Number");
       }
       if(request.getParameter("agentBankName").isEmpty()){
        errorMessages.put("errors11", "Please enter Bank Name");
       }
       if(request.getParameter("agentBankAccountName").isEmpty()){
        errorMessages.put("errors12", "Please enter Bank Account Name");
       }
       if(request.getParameter("agentBankAccountNumber").isEmpty()){
        errorMessages.put("errors13", "Please enter Bank Account Number");
       }
       if(request.getParameter("agentKinName").isEmpty()){
        errorMessages.put("errors14", "Please enter Kin Name");
       }
       if(request.getParameter("agentKinPhone").isEmpty()){
        errorMessages.put("errors15", "Please enter Kin Phone Number");
       }
       if(request.getParameter("agentKinAddress").isEmpty()){
        errorMessages.put("errors16", "Please enter Kin Address");
       }
      
//      if(request.getParameter("agent_id").equals("") ||  (request.getParameter("agent_id")== null)) { //edit mode
//                   
//             Part agentKinPartPhototmp = request.getPart("agentKinPhotoPath");
//             String ffname = getFileName(agentKinPartPhototmp);
//               
//      System.out.println("This is inside the mr validtor where we made sure that things just does nt go the same way"+ffname+" This is the parameter part "+ request.getParameter("agentKinPhotoPath"));
//      }
//        
           
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    /*TP: Listing og agents that exists in the database and are not deleted*/
    public List<Agent> listAgents(){
        //List<Agent> agentList = new ArrayList<Agent>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Agent.findByDeleted");
        jpqlQuery.setParameter("deleted", 0);
        List<Agent> agentList = jpqlQuery.getResultList();

        return agentList;
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
     
    /*TP: Deletion of an agent is done here*/
    public void delete(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
         
        agent = em.find(Agent.class, new Long(id));
        int i = 1;
        short y = (short) i;
        em.getTransaction().begin();
        agent.setDeleted(y);
        em.persist(agent);
        em.getTransaction().commit();

        em.close();
        emf.close();
        
    }
    
    /*TP: Getting the agent Id for public use*/
    public Long getSystemUserId(){
    return agent.getAgentId();
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
