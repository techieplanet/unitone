/*
 * To change this license header, choose License Headers in Agent Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.components.AuditLogger;
import com.tp.neo.controller.helpers.AccountManager;
import com.tp.neo.controller.helpers.AlertManager;
import com.tp.neo.controller.helpers.EmailHelper;
import com.tp.neo.controller.helpers.TransactionManager;
import com.tp.neo.controller.helpers.WithdrawalManager;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Bank;
import com.tp.neo.model.Company;
import com.tp.neo.model.Director;
import com.tp.neo.model.Document;
import com.tp.neo.model.DocumentType;
import com.tp.neo.model.GenericUser;
import com.tp.neo.model.Transaction;
import com.tp.neo.model.User;
import com.tp.neo.model.Withdrawal;
import com.tp.neo.model.utils.AuthManager;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.model.utils.TrailableManager;
import com.tp.neo.service.BankService;
import com.tp.neo.service.DirectorService;
import com.tp.neo.service.DocumentService;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.PropertyException;


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
    private static String AGENT_PROFILE = "/views/agent/profile.jsp";
    private static String AGENTS_REGISTRATION_SUCCESS = "/views/agent/success.jsp";
    private static String AGENTS_VIEW = "/views/agent/view.jsp";
    private static String AGENTS_WITHDRAWAL = "/views/agent/withdrawal.jsp";
    private static String AGENTS_WITHDRAW_APPROVAL = "/views/agent/withdrawApproval.jsp";
    private static String AGENTS_WAIT = "/views/agent/waiting.jsp";
    private static String AGENTS_CREDIT_HISTORY = "/views/agent/credit_history.jsp";
    private static String AGENTS_DEBIT_HISTORY = "/views/agent/debit_history.jsp";
    private static String AGENTS_ACCOUNT_STATEMENT = "/views/agent/account_statement.jsp";
    private final String UPLOAD_DIRECTORY = "C:/Users/John/Documents/uploads";
    private final String APPROVED_WITHDRAWAL_REQUEST = "/views/agent/approved_withdrawal_request.jsp";
    private  Agent agent = new Agent();
   
    private final static Logger LOGGER = Logger.getLogger(Agent.class.getCanonicalName());
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    //private SystemUser sessionUser;
    
    private final static double  minimumBalance = 1000;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em;
    static Gson gson = new GsonBuilder().create();
    
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
         //System.out.println("Inside do get");
         //System.out.println("is Ajax Request : " + isAjaxRequest);
         
         if(action.equalsIgnoreCase("email_validation")){
            validateEmail(request,  response);
           
         }
         else if(action.equalsIgnoreCase("referral"))
         {
             getReferrerInfo( request,  response);
         }
         else if(super.hasActiveUserSession(request, response)){
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
//       //System.out.println(line);
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
                request.setAttribute("action","new");
               viewFile = AGENTS_NEW;
               if(request.getParameter("corporate") != null)
               {
                  request.setAttribute("corporate" , true);
               }
               request.setAttribute("Banks", BankService.getAllBanks());
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
            
             em = emf.createEntityManager();
             Agent agent = em.find(Agent.class, this.sessionUser.getSystemUserId());
             request.setAttribute("agent", agent);
             request.setAttribute("balance", agent.getEligibleWithdrawalBalance() );
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
            request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agents/agentkins");
            
            request.setAttribute("waitingroute", request.getParameter("route").equalsIgnoreCase("waiting") ? true : false);
            //request.setAttribute("waitingroute", true);
            request.setAttribute("documents", DocumentService.getAgentDocuments(id));
            request.setAttribute("documentDir", imageAccessDirPath+"/");
            
            if(agentList.get(0).isCorporate())
            {
                request.setAttribute("corporate" ,true);
                request.setAttribute("Directors" ,DirectorService.getDirectors(agentList.get(0).getAgentId()));
            }
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
            request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agents/agentkins");
            request.setAttribute("Banks", BankService.getAllBanks());
            log("imageAccessDirPath: " + imageAccessDirPath);
        }
        else if(action.equalsIgnoreCase("profile")){
            
            //Check if the user doesnot specify id parameter
            String idString = request.getParameter("id");
            
            Long id ;
                    
            if(idString == null){
                //Then we know that the logged in user is trying to view it own profile
                //we can now procceed to use the id stored in the session
                id = sessionUser.getSystemUserId();
            }
            else
            {
                //Here we know we are about to get the profile of a particular user
                id = Long.parseLong(idString);
            }

            
            viewFile = AGENT_PROFILE;
            
            Agent agent = em.find(Agent.class, id);
            
            String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
            
            request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
            request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agents/agentkins");
            request.setAttribute("sideNav", "profile");
            request.setAttribute("agent", agent);
            
            //transaction history 
            Map<String, Object> historymap = getAgentAccountHistory(request, response);
            request.setAttribute("agentDetails", historymap.get("agentDetails"));
            request.setAttribute("transactionMapsList", historymap.get("transactionMapsList"));
            request.setAttribute("history", request.getHeader("referer"));
            request.setAttribute("networkList", this.getNetwork(agent));
            request.setAttribute("refAgent", em.find(Agent.class, agent.getReferrerId()));
            request.setAttribute("documents", DocumentService.getAgentDocuments(id));
            request.setAttribute("documentDir", imageAccessDirPath+"/");
            request.setAttribute("totalDebit",historymap.get("totalDebit"));
            request.setAttribute("totalCredit",historymap.get("totalCredit"));        
            
             if(agent.isCorporate())
            {
                viewFile = "/views/agent/corporate_profile.jsp";
                request.setAttribute("Directors" ,DirectorService.getDirectors(agent.getAgentId()));
            }
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
        else if(action.equalsIgnoreCase("wallet")){
            Map<String, Object> historymap = getAgentAccountHistory(request, response);
            //System.out.println("Maps List: " + historymap.get("transactionMapsList"));
            request.setAttribute("agentDetails", historymap.get("agentDetails"));
            request.setAttribute("transactionMapsList", historymap.get("transactionMapsList"));
        }
        else if(action.equalsIgnoreCase("account_statement")){
            
            viewFile = AGENTS_ACCOUNT_STATEMENT;
            
            getAgentAccountHistory(request);
            
        }
        else if(action.equalsIgnoreCase("email_validation")){
            validateEmail(request,  response);
            return;
        }
        else if( action.equalsIgnoreCase("makePayout"))
           {
               processPayout( request,  response);
               return;
           }
        
        if(request.getAttribute("sideNav") == null){
            //Keep track of the sideBar
            request.setAttribute("sideNav", "Agent");
            request.setAttribute("sideNavAction",action);
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
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        
            //Ajax Post Request for Password Change
            if(action.equalsIgnoreCase("password_change")){

                changeAgentPassword(request,response);
                return;
            }
            else if (action.equalsIgnoreCase("new"))
            {
                processInsertRequest(request, response);
                return;
            }
        
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
            String referralMode = request.getParameter("referralMode") != null ? request.getParameter("referralMode") : "";
            //System.out.println("referralMode: " + request.getParameter("referralMode"));
            
            try{
                    if(!withdrawal_id.equalsIgnoreCase("")){

                          if(request.getParameter("action").equalsIgnoreCase("approveWithdrawal")){
                              approveWithdrawal(withdrawal_id,request,response);
                          }
                          else{

                              declineWithdrawal(withdrawal_id,request,response);
                          }
                    }else if(!agent_id.equalsIgnoreCase("")) { //edit mode'
                          
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

                    }else if(referralMode.equalsIgnoreCase("email")){
                        String recipientEmail = request.getParameter("recipientEmail") != null ? request.getParameter("recipientEmail") : "";
                        String refAgentId = request.getParameter("refAgentId") != null ? request.getParameter("refAgentId") : "";
                        String returnValue = "";
                        em = emf.createEntityManager();
                        
                        if(referralMode.equalsIgnoreCase("email")){
                            Agent agent = em.find(Agent.class, Long.parseLong(refAgentId));
                            String refLink = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/AgentRegistration?action=ref&refCode=%s&refId=%s&target=%s";
                            //refLink = String.format(refLink, agent.getAccount().getAccountCode(), agent.getAgentId());
                            ////System.out.println("Initial reflink: " + refLink);
                            
                            //String encodeMe = "&refcode=" + agent.getAccount().getAccountCode() + "&refid=" + agent.getAccount().getAccountCode();
                            refLink = String.format(refLink, 
                                                    Base64.getEncoder().encodeToString(agent.getAccount().getAccountCode().getBytes("utf-8")),
                                                    Base64.getEncoder().encodeToString(agent.getAgentId().toString().getBytes("utf-8")),
                                                    Base64.getEncoder().encodeToString(recipientEmail.getBytes("utf-8")));
                            //System.out.println("reflink: " + refLink);
                            //byte[] decodedBytes = Base64.getUrlDecoder().decode(refLink);
                            ////System.out.println("Original String: " + new String(decodedBytes));
                            new AlertManager().sendReferralCodeEmail(recipientEmail, agent, refLink);
                            returnValue = "OK";
                        }
                        
                        response.setContentType("text/plain");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(returnValue);
                    }
                    
                   else{
                        this.processInsertRequest(request, response);
                    }
               
            }
            catch(Exception e){
                e.printStackTrace();
                //System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("errors", errorMessages);    
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
        
       }
    
    /*TP: Processes every insert request of request type POST*/
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Clear When Done
        
        String corporate = request.getParameter("corporate");
        //Check if it is a Corporate agant registartion Form
        if(corporate != null && corporate.equals("true"))
        {
            //Proceed to handle the corporate agent Datas
            processCorporateAgentRegistration(request,response);
            return;
        }
       
            
        
        errorMessages.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        final EntityManager em = emf.createEntityManager();
        Company company = em.find(Company.class, 1);
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
                agent.setMiddlename(request.getParameter("agentMiddlename"));
                agent.setEmail(request.getParameter("agentEmail").toLowerCase().trim());
                String initPass = AuthManager.generateInitialPassword();  //randomly generated password
                agent.setPassword(AuthManager.getSaltedHash(initPass));
                //agent.setPassword(request.getParameter("agentPassword"));
                agent.setStreet(request.getParameter("agentStreet"));
                agent.setCity(request.getParameter("agentCity"));
                agent.setState(request.getParameter("agentState"));
                agent.setPhone(request.getParameter("agentPhone"));
                //agent.setBankName(request.getParameter("agentBankName"));
                Bank bank = em.find(Bank.class, Integer.parseInt(request.getParameter("agentBankId")));
                agent.setBank(bank);
                agent.setBankAcctNumber(request.getParameter("agentBankAccountNumber"));
                agent.setBankAcctName(request.getParameter("agentBankAccountName"));
                agent.setKinName(request.getParameter("agentKinName"));
                agent.setKinPhone(request.getParameter("agentKinPhone"));
                agent.setKinAddress(request.getParameter("agentKinAddress"));
                agent.setKinRelationship(request.getParameter("agentKinRelationship"));
                agent.setActive((short)0);
                agent.setApprovalStatus((short)-1);
                agent.setAgreementStatus((short)1);
                agent.setDeleted((short)0);
                
                agent.setPhotoPath("default");
                agent.setKinPhotoPath("default");
                agent.setCreatedDate(AgentController.getDateTime().getTime());
                agent.setCorporate(false);
                
                //Set the Referral id 
                //Referral id have format of AG000XXXX
                String sRefId = request.getParameter("refCode");
                if(sRefId != null && !sRefId.trim().isEmpty())
                {
                    Long lRefId = Long.parseLong(sRefId.substring(5));
                    agent.setReferrerId(lRefId);
                }
                else
                {
                    agent.setReferrerId(0L);
                }
                
                if(sessionUser != null)
                    agent.setCreatedBy(sessionUser.getSystemUserId());
                //<editor-fold>
                /*
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
                    errorMessages.put("AgentPhoto", "Please upload agent photo");
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
                
                */
                //</editor-fold>
               //persist only on save mode
                em.persist(agent);   
                
                em.flush(); //flush so you can have agent id
                
                 //New Update Handles The Images As Documents
            
            //We are going to put all the image into an Hash map specifying the title of the Image also
            Map<String, Part>  documents = new HashMap<>();
            documents.put("agent_", request.getPart("agentPhoto"));
            documents.put("agentkin_", request.getPart("agentKinPhoto"));
            documents.put("agent_ID_Card_", request.getPart("agentPhotoID"));
            documents.put("agent_utility_bill_", request.getPart("utilityBill"));
            
           em.refresh(agent);
           FileUploader fUpload  = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);
           final Agent temp_agent = agent;
           documents.forEach((String typeAlias , Part path) -> {
                if(path != null && !path.getSubmittedFileName().trim().isEmpty())
               {
                DocumentType doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", typeAlias)
                        .getSingleResult();
                Document doc = new Document();
               
                String saveName = typeAlias + unixTime +"_"+ temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(path);
                String subdir = "agents";
                String  dir = "";
                
                switch(typeAlias)
                {
                    case "agent_":
                        temp_agent.setPhotoPath(saveName); 
                        break;
                        
                    case "agentkin_":
                        temp_agent.setKinPhotoPath(saveName); 
                        subdir = subdir + fUpload.getFileSeparator() + "agentkins" ;
                        dir = "/agentkins";
                        break;
                       
                    case "agent_ID_Card_":
                        subdir = subdir + fUpload.getFileSeparator() + "ids" ;
                         dir = "/ids";
                        break;
                        
                    case "agent_utility_bill_": 
                        subdir = subdir + fUpload.getFileSeparator() + "utilitybills" ;
                         dir = "/utilitybills";
                        break;
                }
                
                
                doc.setDocTypeId(doctype);
                doc.setOwnerId(BigInteger.valueOf(temp_agent.getAgentId()));
                doc.setOwnerTypeId(temp_agent.getSystemUserTypeId());
                doc.setPath("agents" + dir + "/"+saveName);
                doc.setCreatedDate(CustomerController.getDateTime().getTime());
                
                em.persist(doctype);
                em.persist(doc);
                
                try 
                {
                    fUpload.uploadFile(path, subdir, saveName, true);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
           });
                
                agentFileName = agent.getPhotoPath();
                agentKinFileName = agent.getKinPhotoPath();
                em.persist(agent); 
                em.flush();
                 
                //set up the account for this unit
                Account account = new AccountManager().createAgentAccount(agent);
                
                //now link the unit and the account by updating the agent
                em.refresh(agent);
                agent.setAccount(account);
                em.flush();
                
                em.getTransaction().commit();
                String Url = request.getServerName()+"/"+request.getContextPath();
                new EmailHelper().sendUserWelcomeMessageAndPassword(agent.getEmail() , company.getEmail() , initPass , agent , company,Url );
                insertStatus = true;
                if(sessionUser == null)
                    sessionUser = (SystemUser)agent;
                
                new TrailableManager(agent).registerInsertTrailInfo(sessionUser.getSystemUserId());
                em.refresh(agent);  
                
                //HANDLE SYSTEM ID
                //set the agent ID into the generic user table
                GenericUser systemuser = setUpAgentGenericRecord(agent);
                
                //update the agent record with the generic id 
                setAgentGenericId(agent, systemuser);
                
                
                em.close();
                emf.close();
                
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                request.setAttribute("action", "edit");
                insertStatus = true;
            }
            catch(PropertyException e){
                e.printStackTrace();
                agent = new Agent();
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                //errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", /*agent.getAgentId().toString()*/"process Insert Request", e.getMessage());
            }
            catch(RollbackException e){
                agent = new Agent();
                e.printStackTrace();
                //System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                //errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", /*agent.getAgentId().toString()*/"process Insert Request", e.getMessage());
            }
            catch(Exception e){
                agent = new Agent();
                e.printStackTrace();
                //System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                //errorMessages.put("errormessage", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", /*agent.getAgentId().toString()*/"process Insert Request", e.getMessage());
            }
           
            if(insertStatus && requestOrigin.equalsIgnoreCase("agent_registration")){
                viewFile = "/views/agent/success.jsp";
            }
            
            if(requestOrigin.equalsIgnoreCase("agent_registration"))
            {
                request.setAttribute("from", "agent_registration");
            }
            request.setAttribute("Banks", BankService.getAllBanks());
            request.setAttribute("corporate", false);
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }

    
    
    protected void processCorporateAgentRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         errorMessages.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        final EntityManager em = emf.createEntityManager();
        Company company = em.find(Company.class, 1);
        String agentFileName = "";
        
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
               
               validateCorporate(agent,request);
               
                 /**********************************************************************
                 //agentFileName = uploadAgentPicture(agent,request,agentFileName);
                 //agentKinFileName = uploadAgentKinPicture(agent,request,agentKinFileName);
                 **********************************************************************/
                 
                agent.setFirstname(request.getParameter("agentFirstname"));
               agent.setEmail(request.getParameter("agentEmail").toLowerCase().trim());
               agent.setCorporate(true);
               agent.setRCNumber(request.getParameter("agentRCNumber"));
               String initPass = AuthManager.generateInitialPassword();  //randomly generated password
                agent.setPassword(AuthManager.getSaltedHash(initPass));
                //agent.setPassword(request.getParameter("agentPassword"));
                agent.setStreet(request.getParameter("agentStreet"));
                agent.setCity(request.getParameter("agentCity"));
                agent.setState(request.getParameter("agentState"));
                agent.setPhone(request.getParameter("agentPhone"));
                //agent.setBankName(request.getParameter("agentBankName"));
                Bank bank = em.find(Bank.class, Integer.parseInt(request.getParameter("agentBankId")));
                agent.setBank(bank);
                agent.setBankAcctNumber(request.getParameter("agentBankAccountNumber"));
                agent.setBankAcctName(request.getParameter("agentBankAccountName"));
                agent.setActive((short)0);
                agent.setApprovalStatus((short)-1);
                agent.setAgreementStatus((short)1);
                agent.setDeleted((short)0);
                //agent.setReferrerId(0L); //set to ) when Admin is entering the data so it does not return null
                agent.setPhotoPath("default");
                agent.setCreatedDate(AgentController.getDateTime().getTime());
                if(sessionUser != null)
                    agent.setCreatedBy(sessionUser.getSystemUserId());
               
                //Set the Referral id 
                //Referral id have format of AG000XXXX
                String sRefId = request.getParameter("refCode");
                if(sRefId != null && !sRefId.trim().isEmpty())
                {
                    Long lRefId = Long.parseLong(sRefId.substring(5));
                    agent.setReferrerId(lRefId);
                }
                else
                {
                    agent.setReferrerId(0L);
                }
                
               //persist only on save mode
                em.persist(agent);   
                
                em.flush(); //flush so you can have agent id
                
                 //New Update Handles The Images As Documents
            
                em.refresh(agent);
            final Agent temp_agent = agent;
            FileUploader fUpload  = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);
           
            //loops that will get the Documents of Directors
            Enumeration<String> parameterName = request.getParameterNames();
            int count = 1;
            while(parameterName.hasMoreElements())
            {
                
                String pName = parameterName.nextElement();
                if(pName.contains("agentDirectorName")&& !request.getParameter(pName).trim().isEmpty())
                {
                    int index = Integer.parseInt(pName.substring(17));
                    Director director = new Director();
                    director.setName(request.getParameter(pName));
                    director.setAgent(agent);
                    
                    //Setting up  The Passport Document
                    
                    Part part = request.getPart("agentDirectorPassport"+index);
               
                    String saveName = "Director_Passport_"+ unixTime +count  +"_"+ temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(part);
                    String  dir = "agents" + fUpload.getFileSeparator() + "Directors" + fUpload.getFileSeparator() + "Passports" ;
                    
                    DocumentType doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", "Director_Passport_")
                        .getSingleResult();
                    
                    Document doc = new Document();
                    
                    doc.setDocTypeId(doctype);
                    doc.setPath("agents/Directors/Passports/" + saveName);
                    doc.setOwnerId(BigInteger.valueOf(agent.getAgentId()));
                    doc.setOwnerTypeId(agent.getSystemUserTypeId());
                    doc.setCreatedDate(CustomerController.getDateTime().getTime());
                    
                    em.persist(doctype);
                    em.persist(doc);
                    em.flush();
                    try 
                     {
                        fUpload.uploadFile(part, dir, saveName, true);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    
                    //done setting Up the passport
                    //Now lets set The director passport Id 
                    director.setPassport(doc.getId());
                    
                    //Now Lets setUp The ID card 
                     part = request.getPart("agentDirectorIDCard"+index);
               
                     saveName = "Director_IDCard_" + unixTime + count +"_"+  temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(part);
                      dir = "agents" + fUpload.getFileSeparator() + "Directors" + fUpload.getFileSeparator() + "IDCards";
                    
                     doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", "Director_IDCard_")
                        .getSingleResult();
                    
                     doc = new Document();
                    
                    doc.setDocTypeId(doctype);
                    doc.setPath("agents/Directors/IDCards/" + saveName);
                    doc.setOwnerId(BigInteger.valueOf(agent.getAgentId()));
                    doc.setOwnerTypeId(agent.getSystemUserTypeId());
                    doc.setCreatedDate(CustomerController.getDateTime().getTime());
                    
                    em.persist(doctype);
                    em.persist(doc);
                    em.flush();
                    try 
                     {
                        fUpload.uploadFile(part, dir, saveName, true);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    
                    //Done saving The ID Card
                    //Now Lets Load it up into the Director Object
                    director.setiDCard(doc.getId());
                    
                    //Now that we are done dealing with the Director lets Persist The Director
                    em.persist(director);
                    em.flush();
                    count ++;
                }
            }
           
            //We are going to put all the image into an Hash map specifying the title of the Image also
            Map<String, Part>  documents = new HashMap<>();
            documents.put("CompanyLogo_", request.getPart("agentPhoto"));
            documents.put("CertOfIncorporation_", request.getPart("agentCertOfIncorporation"));
            documents.put("BoardResolution_", request.getPart("agentBoardResolution"));
            documents.put("RepPassport_", request.getPart("agentPassportOfRep"));
            documents.put("RepIDCard_", request.getPart("agentIDCardOfRep"));
            documents.put("RepUtilityBill_", request.getPart("agentUtilityBillOfRep"));
            
           documents.forEach((String typeAlias , Part path) -> {
                DocumentType doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", typeAlias)
                        .getSingleResult();
                Document doc = new Document();
               
                String saveName = typeAlias + unixTime + "_"+ temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(path);
                String subdir = "agents";
                String  dir = "";
                
                switch(typeAlias)
                {
                    case "CompanyLogo_":
                        temp_agent.setPhotoPath(saveName); 
                        break;
                        
                    
                    case "CertOfIncorporation_":
                        subdir = subdir + fUpload.getFileSeparator() + "CertOfIncorporations" ;
                         dir = "/CertOfIncorporations";
                        break;
                        
                    case "BoardResolution_": 
                        subdir = subdir + fUpload.getFileSeparator() + "BoardResolutions" ;
                         dir = "/BoardResolutions";
                        break;
                        
                    case "RepPassport_": 
                        subdir = subdir + fUpload.getFileSeparator() + "RepPassports" ;
                         dir = "/RepPassports";
                        break;
                        
                    case "RepIDCard_": 
                        subdir = subdir + fUpload.getFileSeparator() + "RepIDCards" ;
                         dir = "/RepIDCards";
                        break;
                        
                    case "RepUtilityBill_": 
                        subdir = subdir + fUpload.getFileSeparator() + "RepUtilityBills" ;
                         dir = "/RepUtilityBills";
                        break;
                }
                
                
                doc.setDocTypeId(doctype);
                doc.setOwnerId(BigInteger.valueOf(temp_agent.getAgentId()));
                doc.setOwnerTypeId(temp_agent.getSystemUserTypeId());
                doc.setPath("agents" + dir + "/"+saveName);
                doc.setCreatedDate(CustomerController.getDateTime().getTime());
                
                em.persist(doctype);
                em.persist(doc);
                
                try 
                {
                    fUpload.uploadFile(path, subdir, saveName, true);
                }catch(IOException e){
                    e.printStackTrace();
                }
            });
                
                agentFileName = agent.getPhotoPath();
                em.persist(agent); 
                em.flush();
                 
                //set up the account for this unit
                Account account = new AccountManager().createAgentAccount(agent);
                
                //now link the unit and the account by updating the agent
                em.refresh(agent);
                agent.setAccount(account);
                em.flush();
                
                em.getTransaction().commit();
                String Url = request.getServerName()+"/"+request.getContextPath();
                new EmailHelper().sendUserWelcomeMessageAndPassword(agent.getEmail() , company.getEmail() , initPass , agent , company,Url );
                insertStatus = true;
                if(sessionUser == null)
                    sessionUser = (SystemUser)agent;
                
                new TrailableManager(agent).registerInsertTrailInfo(sessionUser.getSystemUserId());
                em.refresh(agent);  
                
                //HANDLE SYSTEM ID
                //set the agent ID into the generic user table
                GenericUser systemuser = setUpAgentGenericRecord(agent);
                
                //update the agent record with the generic id 
                setAgentGenericId(agent, systemuser);
                 
                em.close();
                emf.close();
                
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                request.setAttribute("action" , "edit");
                insertStatus = true;
            }
            catch(PropertyException e){
                agent = new Agent();
                e.printStackTrace();
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                request.setAttribute("agentPhotoHidden",agentFileName);
                //errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", /*agent.getAgentId().toString()*/"Insert Request ", e.getMessage());
            }
            catch(RollbackException e){
                agent = new Agent();
                e.printStackTrace();
                //System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                //errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", /*agent.getAgentId().toString()*/"Insert Request ", e.getMessage());
            }
            catch(Exception e){
                agent = new Agent();
                e.printStackTrace();
                //System.out.println("inside catch area: " + e.getMessage());
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agent", agent);
                request.setAttribute("action", action);
                //errorMessages.put("errormessage", e.getMessage());
                request.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", /*agent.getAgentId().toString()*/"Insert Request ", e.getMessage());
            }
           
           if(insertStatus && requestOrigin.equalsIgnoreCase("agent_registration")){
                viewFile = "/views/agent/success.jsp";
            }
            
            if(requestOrigin.equalsIgnoreCase("agent_registration"))
            {
                request.setAttribute("from", "agent_registration");
            }
            request.setAttribute("Banks", BankService.getAllBanks());
            request.setAttribute("corporate", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
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
            response.flushBuffer();
            new AlertManager().sendAgentApprovalAlerts(agent);
            //System.out.println("jsonResponse: " + jsonResponse);
           }
//        catch(PropertyException err){
//                err.printStackTrace();
//                //System.out.println("inside catch area: " + err.getMessage());
//                  
//                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), err.getMessage());
//            }
          catch(Exception e){
               
                e.printStackTrace();
                //System.out.println("System Error: " + e.getMessage());
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

//            //System.out.println("jsonResponse: " + jsonResponse);
           }
//        catch(PropertyException err){
//                err.printStackTrace();
//                //System.out.println("inside catch area: " + err.getMessage());
//                  
//                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), err.getMessage());
//            }
            catch(Exception e){
               
                e.printStackTrace();
                //System.out.println("System Error: " + e.getMessage());
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
//                    //System.out.println("request attribute: " + request.getAttributeNames().nextElement());
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
    
    protected void processCorporateUpdateRequest(HttpServletRequest request, HttpServletResponse response)
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
                
               //validate(agent,request);
                agent.setFirstname(request.getParameter("agentFirstname"));
               agent.setEmail(request.getParameter("agentEmail").toLowerCase());
               agent.setCorporate(true);
               agent.setRCNumber(request.getParameter("agentRCNumber"));
                agent.setStreet(request.getParameter("agentStreet"));
                agent.setCity(request.getParameter("agentCity"));
                agent.setState(request.getParameter("agentState"));
                agent.setPhone(request.getParameter("agentPhone"));
                //agent.setBankName(request.getParameter("agentBankName"));
                Bank bank = em.find(Bank.class, Integer.parseInt(request.getParameter("agentBankId")));
                agent.setBank(bank);
                agent.setBankAcctNumber(request.getParameter("agentBankAccountNumber"));
                agent.setBankAcctName(request.getParameter("agentBankAccountName"));
                agent.setModifiedDate(AgentController.getDateTime().getTime());
                if(sessionUser != null)
                   agent.setModifiedBy(sessionUser.getSystemUserId());
               //persist only on save mode
                em.persist(agent);   
               
            final Agent temp_agent = agent;
            FileUploader fUpload  = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);
           
            //loops that will get the Documents of Directors
            Enumeration<String> parameterName = request.getParameterNames();
            int count = 1;
            while(parameterName.hasMoreElements())
            {
                String pName = parameterName.nextElement();
                if(pName.contains("agentDirectorName") && !request.getParameter(pName).trim().isEmpty())
                {
                    int index = Integer.parseInt(pName.substring(17));
                    Director director = new Director();
                    director.setName(request.getParameter(pName));
                    director.setAgent(agent);
                    
                    //Setting up  The Passport Document
                    
                    Part part = request.getPart("agentDirectorPassport"+index);
               
                    String saveName = "Director_Passport_"+ unixTime +count  +"_"+ temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(part);
                    String  dir = "agents" + fUpload.getFileSeparator() + "Directors" + fUpload.getFileSeparator() + "Passports" ;
                    
                    DocumentType doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", "Director_Passport_")
                        .getSingleResult();
                    
                    Document doc = new Document();
                    
                    doc.setDocTypeId(doctype);
                    doc.setPath("agents/Directors/Passports/" + saveName);
                    doc.setOwnerId(BigInteger.valueOf(agent.getAgentId()));
                    doc.setOwnerTypeId(agent.getSystemUserTypeId());
                    doc.setCreatedDate(CustomerController.getDateTime().getTime());
                    
                    em.persist(doctype);
                    em.persist(doc);
                    em.flush();
                    try 
                     {
                        fUpload.uploadFile(part, dir, saveName, true);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    
                    //done setting Up the passport
                    //Now lets set The director passport Id 
                    director.setPassport(doc.getId());
                    
                    //Now Lets setUp The ID card 
                     part = request.getPart("agentDirectorIDCard"+index);
               
                     saveName = "Director_IDCard_" + unixTime + count +"_"+  temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(part);
                      dir = "agents" + fUpload.getFileSeparator() + "Directors" + fUpload.getFileSeparator() + "IDCards";
                    
                     doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", "Director_IDCard_")
                        .getSingleResult();
                    
                     doc = new Document();
                    
                    doc.setDocTypeId(doctype);
                    doc.setPath("agents/Directors/IDCards/" + saveName);
                    doc.setOwnerId(BigInteger.valueOf(agent.getAgentId()));
                    doc.setOwnerTypeId(agent.getSystemUserTypeId());
                    doc.setCreatedDate(CustomerController.getDateTime().getTime());
                    
                    em.persist(doctype);
                    em.persist(doc);
                    em.flush();
                    try 
                     {
                        fUpload.uploadFile(part, dir, saveName, true);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    
                    //Done saving The ID Card
                    //Now Lets Load it up into the Director Object
                    director.setiDCard(doc.getId());
                    
                    //Now that we are done dealing with the Director lets Persist The Director
                    em.persist(director);
                    em.flush();
                    count ++;
                }
            }
           
            //We are going to put all the image into an Hash map specifying the title of the Image also
            Map<String, Part>  documents = new HashMap<>();
            documents.put("CompanyLogo_", request.getPart("agentPhoto"));
            documents.put("CertOfIncorporation_", request.getPart("agentCertOfIncorporation"));
            documents.put("BoardResolution_", request.getPart("agentBoardResolution"));
            documents.put("RepPassport_", request.getPart("agentPassportOfRep"));
            documents.put("RepIDCard_", request.getPart("agentIDCardOfRep"));
            documents.put("RepUtilityBill_", request.getPart("agentUtilityBillOfRep"));
            
           documents.forEach((String typeAlias , Part path) -> {
               
               if(path != null && !path.getSubmittedFileName().trim().isEmpty()){
                DocumentType doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", typeAlias)
                        .getSingleResult();
                Document doc = new Document();
                
                //Remove previous Document of This Type
                //formal document
                List<Document> fDoc = em.createNamedQuery("Document.findByOwnerIdDoctypeIdOwnerTypeId")
                                       .setParameter("ownerID", temp_agent.getAgentId() )
                                       .setParameter("docTypeId", doctype )
                                       .setParameter("ownerTypeId", temp_agent.getSystemUserTypeId())
                                       .getResultList();
                if(fDoc != null && !fDoc.isEmpty())
                {
                    for(Document tDoc : fDoc)
                        em.remove(tDoc);
                }
               
                String saveName = typeAlias + unixTime + "_"+ temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(path);
                String subdir = "agents";
                String  dir = "";
                
                switch(typeAlias)
                {
                    case "CompanyLogo_":
                        temp_agent.setPhotoPath(saveName); 
                        break;
                        
                    
                    case "CertOfIncorporation_":
                        subdir = subdir + fUpload.getFileSeparator() + "CertOfIncorporations" ;
                         dir = "/CertOfIncorporations";
                        break;
                        
                    case "BoardResolution_": 
                        subdir = subdir + fUpload.getFileSeparator() + "BoardResolutions" ;
                         dir = "/BoardResolutions";
                        break;
                        
                    case "RepPassport_": 
                        subdir = subdir + fUpload.getFileSeparator() + "RepPassports" ;
                         dir = "/RepPassports";
                        break;
                        
                    case "RepIDCard_": 
                        subdir = subdir + fUpload.getFileSeparator() + "RepIDCards" ;
                         dir = "/RepIDCards";
                        break;
                        
                    case "RepUtilityBill_": 
                        subdir = subdir + fUpload.getFileSeparator() + "RepUtilityBills" ;
                         dir = "/RepUtilityBills";
                        break;
                }
                
                
                doc.setDocTypeId(doctype);
                doc.setOwnerId(BigInteger.valueOf(temp_agent.getAgentId()));
                doc.setOwnerTypeId(temp_agent.getSystemUserTypeId());
                doc.setPath("agents" + dir + "/"+saveName);
                doc.setCreatedDate(AgentController.getDateTime().getTime());
                
                em.persist(doctype);
                em.persist(doc);
                
                try 
                {
                    fUpload.uploadFile(path, subdir, saveName, true);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
           });
                
                agentFileName = agent.getPhotoPath();
                em.persist(agent); 
                em.flush();
               
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                request.setAttribute("corporate", true);
                request.setAttribute("action", "edit");
                
                String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
                log("imageAccessDirPath: " + imageAccessDirPath);
                request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
                request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agentkins");
                
            }//<editor-fold>
//            catch(PropertyException err){
//                err.printStackTrace();
//                //System.out.println("inside catch area: " + err.getMessage());
//                
//                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
//                request.setAttribute("agentPhotoHidden",agentFileName);
//                request.setAttribute("agent", agent);
//                request.setAttribute("errors", errorMessages);   
//                request.setAttribute("corporate", true);
//                request.setAttribute("action", "edit");
//                SystemLogger.logSystemIssue("Agent", agent.getAgentId().toString(), err.getMessage());
//            }</editor-fold>
            catch(Exception e){
                e.printStackTrace();
                //System.out.println("System Error: " + e.getMessage());
                 request.setAttribute("corporate", true);
                request.setAttribute("action", "edit");
                SystemLogger.logSystemIssue("Agent", agent.getAgentId().toString(), e.getMessage());
            }
            request.setAttribute("Banks", BankService.getAllBanks());
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
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
               
                if(agent.isCorporate())
                {
                    processCorporateUpdateRequest(request , response);
                    return;
                }
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
               String formattedDate = sdf.format(date);
              
               
               long unixTime = System.currentTimeMillis() / 1000L;
                
               //validate(agent,request);
               agent.setFirstname(request.getParameter("agentFirstname"));
                agent.setLastname(request.getParameter("agentLastname"));
                agent.setMiddlename(request.getParameter("agentMiddlename"));
                agent.setEmail(request.getParameter("agentEmail").toLowerCase());
               
                agent.setStreet(request.getParameter("agentStreet"));
                agent.setCity(request.getParameter("agentCity"));
                agent.setState(request.getParameter("agentState"));
                agent.setPhone(request.getParameter("agentPhone"));
                //agent.setBankName(request.getParameter("agentBankName"));
                Bank bank = em.find(Bank.class, Integer.parseInt(request.getParameter("agentBankId")));
                agent.setBank(bank);
                agent.setBankAcctNumber(request.getParameter("agentBankAccountNumber"));
                agent.setBankAcctName(request.getParameter("agentBankAccountName"));
                agent.setKinName(request.getParameter("agentKinName"));
                agent.setKinPhone(request.getParameter("agentKinPhone"));
                agent.setKinAddress(request.getParameter("agentKinAddress"));
                agent.setKinRelationship(request.getParameter("agentKinRelationship"));
                agent.setModifiedDate(AgentController.getDateTime().getTime());
                if(sessionUser != null)
                    agent.setModifiedBy(sessionUser.getSystemUserId());
               
            //We are going to put all the image into an Hash map specifying the title of the Image also
            Map<String, Part>  documents = new HashMap<>();
            documents.put("agent_", request.getPart("agentPhoto"));
            documents.put("agentkin_", request.getPart("agentKinPhoto"));
            documents.put("agent_ID_Card_", request.getPart("agentPhotoID"));
            documents.put("agent_utility_bill_", request.getPart("utilityBill"));
            
           
           FileUploader fUpload  = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);
           final Agent temp_agent = agent;
           documents.forEach((String typeAlias , Part path) -> {
               
               if(path != null && !path.getSubmittedFileName().trim().isEmpty())
               {
                DocumentType doctype = (DocumentType)em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", typeAlias)
                        .getSingleResult();
                Document doc = new Document();
                
                //Remove previous Document of This Type
                //formal document
                List<Document> fDoc = em.createNamedQuery("Document.findByOwnerIdDoctypeIdOwnerTypeId")
                                       .setParameter("ownerID", temp_agent.getAgentId() )
                                       .setParameter("docTypeId", doctype )
                                       .setParameter("ownerTypeId", temp_agent.getSystemUserTypeId())
                                       .getResultList();
                if(fDoc != null && !fDoc.isEmpty())
                {
                    for(Document tDoc : fDoc)
                        em.remove(tDoc);
                }
               
                String saveName = typeAlias + unixTime +"_"+ temp_agent.getAgentId()+"." + FileUploader.getSubmittedFileExtension(path);
                String subdir = "agents";
                String  dir = "";
                
                switch(typeAlias)
                {
                    case "agent_":
                        temp_agent.setPhotoPath(saveName); 
                        break;
                        
                    case "agentkin_":
                        temp_agent.setKinPhotoPath(saveName); 
                        subdir = subdir + fUpload.getFileSeparator() + "agentkins" ;
                        dir = "/agentkins";
                        break;
                       
                    case "agent_ID_Card_":
                        subdir = subdir + fUpload.getFileSeparator() + "ids" ;
                         dir = "/ids";
                        break;
                        
                    case "agent_utility_bill_": 
                        subdir = subdir + fUpload.getFileSeparator() + "utilitybills" ;
                         dir = "/utilitybills";
                        break;
                }
                
                
                doc.setDocTypeId(doctype);
                doc.setOwnerId(BigInteger.valueOf(temp_agent.getAgentId()));
                doc.setOwnerTypeId(temp_agent.getSystemUserTypeId());
                doc.setPath("agents" + dir + "/"+saveName);
                doc.setCreatedDate(AgentController.getDateTime().getTime());
                
                em.persist(doctype);
                em.persist(doc);
                
                try 
                {
                    fUpload.uploadFile(path, subdir, saveName, true);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
           });
                
                agentFileName = agent.getPhotoPath();
                agentKinFileName = agent.getKinPhotoPath();
                em.persist(agent); 
                em.flush();
               
                em.getTransaction().commit();
                
                em.close();
                emf.close();
                
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("agents", listAgents());
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                request.setAttribute("corporate", false);
                request.setAttribute("action", "edit");
                
                String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
                log("imageAccessDirPath: " + imageAccessDirPath);
                request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
                request.setAttribute("agentKinImageAccessDir", imageAccessDirPath + "/agentkins");
                
            }
        //<editor-fold>
//            catch(PropertyException err){
//                err.printStackTrace();
//                //System.out.println("inside catch area: " + err.getMessage());
//                
//                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
//                request.setAttribute("agentPhotoHidden",agentFileName);
//                request.setAttribute("agent", agent);
//                request.setAttribute("errors", errorMessages);    
//                SystemLogger.logSystemIssue("Agent", agent.getAgentId().toString(), err.getMessage());
//            }</editor-fold>
            catch(Exception e){
                e.printStackTrace();
                //System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Agent", agent.getAgentId().toString(), e.getMessage());
            }
            request.setAttribute("Banks", BankService.getAllBanks());
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    /*TP: Validation is done here*/
    private void validate(Agent agent, HttpServletRequest request) throws PropertyException, ServletException, IOException {
         errorMessages.clear();
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
         EntityManager em = emf.createEntityManager();
         Query jpqlQuery  = em.createNamedQuery("Agent.findByEmail");
         String email = request.getParameter("agentEmail") != null ? request.getParameter("agentEmail").toLowerCase().trim() : "";
         jpqlQuery.setParameter("email",email);
         List<Agent> agentDetails = jpqlQuery.getResultList();
         
         
        //Validating refCode
         String refCode = request.getParameter("refCode");
         if(refCode != null && !refCode.trim().isEmpty())
         {
             if(refCode.contains("AG000"))
             {
                Account acc;
                
                try
                {
                    acc = (Account)em
                .createNamedQuery("Account.findByAccountCode")
                .setParameter("accountCode", refCode)
                .getSingleResult();
                    if(acc == null)
                       errorMessages.put("errors0", "Invalid Referral Code"); 
                }
                catch(Exception e)
                {
                    errorMessages.put("errors0", "Invalid Referral Code");
                }
             }
             else
             {
                 errorMessages.put("errors0", "Invalid Referral Code");
             }
         }
         
         String formFieldStage1[][] = {{"agentFirstname", "", "Agent First Name"},
             //{"agentMiddlename", "", "Agent Middle Name"},
             {"agentLastname", "", "Agent Last Name"},
             {"agentEmail", "", "Agent Email"},
             {"agentPhone", "", "Agent Phone"},
             {"agentStreet", "", "Agent  Address"},
             {"agentCity", "", "Agent Operational City Name"},
            {"agentState", "", "Agent Operational State Name"},
            {"agentBankId", "select", "Agent Bank Name"},
            {"agentBankAccountName", "", "Agent Bank Account Name"},
            {"agentBankAccountNumber", "", "Agent Bank Account Number"},
            {"agentKinNames", "", "Agent Next Of Kin Name"},
            {"agentKinPhone", "", "Agent Next Of Kin Phone Number"},
            {"agentKinAddress", "", "Agent Next Of Kin Address"},
            {"agentKinRelationship", "", "Agent Next Of Kin Relationship"}};

    String formFieldStage2[][] = {{"agentPhoto", "", "Agent PassPort"},
        {"agentKinPhoto", "", "Agent Next Of Kin PassPort"},
        {"utilityBill", "", "Agent Utility Bill"},
        {"agentPhotoID", "", "Agent ID card /Driver Lincense , etc"}};

        
         int error = 1;
        for(String field[] : formFieldStage1)
        {
            String parameter = request.getParameter(field[0]);
            if(parameter != null)
            {
                if(parameter.trim().isEmpty() || parameter.trim().equals("select"))
                {
                    if(field[1].isEmpty())
                    {
                        errorMessages.put("errors"+error, "Please Enter Value For " + field[2]);
                    }
                    else if(field[1].equals("select"))
                    {
                        errorMessages.put("errors"+error, "Please Select Value For " + field[2]);
                    }
                      error ++;  
                }
            }
        }
 
        for(String field[] : formFieldStage2)
        {
            String fileName = request.getPart(field[0]).getSubmittedFileName();
            if(fileName != null)
            {
                if(fileName.trim().isEmpty())
                {
                    errorMessages.put("errors"+error, "Please Upload File For " + field[2]);
                    error ++;  
                }
            }
        }
        
        if(!agentDetails.isEmpty())
        {
            errorMessages.put("errors"+error, "Email Already Exit");
            error ++;
        }
       
       boolean validateFile = FileUploader.validateFile(request.getParts());
       if(!validateFile)
       {
           errorMessages.put("errors"+error, "One or More Upload Files are Invalid (Please Upload Image File With Max Size Of 2MB)");
       }
        if(!(errorMessages.isEmpty())) throw new PropertyException("Error While Validating Agent Registration/Update Details");
    }
    
    private void validateCorporate(Agent agent, HttpServletRequest request) throws PropertyException, ServletException, IOException {
         errorMessages.clear();
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
         EntityManager em = emf.createEntityManager();
         Query jpqlQuery  = em.createNamedQuery("Agent.findByEmail");
         String email = request.getParameter("agentEmail") != null ? request.getParameter("agentEmail").toLowerCase().trim() : "";
         jpqlQuery.setParameter("email",email);
         List<Agent> agentDetails = jpqlQuery.getResultList();
         
         //Validating refCode
         String refCode = request.getParameter("refCode");
         if(refCode != null && !refCode.trim().isEmpty())
         {
             if(refCode.contains("AG000"))
             {
                Account acc;
                
                try
                {
                    acc = (Account)em
                .createNamedQuery("Account.findByAccountCode")
                .setParameter("accountCode", refCode)
                .getSingleResult();
                    if(acc == null)
                       errorMessages.put("errors0", "Invalid Referral Code"); 
                }
                catch(Exception e)
                {
                    errorMessages.put("errors0", "Invalid Referral Code");
                }
             }
             else
             {
                 errorMessages.put("errors0", "Invalid Referral Code");
             }
         }
         
        String formFieldStage1[][] = {
            {"agentFirstname", "", "Company Name"},
            {"agentRCNumber", "", "Company RC Number"},
            {"agentEmail", "", "Company Email"},
            {"agentPhone", "", "Company Office Phone Number"},
            {"agentStreet", "", "Company's Address"},
        {"agentCity", "", "Company's City"},
        {"agentState", "", "Company's State "},
        {"agentBankId", "select", "Company's Bank Name"},
        {"agentBankAccountName", "", "Company's Bank Account Name"},
        {"agentBankAccountNumber", "", "Company's Bank Account Number"},
        {"agentDirectorName1", "", "Name Of A Director"}, //This should not be classified here actually
        };

    String formFieldStage2[][]= {
        {"agentPhoto", "", "Company Logo"},
        {"agentCertOfIncorporation", "", "Company Certificate of Incorporation"},
        {"agentBoardResolution", "", "Company Board Resolution"},
        {"agentPassportOfRep", "", "Passport Of A Representative"},
        {"agentIDCardOfRep", "", "Representative IDCard"},
        {"agentUtilityBillOfRep", "", "Utility Bill Of Representative"},
        {"agentDirectorPassport1", "", "Director Passport"},
        {"agentDirectorIDCard1", "", "Director IDCard"}
        };

        
         int error = 0;
        for(String field[] : formFieldStage1)
        {
            String parameter = request.getParameter(field[0]);
            if(parameter != null)
            {
                if(parameter.trim().isEmpty() || parameter.trim().equalsIgnoreCase("select"))
                {
                    if(field[1].isEmpty())
                    {
                        errorMessages.put("errors"+error, "Please Enter Value For " + field[2]);
                    }
                    else if(field[1].equalsIgnoreCase("select"))
                    {
                        errorMessages.put("errors"+error, "Please Select Value For " + field[2]);
                    }
                      error ++; 
                }
            }
        }
 
        for(String field[] : formFieldStage2)
        {
            String fileName = request.getPart(field[0]).getSubmittedFileName();
            if(fileName != null)
            {
                if(fileName.trim().isEmpty())
                {
                    errorMessages.put("errors"+error, "Please Upload File For " + field[2]);
                    error ++;  
                }
            }
        }
        
        if(!agentDetails.isEmpty())
        {
            errorMessages.put("errors"+error, "Email Already Exit");
            error ++;
        }
       
       boolean validateFile = FileUploader.validateFile(request.getParts());
       if(!validateFile)
       {
           errorMessages.put("errors"+error, "One or More Upload Files are Invalid (Please Upload Image File With Max Size Of 2MB)");
       }
        if(!(errorMessages.isEmpty())) throw new PropertyException("Error While Validating Agent Registration/Update Details");
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
        
        
        //Lets Validate the Withdrawal Request here
        boolean valid = false;
        
        //prevent a zero and negative amount
        if(amount > 0d)
        {
            valid = true;
        }
   
        double  eligible = agent.getEligibleWithdrawalBalance()-1000;
        if(eligible < 0d)
            eligible = 0d;
        if(valid && (amount <= eligible ))
        {
            valid = true;
        }
        else
        {
            valid = false;
        }
        
        em.close();
        emf.close();
        
        if(valid)
        {
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
        }
        
        response.setContentType("text/html;charset=UTF-8");
        if(valid)
        {
        response.getWriter().write("1");
        }
        else
        {
        response.sendError(401);
        }
    }
    
    private List<Map> getPendingWithdrawalRequest(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query jplQuery = em.createNamedQuery("Withdrawal.findByApproved");
        jplQuery.setParameter("approved", (short)0);
        
        List<Withdrawal> pendingWithdrawal = jplQuery.getResultList();
        
        List<Map> pendingWithdrawalMap = new ArrayList();
        
        for(Withdrawal w : pendingWithdrawal){
            
            Map<String, String> map = new HashMap();
            
            map.put("id", w.getId().toString());
            map.put("agentFullName", w.getAgent().getFullName());
            map.put("agentId", w.getAgent().getAgentId().toString());
            map.put("amount", String.format("%.2f", w.getAmount()));
            map.put("balance", String.format("%.2f", w.getAgent().getEligibleWithdrawalBalance()));
            
            pendingWithdrawalMap.add(map);
        }
        
        emf.getCache().evictAll();
        em.close();
        emf.close();
        
        return pendingWithdrawalMap;
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
        
        //Long notificationId = Long.parseLong(req.getParameter("nof_id"));
        //Notification notification = em.find(Notification.class, notificationId);
        
        WithdrawalManager manager = new WithdrawalManager(sessionUser);
        manager.processWithdrawalDisApproval(withdrawal, req.getContextPath());
        
        res.setContentType("text/html");
        res.getWriter().write("1");
    }
    
    private void changeAgentPassword(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();
            
            
            
            Map<String, String> resMap = new HashMap();
            
            long id = Long.parseLong(request.getParameter("id"));
            
            String oldPassword = request.getParameter("old_password");
            String pwd1 = request.getParameter("pwd1");
            String pwd2 = request.getParameter("pwd2");
            
            //System.out.println("Old Password : " + oldPassword + " Id : " + id);
            em.getTransaction().begin();
            
            Agent agent = em.find(Agent.class, id);
            
            
            
            if(AuthManager.check(oldPassword, agent.getPassword())){
                
                if(pwd1.equals(pwd2) && !pwd1.equals("")){
                    agent.setPassword(AuthManager.getSaltedHash(pwd1));
                    resMap.put("success", "Password changed successfully");
                }else{
                    resMap.put("error", "Password and Re-enter password do not match");
                }
                 
            }else{
                resMap.put("error", "Invalid old password");
            }
            
            if(agent != null){
                em.merge(agent);
                
            }
            
            em.getTransaction().commit();
            em.close();
            emf.close();
            
            Gson gson = new GsonBuilder().create();
            
            String json = gson.toJson(resMap);
            
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
            
            
        } catch (Exception ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

    /**
     * This method is called when admin request for agent account history via Ajax
     * @param request
     * @param response
     * @throws IOException 
     */
    private Map<String, Object> getAgentAccountHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        em = emf.createEntityManager();
        
        long id = Long.parseLong(request.getParameter("id"));
        
        Agent agent = em.find(Agent.class, id);
        
        Account acct = agent.getAccount();
        String acctCode = acct.getAccountCode();
        
        Query query = em.createNamedQuery("Transaction.findByAccount");
        query.setParameter("debitAccount", acct);
        query.setParameter("creditAccount", acct);
        
        List<Transaction> transactionList = query.getResultList();
        
        double agentBalance = agent.getEligibleWithdrawalBalance();
        
        List<Map> transactionMapList = new ArrayList();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
        double totalCredit = 0 , totalDebit = 0 ;
        for(Transaction t : transactionList){
            
            Map<String, String> map = new HashMap();
            
            map.put("amount", String.format("%.2f", t.getAmount()));
            map.put("date", sdf.format(t.getTransactionDate()));
            if(t.getCreditAccount().getAccountCode().equalsIgnoreCase(acctCode)){
                map.put("type", "credit");
                totalCredit += t.getAmount();
            }
            else{
                map.put("type", "debit");
                totalDebit += t.getAmount();
            }
            
            transactionMapList.add(map);
        }
        
        
        HashMap<String, String> agentDetails = new HashMap();
        agentDetails.put("balance", String.format("%.2f",agentBalance >= minimumBalance ? (agentBalance - minimumBalance) : 0.0));
        agentDetails.put("ledgerBalance", String.format("%.2f",agentBalance));
        agentDetails.put("accountCode", acctCode);
        
        Map historyMap = new HashMap<String, Object>();
        historyMap.put("transactionMapsList", transactionMapList);
        historyMap.put("agentDetails", agentDetails);
        historyMap.put("totalCredit" ,  totalCredit);
        historyMap.put("totalDebit" , totalDebit);
        
        return historyMap;
    }

    private void getAgentAccountHistory(HttpServletRequest request) {
        
        em = emf.createEntityManager();
        
        
        Account acct = ((Agent)sessionUser).getAccount();
        String acctCode = ((Agent)sessionUser).getAccount().getAccountCode();
        
        Query query = em.createNamedQuery("Transaction.findByAccount");
        query.setParameter("debitAccount", acct);
        query.setParameter("creditAccount", acct);
        
        List<Transaction> transactionList = query.getResultList();
        
        Agent agent = em.find(Agent.class,sessionUser.getSystemUserId() );
        
        double agentBalance = agent.getEligibleWithdrawalBalance();
        
        List<Map> transactionListMap = new ArrayList();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
        
        Double balance = 0.0;
        Double totalCredit = 0.0 , totalDebit  = 0.0;
        
        for(Transaction t : transactionList){
            
            Map<String, Object> map = new HashMap();
            
            map.put("amount", String.format("%.2f", t.getAmount()));
            map.put("date", sdf.format(t.getTransactionDate()));
            if(t.getCreditAccount().getAccountCode().equalsIgnoreCase(acctCode)){
                map.put("type", "Credit");
                balance += t.getAmount();
                totalCredit += t.getAmount();
                map.put("accbalance" , String.format("%.2f", balance));
            }
            else{
                map.put("type", "Debit");
                balance -= t.getAmount();
                totalDebit += t.getAmount();
                map.put("accbalance" , String.format("%.2f", balance));
            }
            map.put("id", t.getId());
            transactionListMap.add(map);
        }
        
        //lets us arrange the map in decending order item
        transactionListMap.sort((Map a , Map b) ->{ return ((Long)(b.get("id"))).compareTo((Long)(a.get("id"))); });
        
        request.setAttribute("transactions",transactionListMap);
        request.setAttribute("balance", agentBalance - minimumBalance);
        request.setAttribute("ledgerBalance", agentBalance);
        request.setAttribute("totalDedit", totalDebit);
        request.setAttribute("totalCredit", totalCredit);
        request.setAttribute("accountCode", acctCode);
        
    }
    
    /**
     * 
     * @param agent the agent whose network is to be gotten
     * @return 
     */
    private List<Agent> getNetwork(Agent agent){
        List networkList = (List)em.createNamedQuery("Agent.findNetwork").setParameter("agentId", agent.getAgentId()).getResultList();
        return networkList;
    }

    public static Calendar getDateTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        return calendar;
    }

     private void validateEmail(HttpServletRequest request, HttpServletResponse response) {

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();

            String email = request.getParameter("email") != null ? request.getParameter("email").toLowerCase().trim() : "";
            
            if(email == null)
                return;
            Query query = em.createNamedQuery("Agent.findByEmail");
            query.setParameter("email", email);

            List<Agent> agent = query.getResultList();

            //System.out.println("Agent count : " + agent.size());

            Integer code = agent.size() == 0 ? 1 : -1;

            Gson gson = new GsonBuilder().create();

            Map<String, String> map = new HashMap();
            map.put("code", code.toString());

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     private void getReferrerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
         
        String refCode = request.getParameter("refCode");
        
        if(!(refCode != null && !refCode.trim().isEmpty()))
        {
            response.sendError(404, "Error");
            return;
        }
        
        ////System.out.println(refCode);
        response.setStatus(200);
        
        //Agent Detail
        HashMap<String , String> aDtail = new HashMap<>();
        if(em == null)
        em = AppController.emf.createEntityManager();
        
        Account acc = (Account)em
                .createNamedQuery("Account.findByAccountCode")
                .setParameter("accountCode", refCode)
                .getSingleResult();
       
        Agent agent = em.find(Agent.class, acc.getRemoteId());
        
        aDtail.put("imgPath", agent.getPhotoPath());
        aDtail.put("name", agent.getFullName());
       
       String Output = gson.toJson(aDtail);
       response.getOutputStream().println(Output);
     }
     
     private void processPayout(HttpServletRequest request, HttpServletResponse response) throws IOException{
                 //Lets first get the list of all approved withdrawals
                 if(em != null && em.isOpen()){}
                 else { em = emf.createEntityManager();}
       List<Withdrawal> withdrawals = em.createNamedQuery("Withdrawal.findByApproved")
                                        .setParameter("approved",1 )
                                        .getResultList();
       List<Map<String,String>>  payOut = new ArrayList<>();
       
       String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM/d/uuuu"));
       Date tDate = new Date(System.currentTimeMillis());
       Account agentCommission = (Account)em.createNamedQuery("Account.findByAccountCode").setParameter("accountCode", "AGENT_COMMISSION").getSingleResult();
       
       em.getTransaction().begin();
       for(Withdrawal w : withdrawals)
       {
           Map<String,String> agentRequest = new HashMap<>();
           agentRequest.put("PaymentAmount", String.format("%,.2f",w.getAmount()));
           agentRequest.put("PaymentDate",date);
           agentRequest.put("Reference", "\"\"");
           agentRequest.put("Remark", "AGENCY COMMISSION PAYMENT");
           agentRequest.put("VendorCode", w.getAgent().getBankAcctName());
           agentRequest.put("VendorName", w.getAgent().getBankAcctName());
           agentRequest.put("VendorAccountNumber", w.getAgent().getBankAcctNumber());
           agentRequest.put("Bank Sort Code", w.getAgent().getBank().getSortCode());
           payOut.add(agentRequest);
           //We have to debit the agent account with the amount 
           //and debit the agent commision account
          new TransactionManager(sessionUser).doDoubleEntry(w.getAgent().getAccount(), agentCommission, w.getAmount());
          w.setApproved((short)3);
          w.setModifiedDate(tDate);
          w.setModifiedBy(sessionUser.getSystemUserId());
          em.persist(w);
        }
       
       //Lets us now write our CSV File 
       //comma delimited
       StringBuilder csv = new StringBuilder();
       csv.append("PaymentAmount,PaymentDate,Reference,Remark,VendorCode,VendorName,VendorAccountNumber,Bank Sort Code,\n");
       
       for(Map<String,String> agentRequest : payOut)
       {
         csv.append("\"").append(agentRequest.get("PaymentAmount")).append("\"," )
         .append(agentRequest.get("PaymentDate") ).append(",")
         .append(agentRequest.get("Reference")).append(",")
         .append(agentRequest.get("Remark")).append(",")
         .append(agentRequest.get("VendorCode")).append(",")
         .append(agentRequest.get("VendorName")).append(",")
         .append(agentRequest.get("VendorAccountNumber")).append(",")
         .append(agentRequest.get("Bank Sort Code")).append(",\n");
       }
       
       //System.out.print(csv)
       
       response.setContentType("application/csv");
       //response.addHeader("Content-Type", "application/csv");
       response.addHeader("Content-Disposition", "inline; filename=Agent_Disbursement_List_" + LocalDate.now().format(DateTimeFormatter.ofPattern("d-M-uuuu")) + ".csv");
       response.setContentLength( csv.length());
       PrintWriter writer = new PrintWriter(response.getOutputStream());
       writer.append(csv);
       writer.flush();
       writer.close();
       
       em.getTransaction().commit();
     }
}