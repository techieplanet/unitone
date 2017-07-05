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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "customer_balance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerBalance.findAll", query = "SELECT c FROM CustomerBalance c"),
    @NamedQuery(name = "CustomerBalance.findByCustomerId", query = "SELECT c FROM CustomerBalance c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "CustomerBalance.findByAccountCode", query = "SELECT c FROM CustomerBalance c WHERE c.accountCode = :accountCode"),
    @NamedQuery(name = "CustomerBalance.findByTotaldebit", query = "SELECT c FROM CustomerBalance c WHERE c.totaldebit = :totaldebit"),
    @NamedQuery(name = "CustomerBalance.findByTotalcredit", query = "SELECT c FROM CustomerBalance c WHERE c.totalcredit = :totalcredit")})

public class CustomerBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "account_code")
    private String accountCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totaldebit")
    private Double totaldebit;
    @Column(name = "totalcredit")
    private Double totalcredit;
    
   @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
   @OneToOne
   private Customer customer;

    public CustomerBalance() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Double getTotaldebit() {
        return totaldebit;
    }

    public void setTotaldebit(Double totaldebit) {
        this.totaldebit = totaldebit;
    }

    public Double getTotalcredit() {
        return totalcredit;
    }

    public void setTotalcredit(Double totalcredit) {
        this.totalcredit = totalcredit;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}