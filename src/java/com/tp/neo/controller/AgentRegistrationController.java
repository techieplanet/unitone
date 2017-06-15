/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import static com.tp.neo.controller.components.AppController.APP_NAME;
import com.tp.neo.controller.helpers.AccountManager;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.GenericUser;
import com.tp.neo.model.utils.AuthManager;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.PropertyException;

/**
 *
 * @author Prestige
 */
@WebServlet(name="RegisterAgent", urlPatterns = {"/AgentRegistration"})
@MultipartConfig
public class AgentRegistrationController extends AppController {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    private static String AGENTS_REGISTRATION = "/views/agent/registration.jsp";
    private static String AGENTS_NEW = "/views/agent/add.jsp";
    private String action = "";
    private String viewFile = "";
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        action = req.getParameter("action") != null ? req.getParameter("action") : "";
         processInsertRequest(req, resp);
    }
    
    
    
    /*TP: Processes every insert request of request type POST*/
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Agent agent = new Agent();
        
        errorMessages.clear();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String agentFileName = "";
        String agentKinFileName = "";
        viewFile = AGENTS_REGISTRATION;
        
        boolean insertStatus = false;
        long referrerId=0L;
                
        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();
        System.out.println("Referer Linnk: " + request.getHeader("referrer"));
        
        HttpSession session = request.getSession();
        
        //for this class, this is the appropriate line to clear the error variable
        errorMessages.clear();
        
        try{

               em.getTransaction().begin();
               
               Date date = new Date();
               SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
               String formattedDate = sdf.format(date);
               
               long unixTime = System.currentTimeMillis() / 1000L;
                 
                agent.setFirstname(request.getParameter("agentFirstname"));
                agent.setLastname(request.getParameter("agentLastname"));
                agent.setMiddlename(request.getParameter("agentMiddlename"));
                agent.setEmail(request.getParameter("agentEmail"));
                agent.setPassword(AuthManager.getSaltedHash(request.getParameter("agentPassword")));
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
                                
                if(request.getParameter("ref_code") != null){
                    Account referrerAccount = (Account)em.createNamedQuery("Account.findByAccountCode")
                                        .setParameter("accountCode", request.getParameter("ref_code"))
                                        .getSingleResult();
                    agent.setReferrerId(referrerAccount.getRemoteId());
                }else{
                    agent.setReferrerId(0L);
                }
                
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
                    //throw new PropertyException("Please upload agent picture");
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
                
                validate(request);
                
               //persist only on save mode
                em.persist(agent);   
                
                em.flush(); //flush so you can have agent id
                
                //set up the account for this unit
                Account account = new AccountManager().createAgentAccount(agent);
                
                //now link the agent and the account by updating the agent
                em.refresh(agent);
                agent.setAccount(account);
                em.flush();
                
                em.getTransaction().commit();
                
                if(sessionUser == null)
                    sessionUser = (SystemUser)agent;
                
                new TrailableManager(agent).registerInsertTrailInfo(sessionUser.getSystemUserId());
                
                em.refresh(agent);  
                
                
                em.close();
                emf.close();
                
                insertStatus = true;
                
                if(referrerId > 0L){
                    Agent refAgent = em.find(Agent.class, referrerId);
                    request.setAttribute("refAgent", refAgent);
                    request.setAttribute("referralMode", true);
                }
                
                request.setAttribute("agentKinPhotoHidden",agentKinFileName);
                request.setAttribute("agentPhotoHidden",agentFileName);
                request.setAttribute("success",true);
                request.setAttribute("agent", agent);
                
            }
            catch(NoResultException e){
                e.printStackTrace();
                session.setAttribute("agent", agent);
                session.setAttribute("action", action);
                session.setAttribute("agentKinPhotoHidden",agentKinFileName);
                session.setAttribute("agentPhotoHidden",agentFileName);
                errorMessages.put("noResultError", "Enter a valid referral code");
                session.setAttribute("errors", errorMessages);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
            catch(PropertyException e){
                e.printStackTrace();
                session.setAttribute("agent", agent);
                session.setAttribute("action", action);
                session.setAttribute("agentKinPhotoHidden",agentKinFileName);
                session.setAttribute("agentPhotoHidden",agentFileName);
                errorMessages.put("mysqlviolation", e.getMessage());
                session.setAttribute("errors", errorMessages);
                if(referrerId > 0L) session.setAttribute("referralMode", true);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
                System.out.println("Property exception");
            }
            catch(RollbackException e){
                e.printStackTrace();
                System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                session.setAttribute("agentKinPhotoHidden",agentKinFileName);
                session.setAttribute("agentPhotoHidden",agentFileName);
                session.setAttribute("agent", agent);
                session.setAttribute("action", action);
                errorMessages.put("mysqlviolation", e.getMessage());
                session.setAttribute("errors", errorMessages);
                if(referrerId > 0L) session.setAttribute("referralMode", true);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                session.setAttribute("agentKinPhotoHidden",agentKinFileName);
                session.setAttribute("agentPhotoHidden",agentFileName);
                session.setAttribute("agent", agent);
                errorMessages.put("errormessage", e.getMessage());
                session.setAttribute("errors", errorMessages);
                if(referrerId > 0L) session.setAttribute("referralMode", true);
                SystemLogger.logSystemIssue("Agent", gson.toJson(agent), e.getMessage());
            }
           
            if(!insertStatus){
                String redirectString = request.getParameter("redirect") != null ? "AgentRegistration?" + request.getParameter("redirect") : "";
                log("redirect zone: " + redirectString);
                response.sendRedirect(redirectString);
            }
            else{
                session.invalidate();
                String redirectString = "AgentRegistration?action=success";
                String page = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/AgentRegistration?action=success";
                response.sendRedirect(page);
            }
            
    }
    
    
    private void validate(HttpServletRequest request) throws PropertyException, ServletException, IOException {
         
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
            System.out.println("Last name empty: " + request.getParameter("agentLastname"));
        }
        else{
            System.out.println("Last name not empty: " + request.getParameter("agentLastname"));
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        String viewFile = "/views/agent/registration.jsp";
        
        String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
        
        if(action.equalsIgnoreCase("success")){
            viewFile = "/views/agent/success.jsp";
        }else if(action.equalsIgnoreCase("ref")){
            //http://localhost:8080/NeoForce/AgentRegistration?action=ref&refCode=QUcwMDAyOA==&refId=Mjg=
            viewFile = "/views/agent/registration.jsp";
            String refCodeEncoded = req.getParameter("refCode") != null ? req.getParameter("refCode") : "";
            String targetEncoded = req.getParameter("target") != null ? req.getParameter("target") : "";
                    
            String refIdEncoded = req.getParameter("refId") != null ? req.getParameter("refId") : "";
            byte[] decodedBytes = Base64.getDecoder().decode(refIdEncoded);
            String refIdDecoded = new String(decodedBytes);
            
            Agent agent = em.find(Agent.class, Long.parseLong(refIdDecoded));
            req.setAttribute("refAgent", agent);
            req.setAttribute("referralMode", true);
            req.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
            req.setAttribute("redirectString", "action="+action+"&refCode="+refCodeEncoded+"&refId="+refIdEncoded+"&target="+targetEncoded);
            //req.setAttribute("action", action);
            //req.setAttribute("refCode", refCodeEncoded);
            //req.setAttribute("target", targetEncoded);
        }
        
        req.getRequestDispatcher(viewFile).forward(req, resp);
    }
    
    
}