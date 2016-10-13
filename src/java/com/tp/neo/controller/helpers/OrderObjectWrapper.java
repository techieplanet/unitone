/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
import java.util.List;

/**
 *
 * @author Prestige
 */
public class OrderObjectWrapper {
    
    public ProductOrder order;
    public List<OrderItem> orderItems;
    
    public OrderObjectWrapper(ProductOrder order, List<OrderItem> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
    
    public String getCustomerName() {
        return order.getCustomer().getLastname() + " " + order.getCustomer().getFirstname();
    }
    
    public String getAgentName() {
        return order.getAgent().getLastname() + " " + order.getAgent().getFirstname();
    }
    
    public Long getOrderId(){
        return order.getId();
    }
    public List<OrderItem> getOrderItems(){
        
        return orderItems;
    }
    
    public String getProjectName(int index){
        
        OrderItem orderItem = orderItems.get(index);
        if(orderItem.getUnit() == null){
            return "";
        }
        return orderItem.getUnit().getProject().getName();
    }
    
    public String getUnitTitle(int index){
        OrderItem orderItem = orderItems.get(index);
        if(orderItem.getUnit() == null){
            return "";
        }
        return orderItem.getUnit().getTitle();
    }
    
}
