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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
@WebServlet(name = "CompanyAccountController", urlPatterns = {"/CompanyAccount"})
public class CompanyAccountController extends AppController {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CompanyAccountController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CompanyAccountController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
                processGetRequest(request, response);
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
            
            viewFile = "/views/companyAccount/form.jsp";
        }
        else if(action.equalsIgnoreCase("edit")){
            
        }
        else{
            
            request.setAttribute("companyAccounts", getCompanyAccounts());
        }
        
        request.getRequestDispatcher(viewFile).forward(request, response);
        
    }

    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) {
        
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if(action.equals("new")){
            
        }
        else if(action.equalsIgnoreCase("edit")){
            
        }
        else if(action.equalsIgnoreCase("delete")){
            
        }
        
        
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
