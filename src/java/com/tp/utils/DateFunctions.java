/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author swedge-mac
 */
public class DateFunctions {
    
    public static int getNumberOfMonthsBetweenDates(Date startDate, Date endDate){
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        
        LocalDate startLocalDate = LocalDate.of(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH)+1, startCalendar.get(Calendar.DAY_OF_MONTH));
        LocalDate endLocalDate = LocalDate.of(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH)+1, endCalendar.get(Calendar.DAY_OF_MONTH));
        
        Period period  = Period.between(startLocalDate, endLocalDate);
        Long months = Math.abs(period.toTotalMonths());
        
        return months.intValue();
    }
    
    public static Date getDateAfterAddingDays(int numberOfDays){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add( Calendar.DAY_OF_YEAR, numberOfDays);
        Date newDate = cal.getTime();
        
        //LocalDate tenDaysAgo = LocalDate.now().minusDays(10);
        
        return newDate;       
        
    }
    
    public static Date getDateAfterSubtractDays(int numberOfDays){
        return DateFunctions.getDateAfterAddingDays(-1 * numberOfDays);
    }
    
    public static Date getDateFromValues(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date newDate = cal.getTime();
        return newDate;       
    }
}
