/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Country;
import  java.util.List;

/**
 *
 * @author SWEDGE
 */
public class testCountryService {
    
    public static void main(String... arg){
        List<Country> countries = CountryService.getCountryList();
        
        for(Country country : countries)
        {
            //System.out.println(country.getName() + "  " + country.getPhonecode());
        }
    }
}
