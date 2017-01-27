/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import java.util.ArrayList;

/**
 * ------------------------------------------------------------
 * Please Note:
 * -------------------------------------------------------------
 * This class is used to Deserialize gson string of OrderItems,
 * from either a New Order Form, New Customer Form, and Make Lodgement form.
 * It has just one class member, which is a list of orderItems. which is also
 * de-serialized by the OrderItemObject class.
 * 
 * @author Prestige
 */
public class OrderItemObjectsList {
    
    public ArrayList<OrderItemObject> sales;
}
