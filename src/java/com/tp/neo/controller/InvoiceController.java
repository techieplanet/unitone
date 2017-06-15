/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Prestige
 */
@WebServlet(name="Invoice", urlPatterns = {"/Invoice"})
public class InvoiceController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Inside invoice");
        String viewFile = "/views/customer/invoice.jsp";
        
        req.getRequestDispatcher(viewFile).forward(req, resp);
    }
    
    
    
}
