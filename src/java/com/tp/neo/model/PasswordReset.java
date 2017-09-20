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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SWEDGE
 */
@Entity
@Table(name = "password_reset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PasswordReset.findAll", query = "SELECT p FROM PasswordReset p")
    , @NamedQuery(name = "PasswordReset.findById", query = "SELECT p FROM PasswordReset p WHERE p.id = :id")
    , @NamedQuery(name = "PasswordReset.findByToken", query = "SELECT p FROM PasswordReset p WHERE p.token = :token")
    , @NamedQuery(name = "PasswordReset.findByEmail", query = "SELECT p FROM PasswordReset p WHERE p.email = :email")
    , @NamedQuery(name = "PasswordReset.findByUserType", query = "SELECT p FROM PasswordReset p WHERE p.userType = :userType")
    , @NamedQuery(name = "PasswordReset.findByExpiryDate", query = "SELECT p FROM PasswordReset p WHERE p.expiryDate = :expiryDate")})
public class PasswordReset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "email")
    private String email;
    @Column(name = "user_type")
    private Short userType;
    @Column(name = "expiry_date")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    public PasswordReset() {
    }

    public PasswordReset(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
        if (!(object instanceof PasswordReset)) {
            return false;
        }
        PasswordReset other = (PasswordReset) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.PasswordReset[ id=" + id + " ]";
    }
    
}
