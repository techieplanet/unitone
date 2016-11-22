/*
 * To change this license header, choose License Headers in Agent Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.Agent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AuditLogger;
import com.tp.neo.controller.helpers.AccountManager;
import com.tp.neo.controller.helpers.AlertManager;
import com.tp.neo.controller.helpers.TransactionManager;
import com.tp.neo.controller.helpers.WithdrawalManager;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.AgentBalance;
import com.tp.neo.model.GenericUser;
import com.tp.neo.model.Notification;
import com.tp.neo.model.Transaction;
import com.tp.neo.model.utils.TrailableManager;
import com.tp.neo.model.utils.AuthManager;
import com.tp.neo.model.User;
import com.tp.neo.model.Withdrawal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.FileItemFactory;
//import org.apache.tomcat.util.http.fileupload.RequestContext;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import javax.xml.bind.PropertyException;
import javax.persistence.RollbackException;



/**
 *
 * @author John
 */
@WebServlet(name = "Agent", urlPatterns = {"/Agent"})
@MultipartConfig
public class AgentController extends AppController {
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String AGENTS_ADMIN = "/views/agent/admin.jsp"; 
    private static String AGENTS_NEW = "/views/agent/add.jsp";
    private static String AGENTS_REGISTRATION = "/views/agent/registration.jsp";
    private static String AGENTS_REGISTRATION_SUCCESS = "/views/agent/success.jsp";
    private static String AGENTS_VIEW = "/views/agent/view.jsp";
    private static String AGENTS_WITHDRAWAL = "/views/agent/withdrawal.jsp";
    private static String AGENTS_WITHDRAW_APPROVAL = "/views/agent/withdrawApproval.jsp";
    private static String AGENTS_WAIT = "/views/agent/waiting.jsp";
    private static String AGENTS_CREDIT_HISTORY = "/views/agent/credit_history.jsp";
    private static String AGENTS_DEBIT_HISTORY = "/views/agent/debit_history.jsp";
    private final String UPLOAD_DIRECTORY = "C:/Users/John/Documents/uploads";
    private final String APPROVED_WITHDRAWAL_REQUEST = "/views/agent/approved_withdrawal_request.jsp";
    private Agent agent = new Agent();
   
    private final static Logger LOGGER = Logger.getLogger(Agent.class.getCanonicalName());
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    //private SystemUser sessionUser;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    Gson gson = new GsonBuilder().create();
    
    private String action = "";
    private String viewFile = "";
    
    

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
         
         action = request.getParameter("action") != null ? request.getParameter("action") : "";
         log("action in do get: " + action);
         System.out.println("Inside do get");
         
