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
import com.tp.neo.model.ProjectUnitPK;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.utils.TrailableManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;


/**
 *
 * @author Swedge
 */
@WebServlet(name = "ProjectUnit", urlPatterns = {"/ProjectUnit"})
public class ProjectUnitController extends AppController {

    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String PROJECTS_ADMIN = "/views/project/admin.jsp"; 
    private static String PROJECTS_NEW = "/views/project/add.jsp";
    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    private HashMap<String, String> messages = new HashMap<String, String>();
    
    
    
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
            out.println("<title>Servlet ProjectUnitController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProjectUnitController at " + request.getContextPath() + "</h1>");
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

        
        
            processGetRequest(request, response);

    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
//        String viewFile = PROJECTS_ADMIN; 
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
//        if (action.equalsIgnoreCase("new")){
//               viewFile = PROJECTS_NEW;
//        }
        if(action.equalsIgnoreCase("delete")){
            delete(Integer.parseInt(request.getParameter("id")));
        }


        else if(action.equalsIgnoreCase("edit")){            
            int id = (Integer.parseInt(request.getParameter("id")));
            Query query = em.createNamedQuery("ProjectUnit.findById").setParameter("id", id);
            ProjectUnit projectUnit = (ProjectUnit)query.getSingleResult();
            em.close(); emf.close();
            
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", projectUnit.getTitle());
            map.put("cpu", projectUnit.getCpu().toString());
            map.put("lid", projectUnit.getLeastInitDep().toString());
            map.put("discount", projectUnit.getDiscount().toString());
            map.put("mpd", projectUnit.getMaxPaymentDuration().toString());
            map.put("commp", projectUnit.getCommissionPercentage().toString());
            map.put("quantity", projectUnit.getQuantity() + "");

            map.put("amt_payable", projectUnit.getAmountPayable()+ "");
            map.put("monthly_pay", projectUnit.getMonthlyPay()+ "");
            

            
            Gson gson = new GsonBuilder().create();
            String jsonResponse = gson.toJson(map);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            System.out.println("jsonResponse: " + jsonResponse);
        }
//        else if (action.isEmpty() || action.equalsIgnoreCase("listprojects")){
//            viewFile = PROJECTS_ADMIN;
//            request.setAttribute("projects", listProjects());
//        }
//
          
            
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

