/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "withdrawal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Withdrawal.findAll", query = "SELECT w FROM Withdrawal w"),
    @NamedQuery(name = "Withdrawal.findById", query = "SELECT w FROM Withdrawal w WHERE w.id = :id"),
    @NamedQuery(name = "Withdrawal.findByDate", query = "SELECT w FROM Withdrawal w WHERE w.date = :date"),
    @NamedQuery(name = "Withdrawal.findByAmount", query = "SELECT w FROM Withdrawal w WHERE w.amount = :amount"),
    @NamedQuery(name = "Withdrawal.findByApproved", query = "SELECT w FROM Withdrawal w WHERE w.approved = :approved ORDER BY w.id DESC"),
    @NamedQuery(name = "Withdrawal.findByCreatedDate", query = "SELECT w FROM Withdrawal w WHERE w.createdDate = :createdDate"),
    @NamedQuery(name = "Withdrawal.findByCreatedBy", query = "SELECT w FROM Withdrawal w WHERE w.createdBy = :createdBy"),
    @NamedQuery(name = "Withdrawal.findByModifiedDate", query = "SELECT w FROM Withdrawal w WHERE w.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Withdrawal.findByModifiedBy", query = "SELECT w FROM Withdrawal w WHERE w.modifiedBy = :modifiedBy")})

public class Withdrawal extends BaseModel{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;
    @Basic(optional = false)
    @Column(name = "approved")
    private short approved;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @JoinColumn(name = "agent_id", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agent;

    public Withdrawal() {
    }

    public Withdrawal(Long id) {
        this.id = id;
    }

    public Withdrawal(Long id, Date date, double amount, short approved) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public short getApproved() {
        return approved;
    }

    public void setApproved(short approved) {
        this.approved = approved;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
        if (!(object instanceof Withdrawal)) {
            return false;
        }
        Withdrawal other = (Withdrawal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Withdrawal[ id=" + id + " ]";
    }
    
}
