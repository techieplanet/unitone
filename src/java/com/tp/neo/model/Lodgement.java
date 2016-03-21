/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import com.tp.neo.interfaces.ITrailable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author John
 */
@Entity
@Table(name = "lodgement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lodgement.findAll", query = "SELECT l FROM Lodgement l"),
    @NamedQuery(name = "Lodgement.findByLodgementId", query = "SELECT l FROM Lodgement l WHERE l.lodgementPK.lodgementId = :lodgementId"),
    @NamedQuery(name = "Lodgement.findByLodgmentDate", query = "SELECT l FROM Lodgement l WHERE l.lodgmentDate = :lodgmentDate"),
    @NamedQuery(name = "Lodgement.findByAmount", query = "SELECT l FROM Lodgement l WHERE l.amount = :amount"),
    @NamedQuery(name = "Lodgement.findByPaymentMode", query = "SELECT l FROM Lodgement l WHERE l.paymentMode = :paymentMode"),
    @NamedQuery(name = "Lodgement.findByBankName", query = "SELECT l FROM Lodgement l WHERE l.bankName = :bankName"),
    @NamedQuery(name = "Lodgement.findByDepositorsName", query = "SELECT l FROM Lodgement l WHERE l.depositorsName = :depositorsName"),
    @NamedQuery(name = "Lodgement.findByTellerNo", query = "SELECT l FROM Lodgement l WHERE l.tellerNo = :tellerNo"),
    @NamedQuery(name = "Lodgement.findByGatewayTransId", query = "SELECT l FROM Lodgement l WHERE l.gatewayTransId = :gatewayTransId"),
    @NamedQuery(name = "Lodgement.findByTransAmount", query = "SELECT l FROM Lodgement l WHERE l.transAmount = :transAmount"),
    @NamedQuery(name = "Lodgement.findByVerificationStatus", query = "SELECT l FROM Lodgement l WHERE l.verificationStatus = :verificationStatus"),
    @NamedQuery(name = "Lodgement.findByCreatedDate", query = "SELECT l FROM Lodgement l WHERE l.createdDate = :createdDate"),
    @NamedQuery(name = "Lodgement.findByCreatedBy", query = "SELECT l FROM Lodgement l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "Lodgement.findByModifiedDate", query = "SELECT l FROM Lodgement l WHERE l.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Lodgement.findByModifiedBy", query = "SELECT l FROM Lodgement l WHERE l.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Lodgement.findBySaleId", query = "SELECT l FROM Lodgement l WHERE l.lodgementPK.saleId = :saleId")})
public class Lodgement implements Serializable, ITrailable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LodgementPK lodgementPK;
    @Column(name = "lodgment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lodgmentDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "payment_mode")
    private Short paymentMode;
    @Basic(optional = false)
    @Column(name = "bank_name")
    private String bankName;
    @Basic(optional = false)
    @Column(name = "depositors_name")
    private String depositorsName;
    @Column(name = "teller_no")
    private String tellerNo;
    @Column(name = "gateway_trans_id")
    private String gatewayTransId;
    @Basic(optional = false)
    @Column(name = "trans_amount")
    private double transAmount;
    @Column(name = "verification_status")
    private Short verificationStatus;
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
    @JoinColumn(name = "sale_id", referencedColumnName = "sale_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sale sale;

    public Lodgement() {
    }

    public Lodgement(LodgementPK lodgementPK) {
        this.lodgementPK = lodgementPK;
    }

    public Lodgement(LodgementPK lodgementPK, String bankName, String depositorsName, double transAmount) {
        this.lodgementPK = lodgementPK;
        this.bankName = bankName;
        this.depositorsName = depositorsName;
        this.transAmount = transAmount;
    }

    public Lodgement(long lodgementId, long saleId) {
        this.lodgementPK = new LodgementPK(lodgementId, saleId);
    }

    public LodgementPK getLodgementPK() {
        return lodgementPK;
    }

    public void setLodgementPK(LodgementPK lodgementPK) {
        this.lodgementPK = lodgementPK;
    }

    public Date getLodgmentDate() {
        return lodgmentDate;
    }

    public void setLodgmentDate(Date lodgmentDate) {
        this.lodgmentDate = lodgmentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Short getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Short paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDepositorsName() {
        return depositorsName;
    }

    public void setDepositorsName(String depositorsName) {
        this.depositorsName = depositorsName;
    }

    public String getTellerNo() {
        return tellerNo;
    }

    public void setTellerNo(String tellerNo) {
        this.tellerNo = tellerNo;
    }

    public String getGatewayTransId() {
        return gatewayTransId;
    }

    public void setGatewayTransId(String gatewayTransId) {
        this.gatewayTransId = gatewayTransId;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public Short getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(Short verificationStatus) {
        this.verificationStatus = verificationStatus;
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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lodgementPK != null ? lodgementPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lodgement)) {
            return false;
        }
        Lodgement other = (Lodgement) object;
        if ((this.lodgementPK == null && other.lodgementPK != null) || (this.lodgementPK != null && !this.lodgementPK.equals(other.lodgementPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Lodgement[ lodgementPK=" + lodgementPK + " ]";
    }
    
}
