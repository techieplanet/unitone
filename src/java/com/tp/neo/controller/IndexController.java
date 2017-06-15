/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.model.Project;
import com.tp.neo.model.ProjectUnit;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author hp
 */
@WebServlet(name = "IndexController", urlPatterns = {"","/home"})
public class IndexController extends HttpServlet {
    
    private static final String INDEX_FILE = "/views/index/index.jsp";

    

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
        
        loadHome(request, response);
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
        
        loadHome(request, response);
    }
    
    public List<Project> listProjects(){
        //List<Project> projectList = new ArrayList<Project>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Project.findByDeleted").setParameter("deleted", 0);
        List<Project> projectList = jpqlQuery.getResultList();
        
        em.close();
        emf.close();
        
        return projectList;
    }
    
    public List<ProjectUnit> getUnits(HttpServletRequest request){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Project project = em.find(Project.class, Long.parseLong(request.getParameter("project_id")));
                
        Query query = em.createNamedQuery("ProjectUnit.findByProjectAndQty");
        query.setParameter("project", project);
        
        List<ProjectUnit> units  = query.getResultList();
        
        em.close();
        emf.close();
        
        return units;
    } 
    
    private void addToCart(HttpServletRequest request,HttpServletResponse response) throws IOException{
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String[] unitIds = request.getParameterValues("unit_id");
        
        List<ProjectUnit> units = (List<ProjectUnit>)request.getSession().getAttribute("unit_cart");
        
        if(units == null){
            units = new ArrayList();
        }
        
        long project_id = 0;
        
        for(String id : unitIds){
            
            ProjectUnit unit = em.find(ProjectUnit.class, Long.parseLong(id));
            units.add(unit);
            project_id = unit.getProject().getId();
            System.out.println("Unit Id : " + id);
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("unit_cart", units);
        
        em.close();
        emf.close();
        
        
        
        response.sendRedirect("Project?action=listunits&project_id="+project_id);
            
    }
    
    private void removeFromCart(HttpServletRequest request,HttpServletResponse response) throws IOException{
        
        
        long project_id = 0;
     
        long unitId =Long.parseLong(request.getParameter("unit_id"));
         
        HttpSession session = request.getSession();
        List<ProjectUnit> units = (List<ProjectUnit>)session.getAttribute("unit_cart");
        
        if(units != null){
            int counter = 0;
            int deleteIndex = -1;
            
            for(ProjectUnit unit : units){
                
                if(unitId == unit.getId()){
                    deleteIndex = counter;
                    project_id = unit.getProject().getId();
                }
                
                counter += 1;
            }
            
            if(deleteIndex != -1)
                units.remove(deleteIndex);
        }
        
        session.setAttribute("unit_cart", units);
       
        response.sendRedirect("Project?action=listunits&project_id="+project_id);
            
    }
    
    private void loadHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        String viewFile = INDEX_FILE;
        
        if(action.equals("")){
            
            request.setAttribute("projects", listProjects());
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
        
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
