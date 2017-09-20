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
import static com.tp.neo.controller.components.AppController.defaultEmail;
import com.tp.neo.controller.helpers.OrderItemHelper;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Message;
import com.tp.neo.model.MessageToRecipient;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.model.utils.MailSender;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
@WebServlet(name = "Message", urlPatterns = {"/Message"})
public class MessageController extends AppController {

    private static final String ADMIN_COMPOSE_MAIL = "/views/message/admin_compose_mail.jsp";
    private static final String AGENT_COMPOSE_MAIL = "/views/message/agent_compose_mail.jsp";
    private static final String CUSTOMER_COMPOSE_MAIL = "/views/message/customer_compose_mail.jsp";
    
    private static final String ADMIN_MAILBOX = "/views/message/admin_box.jsp";
    private static final String AGENT_MAILBOX = "/views/message/agent_box.jsp";
    private static final String CUSTOMER_MAILBOX = "/views/message/customer_box.jsp";
    
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
        
        
        if(super.hasActiveUserSession(request, response)){
                processGetRequest(request, response);
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
        
        if(super.hasActiveUserSession(request, response)){
                processPostRequest(request, response);
        }
        else{
            super.errorPageHandler("forbidden", request, response);
        }
    }

    

    private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        String viewFile = ADMIN_COMPOSE_MAIL;
        
        Long userId = sessionUser.getSystemUserId();
        Integer userType = sessionUser.getSystemUserTypeId();
        
        //System.out.println("UserType : " + userType);
        
        if(action.equalsIgnoreCase("new") && userType == 1){
            viewFile = ADMIN_COMPOSE_MAIL;
            request.setAttribute("agents", getAgents());
        }
        if(action.equalsIgnoreCase("new") && userType == 2){
            viewFile = AGENT_COMPOSE_MAIL;
            request.setAttribute("customers", getAgentCustomers());
        }
        else if(action.equalsIgnoreCase("new") && userType == 3){
            
            viewFile = CUSTOMER_COMPOSE_MAIL;
            
            request.setAttribute("agentId", getCustomerAgent(userId).getAgentId());
        }
        if(action.equalsIgnoreCase("reply") && userType == 1){
            viewFile = ADMIN_COMPOSE_MAIL;
            request.setAttribute("reply", request.getParameter("id"));
        }
        if(action.equalsIgnoreCase("reply") && userType == 2){
            viewFile = AGENT_COMPOSE_MAIL;
            request.setAttribute("reply", request.getParameter("id"));
        }
        else if(action.equalsIgnoreCase("reply") && userType == 3){
            
            viewFile = CUSTOMER_COMPOSE_MAIL;
            
            request.setAttribute("reply", request.getParameter("id"));
        }
        else if(action.equalsIgnoreCase("mailbox") && userType == 1){
            viewFile = ADMIN_MAILBOX;
            request.setAttribute("id", sessionUser.getSystemUserId());
            request.setAttribute("agents", getAgents());
        }
        else if(action.equalsIgnoreCase("mailbox") && userType == 2){
            viewFile = AGENT_MAILBOX;
            request.setAttribute("id", sessionUser.getSystemUserId());
            request.setAttribute("customers", getAgentCustomers());
        }
        else if(action.equalsIgnoreCase("mailbox") && userType == 3){
            viewFile = CUSTOMER_MAILBOX;
            request.setAttribute("id", sessionUser.getSystemUserId());
        }
        else if(action.equalsIgnoreCase("filter_customer")){
            
            getFilteredCustomer(request,response);
            return;
        }
        else if(action.equalsIgnoreCase("message_thread")){
            
            if(userType == 1){
                Long agentId = Long.parseLong(request.getParameter("agentId"));
                getAllAdminMessages(agentId, response);
            }
            if(userType == 2){
                Long customerId = Long.parseLong(request.getParameter("customerId"));
                getAllAgentMessages(customerId, response);
            }
            if(userType == 3){
                getAllCustomerMessages(response);
            }
            
            return;
            
        }
        
