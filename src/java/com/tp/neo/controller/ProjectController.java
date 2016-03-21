/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Project;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.utils.TPController;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "Project", urlPatterns = {"/Project"})
public class ProjectController extends TPController {
    public final String pageTitle = "Project";
    
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String PROJECTS_ADMIN = "/views/project/admin.jsp"; 
    private static String PROJECTS_NEW = "/views/project/add.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProjectController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProjectController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
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
        if(super.hasActiveUserSession(request, response, request.getRequestURL().toString()))
            processGetRequest(request, response);
    }
    
    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = PROJECTS_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String stringId = request.getParameter("id") != null ? request.getParameter("id") : "";
        
        if (action.equalsIgnoreCase("new")){
               viewFile = PROJECTS_NEW;
               //request.setAttribute("action", "edit");
               request.setAttribute("id", "");
        }
        else if(action.equalsIgnoreCase("delete")){
            this.delete(Integer.parseInt(request.getParameter("id")));
        }
        else if(action.equalsIgnoreCase("edit") && !(stringId.equals(""))){
            viewFile = PROJECTS_NEW;
            
            //find by ID
            int id = Integer.parseInt(stringId);
            Project project = em.find(Project.class, id);
            
            Query query = em.createNamedQuery("ProjectUnit.findByProjectId").setParameter("projectId",id);
            List<ProjectUnit> projectUnits = query.getResultList();
            
            request.setAttribute("units", projectUnits);
            request.setAttribute("project", project);
            request.setAttribute("action", "edit");
            request.setAttribute("id", id);
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listprojects")){
            viewFile = PROJECTS_ADMIN;
            request.setAttribute("projects", listProjects());
        }
        else{
            viewFile = PROJECTS_ADMIN;
            request.setAttribute("projects", listProjects());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
            
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
        
        if(super.hasActiveUserSession(request, response, request.getRequestURL().toString())){
            if(request.getParameter("id").equals(""))
                processInsertRequest(request, response);
            else
                processUpdateRequest(request, response);
        }
    }

    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Insert mode");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = PROJECTS_NEW;
        request.setAttribute("success", false);
        
        Project project = new Project();
        Gson gson = new GsonBuilder().create();
        List<ProjectUnit> projectUnits = new ArrayList<ProjectUnit>();
        
            try{                                

                em.getTransaction().begin();
                
                project.setName(request.getParameter("pname"));
                project.setDescription(request.getParameter("desc"));               
                project.setLocation(request.getParameter("location"));
                project.setProjectManager(request.getParameter("pmanager"));
                project.setDeleted((short)0);
                project.setActive((short)1);
                
                new TrailableManager(project).registerInsertTrailInfo(1);
                
                validate(project);
                
                em.persist(project);
                
                em.getTransaction().commit();
                
                em.refresh(project);
                
                //get this as early as possible
                Query query = em.createNamedQuery("ProjectUnit.findByProjectId").setParameter("projectId",project.getId());
                projectUnits = query.getResultList();
            
                request.setAttribute("project", project);
                request.setAttribute("success", true);
                request.setAttribute("units", projectUnits);
                request.setAttribute("action", "edit");
                request.setAttribute("id", project.getId());
               
                em.close();
                emf.close();
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = PROJECTS_NEW;
                request.setAttribute("project", project);
                request.setAttribute("units", projectUnits);
                request.setAttribute("action", "edit");
                //request.setAttribute("id", project.getId());
                request.setAttribute("errors", errorMessages);
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Project", gson.toJson(project), e.getMessage());
            }
        
            //new URI(request.getHeader("referer")).getPath();
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Update mode");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = PROJECTS_NEW;
        request.setAttribute("success", false);
        
        Project project = new Project();
        Gson gson = new GsonBuilder().create();
        List<ProjectUnit> projectUnits = new ArrayList<ProjectUnit>();
        
        try{                                
                em.getTransaction().begin();
                
                project = em.find(Project.class, Integer.parseInt(request.getParameter("id")));
                project.setName(request.getParameter("pname"));
                project.setDescription(request.getParameter("desc"));               
                project.setLocation(request.getParameter("location"));
                project.setProjectManager(request.getParameter("pmanager"));
                project.setDeleted((short)0);
                project.setActive((short)1);
                
                //get this as early as possible
                Query query = em.createNamedQuery("ProjectUnit.findByProjectId").setParameter("projectId",project.getId());
                projectUnits = query.getResultList();
                
                new TrailableManager(project).registerUpdateTrailInfo(1);
                
                validate(project);
                                
                em.getTransaction().commit();
            
                request.setAttribute("units", projectUnits);
                request.setAttribute("project", project);
                request.setAttribute("action", "edit");
                request.setAttribute("id", project.getId());
                request.setAttribute("success", true);
               
                em.close();
                emf.close();
            }
            catch(PropertyException e){
                e.printStackTrace();
                System.out.println("inside catch area: " + e.getMessage());
                viewFile = PROJECTS_NEW;
                request.setAttribute("project", project);
                request.setAttribute("units", projectUnits);
                request.setAttribute("action", "edit");
                request.setAttribute("id", project.getId());
                request.setAttribute("errors", errorMessages);    
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Project", gson.toJson(project), e.getMessage());
            }
        
            
            //new URI(request.getHeader("referer")).getPath();
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);
    }
    
    
    
    
    public void delete(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Project project = em.find(Project.class, id);
        em.getTransaction().begin();
        project.setDeleted((short)1);
        new TrailableManager(project).registerUpdateTrailInfo(1);
        em.getTransaction().commit();

        em.close();
        emf.close();
        
        //echo ""
    }
    
    private void validate(Project project) throws PropertyException{
        errorMessages.clear();
        
        if(project.getName().isEmpty()){
            errorMessages.put("errors1", "Please enter a project name");
        }
        if(project.getDescription().isEmpty()){
            errorMessages.put("errors2", "Please enter a project description");
        }
        if(project.getLocation().isEmpty()){
            errorMessages.put("errors3", "Please enter a location");
        }
        if(Integer.parseInt(project.getProjectManager()) == 0){
            errorMessages.put("errors4", "Please select a project manager");
        }    
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
    }
    
    
     public List<Project> listProjects(){
        //List<Project> projectList = new ArrayList<Project>();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery  = em.createNamedQuery("Project.findByDeleted").setParameter("deleted", 0);
        List<Project> projectList = jpqlQuery.getResultList();

        return projectList;
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
