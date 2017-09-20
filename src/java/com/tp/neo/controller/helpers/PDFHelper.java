/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.utils.MailSender;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author SWEDGE
 */
public class PDFHelper {
    
     static SimpleDateFormat sdf ;
    
   public void sendPDFInvoice(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //header "Content-type:application/pdf"
        
        String filename = "Receipt" + (long)(Math.random()*9485973684L) + ".pdf";
        
        OutputStream stream = response.getOutputStream();
        
        Long LodgementID = Long.parseLong(request.getParameter("id"));
        processPDFDoc(LodgementID,filename);

        byte[] bytes = getFile(filename);
       
        response.setContentType("application/pdf");
        response.addHeader("Content-Type", "application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=Invoice" + LodgementID + ".pdf");
        response.setContentLength((int) bytes.length);
        stream.write(bytes);
        stream.close();
        
        File file = new File(filename);
        file.delete();
    
    }
    
    
   private void exportToPdfFile(String url, String outputFileName)
	{
            
            OutputStream os = null;
              try {
               
                  os = new FileOutputStream(outputFileName);
               try {
                     // There are more options on the builder than shown below.
                     PdfRendererBuilder builder = new PdfRendererBuilder();

                     builder.withUri(url);
                     builder.toStream(os);
                     builder.run();
                     
               } catch (Exception e) {
                     e.printStackTrace();
                     // LOG exception
               } finally {
                     try {
                            os.close();
                     } catch (IOException e) {
                            // swallow
                     }
               }
              }
              catch (IOException e) {
                     e.printStackTrace();
                     // LOG exception.
              }
	}
   
