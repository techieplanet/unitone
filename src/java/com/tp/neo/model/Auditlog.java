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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Swedge
 */
@Entity
@Table(name = "auditlog")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auditlog.findAll", query = "SELECT a FROM Auditlog a"),
    @NamedQuery(name = "Auditlog.findById", query = "SELECT a FROM Auditlog a WHERE a.id = :id"),
    @NamedQuery(name = "Auditlog.findByActionName", query = "SELECT a FROM Auditlog a WHERE a.actionName = :actionName"),
    @NamedQuery(name = "Auditlog.findByLogDate", query = "SELECT a FROM Auditlog a WHERE a.logDate = :logDate"),
    @NamedQuery(name = "Auditlog.findByUsertype", query = "SELECT a FROM Auditlog a WHERE a.usertype = :usertype"),
    @NamedQuery(name = "Auditlog.findByUserId", query = "SELECT a FROM Auditlog a WHERE a.userId = :userId")})
public class Auditlog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "action_name")
    private String actionName;
    @Lob
    @Column(name = "note")
    private String note;
    @Basic(optional = false)
    @Column(name = "log_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    @Basic(optional = false)
    @Column(name = "usertype")
    private int usertype;
    @Basic(optional = false)
    @Column(name = "user_id")
    private Long userId;

    public Auditlog() {
    }

    public Auditlog(Long id) {
        this.id = id;
    }

    public Auditlog(Long id, String actionName, Date logDate, int usertype, Long userId) {
        this.id = id;
        this.actionName = actionName;
        this.logDate = logDate;
        this.usertype = usertype;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        if (!(object instanceof Auditlog)) {
            return false;
        }
        Auditlog other = (Auditlog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Auditlog[ id=" + id + " ]";
    }
    
}
