/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author John
 */
@Entity
@Table(name = "sale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sale.findAll", query = "SELECT s FROM Sale s"),
    @NamedQuery(name = "Sale.findBySaleId", query = "SELECT s FROM Sale s WHERE s.saleId = :saleId"),
    @NamedQuery(name = "Sale.findByDatetime", query = "SELECT s FROM Sale s WHERE s.datetime = :datetime"),
    @NamedQuery(name = "Sale.findByQuantity", query = "SELECT s FROM Sale s WHERE s.quantity = :quantity"),
    @NamedQuery(name = "Sale.findByInitialDep", query = "SELECT s FROM Sale s WHERE s.initialDep = :initialDep"),
    @NamedQuery(name = "Sale.findByDiscountAmt", query = "SELECT s FROM Sale s WHERE s.discountAmt = :discountAmt"),
    @NamedQuery(name = "Sale.findByDiscountPercentage", query = "SELECT s FROM Sale s WHERE s.discountPercentage = :discountPercentage"),
    @NamedQuery(name = "Sale.findByDeleted", query = "SELECT s FROM Sale s WHERE s.deleted = :deleted"),
    @NamedQuery(name = "Sale.findByCreatedDate", query = "SELECT s FROM Sale s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "Sale.findByCreatedBy", query = "SELECT s FROM Sale s WHERE s.createdBy = :createdBy"),
    @NamedQuery(name = "Sale.findByModifiedDate", query = "SELECT s FROM Sale s WHERE s.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Sale.findByModifiedBy", query = "SELECT s FROM Sale s WHERE s.modifiedBy = :modifiedBy")})
public class Sale implements Serializable {

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
    private Boolean deleted;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modified_by")
    private Integer modifiedBy;
    @JoinColumn(name = "agent_id", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agentId;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sale")
    private Collection<Lodgement> lodgementCollection;

    public Sale() {
    }

    public Sale(Long saleId) {
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Agent getAgentId() {
        return agentId;
    }

    public void setAgentId(Agent agentId) {
        this.agentId = agentId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    @XmlTransient
    public Collection<Lodgement> getLodgementCollection() {
        return lodgementCollection;
    }

    public void setLodgementCollection(Collection<Lodgement> lodgementCollection) {
        this.lodgementCollection = lodgementCollection;
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
        if (!(object instanceof Sale)) {
            return false;
        }
        Sale other = (Sale) object;
        if ((this.saleId == null && other.saleId != null) || (this.saleId != null && !this.saleId.equals(other.saleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Sale[ saleId=" + saleId + " ]";
    }
    
}
