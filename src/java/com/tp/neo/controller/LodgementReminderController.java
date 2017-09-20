/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.helpers.AlertManager;
import com.tp.neo.controller.helpers.OrderItemHelper;
import com.tp.neo.model.Customer;
import com.tp.neo.model.OrderItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class LodgementReminderController extends HttpServlet {
    
    EntityManagerFactory emf ;
    EntityManager em ;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        
        sendLodgementReminder();
        
    }
    
    
    private void sendLodgementReminder(){
        
        emf = Persistence.createEntityManagerFactory("NeoForcePU");
        em = emf.createEntityManager();
        
        int[] days = new int[]{15,7,1};
        
        for(int day : days) {
            
            prepareLodgementReminder(day);
            
        }
        
        em.close();
        emf.close();
    }
    
    public void prepareLodgementReminder(int day){
         
         Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
         int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
         
         Query query = em.createNamedQuery("OrderItem.findByDayOfReminder").setParameter("notificationDay", day + dayOfMonth); 
         List<Object[]> customer_OrderItemObjList = query.getResultList();
         
         if(customer_OrderItemObjList.size() < 1){
             return;
         }
         
         OrderItemHelper itemHelper = new OrderItemHelper();
         
         List<Map> customersAndItemsList = new ArrayList();
         
         
         List<OrderItem> itemList = new ArrayList();
         List<Double> balanceList = new ArrayList();
         
         for(int i =0; i < customer_OrderItemObjList.size(); i++){
             
             Customer customer = (Customer)customer_OrderItemObjList.get(i)[1];
             
             OrderItem item = (OrderItem)customer_OrderItemObjList.get(i)[0];
             
             //check if item still has pending balance
             double totalAmtPaid = itemHelper.getTotalItemPaidAmount((List)item.getLodgementItemCollection());
             double balance = itemHelper.getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), totalAmtPaid, "double");
             
             if(balance <= 0){
                 continue;
             }
             
             if(i != customer_OrderItemObjList.size() - 1){
                 
                 itemList.add(item);
                 balanceList.add(balance);
                 
                 Customer Nextcustomer = (Customer)customer_OrderItemObjList.get(i+1)[1];
                 
                 if(customer.getCustomerId() != Nextcustomer.getCustomerId()){
                     
                     
                     List<Map> orderItemsMap = new ArrayList();
                     
                     for(int j=0;j<balanceList.size();j++){
                         Map<String, Object> orderItemAndBalTempMap = new HashMap();
                         orderItemAndBalTempMap.put("balance", balanceList.get(j));
                         orderItemAndBalTempMap.put("order_item", itemList.get(j));
                         
                         orderItemsMap.add(orderItemAndBalTempMap);
                     }
                     
                     Map customerAndItemsMap = new HashMap();
                     customerAndItemsMap.put("customer", customer);
                     customerAndItemsMap.put("order_items", orderItemsMap);
                     
                     customersAndItemsList.add(customerAndItemsMap);
                     
                     itemList.clear();
                     balanceList.clear();
                     
                 }
             }
             else{
                 
                    itemList.add(item);
                    balanceList.add(balance);
                    
                    List<Map> orderItemsMap = new ArrayList();
                     
                     for(int j=0;j<balanceList.size();j++){
                         Map<String, Object> orderItemAndBalTempMap = new HashMap();
                         orderItemAndBalTempMap.put("balance", balanceList.get(j));
                         orderItemAndBalTempMap.put("order_item", itemList.get(j));
                         
                         orderItemsMap.add(orderItemAndBalTempMap);
                     }
                     
                     Map customerAndItemsMap = new HashMap();
                     customerAndItemsMap.put("customer", customer);
                     customerAndItemsMap.put("order_items", orderItemsMap);
                     
                     customersAndItemsList.add(customerAndItemsMap);
                     
                     itemList.clear();
                     balanceList.clear();
                 
             }
             
             
         }
         
         AlertManager alert = new AlertManager();
         alert.sendRemiderAlerts(customersAndItemsList, day);

//         for(Map map : customersAndItemsList){
//             
//             Customer customer = (Customer)map.get("customer");
//             List<Map> items = (List)map.get("order_items");
//             
//             //System.out.println("***************************************");
//             //System.out.println(customer.getFullName());
//             //System.out.println("***************************************");
//             
//             for(Map m : items){
//                 
//                 //System.out.println("Unit name : " + ((OrderItem)m.get("order_item")).getUnit().getTitle());
//                 //System.out.println("Balance : " + (Double)m.get("balance"));
//             }
//         }
         
         
     }
    
    
    
}