        request.setAttribute("sideNav", "Message");
        request.setAttribute("sideNavAction",action);
        
        String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
        request.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
        request.setAttribute("customerImageAccessDir", imageAccessDirPath + "/customer");
        
        request.getRequestDispatcher(viewFile).forward(request, response);
        
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        String viewFile = AGENT_COMPOSE_MAIL;
        if(action.equalsIgnoreCase("agent_email")){
            
            Long replyId = Long.parseLong(request.getParameter("reply_msg_id"));
            boolean isReply = false;
            if(replyId > 0){
                isReply = true;
            }
                 
            sendEmailToCustomerFromAgent(request,isReply,replyId);
            viewFile = AGENT_COMPOSE_MAIL;
            request.setAttribute("customers", getAgentCustomers());
        }
        else if(action.equalsIgnoreCase("admin_email")){
            
            Long replyId = Long.parseLong(request.getParameter("reply_msg_id"));
            boolean isReply = false;
            if(replyId > 0){
                isReply = true;
            }
            
            sendEmailToAgentFromAdmin(request,isReply,replyId);
            viewFile = ADMIN_COMPOSE_MAIL;
            request.setAttribute("agents",getAgents());
            request.setAttribute("Success", "Email was sent successfully");
        }
        else if(action.equalsIgnoreCase("customer_email")){
            
            Long replyId = Long.parseLong(request.getParameter("reply_msg_id"));
            boolean isReply = false;
            if(replyId > 0){
                isReply = true;
            }
            
            sendEmailToAgentFromCustomer(request,isReply,replyId);
            viewFile = CUSTOMER_COMPOSE_MAIL;
            request.setAttribute("Success", "Email was sent successfully");
        }
        else if(action.equalsIgnoreCase("agent_sms")){
            sendSMSToCustomerFromAgent(request);
            viewFile = AGENT_COMPOSE_MAIL;
            request.setAttribute("customers", getAgentCustomers());
        }
        else if(action.equalsIgnoreCase("admin_sms")){
            sendSMSToAgentFromAdmin(request);
            viewFile = AGENT_COMPOSE_MAIL;
            request.setAttribute("agents",getAgents());
            request.setAttribute("customers", getAgentCustomers());
        }
        else if(action.equalsIgnoreCase("customer_sms")){
            sendSMSToAgentFromAdmin(request);
            viewFile = AGENT_COMPOSE_MAIL;
            request.setAttribute("customers", getAgentCustomers());
        }
        
        
        request.setAttribute("sideNav", "Message");
        request.setAttribute("sideNavAction","new");
        