   private  byte[] getFile(String filename) {

    byte[] bytes = null;

    try {
        java.io.File file = new java.io.File(filename);
        FileInputStream fis = new FileInputStream(file);
        bytes = new byte[(int) file.length()];
        fis.read(bytes);
        fis.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

    return bytes;
}
    
   
   public void sendPDFEmail(HttpServletRequest request, HttpServletResponse response){
       
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        long lodgement_id = Long.parseLong(request.getParameter("id"));

        String tempFilename = "Receipt" + (long)(Math.random()*22536289L) + ".pdf";
        
        Lodgement lodgement = em.find(Lodgement.class, lodgement_id);
        Customer customer = lodgement.getCustomer();
        List<LodgementItem> LItems = (List) lodgement.getLodgementItemCollection();
       
        String filename = "Receipt" + lodgement_id + ".pdf";
        Company company = em.find(Company.class, 1);
        String htmlMessage = prepareInvoiceEmail(lodgement , customer , company ,LItems , 0.0 , 0.0  );
        processPDFDoc(htmlMessage,tempFilename );
        
        MailSender.sendEmailAttachment(customer.getEmail(),company.getEmail(), "Lodgement Invoice for " + sdf.format(lodgement.getCreatedDate()), htmlMessage, tempFilename , filename);
        
        
        new File(tempFilename).delete();
   }
   
   public String prepareInvoiceEmail(Lodgement lodgement, Customer customer, Company company ,  List<LodgementItem> LItems, double vat, double gateWayCharge) {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Date date = cal.getTime();
            
        sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        String html = "";

        html += "<table cellspacing='0px' >";
        
        html += "<tr style='background-color:blue'>"
                + "<tr style='background-color:blue;color:white'>"
                + "<td style='text-align:left;border-right: solid 2px blue'>"
                + "<b>Receipt #</b>" + lodgement.getId() 
                +"</td><td style='text-align:right'>Sent : " + sdf.format(date) + "</td></tr>";
        
        html += "<tr><td colspan='4' >";

        html += "<table width='100%' style='background-color:#eee;padding:20px'>";
        html += "<tr>";
        html += "<td colspan='5' style='text-align:left;border-bottom: solid 2px blue;'>"+company.getName()+"</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td colspan='2'>";
        html += "<b>From</b> <br/>";
        html += company.getName() + " <br/>";
        html += company.getAddressLine1() + " , " + company.getAddressLine2() + " <br/>";
        html += "Phone: " + company.getPhone() + " <br/>";
        html += "Email: " + company.getEmail();
        html += "</td>";

        html += "<td colspan='3'>";
        html += "<b>To</b> <br/>";
        html += customer.getFullName() + " <br/>";
        html += customer.getStreet() + ", " + customer.getState() + " <br/>";
        html += "Phone: " + customer.getPhone() + " <br/>";
        html += "Email: " + customer.getEmail();
        html += "</td>";

        html += "</tr>";
        html +="<tr><td colspan='4'></td></tr>";
        html += "<tr><td> Transaction Date: " + sdf.format(lodgement.getCreatedDate())
                                             + "</td></tr>";
       
        //Prepare the OrderItems Table
        html += "<tr>";
        html += "<td colspan='5'>";

        html += "<table width='100%' style='margin-top:40px;margin-right:0px'>";
        html += "<tr >";
        html += "<th style='border: solid 1px #ccc;background-color:blue;color:white;padding:5px'>SN</th>  <th colspan='1' style='border: solid 1px #ccc;background-color:blue;color:white;padding:5px'>Description</th> <th style='border: solid 1px #ccc;background-color:blue;color:white;padding:5px'>Qty</th>  <th style='border: solid 1px #ccc;background-color:blue;color:white;padding:5px'>Amount (&#8358;)</th>";
        html += "</tr>";

        int count = 1;
        double total = 0;
        for (LodgementItem LI : LItems) {
            double reward = LI.getRewardAmount() != null ? LI.getRewardAmount() : 0;
            double amount = LI.getAmount() + reward;

            html += "<tr>";
            html += "<td style='border: solid 1px #ccc;padding:5px'>";
            html += count;
            html += "</td>";
            html += "<td style='border: solid 1px #ccc;padding:5px'>";
            html += LI.getItem().getUnit().getProject().getName() + " - " + LI.getItem().getUnit().getTitle();
            html += "</td>";
            html += "<td style='border: solid 1px #ccc;padding:5px'>";
            html += LI.getItem().getQuantity();
            html += "</td>";
            html += "<td style='text-align:right;border: solid 1px #ccc;padding:5px'>";
            html += String.format("%1$,.2f", amount);
            html += "</td>";

            html += "</tr>";

            total += amount;
            count++;
        }
        html += "<tfoot style='background-color:#F3F5F6;'>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>Total: </td>";
        html += "<td style='text-align:right;border: solid 1px #ccc;padding:5px'><b>" + String.format("%1$,.2f", total) + "</b></td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>VAT: </td>";
        html += "<td style='text-align:right;border: solid 1px #ccc;padding:5px'><b>" + String.format("%1$,.2f", vat) + "</b></td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>GateWay Charge: </td>";
        html += "<td style='text-align:right;border: solid 1px #ccc;padding:5px'><b>" + String.format("%1$,.2f",gateWayCharge) + "</b></td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>Grand Total: </td>";
        html += "<td style='text-align:right;border: solid 1px #ccc;padding:5px'><b>" + String.format("%1$,.2f", total + vat + gateWayCharge) + "</b></td>";
        html += "</tr>";
        html += "</tfoot>";

        html += "</table>";
        html += "</td>";
        html += "</tr>";
       
        html += "</table>";

        html += "</td></tr></tr>";
        html +="<tr><td colspan='4' style='text-align:right;'>NEOFORCE SFA.</td></tr><tr><td colspan='4' style='text-align:right;'>Powered By Techie Planet</td></tr>";
        html += "</table>";

        return html;

    }
   
   public void processPDFDoc(Long lodgementId , String Filename){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        long lodgement_id = lodgementId;

        Lodgement lodgement = em.find(Lodgement.class, lodgement_id);
        Customer customer = lodgement.getCustomer();
        List<LodgementItem> LItems = (List) lodgement.getLodgementItemCollection();
       
        Company company = em.find(Company.class, 1);
        String htmlMessage = prepareInvoiceEmail(lodgement , customer , company ,LItems , 0.0 , 0.0  );
        
        processPDFDoc(htmlMessage , Filename);
   }
   
   private void processPDFDoc(String htmlMessage , String Filename){
        
       OutputStream os = null;
       
              try {
               
                  os = new FileOutputStream(Filename);
               try {
                     // There are more options on the builder than shown below.
                     PdfRendererBuilder builder = new PdfRendererBuilder();

                     builder.withHtmlContent(htmlMessage, "/");
                     builder.toStream(os);
                     builder.run();
                     
               } catch (Exception e) {
                     e.printStackTrace();
                     // LOG exception
               } finally {
                     try {
                            os.close();
                     } catch (IOException e) {
                            // swallow
                     }
               }
              }
              catch (IOException e) {
                     e.printStackTrace();
                     // LOG exception.
              }
        
        
   }

}
