/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.exception;

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
@Table(name = "exception_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExceptionLog.findAll", query = "SELECT e FROM ExceptionLog e"),
    @NamedQuery(name = "ExceptionLog.findById", query = "SELECT e FROM ExceptionLog e WHERE e.id = :id"),
    @NamedQuery(name = "ExceptionLog.findByEntity", query = "SELECT e FROM ExceptionLog e WHERE e.entity = :entity"),
    @NamedQuery(name = "ExceptionLog.findByErrorMessage", query = "SELECT e FROM ExceptionLog e WHERE e.errorMessage = :errorMessage"),
    @NamedQuery(name = "ExceptionLog.findByDate", query = "SELECT e FROM ExceptionLog e WHERE e.date = :date")})
public class ExceptionLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "entity")
    private String entity;
    @Basic(optional = false)
    @Lob
    @Column(name = "inputvalues")
    private String inputvalues;
    @Basic(optional = false)
    @Column(name = "error_message")
    private String errorMessage;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public ExceptionLog() {
    }

    public ExceptionLog(Long id) {
        this.id = id;
    }

    public ExceptionLog(Long id, String entity, String inputvalues, String errorMessage, Date date) {
        this.id = id;
        this.entity = entity;
        this.inputvalues = inputvalues;
        this.errorMessage = errorMessage;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getInputvalues() {
        return inputvalues;
    }

    public void setInputvalues(String inputvalues) {
        this.inputvalues = inputvalues;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        if (!(object instanceof ExceptionLog)) {
            return false;
        }
        ExceptionLog other = (ExceptionLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.exception.ExceptionLog[ id=" + id + " ]";
    }
    
}
