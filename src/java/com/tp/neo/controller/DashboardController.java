/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class DashboardController extends HttpServlet {
    public final String pageTitle = "Dashboard";
            
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
        PrintWriter out = response.getWriter();
        try {
            //String referrerURI = new URI(request.getHeader("referer")).toString();
            //String referrerPath = new URI(request.getHeader("referer")).getPath();
            //String paths = "referrerURI: " + referrerURI + " referrerPath: " + referrerPath;
                
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DashBoard</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DashBoard at " + request.getContextPath() + "</h1>");
            //out.println("<h1>Paths: " + paths + "</h1>");
            out.println("</body>");
            out.println("</html>");
                
        }catch(Exception e){
            System.out.println("err: "+ e.getMessage());
        }
        finally {
                out.close();
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
        
        if(request.getParameter("action") !=null && request.getParameter("action").equals("logout")) {
            request.getSession().invalidate();
            System.out.println("invalidated");
        }
        else
            processRequest(request, response);
        
        //request.getSession().invalidate();
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
        processRequest(request, response);
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