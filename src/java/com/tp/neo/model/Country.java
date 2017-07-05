/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 *
 * @author SWEDGE
 */
@Entity
@Table(name = "country")
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c where c.id = :id"),
    @NamedQuery(name = "Country.findByIso", query = "SELECT c FROM Country c where c.iso = :iso"),
    @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c where c.name = :name"),
    @NamedQuery(name = "Country.findByNiceName", query = "SELECT c FROM Country c where c.nicename = :nicename"),
    @NamedQuery(name = "Country.findByIso3", query = "SELECT c FROM Country c where c.iso3 = :iso3"),
    @NamedQuery(name = "Country.findByNumcode", query = "SELECT c FROM Country c where c.numcode = :numcode"),
    @NamedQuery(name = "Country.findByPhonecode", query = "SELECT c FROM Country c where c.phonecode = :phonecode"),
    })
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    
    @Column(name = "iso")
    private String iso;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "nicename")
    private String nicename;
    
    @Column(name = "iso3")
    private String iso3;
    
    @Column(name = "numcode")
    private int numcode;
    
    @Column(name = "phonecode")
    private int phonecode;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the iso
     */
    public String getIso() {
        return iso;
    }

    /**
     * @param iso the iso to set
     */
    public void setIso(String iso) {
        this.iso = iso;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the nicename
     */
    public String getNicename() {
        return nicename;
    }

    /**
     * @param nicename the nicename to set
     */
    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    /**
     * @return the iso3
     */
    public String getIso3() {
        return iso3;
    }

    /**
     * @param iso3 the iso3 to set
     */
    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    /**
     * @return the numcode
     */
    public int getNumcode() {
        return numcode;
    }

    /**
     * @param numcode the numcode to set
     */
    public void setNumcode(int numcode) {
        this.numcode = numcode;
    }

    /**
     * @return the phonecode
     */
    public int getPhonecode() {
        return phonecode;
    }

    /**
     * @param phonecode the phonecode to set
     */
    public void setPhonecode(int phonecode) {
        this.phonecode = phonecode;
    }
}
