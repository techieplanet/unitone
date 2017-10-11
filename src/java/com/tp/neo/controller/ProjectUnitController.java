/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.AccountManager;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.model.Account;
import com.tp.neo.model.Project;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.ProjectUnitType;
import com.tp.neo.model.User;
import com.tp.neo.model.utils.FileUploader;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.PropertyException;


/**
 *
 * @author Swedge
 */
@WebServlet(name = "ProjectUnit", urlPatterns = {"/ProjectUnit"})
@MultipartConfig
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
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        
        if(action.equalsIgnoreCase("edit"))
        {
            processGetRequest(request, response);
        }
          else  if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new ProjectUnit().getPermissionName(action), request, response)){
                if(action.equalsIgnoreCase("delete")){
                 delete(Long.parseLong(request.getParameter("id")));
                 }
            }else{
                super.errorPageHandler("forbidden", request, response);
            }
        }
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
        
         if(action.equalsIgnoreCase("edit")){            
            long id = (Long.parseLong(request.getParameter("id")));
            Query query = em.createNamedQuery("ProjectUnit.findById").setParameter("id", id);
            ProjectUnit projectUnit = (ProjectUnit)query.getSingleResult();
            em.close(); emf.close();
            
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", projectUnit.getTitle());
            map.put("cpu", projectUnit.getCpu().toString());
            map.put("building_cost", projectUnit.getBuildingCost().toString());
            map.put("service_value", projectUnit.getServiceValue().toString());
            map.put("income", projectUnit.getIncome().toString());
            map.put("lid", projectUnit.getLeastInitDep().toString());
            map.put("discount", projectUnit.getDiscount().toString());
            map.put("mpd", projectUnit.getMaxPaymentDuration().toString());
            map.put("commp", projectUnit.getCommissionPercentage().toString());
            map.put("vatp", projectUnit.getVatPercentage().toString());
            map.put("amp", projectUnit.getAnnualMaintenancePercentage().toString());
            map.put("reward_points", projectUnit.getRewardPoints().toString());
            map.put("quantity", projectUnit.getQuantity() + "");

            map.put("amt_payable", projectUnit.getAmountPayable()+ "");
            map.put("monthly_pay", projectUnit.getMonthlyPay()+ "");
            map.put("unit_type_id", projectUnit.getUnitType() != null ? projectUnit.getUnitType().getId().toString() : "0");

            
            Gson gson = new GsonBuilder().create();
            String jsonResponse = gson.toJson(map);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            response.getWriter().flush(); 
            response.getWriter().close();
            //System.out.println("jsonResponse: " + jsonResponse);
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
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        
        
            if(super.hasActiveUserSession(request, response)){
            if(super.hasActionPermission(new ProjectUnit().getPermissionName(action), request, response)){
            
            if(action.equals("imageUpload"))
                projectUnitImageUpload(request, response);
            else if(request.getParameter("id").equals(""))  //save mode
                processInsertRequest(request, response);
            else
                processUpdateRequest(request, response);
            }else{
                super.errorPageHandler("forbidden", request, response);
            }
            
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
                
                Project project = em.find(Project.class, Long.parseLong(request.getParameter("projectid")));
                
                projectUnit.setTitle(request.getParameter("title"));
                projectUnit.setCpu(Double.parseDouble(request.getParameter("cpu")));
                projectUnit.setBuildingCost(Double.parseDouble(request.getParameter("building_cost")));
                projectUnit.setServiceValue(Double.parseDouble(request.getParameter("service_value")));
                projectUnit.setLeastInitDep(Double.parseDouble(request.getParameter("lid")));
                projectUnit.setDiscount(Double.parseDouble(request.getParameter("discount")));
                projectUnit.setMaxPaymentDuration(Integer.parseInt(request.getParameter("mpd")));
                projectUnit.setCommissionPercentage(Double.parseDouble(request.getParameter("commp")));
                projectUnit.setVatPercentage(Double.parseDouble(request.getParameter("vatp")));
                projectUnit.setAnnualMaintenancePercentage(Double.parseDouble(request.getParameter("amp")));
                projectUnit.setRewardPoints(Double.parseDouble(request.getParameter("reward_points")));
                projectUnit.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                projectUnit.setIncome(Double.parseDouble(request.getParameter("income")));
                projectUnit.setUnitType(em.find(ProjectUnitType.class, Integer.parseInt(request.getParameter("unittype"))));

                projectUnit.setMonthlyPay(Double.parseDouble(request.getParameter("monthly_pay")));
                projectUnit.setAmountPayable(Double.parseDouble(request.getParameter("amt_payable")));
               /*
                
                */
                new TrailableManager(projectUnit).registerInsertTrailInfo(sessionUser.getSystemUserId());
                
                //set the Project object
                projectUnit.setProject(project);
                project.getProjectUnitCollection().add(projectUnit);
                
                em.persist(projectUnit);
                
                em.flush();
                                
                //set up the account for this unit
                Account account = new AccountManager().createUnitAccount(projectUnit);
                
                //now link the unit and the account by updating the unit
                em.refresh(projectUnit);
                projectUnit.setAccount(account);
                em.flush();
                
                em.getTransaction().commit();
                
                //find by last ID
                if(request.getParameter("id").equals("")){
                    Query jpqlQuery  = em.createNamedQuery("ProjectUnit.findLastInserted");
                    List puList = jpqlQuery.setMaxResults(1).getResultList();
                    projectUnit = (ProjectUnit)puList.get(0);                                    
                }
               ////System.out.println("Getting the ID: " + projectUnit.getTitle());
                
                em.close();
                emf.close();
                
                messages.put("STATUS", "OK");
                messages.put("UNIT_ID", projectUnit.getId() + "");
                messages.put("TITLE", projectUnit.getTitle());
                messages.put("QUANTITY", projectUnit.getQuantity() + "");
                
                jsonResponse = gson.toJson(messages);
                ////System.out.println("BEFORE RETURN: " + jsonResponse);
            }
            catch(PropertyException e){
                //System.out.println("inside catch area: " + gson.toJson(errorMessages));
                Iterator it = errorMessages.entrySet().iterator(); String errorString="";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    ////System.out.println(pair.getKey() + " = " + pair.getValue());
                    errorString += pair.getValue() + "<br/>";
                    //it.remove(); // avoids a ConcurrentModificationException
                }
                messages.put("STATUS", "ERROR");
                messages.put("MESSAGE", errorString);
                jsonResponse = gson.toJson(messages);
                //System.out.println("Error Messages: " + jsonResponse);
            }
            catch(Exception e){
                e.printStackTrace();
                //System.out.println("System Error: " + e.getMessage());
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
                map.put("vatp", projectUnit.getVatPercentage().toString());
                map.put("amp", projectUnit.getAnnualMaintenancePercentage().toString());
                map.put("reward_points", projectUnit.getRewardPoints().toString());
                map.put("building_cost", projectUnit.getBuildingCost().toString());
                map.put("income", projectUnit.getIncome().toString());
                map.put("service_value", projectUnit.getServiceValue().toString());

                SystemLogger.logSystemIssue("ProjectUnit", gson.toJson(map), e.getMessage());
                
            }
        
           //boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
           
           response.setContentType("text/plain");
           response.setCharacterEncoding("UTF-8");
           response.getWriter().write(jsonResponse);
           
    }
    
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("This is the update mode");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String jsonResponse = "";
        ProjectUnit projectUnit = new ProjectUnit();
        Gson gson = new GsonBuilder().create();
        
            try{     
                em.getTransaction().begin();
                
                validate(request);
               
                long id = (Long.parseLong(request.getParameter("id")));
                Query query = em.createNamedQuery("ProjectUnit.findById").setParameter("id", id);
                projectUnit = (ProjectUnit)query.getSingleResult();
                
                projectUnit.setTitle(request.getParameter("title"));
                projectUnit.setCpu(Double.parseDouble(request.getParameter("cpu")));
                projectUnit.setBuildingCost(Double.parseDouble(request.getParameter("building_cost")));
                projectUnit.setServiceValue(Double.parseDouble(request.getParameter("service_value")));
                projectUnit.setLeastInitDep(Double.parseDouble(request.getParameter("lid")));
                projectUnit.setDiscount(Double.parseDouble(request.getParameter("discount")));
                projectUnit.setMaxPaymentDuration(Integer.parseInt(request.getParameter("mpd")));
                projectUnit.setCommissionPercentage(Double.parseDouble(request.getParameter("commp")));
                projectUnit.setVatPercentage(Double.parseDouble(request.getParameter("vatp")));
                projectUnit.setAnnualMaintenancePercentage(Double.parseDouble(request.getParameter("amp")));
                projectUnit.setRewardPoints(Double.parseDouble(request.getParameter("reward_points")));
                projectUnit.setQuantity(Integer.parseInt(request.getParameter("quantity")));
                projectUnit.setIncome(Double.parseDouble(request.getParameter("income")));

                projectUnit.setMonthlyPay(Double.parseDouble(request.getParameter("monthly_pay")));
                projectUnit.setAmountPayable(Double.parseDouble(request.getParameter("amt_payable")));
                projectUnit.setUnitType(em.find(ProjectUnitType.class, Integer.parseInt(request.getParameter("unittype"))));
                
                new TrailableManager(projectUnit).registerUpdateTrailInfo(sessionUser.getSystemUserId());
                /*
                String saveName = request.getPart("Image").getSubmittedFileName().trim();
                
                if(!saveName.isEmpty())
                {
                FileUploader fUpload = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);String subdir = "projectunits";
                saveName = "pojectunit" + System.currentTimeMillis()+ "." + FileUploader.getSubmittedFileExtension(saveName);
                projectUnit.setImage("projects/"+saveName);
                fUpload.uploadFile(request.getPart("Image"), subdir, saveName, true);
                }
                */
                em.getTransaction().commit();
                                
                em.close();
                emf.close();
                
                messages.put("STATUS", "OK");
                messages.put("UNIT_ID", projectUnit.getId() + "");
                messages.put("TITLE", projectUnit.getTitle());
                messages.put("QUANTITY", projectUnit.getQuantity() + "");
                
                jsonResponse = gson.toJson(messages);
                ////System.out.println("BEFORE RETURN: " + jsonResponse);
            }
            catch(PropertyException e){
                //System.out.println("inside catch area: " + gson.toJson(errorMessages));
                Iterator it = errorMessages.entrySet().iterator(); String errorString="";
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    ////System.out.println(pair.getKey() + " = " + pair.getValue());
                    errorString += pair.getValue() + "<br/>";
                    //it.remove(); // avoids a ConcurrentModificationException
                }
                messages.put("STATUS", "ERROR");
                messages.put("MESSAGE", errorString);
                jsonResponse = gson.toJson(messages);
                //System.out.println("Error Messages: " + jsonResponse);
            }
            catch(Exception e){
                e.printStackTrace();
                //System.out.println("System Error: " + e.getMessage());
                Map<String, String> map = new HashMap<String, String>();
                map.put("title", projectUnit.getTitle());

                map.put("quantity", projectUnit.getQuantity() + "");
                map.put("cpu", projectUnit.getCpu().toString());
                map.put("lid", projectUnit.getLeastInitDep().toString());
                map.put("discount", projectUnit.getDiscount().toString());
                map.put("amt_payable", projectUnit.getAmountPayable()+ "");
                map.put("mpd", projectUnit.getMaxPaymentDuration().toString());
                map.put("monthly_pay", projectUnit.getMonthlyPay()+ "");
                map.put("buidling_cost", projectUnit.getBuildingCost().toString());
                map.put("service_value", projectUnit.getServiceValue().toString());
                map.put("income", projectUnit.getIncome().toString());
                map.put("commp", projectUnit.getCommissionPercentage().toString());
                map.put("vatp", projectUnit.getVatPercentage().toString());
                map.put("amp", projectUnit.getAnnualMaintenancePercentage().toString());
                map.put("reward_points", projectUnit.getRewardPoints().toString());
                

                SystemLogger.logSystemIssue("ProjectUnit", gson.toJson(map), e.getMessage());
            }
