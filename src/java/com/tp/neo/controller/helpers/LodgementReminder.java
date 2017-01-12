/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.OrderItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
@WebServlet(name ="LodgementReminder" , urlPatterns = {"/LodgementReminder"})
public class LodgementReminder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        sendLodgementReminder();
        
    }
    
    
    
    
    public void sendLodgementReminder(){
         
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
         EntityManager em = emf.createEntityManager();
         
         Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
         
         int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
         
         Query query = em.createNamedQuery("OrderItem.findByDayOfReminder");
         
         List<OrderItem> orderItems = query.getResultList();
         
         OrderItemHelper itemHelper = new OrderItemHelper();
         
         List<OrderItem> listOfItemsForReminder = new ArrayList();
         
         for(OrderItem orderItem : orderItems){
             
             double totalAmtPaid = itemHelper.getTotalItemPaidAmount((List)orderItem.getLodgementItemCollection());
             double balance = itemHelper.getOrderItemBalance(orderItem.getUnit().getAmountPayable(), orderItem.getQuantity(), totalAmtPaid, "double");
             
             if(balance > 0){
                 listOfItemsForReminder.add(orderItem);
             }
         }
         
         // Pass the list of orderItems to EmailHelper to send Remainder Mail
         
     }
    
}
