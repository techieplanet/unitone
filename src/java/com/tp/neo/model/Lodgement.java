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
@Table(name = "lodgement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lodgement.findAll", query = "SELECT l FROM Lodgement l"),
    @NamedQuery(name = "Lodgement.findByAgent", query = "SELECT l FROM Lodgement l WHERE l.customer.agent = :agent"),
    @NamedQuery(name = "Lodgement.findByCustomer", query = "SELECT l FROM Lodgement l WHERE l.customer = :customer ORDER BY l.id desc"),
    @NamedQuery(name = "Lodgement.findByAgentApproval", query = "SELECT l FROM Lodgement l WHERE l.customer.agent = :agent AND l.approvalStatus = :approvalStatus"),
    @NamedQuery(name = "Lodgement.findByCustomerApproval", query = "SELECT l FROM Lodgement l WHERE l.customer = :customer AND l.approvalStatus = :approvalStatus"),
    @NamedQuery(name = "Lodgement.findById", query = "SELECT l FROM Lodgement l WHERE l.id = :id"),
    @NamedQuery(name = "Lodgement.findByTransactionId", query = "SELECT l FROM Lodgement l WHERE l.transactionId = :transactionId"),
    @NamedQuery(name = "Lodgement.findByAmount", query = "SELECT l FROM Lodgement l WHERE l.amount = :amount"),
    @NamedQuery(name = "Lodgement.findByDepositorName", query = "SELECT l FROM Lodgement l WHERE l.depositorName = :depositorName"),
    @NamedQuery(name = "Lodgement.findByOriginAccountName", query = "SELECT l FROM Lodgement l WHERE l.originAccountName = :originAccountName"),
    @NamedQuery(name = "Lodgement.findByOriginAccountNumber", query = "SELECT l FROM Lodgement l WHERE l.originAccountNumber = :originAccountNumber"),
    @NamedQuery(name = "Lodgement.findByPaymentMode", query = "SELECT l FROM Lodgement l WHERE l.paymentMode = :paymentMode"),
    @NamedQuery(name = "Lodgement.findByLodgmentDate", query = "SELECT l FROM Lodgement l WHERE l.lodgmentDate = :lodgmentDate"),
    @NamedQuery(name = "Lodgement.findByCreatedDate", query = "SELECT l FROM Lodgement l WHERE l.createdDate = :createdDate"),
    @NamedQuery(name = "Lodgement.findByCreatedBy", query = "SELECT l FROM Lodgement l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "Lodgement.findByModifiedDate", query = "SELECT l FROM Lodgement l WHERE l.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Lodgement.findByModifiedDate", query = "SELECT l FROM Lodgement l WHERE l.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Lodgement.findByApprovalStatus", query = "SELECT l FROM Lodgement l WHERE l.approvalStatus = :approvalStatus ORDER BY l.id ASC"),
    @NamedQuery(name = "Lodgement.findByApprovalStatusForAgent", query = "SELECT l FROM Lodgement l JOIN l.customer c JOIN c.agent a WHERE l.approvalStatus = :approvalStatus AND a.agentId = :agendId ORDER BY l.id ASC")})
public class Lodgement extends BaseModel {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @Column(name = "reward_amount")
    private Double rewardAmount;
    @Column(name = "created_by_user_type")
    private Short createdByUserType;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customer;
    @Column(name = "approval_status")
    private Short approvalStatus;

    @OneToMany(mappedBy = "lodgement")
    private Collection<LodgementItem> lodgementItemCollection;


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "transaction_id")
    private String transactionId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "depositor_name")
    private String depositorName;
    @Column(name = "origin_account_name")
    private String originAccountName;
    @Column(name = "origin_account_number")
    private String originAccountNumber;
    @Column(name = "payment_mode")
    private Short paymentMode;
    @Column(name = "lodgment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lodgmentDate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @JoinColumn(name = "company_account_id", referencedColumnName = "id")
    @ManyToOne
    private CompanyAccount companyAccountId;

    public Lodgement() {
    }

    public Lodgement(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDepositorName() {
        return depositorName;
    }

    public void setDepositorName(String depositorName) {
        this.depositorName = depositorName;
    }

    public String getOriginAccountName() {
        return originAccountName;
    }

    public void setOriginAccountName(String originAccountName) {
        this.originAccountName = originAccountName;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public void setOriginAccountNumber(String originAccountNumber) {
        this.originAccountNumber = originAccountNumber;
    }

    public Short getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Short paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Date getLodgmentDate() {
        return lodgmentDate;
    }

    public void setLodgmentDate(Date lodgmentDate) {
        this.lodgmentDate = lodgmentDate;
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

    public CompanyAccount getCompanyAccountId() {
        return companyAccountId;
    }

    public void setCompanyAccountId(CompanyAccount companyAccountId) {
        this.companyAccountId = companyAccountId;
    }
    
    
    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) 
            return "new_lodgement";
        else if(action.toUpperCase().equals("LIST_APPROVED"))
            return "list_approved_lodgement";
        else if(action.toUpperCase().equals("LIST_UNAPPROVED"))
            return "list_unapproved_lodgement";
        else if(action.toUpperCase().equals("LIST_PENDING") || action.equalsIgnoreCase("notification"))
            return "list_pending_lodgement";
        else if(action.toUpperCase().equals("APPROVAL"))
            return "list_pending_lodgement";
        else if(action.toUpperCase().equals("APPROVE"))
            return "approve_lodgement";
        else if(action.toUpperCase().equals("DECLINE"))
            return "decline_lodgement";
        else 
            return "view_lodgement";
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
        if (!(object instanceof Lodgement)) {
            return false;
        }
        Lodgement other = (Lodgement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Lodgement[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<LodgementItem> getLodgementItemCollection() {
        return lodgementItemCollection;
    }

    public void setLodgementItemCollection(Collection<LodgementItem> lodgementItemCollection) {
        this.lodgementItemCollection = lodgementItemCollection;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Short getCreatedByUserType() {
        return createdByUserType;
    }

    public void setCreatedByUserType(Short createdByUserType) {
        this.createdByUserType = createdByUserType;
    }

    public Double getRewardAmount() {
        if(rewardAmount == null)
            return (double)0;
        else
            return rewardAmount;
    }

    public void setRewardAmount(Double rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

}
