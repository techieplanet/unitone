/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "notification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findById", query = "SELECT n FROM Notification n WHERE n.id = :id"),
    @NamedQuery(name = "Notification.findByTitle", query = "SELECT n FROM Notification n WHERE n.title = :title"),
    @NamedQuery(name = "Notification.findByRoute", query = "SELECT n FROM Notification n WHERE n.route = :route"),
    @NamedQuery(name = "Notification.findByStatus", query = "SELECT n FROM Notification n WHERE n.status = :status ORDER BY n.id DESC"),
    @NamedQuery(name = "Notification.findByClearOnClick", query = "SELECT n FROM Notification n WHERE n.clearOnClick = :clearOnClick"),
    @NamedQuery(name = "Notification.findByAccessedBy", query = "SELECT n FROM Notification n WHERE n.accessedBy = :accessedBy"),
    @NamedQuery(name = "Notification.findByCreatedDate", query = "SELECT n FROM Notification n WHERE n.createdDate = :createdDate"),
    @NamedQuery(name = "Notification.findByAccessedDate", query = "SELECT n FROM Notification n WHERE n.accessedDate = :accessedDate")})
public class Notification implements Serializable {

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "route")
    private String route;
    @Column(name = "status")
    private Short status;
    @Column(name = "clear_on_click")
    private Short clearOnClick;
    @Column(name = "accessed_by")
    private BigInteger accessedBy;
    @Column(name = "accessed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessedDate;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne
    private NotificationType type;

    public Notification() {
    }

    public Notification(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getClearOnClick() {
        return clearOnClick;
    }

    public void setClearOnClick(Short clearOnClick) {
        this.clearOnClick = clearOnClick;
    }

    public BigInteger getAccessedBy() {
        return accessedBy;
    }

    public void setAccessedBy(BigInteger accessedBy) {
        this.accessedBy = accessedBy;
    }

    public Date getAccessedDate() {
        return accessedDate;
    }

    public void setAccessedDate(Date accessedDate) {
        this.accessedDate = accessedDate;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType typeId) {
        this.type = typeId;
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
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Notification[ id=" + id + " ]";
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
}
