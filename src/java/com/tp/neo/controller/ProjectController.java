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

import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.ProjectUnit;

import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Swedge
 */
@WebServlet(name = "Project", urlPatterns = {"/Project"})
public class ProjectController extends AppController {
    public final String pageTitle = "Project";

    private static String PROJECTS_ADMIN = "/views/project/admin.jsp"; 
    private static String PROJECTS_NEW = "/views/project/add.jsp";
    private static String ECOMMERCE = "/views/project/ecommerce.jsp";
    private static String ECOMMERCE_UNITS = "/views/project/ecommerce_units.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    
    private String action = "";
    private String viewFile = "";
    
  

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
        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        if(action.equalsIgnoreCase("punits")){
                  long id = Long.parseLong(request.getParameter("project_id"));
                  sendProjectUnitsData(request, response,id);
                  return;
         }
        
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new Project().getPermissionName(action), request, response)){
                if(action.equalsIgnoreCase("punits")){
                  int id = Integer.parseInt(request.getParameter("project_id"));
                  sendProjectUnitsData(request, response,id);
                }else {
                    processGetRequest(request, response);
                }
            }
        }
    }
    
    
    protected void sendProjectUnitsData(HttpServletRequest request, HttpServletResponse response,long id) throws ServletException, IOException{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        Project project = em.find(Project.class, id);
            
            List<ProjectUnit> projectUnits =  (List)project.getProjectUnitCollection();
            em.close(); emf.close();
           
            Map<Integer, Map> map = new HashMap<Integer, Map>();
           for(int i=0; i< projectUnits.size(); i++){
           
            
            Map<String, String> mapSmall = new HashMap<String, String>();
            mapSmall.put("id",projectUnits.get(i).getId() + "");
            mapSmall.put("title", projectUnits.get(i).getTitle());
            mapSmall.put("cpu", projectUnits.get(i).getCpu().toString());
            mapSmall.put("lid", projectUnits.get(i).getLeastInitDep().toString());
            mapSmall.put("discount", projectUnits.get(i).getDiscount().toString());
            mapSmall.put("mpd", projectUnits.get(i).getMaxPaymentDuration().toString());
            mapSmall.put("commp", projectUnits.get(i).getCommissionPercentage().toString());
            mapSmall.put("quantity", projectUnits.get(i).getQuantity() + "");
            map.put(i,mapSmall);
          
           }
        
            Gson gson = new GsonBuilder().create();
            String jsonResponse = gson.toJson(map);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            System.out.println("jsonResponse: " + jsonResponse);
  
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        viewFile = PROJECTS_ADMIN; 

        String stringId = request.getParameter("id") != null ? request.getParameter("id") : "";
        int addstat = request.getParameter("addstat") != null   ? Integer.parseInt(request.getParameter("addstat")) : 0;
        
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
            
            /**
             * the collection is usually not updated in runtime, even after refreshing the page severally.
             * So force a hard refresh by querying the db to get the actual set of valid elements 
             * then use that as the valid collection
             */
            Query query = em.createNamedQuery("ProjectUnit.findByProjectAndActive")
                                                .setParameter("project", project)
                                                .setParameter("deleted", 0);
            List<ProjectUnit> projectUnits = query.getResultList();
            
            //good to do this to put every object/entity in sync
            project.setProjectUnitCollection(projectUnits);
            
            
            request.setAttribute("units", projectUnits);
            request.setAttribute("project", project);
            request.setAttribute("action", "edit");
            request.setAttribute("id", id);
            if(addstat == 1) request.setAttribute("success", true);
        }
        else if(sessionUser.getSystemUserTypeId() == 3 && action.equalsIgnoreCase("listprojects")){
            
            viewFile = ECOMMERCE;
            request.setAttribute("projects", listProjects());
        }
        else if(sessionUser.getSystemUserTypeId() == 3 && action.equalsIgnoreCase("listunits")){
            
            viewFile = ECOMMERCE_UNITS;
            request.setAttribute("projectUnits", getUnits(request));
        }
        else if(sessionUser.getSystemUserTypeId() == 3 && action.equalsIgnoreCase("removeFromCart")){
            
            removeFromCart(request, response);
            return;
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

        action = request.getParameter("action") != null ? request.getParameter("action") : "";
        if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new Project().getPermissionName(action), request, response)){
                if(action.equalsIgnoreCase("addToCart")){
                    addToCart(request,response);
                    
                }
                else if(action.equalsIgnoreCase("removeFromCart")){
                    removeFromCart(request, response);
                    
                }
                else if(request.getParameter("id").equals("")){
                    processInsertRequest(request, response);
                }
                else{
                    processUpdateRequest(request, response);
                }
            }
        }

    }

    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Insert mode");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        viewFile = PROJECTS_NEW;
        boolean insertStatus = false;
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
                
                //sessionUser is a class variable 
                new TrailableManager(project).registerInsertTrailInfo(sessionUser.getSystemUserId());
                
                validate(project);
                
                em.persist(project);
                
                em.getTransaction().commit();
                
                insertStatus = true;
                
                em.refresh(project);
                
                //get this as early as possible
                //Query query = em.createNamedQuery("ProjectUnit.findByProjectId").setParameter("projectId",project.getId());
                //projectUnits = query.getResultList();
            
                //request.setAttribute("project", project);
                //request.setAttribute("success", true);
                //request.setAttribute("units", projectUnits);
                //request.setAttribute("action", "edit");
                //request.setAttribute("id", project.getId());
               
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
            catch(RollbackException e){
                e.printStackTrace();
                System.out.println("inside MYSQL area: " + e.getMessage() + "ACTION: " + action);
                viewFile = PROJECTS_NEW;
                request.setAttribute("project", project);
                request.setAttribute("action", action);
                //request.setAttribute("rolesList", rolesList);
                errorMessages.put("mysqlviolation", e.getMessage());
                request.setAttribute("errors", errorMessages);
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                SystemLogger.logSystemIssue("Project", gson.toJson(project), e.getMessage());
            }
        
            if(insertStatus){
                String page = request.getScheme()+ "://" + request.getHeader("host") + "/" + APP_NAME + "/Project?action=edit&id=" + project.getId() + "&addstat=1";
                response.sendRedirect(page);
            }
            else {
                RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
                dispatcher.forward(request, response);
            }
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
                projectUnits = (List)project.getProjectUnitCollection();
                
                //sessionUser is a class variable 
                System.out.println("sessionUser.getSystemUserId(): " + sessionUser.getSystemUserId());
                new TrailableManager(project).registerUpdateTrailInfo(sessionUser.getSystemUserId());
                log("update");
                //log("Project data" + gson.toJson(projectUnits));
                
                validate(project);
                log("validate");
                                
                em.getTransaction().commit();
                log("commit");
                
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
                HashMap<String, String> projectValues = new HashMap<String, String>();
                projectValues.put("id", project.getId().toString());
                projectValues.put("pname", project.getName());
                projectValues.put("desc", project.getDescription());
                projectValues.put("location", project.getLocation());
                projectValues.put("pmanager", project.getProjectManager());;
                projectValues.put("deleted", String.valueOf(project.getDeleted()));
                projectValues.put("active", String.valueOf(project.getActive()));
                SystemLogger.logSystemIssue("Project", gson.toJson(projectValues), e.getMessage());
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
        
        //sessionUser is a class variable 
        new TrailableManager(project).registerUpdateTrailInfo(sessionUser.getSystemUserId());
        
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