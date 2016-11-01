/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

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
@WebServlet(name="RegisterAgent", urlPatterns = {"/RegisterAgent"})
@MultipartConfig
public class AgentRegistrationController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        AgentController agentController = new AgentController();
        agentController.processInsertRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        String viewFile = "/views/agent/registration.jsp";
        
        if(action.equalsIgnoreCase("success")){
            viewFile = "/views/agent/success.jsp";
        }
        
        
        req.getRequestDispatcher(viewFile).forward(req, resp);
    }
    
    
}
