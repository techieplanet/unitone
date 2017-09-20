/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.Agent;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.service.BankService;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Prestige
 */
@WebServlet(name="RegisterAgent", urlPatterns = {"/AgentRegistration"})
@MultipartConfig
public class AgentRegistrationController extends AppController {

    
    private HashMap<String, String> errorMessages = new HashMap<String, String>();
    private static String AGENTS_REGISTRATION = "/views/agent/registration.jsp";
    private static String AGENTS_NEW = "/views/agent/add.jsp";
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        String viewFile = "/views/agent/registration.jsp";
        
        String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
        
        if(action.equalsIgnoreCase("success")){
            viewFile = "/views/agent/success.jsp";
        }else if(action.equalsIgnoreCase("ref")){
            //http://localhost:8080/NeoForce/AgentRegistration?action=ref&refCode=QUcwMDAyOA==&refId=Mjg=
            viewFile = "/views/agent/registration.jsp";
            String refCodeEncoded = req.getParameter("refCode") != null ? req.getParameter("refCode") : "";
            String targetEncoded = req.getParameter("target") != null ? req.getParameter("target") : "";
                    
            String refIdEncoded = req.getParameter("refId") != null ? req.getParameter("refId") : "";
            byte[] decodedBytes = Base64.getDecoder().decode(refIdEncoded);
            String refIdDecoded = new String(decodedBytes);
            
            Agent agent = AppController.emf.createEntityManager().find(Agent.class, Long.parseLong(refIdDecoded));
            req.setAttribute("refAgent", agent);
            req.setAttribute("referralMode", true);
            req.setAttribute("agentImageAccessDir", imageAccessDirPath + "/agents");
            req.setAttribute("redirectString", "action="+action+"&refCode="+refCodeEncoded+"&refId="+refIdEncoded+"&target="+targetEncoded);
            //req.setAttribute("action", action);
            //req.setAttribute("refCode", refCodeEncoded);
            //req.setAttribute("target", targetEncoded);
        }
        req.setAttribute("Banks", BankService.getAllBanks());
        req.setAttribute("action", "new");
         if(req.getParameter("corporate") != null)
               {
                  req.setAttribute("corporate" , true);
               }
        req.setAttribute("from", "agent_registration");
        req.getRequestDispatcher(viewFile).forward(req, resp);
    }
    
    
}