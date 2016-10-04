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
 * @author swedge-mac
 */
@Entity
@Table(name = "\"order\"")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findById", query = "SELECT o FROM Order1 o WHERE o.id = :id"),
    @NamedQuery(name = "Order1.findByCustomer", query="SELECT o FROM Order1 o WHERE o.customerId = :customerId"),
    @NamedQuery(name = "Order1.findByCreatedBy", query = "SELECT o FROM Order1 o WHERE o.createdBy = :createdBy"),
    @NamedQuery(name = "Order1.findByCreatedDate", query = "SELECT o FROM Order1 o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "Order1.findByModifiedBy", query = "SELECT o FROM Order1 o WHERE o.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Order1.findByModifiedDate", query = "SELECT o FROM Order1 o WHERE o.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Order1.findByCreatorUserType", query = "SELECT o FROM Order1 o WHERE o.creatorUserType = :creatorUserType"),
    @NamedQuery(name = "Order1.findByModifierUserType", query = "SELECT o FROM Order1 o WHERE o.modifierUserType = :modifierUserType"),
    @NamedQuery(name = "Order1.findByApprovedBy", query = "SELECT o FROM Order1 o WHERE o.approvedBy = :approvedBy"),
    @NamedQuery(name = "Order1.findByApprovedDate", query = "SELECT o FROM Order1 o WHERE o.approvedDate = :approvedDate"),
    @NamedQuery(name = "Order1.findByApprovalStatus", query = "SELECT o FROM Order1 o WHERE o.approvalStatus = :approvalStatus"),
    @NamedQuery(name = "Order1.findLastInsertedId", query = "SELECT o FROM Order1 o ORDER BY o.id DESC")})

public class Order1 extends BaseModel {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @Column(name = "approved_by")
    private Long approvedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<OrderItem> orderItemCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIME)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIME)
    private Date modifiedDate;
    @Column(name = "creator_user_type")
    private Integer creatorUserType;
    @Column(name = "modifier_user_type")
    private Integer modifierUserType;
    @Column(name = "approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Column(name = "approval_status")
    private Short approvalStatus;
    @JoinColumn(name = "agent_id", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agentId;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;

    public Order1() {
    }

    public Order1(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCreatorUserType() {
        return creatorUserType;
    }

    public void setCreatorUserType(Integer creatorUserType) {
        this.creatorUserType = creatorUserType;
    }

    public Integer getModifierUserType() {
        return modifierUserType;
    }

    public void setModifierUserType(Integer modifierUserType) {
        this.modifierUserType = modifierUserType;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Order1[ id=" + id + " ]";
    }
    
    public String getPermissionName(String alias){
        return "";
    }


    @XmlTransient
    public Collection<OrderItem> getOrderItemCollection() {
        return orderItemCollection;
    }

    public void setOrderItemCollection(Collection<OrderItem> orderItemCollection) {
        this.orderItemCollection = orderItemCollection;
    }
    
}