//        
//           boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
           
           response.setContentType("text/plain");
           response.setCharacterEncoding("UTF-8");
           response.getWriter().write(jsonResponse);
    }
    
    
    public void delete(long id){
        log("starting to delete: " + id);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        try{
            Query query = em.createNamedQuery("ProjectUnit.findById").setParameter("id", id);
            ProjectUnit projectUnit = (ProjectUnit)query.getSingleResult();

            Project project = em.find(Project.class, projectUnit.getProject().getId());

            em.getTransaction().begin();

            projectUnit.setDeleted((short)1);
            new TrailableManager(projectUnit).registerUpdateTrailInfo(sessionUser.getSystemUserId());

            em.getTransaction().commit();

            em.merge(project);
            
            //deactivate the account associated with the unit
            new AccountManager().deactivateAccount(projectUnit.getAccount());

            em.merge(projectUnit);
            
            em.close();
            emf.close();
            
        } catch(PropertyException e){
               log("Property: " + e.getMessage());
            }
            catch(Exception e){
              log("Exception: " + e.getMessage());
            }
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
          //  errorMessages.put("monthly_pay", "Monthly Pay cannot be empty or 0. Please adjust other values.");

        }    
        
        if(!request.getParameter("commp").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("commp", "Please enter Commission Percentage");
        }    
        
        if(!request.getParameter("vatp").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("vatp", "Please enter VAT Percentage");
        }    
        
        if(!request.getParameter("amp").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("amp", "Please enter Annual Maintenance Percentage");
        }
        
        if(!request.getParameter("reward_points").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("reward_points", "Please enter a valid Reward Point value");
        }
        
        if(!request.getParameter("quantity").matches("^\\d+$")){
            errorMessages.put("quantity", "Please enter a valid Quantity");
        }
        
        if(!request.getParameter("building_cost").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("building_cost", "Please enter valid money value");
        }
        
        if(!request.getParameter("service_value").matches("^\\d+(\\.?\\d+$)?")){
            errorMessages.put("service_value", "Please enter valid money value");
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

    public void projectUnitImageUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
                EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
                EntityManager em = emf.createEntityManager();
                String unitId = request.getParameter("imgUnitId");
                Long id = Long.valueOf(unitId);
                if(id == null)
                {
                    response.sendError(404, "You Have Made An Invalid Request");
                    return;
                }
                
                ProjectUnit projectUnit = em.find(ProjectUnit.class, id);
                String saveName = request.getPart("Image").getSubmittedFileName().trim();
                em.getTransaction().begin();
                if(!saveName.isEmpty())
                {
                FileUploader fUpload = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);
                String subdir = "projectunits";
                saveName = "projectunit" + System.currentTimeMillis()+ "." + FileUploader.getSubmittedFileExtension(saveName);
                projectUnit.setImage("projectunits/"+saveName);
                fUpload.uploadFile(request.getPart("Image"), subdir, saveName, true);
                }
                em.getTransaction().commit();
                
             List<User> usersList = em.createNamedQuery("User.findAll").getResultList();
             
            //find by ID
            Project project = projectUnit.getProject();
            
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
            
            /*
                Get the project unit types
            */
            List<ProjectUnitType> unitTypes = em.createNamedQuery("ProjectUnitType.findByActive").setParameter("active", 1).getResultList();
                    
            log("length: " + unitTypes.size());
            request.setAttribute("units", projectUnits);
            request.setAttribute("unitTypes", unitTypes);
            request.setAttribute("project", project);
            request.setAttribute("action", "edit");
            request.setAttribute("id", project.getId());
            request.setAttribute("users", usersList);
            request.setAttribute("unitSuccess", true);
            request.getRequestDispatcher(PROJECTS_NEW).forward(request, response);
            return;
    }
}