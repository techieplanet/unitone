/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Director;
import java.util.List;

/**
 *
 * @author SWEDGE
 */
public class testDirectorService {
    public static void main(String... args){
        
       
        List<Director> directors = DirectorService.getDirectors(47L);
        directors.forEach(director ->{
        //System.out.println(director.getName() + "   passport Id :" + director.getPassport() + "   IDCard Id:" + director.getiDCard());
        });
    }
}
