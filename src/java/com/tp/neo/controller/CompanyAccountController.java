/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.Agent;
import com.tp.neo.model.CompanyAccount;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;

/**
 *
 * @author hp
 */
@WebServlet(name = "CompanyAccountController", urlPatterns = {"/CompanyAccount"})
public class CompanyAccountController extends AppController {

    
    Map errorMessages = new HashMap();

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
        
        if(super.hasActiveUserSession(request, response))
         {
            if(super.hasActionPermission(new CompanyAccount().getPermissionName(action), request, response)){
                processGetRequest(request, response);
            }
            else{
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
        
          if(super.hasActiveUserSession(request, response))
         {
            if(super.hasActionPermission(new CompanyAccount().getPermissionName(action), request, response)){
                processPostRequest(request, response);
            }
            else{
                super.errorPageHandler("forbidden", request, response);
            }
         }
        
    }

    

    private void processGetRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        String viewFile = "/views/companyAccount/admin.jsp";
        
        if(action.equalsIgnoreCase("new")){
            
            viewFile = "/views/companyAccount/add.jsp";
            request.setAttribute("action", action);
        }
        else if(action.equalsIgnoreCase("edit")){
            
            viewFile = "/views/companyAccount/add.jsp";
            int accountId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("action", action);
            request.setAttribute("id", accountId);
            request.setAttribute("account", getCompanyAccount(accountId));
            
        }
        else if(action.equalsIgnoreCase("delete")){
            
            viewFile = "/views/companyAccount/admin.jsp";
            
            int accountId = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("action", action);
            deleteCompanyAccount(accountId);
            request.setAttribute("companyAccounts", getCompanyAccounts());
        }
        else{
            
            request.setAttribute("companyAccounts", getCompanyAccounts());
        }
        
        request.getRequestDispatcher(viewFile).forward(request, response);
        request.setAttribute("sideNav","companyAccount");
        
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if(action.equals("new")){
            
            processInsertCompanyAccount(request, response);
            
        }
        else if(action.equalsIgnoreCase("edit")){
            
            processUpdateCompanyAccount(request, response);
            
        }
        else if(action.equalsIgnoreCase("delete")){
            
        }
        
        
    }
    
    private void processInsertCompanyAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        CompanyAccount account = new CompanyAccount();
        String viewFile = "/views/companyAccount/add.jsp";
        
        try{
            
            emf = Persistence.createEntityManagerFactory("NeoForcePU");
            em = emf.createEntityManager();
            
            String bankName = request.getParameter("bank_name");
            String accountNo = request.getParameter("account_no");
            String accountName = request.getParameter("account_name");
            
            em.getTransaction().begin();
            
            account.setBankName(bankName);
            account.setAccountNumber(accountNo);
            account.setAccountName(accountName);
            
            validate(account);
            
            em.persist(account);
            
            em.getTransaction().commit();
            em.refresh(account);
            
            request.setAttribute("success", true);
            request.setAttribute("action", "new");
            request.removeAttribute("errors");
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        catch(PropertyException pex){            
            request.setAttribute("account", account);
            request.setAttribute("action", "new");
            request.setAttribute("errors", errorMessages);
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        catch(Exception ex){
                        
            request.setAttribute("account", account);
            request.setAttribute("action", "new");
            request.setAttribute("errors", errorMessages);
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        finally{
            
            if(em != null)
                em.close(); 
            if(emf != null)
                emf.close();
        }
        
    }
    
    private void processUpdateCompanyAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        CompanyAccount account = new CompanyAccount();
        String viewFile = "/views/companyAccount/add.jsp";
        
        
        try{
            
            emf = Persistence.createEntityManagerFactory("NeoForcePU");
            em = emf.createEntityManager();
            
            int accountId = Integer.parseInt(request.getParameter("id"));
            
            String bankName = request.getParameter("bank_name");
            String accountNo = request.getParameter("account_no");
            String accountName = request.getParameter("account_name");
           
            em.getTransaction().begin();
            
            account = em.find(CompanyAccount.class,accountId);
            account.setBankName(bankName);
            account.setAccountNumber(accountNo);
            account.setAccountName(accountName);
            
            validate(account);
            
            em.getTransaction().commit();
            
            
            request.setAttribute("success", true);
            request.setAttribute("account", account);
            request.setAttribute("action", "edit");
            request.removeAttribute("errors");
            request.setAttribute("id", accountId);
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        catch(PropertyException pex){
            
            System.out.println("Error Raised");
            
            request.setAttribute("account", account);
            request.setAttribute("action", "edit");
            request.setAttribute("id", request.getAttribute("id"));
            request.setAttribute("errors", errorMessages);
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        catch(Exception ex){
            System.out.println("Exception : " + ex.getMessage());  
            ex.printStackTrace();
            request.setAttribute("account", account);
            request.setAttribute("action", "edit");
            request.setAttribute("errors", errorMessages);
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        finally{
            
            if(em != null)
                em.close(); 
            if(emf != null)
                emf.close();
        }
        
    }
    
    private void validate(CompanyAccount account) throws PropertyException{
        
        errorMessages.clear();
        
        if(account.getBankName().equalsIgnoreCase("")){
            errorMessages.put("Bank Name", "Bank name is required");
        }
        if(account.getAccountNumber().equalsIgnoreCase("") || account.getAccountNumber().matches("[0-9].{10}")){
            errorMessages.put("Account Number", "Account number is required, must be numbers only and must be of length 10");
        }
        if(account.getAccountName().equalsIgnoreCase("")){
            errorMessages.put("Account Name", "Account name is required");
        }
        
        if(errorMessages.size() > 0)
            throw new PropertyException("Validation error occured");
        
    }
    
    private List<CompanyAccount> getCompanyAccounts() {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("CompanyAccount.findAll");
        
        List<CompanyAccount> companyAccountList = query.getResultList();
        
        em.close();
        emf.close();
        
        return companyAccountList;
        
    }
    
    
    private CompanyAccount getCompanyAccount(int accountId) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        CompanyAccount account = em.find(CompanyAccount.class,accountId);
        
        return account;
        
    }
    
    private void deleteCompanyAccount(int accountId) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        CompanyAccount account = em.find(CompanyAccount.class, accountId);
        
        em.getTransaction().begin();
        em.remove(account);
        em.getTransaction().commit();
        
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
