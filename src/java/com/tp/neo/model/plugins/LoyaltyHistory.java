/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model.plugins;

import com.tp.neo.model.Customer;
import com.tp.neo.model.LodgementItem;
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
@Table(name = "loyalty_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoyaltyHistory.findAll", query = "SELECT l FROM LoyaltyHistory l"),
    @NamedQuery(name = "LoyaltyHistory.findById", query = "SELECT l FROM LoyaltyHistory l WHERE l.id = :id"),
    @NamedQuery(name = "LoyaltyHistory.findByRewardPoints", query = "SELECT l FROM LoyaltyHistory l WHERE l.rewardPoints = :rewardPoints"),
    @NamedQuery(name = "LoyaltyHistory.findByType", query = "SELECT l FROM LoyaltyHistory l WHERE l.type = :type"),
    @NamedQuery(name = "LoyaltyHistory.findByDeleted", query = "SELECT l FROM LoyaltyHistory l WHERE l.deleted = :deleted"),
    @NamedQuery(name = "LoyaltyHistory.findByCreatedBy", query = "SELECT l FROM LoyaltyHistory l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "LoyaltyHistory.findByCreatedDate", query = "SELECT l FROM LoyaltyHistory l WHERE l.createdDate = :createdDate"),
    @NamedQuery(name = "LoyaltyHistory.findByModifiedBy", query = "SELECT l FROM LoyaltyHistory l WHERE l.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "LoyaltyHistory.findByModifiedDate", query = "SELECT l FROM LoyaltyHistory l WHERE l.modifiedDate = :modifiedDate")
    })
public class LoyaltyHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "reward_points")
    private double rewardPoints;
    @Column(name = "type")
    private Short type;
    @Column(name = "deleted")
    private Short deleted;
    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by")
    private BigInteger modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customerId;
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ManyToOne
    private LodgementItem itemId;

    public LoyaltyHistory() {
    }

    public LoyaltyHistory(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(double rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public BigInteger getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigInteger getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(BigInteger modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public LodgementItem getItemId() {
        return itemId;
    }

    public void setItemId(LodgementItem itemId) {
        this.itemId = itemId;
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
        if (!(object instanceof LoyaltyHistory)) {
            return false;
        }
        LoyaltyHistory other = (LoyaltyHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.plugins.LoyaltyHistory[ id=" + id + " ]";
    }
    
}
