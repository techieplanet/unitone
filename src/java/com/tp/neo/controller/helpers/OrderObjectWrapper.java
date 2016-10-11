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
    
    private ProductOrder order;
    private List<OrderItem> orderItems;
    
    public OrderObjectWrapper(ProductOrder order, List<OrderItem> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
    
    public String getCustomerName() {
        return order.getCustomerId().getLastname() + " " + order.getCustomerId().getFirstname();
    }
    
    public String getAgentName() {
        return order.getAgentId().getLastname() + " " + order.getAgentId().getFirstname();
    }
    
    public Long getOrderId(){
        return order.getId();
    }
    public List<OrderItem> getOrderItems(){
        
        return orderItems;
    }
    
    public String getProjectName(){
        
        return "";
    }
    
}
