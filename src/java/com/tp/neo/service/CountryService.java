/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.service;

import com.tp.neo.model.Country;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author SWEDGE
 */
public class CountryService {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    static    EntityManager em = emf.createEntityManager();
    public static List<Country> getCountryList(){
        List<Country> countries = (List<Country>)em.createNamedQuery("Country.findAll").getResultList();
       return countries;
    }
}
