/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.CompanyAccountHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Prestige
 */
@WebServlet(name="CustomerRegistration", urlPatterns={"/RegisterCustomer"})
@MultipartConfig
public class CustomerRegistrationController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setAttribute("from", "customerRegistrationController");
        req.setAttribute("agent_id","18");
        CustomerController customerController = new CustomerController();
        customerController.processInsertRequest(req, resp);
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        ProjectController project = new ProjectController();
        
        String viewFile = "/views/customer/registration.jsp";
        request.setAttribute("projects", project.listProjects());
        request.setAttribute("companyAccount",CompanyAccountHelper.getCompanyAccounts());
        request.getRequestDispatcher(viewFile).forward(request, response);
        
    }
    
    
    
}
