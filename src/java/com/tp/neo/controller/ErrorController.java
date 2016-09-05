/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.User;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Swedge
 */

@WebServlet(name = "Error", urlPatterns = {"/Error"})
public class ErrorController extends AppController {
    public final String pageTitle = "Error";
    private static final String ERROR = "/views/error/error.jsp"; 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {            
        if(super.hasActiveUserSession(request, response, request.getRequestURL().toString())){    
            log("Inside hasActiveUserSession");
            processGetRequest(request, response);
        }
    }
    
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String viewFile = ""; 
        
        log("Inside error get request");
    
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if (action.equalsIgnoreCase("forbidden")){
               log("Inside forbidden action");
               viewFile = ERROR;
               
               request.setAttribute("action", action);
        }
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
            
    }
}
