/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Prestige
 */
@Entity
@Table(name = "sale_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaleItem.findAll", query = "SELECT s FROM SaleItem s"),
    @NamedQuery(name = "SaleItem.findBySaleId", query = "SELECT s FROM SaleItem s WHERE s.saleId = :saleId"),
    @NamedQuery(name = "SaleItem.findByDatetime", query = "SELECT s FROM SaleItem s WHERE s.datetime = :datetime"),
    @NamedQuery(name = "SaleItem.findByQuantity", query = "SELECT s FROM SaleItem s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "SaleItem.findByInitialDep", query = "SELECT s FROM SaleItem s WHERE s.initialDep = :initialDep"),
    @NamedQuery(name = "SaleItem.findByDiscountAmt", query = "SELECT s FROM SaleItem s WHERE s.discountAmt = :discountAmt"),
    @NamedQuery(name = "SaleItem.findByDiscountPercentage", query = "SELECT s FROM SaleItem s WHERE s.discountPercentage = :discountPercentage"),
    @NamedQuery(name = "SaleItem.findByDeleted", query = "SELECT s FROM SaleItem s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "SaleItem.findByCreatedDate", query = "SELECT s FROM SaleItem s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "SaleItem.findByCreatedBy", query = "SELECT s FROM SaleItem s WHERE s.createdBy = :createdBy"),
    @NamedQuery(name = "SaleItem.findByModifiedDate", query = "SELECT s FROM SaleItem s WHERE s.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "SaleItem.findByModifiedBy", query = "SELECT s FROM SaleItem s WHERE s.modifiedBy = :modifiedBy"),
    @NamedQuery(name ="SaleItem.findByOrderId", query="SELECT s FROM SaleItem s WHERE s.orderId = :orderId") })
public class SaleItem implements Serializable {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saleitem")
//    private Collection<Lodgement> lodgementCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sale_id")
    private Long saleId;
    
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "initial_dep")
    private Double initialDep;
    
    @Column(name = "discount_amt")
    private Double discountAmt;
    
    @Column(name = "discount_percentage")
    private Double discountPercentage;
    
    @Column(name = "deleted")
    private Short deleted;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order1 orderId;
    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    protected ProjectUnit unitId;
    
    
    
    public SaleItem() {
    }

    public SaleItem(Long saleId) {
        this.saleId = saleId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getInitialDep() {
        return initialDep;
    }

    public void setInitialDep(Double initialDep) {
        this.initialDep = initialDep;
    }

    public Double getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(Double discountAmt) {
        this.discountAmt = discountAmt;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
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

    public Order1 getOrderId() {
        return orderId;
    }

    public void setOrderId(Order1 orderId) {
        this.orderId = orderId;
    }

    public ProjectUnit getUnitId() {
        return unitId;
    }

    public void setUnitId(ProjectUnit unitId) {
        this.unitId = unitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (saleId != null ? saleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaleItem)) {
            return false;
        }
        SaleItem other = (SaleItem) object;
        if ((this.saleId == null && other.saleId != null) || (this.saleId != null && !this.saleId.equals(other.saleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.SaleItem[ saleId=" + saleId + " ]";
    }

//    public Integer getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(Integer createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public Integer getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(Integer modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }

    @XmlTransient
    public Collection<Lodgement> getLodgementCollection() {
        List<Lodgement> lodgement = new ArrayList(); 
        return lodgement;
    }

    public void setLodgementCollection(Collection<Lodgement> lodgementCollection) {
        //this.lodgementCollection = lodgementCollection;
    }
    
}