        request.getRequestDispatcher(viewFile).forward(request, response);
    }
    
    
    private List<Map> getAgentCustomers(){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Customer.findByAgent")
                .setParameter("agent", (Agent)sessionUser)
                .setParameter("deleted", 0);
        
        List<Customer> customers = query.getResultList();
        
        List<Map> customersMap = new ArrayList();        
        
        OrderItemHelper itemHelper = new OrderItemHelper();
        
        for(Customer customer : customers){
            
            Map<String, String> map = new HashMap();
            
            map.put("photoPath", customer.getPhotoPath());
            map.put("firstname", customer.getFirstname());
            map.put("middlename", customer.getMiddlename());
            map.put("lastname", customer.getLastname());
            map.put("fullname", customer.getFullName());
            map.put("phone", customer.getPhone());
            map.put("email", customer.getEmail());
            map.put("street", customer.getStreet());
            map.put("city", customer.getCity());
            map.put("state", customer.getState());
            map.put("id", customer.getCustomerId().toString());
            map.put("defaulter", (itemHelper.isCustomerDefaulter(customer)) ? "defaulter" : "");
            
            customersMap.add(map); 
        }
        
        return customersMap;
        
    }
    
    private void getFilteredCustomer(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            Gson gson = new GsonBuilder().create();
            String jsonResponse = gson.toJson(getAgentCustomers());
            
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    

    private void sendEmailToCustomerFromAgent(HttpServletRequest request, boolean reply, Long parentId) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String subject = request.getParameter("email_subject");
        String body = request.getParameter("email_body");
        
        Gson gson = new GsonBuilder().create();
        Type token = new TypeToken<List<Long>>(){}.getType();
        List<Long> ids = gson.fromJson(request.getParameter("customersId"), token);
        
        List<Customer> customerList = new ArrayList();
        
        if(ids != null)
            for(Long id : ids){

                Customer customer  = em.find(Customer.class, id);
                customerList.add(customer);
            }
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Message message = null;
        
        em.getTransaction().begin();
        
        if(sessionUser.getSystemUserTypeId() == 2){
            
            message = new Message();
            
            message.setChannel(Short.parseShort("0"));
            message.setSubject(subject);
            message.setMessage(body);
            message.setCreatedBy(sessionUser.getSystemUserId());
            message.setCreatorUserType(Short.parseShort("2"));
            message.setCreatedDate(calendar.getTime());
            if(reply){
                Message parentMessage = em.find(Message.class, parentId);
                Long replyParentId = parentMessage.getParentId() != 0 ? parentMessage.getParentId() : parentMessage.getId();
                message.setParentId(replyParentId); 
            }
            else{
                message.setParentId(Long.parseLong("0"));
            }
            
            em.persist(message);
            
            
            if(reply){
                Message m = em.find(Message.class, parentId);
                short recipientType = m.getCreatorUserType();
                Long recipientId = m.getCreatedBy();
                
                Customer cust = em.find(Customer.class, recipientId);
                
                MessageToRecipient recipient = new MessageToRecipient();
                
                recipient.setMessageId(message);
                recipient.setRecipientId(recipientId);
                recipient.setRecipientType(recipientType);
                recipient.setStatus(Short.parseShort("0"));
                
                em.persist(recipient);
                Company company = em.find(Company.class, 1);
                  
                new MailSender().sendHtmlEmail(cust.getEmail(), company.getEmail(), subject, body);
            }
            
            for(Customer customer : customerList){
                
                MessageToRecipient recipient = new MessageToRecipient();
                
                recipient.setMessageId(message);
                recipient.setRecipientId(customer.getCustomerId());
                recipient.setRecipientType(Short.parseShort("3"));
                recipient.setStatus(Short.parseShort("0"));
                
                em.persist(recipient);
                Company company = em.find(Company.class, 1);
                new MailSender().sendHtmlEmail(customer.getEmail(), company.getEmail(), subject, body);
            }
            
        }
        
        em.getTransaction().commit();
        
        em.close();
        
        request.setAttribute("success", customerList.size() == 0 ? 1 : customerList.size() + " Email was sent successfully");
    }

    
    
    private void sendEmailToAgentFromCustomer(HttpServletRequest request, boolean reply, Long parentId) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String subject = request.getParameter("email_subject");
        String body = request.getParameter("email_body");
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        
        Customer customer = (Customer)sessionUser;
        
        em.getTransaction().begin();
        
        Message message = new Message();
        message.setChannel(Short.parseShort("0"));
        message.setSubject(subject);
        message.setMessage(body);
        message.setCreatedBy(customer.getCustomerId());
        message.setCreatorUserType(Short.parseShort("3"));
        message.setCreatedDate(calendar.getTime());
        if(reply){
            Message parentMessage = em.find(Message.class, parentId);
            Long replyParentId = parentMessage.getParentId() != 0 ? parentMessage.getParentId() : parentMessage.getId();
            message.setParentId(replyParentId); 
        }
        else{
            message.setParentId((long)0);
        }
        em.persist(message);
        
        if(reply){
                Message m = em.find(Message.class, parentId);
                short recipientType = m.getCreatorUserType();
                Long recipientId = m.getCreatedBy();
                
                
                MessageToRecipient recipient = new MessageToRecipient();
                
                recipient.setMessageId(m);
                recipient.setRecipientId(recipientId);
                recipient.setRecipientType(recipientType);
                recipient.setStatus(Short.parseShort("0"));
                
                em.persist(recipient);
                Company company = em.find(Company.class, 1);
                
                new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), company.getEmail(), subject, body);
            }
        else{
            MessageToRecipient recipient = new MessageToRecipient();
            recipient.setMessageId(message);
            recipient.setRecipientId(customer.getAgent().getAgentId());
            recipient.setRecipientType(Short.parseShort("2"));
            recipient.setStatus(Short.parseShort("0"));

            em.persist(recipient);
            Company company = em.find(Company.class, 1);
            new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), company.getEmail(), subject, body);
        }
        
        em.getTransaction().commit();
        
        em.close();
        
        request.setAttribute("success",  "1 Email was sent successfully");
    }
    
    private void sendEmailToAgentFromAdmin(HttpServletRequest request, boolean reply, Long parentId){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String subject = request.getParameter("email_subject");
        String body = request.getParameter("email_body");
        
        Gson gson = new GsonBuilder().create();
        Type token = new TypeToken<List<Long>>(){}.getType();
        List<Long> ids = gson.fromJson(request.getParameter("agentsId"), token);
        
        List<Agent> agentList = new ArrayList();
        
        if(ids != null)
            for(Long id : ids){

                Agent agent  = em.find(Agent.class, id);
                agentList.add(agent);
            }
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Message message = null;
        
        em.getTransaction().begin();
        
        if(sessionUser.getSystemUserTypeId() == 1){
            
            message = new Message();
            
            message.setChannel(Short.parseShort("0"));
            message.setSubject(subject);
            message.setMessage(body);
            message.setCreatedBy(sessionUser.getSystemUserId());
            message.setCreatorUserType(Short.parseShort("1"));
            message.setCreatedDate(calendar.getTime());
            if(reply){
                Message parentMessage = em.find(Message.class, parentId);
                Long replyParentId = parentMessage.getParentId() != 0 ? parentMessage.getParentId() : parentMessage.getId();
                message.setParentId(replyParentId); 
            }
            else{
                message.setParentId((long)0);
            }
            em.persist(message);
            
            
            if(reply){
                Message m = em.find(Message.class, parentId);
                short recipientType = m.getCreatorUserType();
                Long recipientId = m.getCreatedBy();
                
                Agent agent = em.find(Agent.class, recipientId);
                
                MessageToRecipient recipient = new MessageToRecipient();
                
                recipient.setMessageId(m);
                recipient.setRecipientId(recipientId);
                recipient.setRecipientType(recipientType);
                recipient.setStatus(Short.parseShort("0"));
                
                em.persist(recipient);
                Company company = em.find(Company.class, 1);
                new MailSender().sendHtmlEmail(agent.getEmail(), company.getEmail(), subject, body);
            }
            
            for(Agent agent : agentList){
                
                MessageToRecipient recipient = new MessageToRecipient();
                
                recipient.setMessageId(message);
                recipient.setRecipientId(agent.getAgentId());
                recipient.setRecipientType(Short.parseShort("2"));
                recipient.setStatus(Short.parseShort("0"));
                
                em.persist(recipient);
                Company company = em.find(Company.class, 1);
                new MailSender().sendHtmlEmail(agent.getEmail(), company.getEmail(), subject, body);
            }
            
        }
        
        em.getTransaction().commit();
        
        em.close();
        
        request.setAttribute("success", agentList.size() == 0 ? 1 : agentList.size() + " Email was sent successfully");
        
    }
    
    private void sendSMSToCustomerFromAgent(HttpServletRequest request) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String msg = request.getParameter("sms_body");
        
        Gson gson = new GsonBuilder().create();
        
        Type token = new TypeToken<List<Long>>(){}.getType();
        List<Long> ids = gson.fromJson(request.getParameter("customersId"), token);
        
        List<Customer> customerList = new ArrayList();
        
        for(Long id : ids){
            
            Customer customer  = em.find(Customer.class, id);
            customerList.add(customer);
            
            //new SMSSender(customer.getPhone(),msg).start();
        }
        
        request.setAttribute("success", customerList.size() + " SMS was sent successfully");
    }
    
    
    private void sendSMSToAgentFromAdmin(HttpServletRequest request){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String msg = request.getParameter("sms_body");
        
        Gson gson = new GsonBuilder().create();
        
        Type token = new TypeToken<List<Long>>(){}.getType();
        List<Long> ids = gson.fromJson(request.getParameter("agentsId"), token);
        
        List<Agent> agentList = new ArrayList();
        
        for(Long id : ids){
            
            Agent agent  = em.find(Agent.class, id);
            agentList.add(agent);
            //new SMSSender(customer.getPhone(),msg).start();
        }
        
        request.setAttribute("success", agentList.size() + " SMS was sent successfully");
    }
    
    private void sendSMSToAgentFromCustomer(HttpServletRequest request){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String msg = request.getParameter("sms_body");
        
        Customer customer = (Customer)sessionUser;
        Agent agent = customer.getAgent();
        
        request.setAttribute("success", " SMS was sent successfully");
        
        //new SMSSender(customer.getPhone(),msg).start();
        
    }
    
    private void getAllAgentMessages(Long customerId, HttpServletResponse response) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Message.findAllThreadByAgent");
        
        ////System.out.println("Agent id : " + sessionUser.getSystemUserId());
        
        Customer c = em.find(Customer.class, customerId);
        query.setParameter("cust_id", c.getCustomerId());
        query.setParameter("agent_id", sessionUser.getSystemUserId());
        
        List<Object[]> messageObjectList = query.getResultList();
        
        List<Map> messageList = new ArrayList();
        
        
        List<Map> replies = new ArrayList();
        
        for(int i=0; i < messageObjectList.size(); i++) {
            
            Message msg = (Message)messageObjectList.get(i)[0];
            Message msgReply = (Message)messageObjectList.get(i)[1] != null ? (Message)messageObjectList.get(i)[1] : null;
            
            //if we are not at the end of the message list
            if(i < messageObjectList.size() - 1){
                
                Message nextMsg = (Message)messageObjectList.get(i+1)[0];
                
                //If the next message in the list is not a reply
                if(msg.getId() != nextMsg.getId()){
                    
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                    
                    Map messageMap = new HashMap();
                    messageMap.put("id", msg.getId());
                    messageMap.put("userId", msg.getCreatedBy());
                    messageMap.put("userType", msg.getCreatorUserType());
                    messageMap.put("subject", msg.getSubject());
                    messageMap.put("body", msg.getMessage());
                    messageMap.put("date", getFormattedDate(msg.getCreatedDate()));
                    
                    List<Map> tempReplies = new ArrayList();
                    for(Map map : replies){
                        Map m = new HashMap();
                        m.put("id", map.get("id"));
                        m.put("userId", map.get("userId"));
                        m.put("userType", map.get("userType"));
                        m.put("subject", map.get("subject"));
                        m.put("body", map.get("body"));
                        m.put("date", map.get("date"));
                        tempReplies.add(m); 
                    }
                    
                    messageMap.put("replies", tempReplies);
                    messageList.add(messageMap);
                    
                    //clear the replies list, so
                    replies.clear();
                }
                else{ // If the next message in the list is a reply
                    
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                }
            }else{ // if this is the end of the list of messages
                
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                    
                    Map messageMap = new HashMap();
                    messageMap.put("id", msg.getId());
                    messageMap.put("userId", msg.getCreatedBy());
                    messageMap.put("userType", msg.getCreatorUserType());
                    messageMap.put("subject", msg.getSubject());
                    messageMap.put("body", msg.getMessage());
                    messageMap.put("date", getFormattedDate(msg.getCreatedDate()));
                    
                    List<Map> tempReplies = new ArrayList();
                    for(Map map : replies){
                        Map m = new HashMap();
                        m.put("id", map.get("id"));
                        m.put("userId", map.get("userId"));
                        m.put("userType", map.get("userType"));
                        m.put("subject", map.get("subject"));
                        m.put("body", map.get("body"));
                        m.put("date", map.get("date"));
                        tempReplies.add(m); 
                    }
                    
                    messageMap.put("replies", tempReplies);
                    messageList.add(messageMap);
                
            }
        }
        
        
        Gson gson = new GsonBuilder().create();
        
        String jsonResponse = gson.toJson(messageList);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }

    
    private void getAllAdminMessages(Long agentId, HttpServletResponse response) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Message.findAllThreadByAdmin");
        
        query.setParameter("admin_id", sessionUser.getSystemUserId());
        query.setParameter("agent_id", agentId);
        
        List<Object[]> messageObjectList = query.getResultList();
        
        List<Map> messageList = new ArrayList();
        
        
        List<Map> replies = new ArrayList();
        
        for(int i=0; i < messageObjectList.size(); i++) {
            
            Message msg = (Message)messageObjectList.get(i)[0];
            Message msgReply = (Message)messageObjectList.get(i)[1] != null ? (Message)messageObjectList.get(i)[1] : null;
            
            //if we are not at the end of the message list
            if(i < messageObjectList.size() - 1){
                
                Message nextMsg = (Message)messageObjectList.get(i+1)[0];
                
                //If the next message in the list is not a reply
                if(msg.getId() != nextMsg.getId()){
                    
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                    
                    Map messageMap = new HashMap();
                    messageMap.put("id", msg.getId());
                    messageMap.put("userId", msg.getCreatedBy());
                    messageMap.put("userType", msg.getCreatorUserType());
                    messageMap.put("subject", msg.getSubject());
                    messageMap.put("body", msg.getMessage());
                    messageMap.put("date", getFormattedDate(msg.getCreatedDate()));
                    
                    List<Map> tempReplies = new ArrayList();
                    for(Map map : replies){
                        Map m = new HashMap();
                        m.put("id", map.get("id"));
                        m.put("userId", map.get("userId"));
                        m.put("userType", map.get("userType"));
                        m.put("subject", map.get("subject"));
                        m.put("body", map.get("body"));
                        m.put("date", map.get("date"));
                        tempReplies.add(m); 
                    }
                    
                    messageMap.put("replies", tempReplies);
                    messageList.add(messageMap);
                    
                    //clear the replies list, so
                    replies.clear();
                }
                else{ // If the next message in the list is a reply
                    
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                }
            }else{ // if this is the end of the list of messages
                
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                    
                    Map messageMap = new HashMap();
                    messageMap.put("id", msg.getId());
                    messageMap.put("userId", msg.getCreatedBy());
                    messageMap.put("userType", msg.getCreatorUserType());
                    messageMap.put("subject", msg.getSubject());
                    messageMap.put("body", msg.getMessage());
                    messageMap.put("date", getFormattedDate(msg.getCreatedDate()));
                    
                    List<Map> tempReplies = new ArrayList();
                    for(Map map : replies){
                        Map m = new HashMap();
                        m.put("id", map.get("id"));
                        m.put("userId", map.get("userId"));
                        m.put("userType", map.get("userType"));
                        m.put("subject", map.get("subject"));
                        m.put("body", map.get("body"));
                        m.put("date", map.get("date"));
                        tempReplies.add(m); 
                    }
                    
                    messageMap.put("replies", tempReplies);
                    messageList.add(messageMap);
                
            }
        }
        
        
        Gson gson = new GsonBuilder().create();
        
        String jsonResponse = gson.toJson(messageList);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    private void getAllCustomerMessages(HttpServletResponse response) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Message.findAllThreadByCustomer");
        
        query.setParameter("cust_id", sessionUser.getSystemUserId());
        query.setParameter("agent_id", ((Customer)sessionUser).getAgent().getAgentId());
        
        List<Object[]> messageObjectList = query.getResultList();
        
        List<Map> messageList = new ArrayList();
        
        
        List<Map> replies = new ArrayList();
        
        for(int i=0; i < messageObjectList.size(); i++) {
            
            Message msg = (Message)messageObjectList.get(i)[0];
            Message msgReply = (Message)messageObjectList.get(i)[1] != null ? (Message)messageObjectList.get(i)[1] : null;
            
            //if we are not at the end of the message list
            if(i < messageObjectList.size() - 1){
                
                Message nextMsg = (Message)messageObjectList.get(i+1)[0];
                
                //If the next message in the list is not a reply
                if(msg.getId() != nextMsg.getId()){
                    
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                    
                    Map messageMap = new HashMap();
                    messageMap.put("id", msg.getId());
                    messageMap.put("userId", msg.getCreatedBy());
                    messageMap.put("userType", msg.getCreatorUserType());
                    messageMap.put("subject", msg.getSubject());
                    messageMap.put("body", msg.getMessage());
                    messageMap.put("date", getFormattedDate(msg.getCreatedDate()));
                    
                    List<Map> tempReplies = new ArrayList();
                    for(Map map : replies){
                        Map m = new HashMap();
                        m.put("id", map.get("id"));
                        m.put("userId", map.get("userId"));
                        m.put("userType", map.get("userType"));
                        m.put("subject", map.get("subject"));
                        m.put("body", map.get("body"));
                        m.put("date", map.get("date"));
                        tempReplies.add(m); 
                    }
                    
                    messageMap.put("replies", tempReplies);
                    messageList.add(messageMap);
                    
                    //clear the replies list, so
                    replies.clear();
                }
                else{ // If the next message in the list is a reply
                    
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                }
            }else{ // if this is the end of the list of messages
                
                    if(msgReply != null){
                        Map mReply = new HashMap();
                        mReply.put("id", msgReply.getId());
                        mReply.put("userId", msgReply.getCreatedBy());
                        mReply.put("userType", msgReply.getCreatorUserType());
                        mReply.put("subject", msgReply.getSubject());
                        mReply.put("body", msgReply.getMessage());
                        mReply.put("date", getFormattedDate(msgReply.getCreatedDate()));

                        replies.add(mReply);
                        //System.out.println("Reply added");
                    }
                    
                    Map messageMap = new HashMap();
                    messageMap.put("id", msg.getId());
                    messageMap.put("userId", msg.getCreatedBy());
                    messageMap.put("userType", msg.getCreatorUserType());
                    messageMap.put("subject", msg.getSubject());
                    messageMap.put("body", msg.getMessage());
                    messageMap.put("date", getFormattedDate(msg.getCreatedDate()));
                    
                    List<Map> tempReplies = new ArrayList();
                    for(Map map : replies){
                        Map m = new HashMap();
                        m.put("id", map.get("id"));
                        m.put("userId", map.get("userId"));
                        m.put("userType", map.get("userType"));
                        m.put("subject", map.get("subject"));
                        m.put("body", map.get("body"));
                        m.put("date", map.get("date"));
                        tempReplies.add(m); 
                    }
                    
                    messageMap.put("replies", tempReplies);
                    messageList.add(messageMap);
                
            }
        }
        
        
        Gson gson = new GsonBuilder().create();
        
        String jsonResponse = gson.toJson(messageList);
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
        response.getWriter().close();
    }
    
    private Agent getCustomerAgent(Long userId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Customer customer = em.find(Customer.class, userId);
        Agent agent = customer.getAgent();
        
        em.close();
        emf.close();
        
        return agent;
    } 
    
    
    
    private List<Agent> getAgents() {
       
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("Agent.findByActiveAndDeletedAndApprovalStatus");
        query.setParameter("approvalStatus", 1).setParameter("deleted", 0).setParameter("active", 1);
        
        List<Agent> agents = query.getResultList();
       
        return agents;
    }
    
    private String getFormattedDate(Date date){
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        
        calendar.setTime(date);
        
        String dateString = calendar.get(Calendar.MONTH)+1 + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR); 
        dateString += " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " " + ( calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
        return dateString;
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
