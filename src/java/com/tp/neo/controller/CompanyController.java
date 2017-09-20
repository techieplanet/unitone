/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.tp.neo.model.Company;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.service.CompanyService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author SWEDGE
 */
@WebServlet(name="CompanyController" , urlPatterns={"/Company"})
@MultipartConfig
public class CompanyController extends AppController {


    private static String CompanyForm = "views/company/add.jsp";
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
        
        if(super.hasActiveUserSession(request, response))
         {
            //if(super.hasActionPermission(new CompanyAccount().getPermissionName(action), request, response)){
                request.setAttribute("company", CompanyService.getCompany());
                request.getRequestDispatcher(CompanyForm).forward(request, response);
            //}
            //else{
            //    super.errorPageHandler("forbidden", request, response);
            //}
         }
            
            
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
       
        if(super.hasActiveUserSession(request, response))
         {
            //if(super.hasActionPermission(new CompanyAccount().getPermissionName(action), request, response)){
                updateCompanyInformation(request, response);
            //}
            //else{
            //    super.errorPageHandler("forbidden", request, response);
            //}
         }
    }

    private void updateCompanyInformation(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
       Company company = new Company();
       company.setName(request.getParameter("companyName"));
       company.setEmail(request.getParameter("companyEmail"));
       company.setPhone(request.getParameter("companyPhone"));
       company.setAddressLine1(request.getParameter("companyAddress1"));
       company.setAddressLine2(request.getParameter("companyAddress2"));
       
       if(request.getPart("companyLogo") != null)
       {
          company.setLogoPath(saveCompanyLogo(request.getPart("companyLogo")));
       }
       
       CompanyService.saveCompany(company);
       request.setAttribute("company", company);
       request.setAttribute("success" , true);
       request.getRequestDispatcher(CompanyForm).forward(request, response);
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

    private String  saveCompanyLogo(Part part) throws IOException{
        String saveName = part.getSubmittedFileName().trim();
                if(!saveName.isEmpty())
                {
                FileUploader fUpload = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);
                saveName = "companyLogo" + System.currentTimeMillis()+ "." + FileUploader.getSubmittedFileExtension(saveName);
                fUpload.uploadFile(part, saveName, true);
                }
                return saveName;
    }
}
