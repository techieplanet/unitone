/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "company_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyAccount.findAll", query = "SELECT c FROM CompanyAccount c"),
    @NamedQuery(name = "CompanyAccount.findById", query = "SELECT c FROM CompanyAccount c WHERE c.id = :id"),
    @NamedQuery(name = "CompanyAccount.findByAccountName", query = "SELECT c FROM CompanyAccount c WHERE c.accountName = :accountName"),
    @NamedQuery(name = "CompanyAccount.findByAccountNumber", query = "SELECT c FROM CompanyAccount c WHERE c.accountNumber = :accountNumber")})
public class CompanyAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "bank_name")
    private String bankName;
    @OneToMany(mappedBy = "companyAccountId")
    private Collection<Lodgement> lodgementCollection;

    public CompanyAccount() {
    }

    public CompanyAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public String getAccountDetails(){
        
        return bankName + " - " + accountNumber + " - " + accountName;
    }

    @XmlTransient
    public Collection<Lodgement> getLodgementCollection() {
        return lodgementCollection;
    }

    public void setLodgementCollection(Collection<Lodgement> lodgementCollection) {
        this.lodgementCollection = lodgementCollection;
    }

    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) return "create_company_account";
        else if(action.toUpperCase().equals("EDIT")) return "edit_company_account";
        else if(action.toUpperCase().equals("DELETE")) return "delete_company_account";
        else return "view_company_account";
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
        if (!(object instanceof CompanyAccount)) {
            return false;
        }
        CompanyAccount other = (CompanyAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.CompanyAccount[ id=" + id + " ]";
    }
    
}
