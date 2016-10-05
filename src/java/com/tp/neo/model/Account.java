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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Swedge
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id")})
public class Account implements Serializable {

    @OneToMany(mappedBy = "account")
    private Collection<Agent> agentCollection;
    @OneToMany(mappedBy = "account")
    private Collection<ProjectUnit> projectUnitCollection;
    @OneToMany(mappedBy = "account")
    private Collection<Customer> customerCollection;

    @Basic(optional = false)
    @Column(name = "account_code")
    private String accountCode;
    @Basic(optional = false)
    @Column(name = "remote_id")
    private long remoteId;
    @Basic(optional = false)
    @Column(name = "active")
    private short active;
    @JoinColumn(name = "account_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccountType accountTypeId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    public Account() {
    }

    public Account(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Account[ id=" + id + " ]";
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    public AccountType getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(AccountType accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    @XmlTransient
    public Collection<Agent> getAgentCollection() {
        return agentCollection;
    }

    public void setAgentCollection(Collection<Agent> agentCollection) {
        this.agentCollection = agentCollection;
    }

    @XmlTransient
    public Collection<ProjectUnit> getProjectUnitCollection() {
        return projectUnitCollection;
    }

    public void setProjectUnitCollection(Collection<ProjectUnit> projectUnitCollection) {
        this.projectUnitCollection = projectUnitCollection;
    }

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }
    
}
