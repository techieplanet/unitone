/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import java.util.Date;

/**
 *
 * @author SWEDGE
 */
public class PasswordResetControllerTest {

    public static void main(String... args) {
       
        generateResetToken();
    }

    private static void generateResetToken() {
         // The Format for reset token i reset_token – 15: ddmmyyhms + 9 random numbers
        //made up of 15 numbers 
        //day month year hour minute sec + extra 9 randon number 
        // I hope you get that -- time for implementation --@{0^0}@
        double rand = Math.random();

        String rString = Double.toString(rand).substring(3);
        Date date = new Date(System.currentTimeMillis());
        rString = String.format("%d%d%d%d%d%d%s",date.getDate(),
                 date.getMonth(),
                date.getYear(),
                date.getHours(),
                date.getMinutes(),
                date.getSeconds(),
                rString);
        //System.out.println(rString.substring(0, 14));
    }
}
