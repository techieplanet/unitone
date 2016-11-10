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
@Table(name = "lodgement_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LodgementItem.findAll", query = "SELECT l FROM LodgementItem l"),
    @NamedQuery(name = "LodgementItem.findTotalApprovedOrderSum", query = "SELECT COALESCE(sum(LI.amount),0) FROM LodgementItem LI JOIN LI.item oi where LI.approvalStatus = 1 AND oi.order.id = :orderId"),
    @NamedQuery(name = "LodgementItem.findById", query = "SELECT l FROM LodgementItem l WHERE l.id = :id"),
    @NamedQuery(name = "LodgementItem.findByAmount", query = "SELECT l FROM LodgementItem l WHERE l.amount = :amount"),
    @NamedQuery(name = "LodgementItem.findByCreatedDate", query = "SELECT l FROM LodgementItem l WHERE l.createdDate = :createdDate"),
    @NamedQuery(name = "LodgementItem.findByCreatedBy", query = "SELECT l FROM LodgementItem l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "LodgementItem.findByModifiedDate", query = "SELECT l FROM LodgementItem l WHERE l.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "LodgementItem.findByModifiedBy", query = "SELECT l FROM LodgementItem l WHERE l.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "LodgementItem.findTotalApprovedSum", query = "SELECT SUM(l.amount) FROM LodgementItem l WHERE l.approvalStatus = :approvalStatus") })

public class LodgementItem extends BaseModel {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @Column(name = "approval_status")
    private Short approvalStatus;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "lodgement_id", referencedColumnName = "id")
    @ManyToOne
    private Lodgement lodgement;
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrderItem item;

    public LodgementItem() {
    }

    public LodgementItem(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Lodgement getLodgement() {
        return lodgement;
    }

    public void setLodgement(Lodgement lodgement) {
        this.lodgement = lodgement;
    }

    public OrderItem getItem() {
        return item;
    }

    public void setItem(OrderItem item) {
        this.item = item;
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
        if (!(object instanceof LodgementItem)) {
            return false;
        }
        LodgementItem other = (LodgementItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.LodgementItem[ id=" + id + " ]";
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
}