         if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new Agent().getPermissionName(action), request, response)){
                processGetRequest(request, response);
            }
            else{
                super.errorPageHandler("forbidden", request, response);
            }
         }
    }

    /*TP: processes the get request in general*/
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//      URL url = new URL("http://developers.cloudsms.com.ng/api.php?userid=18012676&password=damilaregrace&type=5&destination=2348169013692&sender=NeoForce&message=TestJava");
//        InputStream is = url.openConnection().getInputStream();
//
//        BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );
//
//        String line = null;
//            while( ( line = reader.readLine() ) != null )  {
//       System.out.println(line);
//        }
//        reader.close();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        viewFile = AGENTS_ADMIN;
        
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        int addstat = request.getParameter("addstat") != null   ? Integer.parseInt(request.getParameter("addstat")) : 0;
        
        String agent_id = request.getParameter("agent_id") != null ? request.getParameter("agent_id") : "";
        String status = request.getParameter("status") != null ? request.getParameter("status") : "";
        
        if (action.equalsIgnoreCase("new")){
               viewFile = AGENTS_NEW;
        }
        else if(action.equalsIgnoreCase("registration")){
              viewFile = AGENTS_REGISTRATION;
        }
        else if(action.equalsIgnoreCase("success")){
            viewFile = AGENTS_REGISTRATION_SUCCESS;
        }
        else if(action.equalsIgnoreCase("delete")){
            this.delete(Long.parseLong(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("waiting")){
            viewFile = AGENTS_WAIT;
            request.setAttribute("agents",listWaitingAgents());
            String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
            request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
        }
        else if(action.equalsIgnoreCase("withdrawal")){
                
            viewFile = AGENTS_WITHDRAWAL;
            request.setAttribute("agent", getAgentDetails());
            request.setAttribute("balance", getAgentBalance());
        }
        else if(action.equalsIgnoreCase("credit_history")){
            
            viewFile = AGENTS_CREDIT_HISTORY;
            request.setAttribute("transactions",getCreditHistory());
        }
        else if(action.equalsIgnoreCase("debit_history")){
            
            viewFile = AGENTS_DEBIT_HISTORY;
            request.setAttribute("transactions",getDebitHistory());
        }
        else if(action.equalsIgnoreCase("withdrawApproval")){
            
            viewFile = AGENTS_WITHDRAW_APPROVAL;
            request.setAttribute("notificationwithdrawalId", 0);
            request.setAttribute("withdrawals", getPendingWithdrawalRequest());
        }
        else if(action.equalsIgnoreCase("approvedWithdrawal")){
            
            viewFile = APPROVED_WITHDRAWAL_REQUEST;
            request.setAttribute("withdrawals", getApprovedWithdrawalRequest());
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
            String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
            request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
            request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agentkins");
            
            request.setAttribute("waitingroute", request.getParameter("route").equalsIgnoreCase("waiting") ? true : false);
            //request.setAttribute("waitingroute", true);
        }
        else if(action.equalsIgnoreCase("edit")){
            viewFile = AGENTS_NEW;
//            
//            //find by ID
            int id = Integer.parseInt(request.getParameter("agentId"));
            Query jpqlQuery  = em.createNamedQuery("Agent.findByAgentId");
            jpqlQuery.setParameter("agentId", id);
            List<Agent> agentList = jpqlQuery.getResultList();
            log("agentsList gotten");
            
            request.setAttribute("agent", agentList.get(0));
            request.setAttribute("action", "edit");
            if(addstat == 1) request.setAttribute("success", true);
            
            String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
            
            request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
            request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agentkins");
            
            log("imageAccessDirPath: " + imageAccessDirPath);
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listagents")){
            viewFile = AGENTS_ADMIN;
            request.setAttribute("agents", listAgents());
            String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
            request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
        }
        else if(action.equalsIgnoreCase("approval")){
            this.processApprovalRequest(Long.parseLong(agent_id), Integer.parseInt(status), request);
        }
        
        //Keep track of the sideBar
        request.setAttribute("sideNav", "Agent");
        
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
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
         if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new Agent().getPermissionName(action), request, response)){
                    processPostRequest(request, response);
            }
            else{
                super.errorPageHandler("forbidden", request, response);
            }
         }
    }

    /*TP: Processes the post requests in general*/
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
              Gson gson = new GsonBuilder().create();
              
            String updateStatus = request.getParameter("updateStatus") != null ? request.getParameter("updateStatus") : "";
            String updateStatusWait = request.getParameter("updateStatusWait")!=null ? request.getParameter("updateStatusWait") : "";
            String amount = request.getParameter("amount") != null ? request.getParameter("amount") : "";
            String withdrawal_id = request.getParameter("withdrawal_id") != null ? request.getParameter("withdrawal_id") : "";
            String agent_id = request.getParameter("agent_id") != null ? request.getParameter("agent_id") : "";
            try{
                     if(!withdrawal_id.equalsIgnoreCase("")){

                          if(request.getParameter("action").equalsIgnoreCase("approveWithdrawal")){
                              approveWithdrawal(withdrawal_id,request,response);
                          }
                          else{

                              declineWithdrawal(withdrawal_id,request,response);
                          }
                    }
                      else if(!agent_id.equalsIgnoreCase("")) { //edit mode'
                        if(!(updateStatus.isEmpty())){
                        this.processAgentAccountStatus(request,response);
                        }
                        else if(!(updateStatusWait.isEmpty())){
                            this.processAgentAccountStatusActivateAndApprove(request,response);
                        }
                        else if(!(amount.isEmpty())){
                            this.processAgentWithdrawalRequest(request,response);
                        }
                        else{
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
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String agentFileName = "";
        String agentKinFileName = "";
        
        boolean insertStatus = false;
        
        String requestOrigin = request.getParameter("from") != null ? request.getParameter("from") : "";
        
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
        
        if(requestOrigin.equalsIgnoreCase("agent_registration")){
            viewFile = AGENTS_REGISTRATION;
        }
        else {
            viewFile = AGENTS_NEW;
        }
        
        try{

               em.getTransaction().begin();
               
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
               String formattedDate = sdf.format(date);
               
               long unixTime = System.currentTimeMillis() / 1000L;
               
               validate(agent,request);
               
                 /**********************************************************************
                 //agentFileName = uploadAgentPicture(agent,request,agentFileName);
                 //agentKinFileName = uploadAgentKinPicture(agent,request,agentKinFileName);
                 **********************************************************************/
                 
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
                agent.setActive((short)0);
                agent.setApprovalStatus((short)-1);
                agent.setAgreementStatus((short)1);
                agent.setDeleted((short)0);
                
                //handle the pictures now
                agentFileName = FileUploader.getSubmittedFileName(request.getPart("agentPhoto"));
                agentKinFileName = FileUploader.getSubmittedFileName(request.getPart("agentKinPhoto"));
                
                if(agentFileName!=null && !agentFileName.equals("")){
                    Part filePart = request.getPart("agentPhoto");
                    String saveName = "agent_" + unixTime + "." + FileUploader.getSubmittedFileExtension(filePart);
                    agent.setPhotoPath(saveName);
                    agentFileName = saveName;
                    new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true).uploadFile(filePart, "agents", saveName, true);
                }
                else{
                    //agent.setPhotoPath("");
                    throw new PropertyException("Please upload agent picture");
                }
                
                if(agentKinFileName!=null && !agentKinFileName.equals("")){
                    Part filePart = request.getPart("agentKinPhoto");
                    String saveName = "agentkin_" + unixTime + "." + FileUploader.getSubmittedFileExtension(filePart);
                    agent.setKinPhotoPath(saveName);
                    agentKinFileName = saveName;
                    new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true).uploadFile(filePart, "agentkins", saveName, true);
                }
                else{
                    agent.setKinPhotoPath("default");
                    //throw new PropertyException("Please upload next of kin picture");
                }
                
                
                
               //persist only on save mode
                em.persist(agent);   
                
                em.flush(); //flush so you can have agent id
                
                //set up the account for this unit
                Account account = new AccountManager().createAgentAccount(agent);
                
                //now link the unit and the account by updating the agent
                em.refresh(agent);
                agent.setAccount(account);
                em.flush();
                
                em.getTransaction().commit();
                
                if(sessionUser == null)
                    sessionUser = (SystemUser)agent;
                
                new TrailableManager(agent).registerInsertTrailInfo(sessionUser.getSystemUserId());
                
                insertStatus = true;
                
                em.refresh(agent);  
                
                //HANDLE SYSTEM ID
                //set the agent ID into the generic user table
                GenericUser systemuser = setUpAgentGenericRecord(agent);
                
                //update the agent record with the generic id 
                setAgentGenericId(agent, systemuser);
                
                em.close();
                emf.close();
                
                viewFile = AGENTS_NEW;
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                
            }
            catch(PropertyException e){
                e.printStackTrace();
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
            catch(RollbackException e){
                e.printStackTrace();
                System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                errorMessages.put("errormessage", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
           
            if(insertStatus && requestOrigin.equalsIgnoreCase("agent_registration")){
                String page = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/AgentRegistration?action=success";
                response.sendRedirect(page);
            }
            else if(insertStatus){
                String page = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/Agent?action=edit&agentId=" + agent.getAgentId() + "&addstat=1";
                response.sendRedirect(page);
            }
            else{
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
                dispatcher.forward(request, response);
            }
    }

    
    /*
        this method inserts the agent id into the system (generic) user table
    */
    private GenericUser setUpAgentGenericRecord(Agent agent) throws Exception{
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        GenericUser genericUser = new GenericUser();
        genericUser.setAgentId(agent.getAgentId());
        em.persist(genericUser);
        em.getTransaction().commit();
        
        em.refresh(genericUser);
        return genericUser;
    }
    
    /*
        this method updates the agent record with the agent's system ID
    */
    private void setAgentGenericId(Agent agent, GenericUser sys)  throws Exception{
        EntityManager em = emf.createEntityManager();
        Agent agent1 = em.find(Agent.class, agent.getAgentId());
        em.getTransaction().begin();
        agent1.setGenericId(sys.getId());
        em.getTransaction().commit();
    }
    
    
      
    protected void processAgentAccountStatusActivateAndApprove(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException, PropertyException{
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
             EntityManager em = emf.createEntityManager();
//             Agent agent = new Agent();
             Gson gson = new GsonBuilder().create();
        try{                                

                em.getTransaction().begin();
                
               
               
                    agent = em.find(Agent.class, new Long(Integer.parseInt(request.getParameter("agent_id"))));
                    int  status = Integer.parseInt(request.getParameter("updateStatusWait"));
                    agent.setActive((short) status);
                    agent.setApprovalStatus((short) status);
                    em.persist(agent);
                    em.getTransaction().commit();
                    em.close();
                    emf.close();
                    Long agentId = agent.getAgentId();
            String jsonResponse = gson.toJson(agentId);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

            System.out.println("jsonResponse: " + jsonResponse);
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
            String jsonResponse = gson.toJson(agentId);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);

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
    
    
    /*
    *   This method executes the approval process for agents 
    *   It collects an agent's information and 
    *   1. Sets the approval status to 1
    *   2. Send an email to the agent notifying them of the approval
    *   3. Send an SMS to the agent notifying them of the approval
    *   Args
        1. Agent Id - id of the agent to be approved
        2. Status - status to put the agent in i.e. approved (1) or declined (0)
    */
    protected void processApprovalRequest (long agentId, int status, HttpServletRequest request){
//            String agentId = request.getParameter("agent_id") != null ? request.getParameter("agent_id") : "";
//            String status  = request.getParameter("status") != null ? request.getParameter("status") : "";
            
            try{
                em = emf.createEntityManager();
                Agent agent = em.find(Agent.class, agentId);

                em.getTransaction().begin();
                agent.setApprovalStatus((short) status);
                agent.setActive((short) status);
                em.flush();
                em.getTransaction().commit();
      
                System.err.println("user ID: " + ((User)request.getSession().getAttribute("user")).getUserId() + " and " + agent.getSystemUserTypeId());
//                while(request.getSession().getAttributeNames().hasMoreElements()){
//                    System.out.println("request attribute: " + request.getAttributeNames().nextElement());
//                }

                //log, send email, send SMS
                new AuditLogger(sessionUser).logAgentApprovalAction(agent);
                new AlertManager().sendAgentApprovalAlerts(agent);
                
                
                emf.getCache().evict(Agent.class);
            } catch (RollbackException e){
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                if(message.equals("")) message="Error occurred";
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), message);
            }
            finally{
                em.close();
            }
    }
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        errorMessages.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        viewFile = AGENTS_NEW;
        
        String agentFileName = ""; String agentKinFileName = "";
        
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
        
        log("Inside processUpdateRequest");
        
        try{

                em.getTransaction().begin();
                
                if(!(request.getParameter("agent_id").equals(""))) { //edit mode
                    agent = em.find(Agent.class, new Long(Integer.parseInt(request.getParameter("agent_id"))));
                }
               
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
               String formattedDate = sdf.format(date);
              
               
               long unixTime = System.currentTimeMillis() / 1000L;
                
               validate(agent,request);
               
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
                
                //handle the pictures now
                agentFileName = FileUploader.getSubmittedFileName(request.getPart("agentPhoto"));
                agentKinFileName = FileUploader.getSubmittedFileName(request.getPart("agentKinPhoto"));
                
                if(agentFileName!=null){
                    log("Inside agent side");
                    Part filePart = request.getPart("agentPhoto");
                    String saveName = "agent_" + unixTime + "." + FileUploader.getSubmittedFileExtension(filePart);
                    agent.setPhotoPath(saveName);
                    agentFileName = saveName;
                    new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true).uploadFile(filePart, "agents", saveName, true);
                }
                if(agentKinFileName!=null){
                    log("Inside kin side");
                    Part filePart = request.getPart("agentKinPhoto");
                    String saveName = "agentkin_" + unixTime + "." + FileUploader.getSubmittedFileExtension(filePart);
                    agent.setKinPhotoPath(saveName);
                    agentKinFileName = saveName;
                    new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true).uploadFile(filePart, "agentkins", saveName, true);
                }
               
                new TrailableManager(agent).registerUpdateTrailInfo(sessionUser.getSystemUserId());
                
                log("Inside processUpdateRequest 2");
                
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                
                String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
                log("imageAccessDirPath: " + imageAccessDirPath);
                request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
                request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agentkins");
                
            }
            catch(PropertyException err){
                err.printStackTrace();
                System.out.println("inside catch area: " + err.getMessage());
                
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
    
    /*TP: Validation is done here*/
    private void validate(Agent agent, HttpServletRequest request) throws PropertyException, ServletException, IOException {
         errorMessages.clear();
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
         EntityManager em = emf.createEntityManager();
         Query jpqlQuery  = em.createNamedQuery("Agent.findByEmail");
         jpqlQuery.setParameter("email",request.getParameter("agentEmail"));
         List<Agent> agentDetails = jpqlQuery.getResultList();
         System.out.println(agentDetails);
         
         String agent_id = request.getParameter("agent_id") != null ? request.getParameter("agent_id") : "";
         
        if(request.getParameter("agentFirstname").isEmpty()){
            errorMessages.put("errors1", "Please enter First Name");
        } 
        
        if(request.getParameter("agentLastname").isEmpty()){
            errorMessages.put("errors2", "Please enter Last Name");
        }
        if(request.getParameter("agentEmail").isEmpty()){
            errorMessages.put("errors3", "Please enter Email");
        }
        
        if(agent_id.equalsIgnoreCase("")) { //edit mode
                if(request.getParameter("agentPassword").isEmpty()){
                         errorMessages.put("errors4", "Please enter Password");
                 }
                else if(!request.getParameter("agentPassword").equals(request.getParameter("agentConfirmPassword"))){
                    errorMessages.put("errors5", "Re-enter password does not match password");
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
       
       /*
       if(request.getPart("agentPhoto") == null){
           errorMessages.put("errors17", "Please upload agent picture");
       }
       
       if(request.getPart("agentKinPhoto") == null){
           errorMessages.put("errors18", "Please upload next of kin picture");
       }*/
       
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    /*TP: Listing og agents that exists in the database and are not deleted*/
    public List<Agent> listAgents(){
        //List<Agent> agentList = new ArrayList<Agent>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        //Query jpqlQuery  = em.createNamedQuery("Agent.findByDeleted");
        Query jpqlQuery = em.createNamedQuery("Agent.findByApprovalStatusAndDeleted");
        jpqlQuery.setParameter("deleted", 0);
        jpqlQuery.setParameter("approvalStatus",1);
        
        List<Agent> agentList = jpqlQuery.getResultList();

        return agentList;
    }
    
     public List<Agent> listWaitingAgents(){
        //List<Agent> agentList = new ArrayList<Agent>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        //Query jpqlQuery  = em.createNamedQuery("Agent.findByDeleted");
        Query jpqlQuery = em.createNamedQuery("Agent.findByActiveAndDeletedAndApprovalStatus");
        jpqlQuery.setParameter("deleted", 0);
        jpqlQuery.setParameter("active",0);
        jpqlQuery.setParameter("approvalStatus",-1);
        List<Agent> agentList = jpqlQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
        
        return agentList;
    }

    
    /*TP: Getting the file name of an uploaded image*/
    private String getFileName(final Part part) {
            final String partHeader = part.getHeader("content-disposition");
            LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
            for (String content : part.getHeader("content-disposition").split(";")) {
                if (content.trim().startsWith("filename")) {
                    return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                }
            }
            return null;
        }
     
    /*TP: Deletion of an agent is done here*/
    public void delete(long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
         
        try{
            agent = em.find(Agent.class, id);

            em.getTransaction().begin();
            agent.setDeleted((short)1);
            new TrailableManager(agent).registerUpdateTrailInfo(sessionUser.getSystemUserId());

            em.getTransaction().commit();
                        
            //deactivate the account associated with the unit
            new AccountManager().deactivateAccount(agent.getAccount());

            em.merge(agent);

            em.close();
            emf.close();
            
         } catch(PropertyException e){
               log("Property: " + e.getMessage());
            }
            catch(Exception e){
              log("Exception: " + e.getMessage());
            }
        
    }
    
    private Agent getAgentDetails(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        long agentId = sessionUser.getSystemUserId();
        Agent agent = em.find(Agent.class, agentId);
        
        
        
        em.close();
        emf.close();
        
        return agent;
    }
    
    private double getAgentBalance(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jpQL = em.createNamedQuery("AgentBalance.findByAgentId");
        jpQL.setParameter("agentId",sessionUser.getSystemUserId());
        
        AgentBalance agentBalance = (AgentBalance)jpQL.getSingleResult();
        
        System.out.println("AgentBalance : " + agentBalance);
        double totalCredit = agentBalance.getTotalcredit() != null ? agentBalance.getTotalcredit() : 0.00;
        double totalDebit = agentBalance.getTotaldebit() != null ? agentBalance.getTotaldebit() : 0.00;
        
        double balance = totalCredit - totalDebit;
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
        return balance;
    }
    
    private List<Transaction> getCreditHistory(){
        
        TransactionManager manager = new TransactionManager(sessionUser);
        Account account = ((Agent)sessionUser).getAccount();
        
        return manager.getCreditHistory(account);
    }
    
    private List<Transaction> getDebitHistory(){
        
        TransactionManager manager = new TransactionManager(sessionUser);
        Account account = ((Agent)sessionUser).getAccount();
        
        return manager.getDebitHistory(account);
    }
    
    private void processAgentWithdrawalRequest(HttpServletRequest req, HttpServletResponse response) throws IOException{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        double amount = Double.parseDouble(req.getParameter("amount"));
        long agentId = Long.parseLong(req.getParameter("agent_id"));
        
        Agent agent = em.find(Agent.class, agentId);
        
        em.close();
        emf.close();
        
        Withdrawal withdrawal = new Withdrawal();
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        
        withdrawal.setAgent(agent);
        withdrawal.setAmount(amount);
        withdrawal.setCreatedBy(agentId);
        withdrawal.setCreatedDate(calendar.getTime());
        withdrawal.setDate(calendar.getTime());
        withdrawal.setApproved((short)0);
        
        WithdrawalManager manager = new WithdrawalManager(sessionUser);
        manager.processWithdrawalRequest(withdrawal, req.getContextPath());
        
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("1");
        
    }
    
    private List<Withdrawal> getPendingWithdrawalRequest(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Withdrawal.findByApproved");
        jplQuery.setParameter("approved", (short)0);
        
        List<Withdrawal> pendingWithdrawal = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
        
        return pendingWithdrawal;
    }
    
    private List<Withdrawal> getApprovedWithdrawalRequest(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Withdrawal.findByApproved");
        jplQuery.setParameter("approved", (short)1);
        
        List<Withdrawal> approvedWithdrawal = jplQuery.getResultList();
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
        
        return approvedWithdrawal;
    }
    
    private void approveWithdrawal(String withdrawal_id,HttpServletRequest req, HttpServletResponse res) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        long id = Long.parseLong(withdrawal_id);
        
        Withdrawal withdrawal = em.find(Withdrawal.class,id);
        
//        Long notificationId = Long.parseLong(req.getParameter("nof_id"));
//        Notification notification = em.find(Notification.class, notificationId);
        
        WithdrawalManager manager = new WithdrawalManager(sessionUser);
        manager.processWithdrawalApproval(withdrawal, req.getContextPath());
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
             
        res.setContentType("text/html");
        res.getWriter().write("1");
    }
    
    
    private void declineWithdrawal(String withdrawal_id,HttpServletRequest req, HttpServletResponse res) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        long id = Long.parseLong(withdrawal_id);
        
        Withdrawal withdrawal = em.find(Withdrawal.class,id);
        
        Long notificationId = Long.parseLong(req.getParameter("nof_id"));
        Notification notification = em.find(Notification.class, notificationId);
        
        WithdrawalManager manager = new WithdrawalManager(sessionUser);
        //manager.processWithdrawalApproval(withdrawal, req.getContextPath());
        
        res.setContentType("text/html");
        res.getWriter().write("1");
    }
    
    /*Getting the agent Id for public use*/
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