        if(super.hasActiveUserSession(request, response)){
            if(request.getParameter("id").equals(""))  //save mode
                processInsertRequest(request, response);
            else
                processUpdateRequest(request, response);
        }

    }

      
    
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        String jsonResponse = "";                
        ProjectUnit projectUnit = new ProjectUnit();
        Gson gson = new GsonBuilder().create();
        
            try{                                
                em.getTransaction().begin();
                
                validate(request);
                
                Project project = em.find(Project.class, Integer.parseInt(request.getParameter("projectid")));
                
                projectUnit.setTitle(request.getParameter("title"));
                projectUnit.setCpu(Double.parseDouble(request.getParameter("cpu")));
                projectUnit.setLeastInitDep(Double.parseDouble(request.getParameter("lid")));
                projectUnit.setDiscount(Double.parseDouble(request.getParameter("discount")));
                projectUnit.setMaxPaymentDuration(Integer.parseInt(request.getParameter("mpd")));
                projectUnit.setCommissionPercentage(Double.parseDouble(request.getParameter("commp")));
                projectUnit.setQuantity(Integer.parseInt(request.getParameter("quantity")));

                projectUnit.setMonthlyPay(Double.parseDouble(request.getParameter("monthly_pay")));
                projectUnit.setAmountPayable(Double.parseDouble(request.getParameter("amt_payable")));

                
                new TrailableManager(projectUnit).registerInsertTrailInfo((long)1);
                
                //connect the Project entity with Project unit entity using PK entity
                ProjectUnitPK pk = new ProjectUnitPK();
                pk.setProjectId(project.getId());
                projectUnit.setProjectUnitPK(pk);
                project.getProjectUnitCollection().add(projectUnit);
                


                em.persist(project);
                
                em.getTransaction().commit();
                
                //find by last ID
                if(request.getParameter("id").equals("")){
                    Query jpqlQuery  = em.createNamedQuery("ProjectUnit.findByLastId");
                    List puList = jpqlQuery.setMaxResults(1).getResultList();
                    projectUnit = (ProjectUnit)puList.get(0);                                    
                }
               //System.out.println("Getting the ID: " + projectUnit.getTitle());
                
                em.close();
                emf.close();
                
                messages.put("STATUS", "OK");
                messages.put("UNIT_ID", projectUnit.getProjectUnitPK().getId() + "");
                messages.put("TITLE", projectUnit.getTitle());
                messages.put("QUANTITY", projectUnit.getQuantity() + "");
                
                jsonResponse = gson.toJson(messages);
                System.out.println("BEFORE RETURN");
            }
            catch(PropertyException e){
                System.out.println("inside catch area: " + gson.toJson(errorMessages));
                Iterator it = errorMessages.entrySet().iterator(); String errorString="";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    errorString += pair.getValue() + "<br/>";
                    //it.remove(); // avoids a ConcurrentModificationException
                }
                messages.put("STATUS", "ERROR");
                messages.put("MESSAGE", errorString);
                jsonResponse = gson.toJson(messages);
                System.out.println("Error Messages: " + jsonResponse);
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", projectUnit.getTitle());

                map.put("quantity", projectUnit.getQuantity() + "");
                map.put("cpu", projectUnit.getCpu().toString());
                map.put("lid", projectUnit.getLeastInitDep().toString());
                map.put("discount", projectUnit.getDiscount().toString());
                map.put("amt_payable", projectUnit.getAmountPayable()+ "");
                map.put("mpd", projectUnit.getMaxPaymentDuration().toString());
                map.put("monthly_pay", projectUnit.getMonthlyPay()+ "");
                map.put("commp", projectUnit.getCommissionPercentage().toString());

                SystemLogger.logSystemIssue("ProjectUnit", gson.toJson(map), e.getMessage());
            }
        
           boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
           
           response.setContentType("text/plain");
           response.setCharacterEncoding("UTF-8");
           response.getWriter().write(jsonResponse);
           
    }
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("This is the update mode");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String jsonResponse = "";
        ProjectUnit projectUnit = new ProjectUnit();
        Gson gson = new GsonBuilder().create();
        
            try{     
                em.getTransaction().begin();
                
                validate(request);
                
                int id = (Integer.parseInt(request.getParameter("id")));
                Query query = em.createNamedQuery("ProjectUnit.findById").setParameter("id", id);
                projectUnit = (ProjectUnit)query.getSingleResult();
                
                projectUnit.setTitle(request.getParameter("title"));
                projectUnit.setCpu(Double.parseDouble(request.getParameter("cpu")));
                projectUnit.setLeastInitDep(Double.parseDouble(request.getParameter("lid")));
                projectUnit.setDiscount(Double.parseDouble(request.getParameter("discount")));
                projectUnit.setMaxPaymentDuration(Integer.parseInt(request.getParameter("mpd")));
                projectUnit.setCommissionPercentage(Double.parseDouble(request.getParameter("commp")));
                projectUnit.setQuantity(Integer.parseInt(request.getParameter("quantity")));

                projectUnit.setMonthlyPay(Double.parseDouble(request.getParameter("monthly_pay")));
                projectUnit.setAmountPayable(Double.parseDouble(request.getParameter("amt_payable")));
                
                new TrailableManager(projectUnit).registerUpdateTrailInfo((long)1);
                

                em.getTransaction().commit();
                                
                em.close();
                emf.close();
                
                messages.put("STATUS", "OK");
                messages.put("UNIT_ID", projectUnit.getProjectUnitPK().getId() + "");
                messages.put("TITLE", projectUnit.getTitle());
                messages.put("QUANTITY", projectUnit.getQuantity() + "");
                
                jsonResponse = gson.toJson(messages);
                System.out.println("BEFORE RETURN");
            }
            catch(PropertyException e){
                System.out.println("inside catch area: " + gson.toJson(errorMessages));
                Iterator it = errorMessages.entrySet().iterator(); String errorString="";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    errorString += pair.getValue() + "<br/>";
                    //it.remove(); // avoids a ConcurrentModificationException
                }
                messages.put("STATUS", "ERROR");
                messages.put("MESSAGE", errorString);
                jsonResponse = gson.toJson(messages);
                System.out.println("Error Messages: " + jsonResponse);
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("System Error: " + e.getMessage());
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", projectUnit.getTitle());

                map.put("quantity", projectUnit.getQuantity() + "");
                map.put("cpu", projectUnit.getCpu().toString());
                map.put("lid", projectUnit.getLeastInitDep().toString());
                map.put("discount", projectUnit.getDiscount().toString());
                map.put("amt_payable", projectUnit.getAmountPayable()+ "");
                map.put("mpd", projectUnit.getMaxPaymentDuration().toString());
                map.put("monthly_pay", projectUnit.getMonthlyPay()+ "");
                map.put("commp", projectUnit.getCommissionPercentage().toString());

                SystemLogger.logSystemIssue("ProjectUnit", gson.toJson(map), e.getMessage());
            }
        
           boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
           
           response.setContentType("text/plain");
           response.setCharacterEncoding("UTF-8");
           response.getWriter().write(jsonResponse);
    }
    
    
    public void delete(int id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        Query query = em.createNamedQuery("ProjectUnit.findById").setParameter("id", id);
        ProjectUnit projectUnit = (ProjectUnit)query.getSingleResult();
        em.getTransaction().begin();
        new TrailableManager(projectUnit).registerUpdateTrailInfo((long)1);
        em.remove(projectUnit);
        em.getTransaction().commit();

        em.close();
        emf.close();
    }
    
    
    private void validate(HttpServletRequest request) throws PropertyException{
        errorMessages.clear();
        
        if(request.getParameter("title").isEmpty()){
            errorMessages.put("title", "Please enter a title ");
        }
        if(!request.getParameter("cpu").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("cpu", "Please enter valid Cost Per Unit");
        }
        if(!request.getParameter("lid").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("lid", "Please enter valid Least Initial Deposit");
        }
        if(!request.getParameter("discount").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("discount", "Please enter Discount Percentage");
        }    

        if(!request.getParameter("mpd").matches("^\\d+(\\.?\\d+$)?")){

            errorMessages.put("mpd", "Please enter a valid whole month number");
        }    
        
        if(!request.getParameter("amt_payable").matches("^\\d+(\\.?\\d+$)?") || Double.parseDouble(request.getParameter("amt_payable")) <= 0 ){
            errorMessages.put("amt_payable", "Ammount Payable cannot be empty or 0. Please adjust other values.");
        }    
        
        if(!request.getParameter("monthly_pay").matches("^\\d+(\\.?\\d+$)?") || Double.parseDouble(request.getParameter("monthly_pay")) <= 0 ){
            errorMessages.put("monthly_pay", "Monthly Pay cannot be empty or 0. Please adjust other values.");

        }    
        
        if(!request.getParameter("commp").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("commp", "Please enter Commission Percentage");
        }    
        
        if(!request.getParameter("quantity").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("quantity", "Please enter a valid Quantity");
        }
        
        if(!(errorMessages.isEmpty())) throw new PropertyException("");
        
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
