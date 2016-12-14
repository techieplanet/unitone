/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author hp
 */
public class OrderItemHelper {
    
    
    public Double getTotalItemPaidAmount(List<LodgementItem> lodgementItem){
       
       double totalAmount = 0.00;
       
       for(LodgementItem item : lodgementItem){
           totalAmount += item.getAmount();
       }
       
       return totalAmount;
   }
   
   public String getOrderItemBalance(double amtPayable, int qty, double amountPaid){
       
       String amt = String.format("%.2f", (Double)((amtPayable * qty) - amountPaid));
       return amt;
   }
   
   public String getOrderItemDiscount(double discountPercent, double cpu, int qty){
       
       String amt = String.format("%.2f", (Double)((discountPercent / 100 ) * (cpu * qty)));
       return amt;
   }
    
   public String getCompletionDate(OrderItem item, double total_paid){
       
       //Get monthly Pay
       double mortgage = item.getUnit().getMonthlyPay();
       double balance = Double.parseDouble(getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), total_paid));
       int qty = item.getQuantity();
       
       double months = Math.ceil(balance / (mortgage * qty));
       Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
       
       SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
       String date = "";
       
       if(months <= 0){
           
           //Date d = item.getOrder().getModifiedDate();
           //cal.setTime(d);
           date = "Completed";
       }
       else{
           
         cal.add(Calendar.MONTH, (int)months);
         date = sdf.format(cal.getTime());
       }
       
       System.out.println("Month = " + (int)months);
       
       
       
       return date;
   }
   
   public String getPaymentStage(OrderItem item, double total_paid){
       
       double mortgage = item.getUnit().getMonthlyPay();
       Date date = item.getCreatedDate();
       int qty = item.getQuantity();
       
       double initialDeposit = item.getInitialDep();
       double months = 0;
       
       Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
       
       SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
       
       String dateString = "";
       
       if((total_paid - initialDeposit) <= 0){
            
            cal.setTime(date);
            return sdf.format(cal.getTime());
       }else{
             months = Math.ceil((total_paid - item.getInitialDep()) / (mortgage * qty));
       }
       
       
       if(months <= 0){
           
          //cal.setTime(item.getOrder().getModifiedDate());
          dateString = "Completed";
       }
       else{
         cal.setTime(date);
         cal.add(Calendar.MONTH, (int)months);
         
         dateString = sdf.format(cal.getTime());
       }
       
       
       
       return dateString;
       
   }
   
   public double getOrderValue(long orderId){
       EntityManagerFactory emf  = Persistence.createEntityManagerFactory("NeoForcePU");
       EntityManager em = emf.createEntityManager();
       
       ProductOrder order = em.find(ProductOrder.class, orderId);
       
       Query query = em.createNamedQuery("OrderItem.findByOrder").setParameter("order", order);
       
       List<OrderItem> items = query.getResultList();
       
       double orderValue = 0;
       
       for(OrderItem item : items){
           
           orderValue += item.getUnit().getAmountPayable() * item.getQuantity();
       }
       
       return orderValue;
   }
    
}
