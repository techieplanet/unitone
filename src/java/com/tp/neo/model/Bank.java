/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SWEDGE
 */
@Entity
@Table(name = "bank")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Bank.findAll", query = "SELECT b FROM Bank b ORDER BY b.bankName asc")
    , @NamedQuery(name = "Bank.findById", query = "SELECT b FROM Bank b WHERE b.id = :id")
    , @NamedQuery(name = "Bank.findByBankName", query = "SELECT b FROM Bank b WHERE b.bankName = :bankName")
    , @NamedQuery(name = "Bank.findBySortCode", query = "SELECT b FROM Bank b WHERE b.sortCode = :sortCode")
    , @NamedQuery(name = "Bank.findByState", query = "SELECT b FROM Bank b WHERE b.state = :state")
    , @NamedQuery(name = "Bank.findByBranchName", query = "SELECT b FROM Bank b WHERE b.branchName = :branchName")
    , @NamedQuery(name = "Bank.findByBranchAddress", query = "SELECT b FROM Bank b WHERE b.branchAddress = :branchAddress")
})
public class Bank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "bank_name")
    private String bankName;
    @Basic(optional = false)
    @Column(name = "sort_code")
    private String sortCode;
    @Column(name = "state")
    private String state;
    @Column(name = "branch_name")
    private String branchName;
    @Column(name = "branch_address")
    private String branchAddress;

    public Bank() {
    }

    public Bank(Integer id) {
        this.id = id;
    }

    public Bank(Integer id, String bankName, String sortCode) {
        this.id = id;
        this.bankName = bankName;
        this.sortCode = sortCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bank))
        {
            return false;
        }
        Bank other = (Bank) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Bank[ id=" + id + " ]";
    }
    
}
