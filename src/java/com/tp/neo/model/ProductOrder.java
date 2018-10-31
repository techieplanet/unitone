/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

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
import javax.persistence.FetchType;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "product_order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductOrder.findAll", query = "SELECT o FROM ProductOrder o ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.totalAmount", query = "SELECT SUM(item.amountPayable) FROM ProductOrder order JOIN OrderItem item ON item.order.id = order.id where order.id = :orderId"),
    @NamedQuery(name = "ProductOrder.findByAgent", query = "SELECT o FROM ProductOrder o WHERE o.agent = :agent "),
    @NamedQuery(name = "ProductOrder.findById", query = "SELECT o FROM ProductOrder o WHERE o.id = :id"),
    @NamedQuery(name = "ProductOrder.findByCreatedBy", query = "SELECT o FROM ProductOrder o WHERE o.createdBy = :createdBy"),
    @NamedQuery(name = "ProductOrder.findByCreatedDate", query = "SELECT o FROM ProductOrder o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "ProductOrder.findByCustomer", query = "SELECT o FROM ProductOrder o WHERE o.customer = :customerId "),
    @NamedQuery(name = "ProductOrder.findByModifiedBy", query = "SELECT o FROM ProductOrder o WHERE o.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "ProductOrder.findByModifiedDate", query = "SELECT o FROM ProductOrder o WHERE o.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "ProductOrder.findByCreatorUserType", query = "SELECT o FROM ProductOrder o WHERE o.creatorUserType = :creatorUserType"),
    @NamedQuery(name = "ProductOrder.findByModifierUserType", query = "SELECT o FROM ProductOrder o WHERE o.modifierUserType = :modifierUserType"),
    @NamedQuery(name = "ProductOrder.findByApprovedBy", query = "SELECT o FROM ProductOrder o WHERE o.approvedBy = :approvedBy"),
    @NamedQuery(name = "ProductOrder.findByApprovedDate", query = "SELECT o FROM ProductOrder o WHERE o.approvedDate = :approvedDate"),
    @NamedQuery(name = "ProductOrder.findByApprovalStatus", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus = :approvalStatus ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByApprovalStatusAgent", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus = :approvalStatus AND o.agent = :agent"),
    @NamedQuery(name = "ProductOrder.findByApprovalStatusCustomer", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus = :approvalStatus AND o.customer = :customer"),
    
    @NamedQuery(name = "ProductOrder.findByProcessing", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus = 1 OR o.approvalStatus = 0 AND o.mortgageStatus = 0 ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByProcessingAgent", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus = 1 OR o.approvalStatus = 0 AND o.mortgageStatus = 0 AND o.agent = :agent"),
    @NamedQuery(name = "ProductOrder.findByProcessingCustomer", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus = 1 OR o.approvalStatus = 0 AND o.mortgageStatus = 0 AND o.customer = :customer"),
    
    @NamedQuery(name = "ProductOrder.findByALLCurrentPayingCustomer", query = "SELECT DISTINCT o.customer FROM ProductOrder o WHERE o.mortgageStatus = 0 ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByALLCompletedPaymentCustomer", query = "SELECT DISTINCT o.customer FROM ProductOrder o WHERE o.mortgageStatus = 1 ORDER BY o.id DESC"),
    
    @NamedQuery(name = "ProductOrder.findByCurrentPayingCustomer", query = "SELECT DISTINCT o.customer FROM ProductOrder o WHERE o.mortgageStatus = 0 AND o.agent = :agent ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByCompletedPaymentCustomer", query = "SELECT DISTINCT o.customer FROM ProductOrder o WHERE o.mortgageStatus = 1 AND o.agent = :agent ORDER BY o.id DESC"),
   
    @NamedQuery(name = "ProductOrder.findByCurrentPaying", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus <= 2  AND o.mortgageStatus = 0 ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByAgentCurrentPaying", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus <= 2 AND o.mortgageStatus = 0 AND o.agent = :agent ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByCustomerCurrentPaying", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus <= 2 AND o.mortgageStatus = 0 AND o.customer = :customer ORDER BY o.id DESC"),
   
    @NamedQuery(name = "ProductOrder.findByCompleted", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus <= 2 AND o.mortgageStatus = 1 ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByAgentCompleted", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus <= 2 AND o.mortgageStatus = 1 AND o.agent = :agent ORDER BY o.id DESC"),
    @NamedQuery(name = "ProductOrder.findByCustomerCompleted", query = "SELECT o FROM ProductOrder o WHERE o.approvalStatus <= 2 AND o.mortgageStatus = 1 AND o.customer = :customer ORDER BY o.id DESC"),
   
    @NamedQuery(name = "ProductOrder.findLastInsertedId", query = "SELECT o FROM ProductOrder o ORDER BY o.id DESC")})

public class ProductOrder extends BaseModel {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @Column(name = "approved_by")
    private Long approvedBy;
    @Column(name = "mortgage_status")
    private Short mortgageStatus;
    @OneToMany(fetch=FetchType.LAZY  , mappedBy = "order")
    private Collection<OrderItem> orderItemCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
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
    private Agent agent;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customer;

    public ProductOrder() {
    }

    public ProductOrder(Long id) {
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
        if (!(object instanceof ProductOrder)) {
            return false;
        }
        ProductOrder other = (ProductOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.ProductOrder[ id=" + id + " ]";
    }
    
    public String getPermissionName(String action){
        
        if(action.toUpperCase().equals("NEW"))
            return "create_order";
        else if(action.toUpperCase().equals("APPROVAL"))
            return "approve_order";
        else if(action.toUpperCase().equals("APPROVEORDER")) 
            return "approve_order";
        else if(action.toUpperCase().equals("LIST_ORDERS")) 
            return "view_order";
        else if(action.toUpperCase().equals("NEW_ORDER")) 
            return "create_order";
        else if(action.toUpperCase().equals("APPROVED")) 
            return "view_approved_order";
        else if(action.toUpperCase().equals("DECLINED")) 
            return "view_declined_order";
        else if(action.toUpperCase().equals("PROCESSING")) 
            return "view_processing_order";
        else if(action.toUpperCase().equals("CURRENT")) return "view_currently_paying_order";
        else if(action.toUpperCase().equals("COMPLETED")) return "view_completed_payment_order";
        else if(action.toUpperCase().equals("NOTIFICATION")) return "approve_order";
        else
            return "view_order";
    }


    @XmlTransient
    public Collection<OrderItem> getOrderItemCollection() {
        return orderItemCollection;
    }

    public void setOrderItemCollection(Collection<OrderItem> orderItemCollection) {
        this.orderItemCollection = orderItemCollection;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }


    public Short getMortgageStatus() {
        return mortgageStatus;
    }

    public void setMortgageStatus(Short mortgageStatus) {
        this.mortgageStatus = mortgageStatus;
    }


    
}