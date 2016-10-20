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
 * @author Swedge
 */
@Entity
@Table(name = "project_unit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectUnit.findAll", query = "SELECT p FROM ProjectUnit p"),
    @NamedQuery(name = "ProjectUnit.findAllActive", query = "SELECT p FROM ProjectUnit p WHERE p.deleted = :deleted"),
    @NamedQuery(name = "ProjectUnit.findById", query = "SELECT p FROM ProjectUnit p WHERE p.id = :id"),
    @NamedQuery(name = "ProjectUnit.findByTitle", query = "SELECT p FROM ProjectUnit p WHERE p.title = :title"),
    @NamedQuery(name = "ProjectUnit.findByCpu", query = "SELECT p FROM ProjectUnit p WHERE p.cpu = :cpu"),
    @NamedQuery(name = "ProjectUnit.findByLeastInitDep", query = "SELECT p FROM ProjectUnit p WHERE p.leastInitDep = :leastInitDep"),
    @NamedQuery(name = "ProjectUnit.findByDiscount", query = "SELECT p FROM ProjectUnit p WHERE p.discount = :discount"),
    @NamedQuery(name = "ProjectUnit.findByAmountPayable", query = "SELECT p FROM ProjectUnit p WHERE p.amountPayable = :amountPayable"),
    @NamedQuery(name = "ProjectUnit.findByMaxPaymentDuration", query = "SELECT p FROM ProjectUnit p WHERE p.maxPaymentDuration = :maxPaymentDuration"),
    @NamedQuery(name = "ProjectUnit.findByMonthlyPay", query = "SELECT p FROM ProjectUnit p WHERE p.monthlyPay = :monthlyPay"),
    @NamedQuery(name = "ProjectUnit.findByCommissionPercentage", query = "SELECT p FROM ProjectUnit p WHERE p.commissionPercentage = :commissionPercentage"),
    @NamedQuery(name = "ProjectUnit.findByQuantity", query = "SELECT p FROM ProjectUnit p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "ProjectUnit.findByDeleted", query = "SELECT p FROM ProjectUnit p WHERE p.deleted = :deleted"),
    @NamedQuery(name = "ProjectUnit.findByCreatedDate", query = "SELECT p FROM ProjectUnit p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "ProjectUnit.findByCreatedBy", query = "SELECT p FROM ProjectUnit p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "ProjectUnit.findByModifiedDate", query = "SELECT p FROM ProjectUnit p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "ProjectUnit.findByModifiedBy", query = "SELECT p FROM ProjectUnit p WHERE p.modifiedBy = :modifiedBy"),
    //@NamedQuery(name = "ProjectUnit.findProject", query = "SELECT p.project FROM ProjectUnit p WHERE p.project = :project"),
    @NamedQuery(name = "ProjectUnit.findByProject", query = "SELECT p FROM ProjectUnit p WHERE p.project = :project"),
    @NamedQuery(name = "ProjectUnit.findByProjectAndActive", query = "SELECT p FROM ProjectUnit p WHERE p.project = :project AND p.deleted = :deleted"),
    @NamedQuery(name = "ProjectUnit.findLastInserted", query = "SELECT p FROM ProjectUnit p ORDER BY p.createdDate DESC")})
    
public class ProjectUnit extends BaseModel {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Account account;

    @Basic(optional = false)
    @Column(name = "deleted")
    private short deleted;
    @OneToMany(mappedBy = "unit")
    private Collection<OrderItem> orderItemCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cpu")
    private Double cpu;
    @Column(name = "least_init_dep")
    private Double leastInitDep;
    @Column(name = "discount")
    private Double discount;
    @Basic(optional = false)
    @Column(name = "amount_payable")
    private double amountPayable;
    @Column(name = "max_payment_duration")
    private Integer maxPaymentDuration;
    @Basic(optional = false)
    @Column(name = "monthly_pay")
    private double monthlyPay;
    @Column(name = "commission_percentage")
    private Double commissionPercentage;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project project;

    public ProjectUnit() {
    }

    public ProjectUnit(Long id) {
        this.id = id;
    }

    public ProjectUnit(Long id, double amountPayable, double monthlyPay, int quantity) {
        this.id = id;
        this.amountPayable = amountPayable;
        this.monthlyPay = monthlyPay;
        this.quantity = quantity;
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

    public Double getCpu() {
        return cpu;
    }

    public void setCpu(Double cpu) {
        this.cpu = cpu;
    }

    public Double getLeastInitDep() {
        return leastInitDep;
    }

    public void setLeastInitDep(Double leastInitDep) {
        this.leastInitDep = leastInitDep;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public Integer getMaxPaymentDuration() {
        return maxPaymentDuration;
    }

    public void setMaxPaymentDuration(Integer maxPaymentDuration) {
        this.maxPaymentDuration = maxPaymentDuration;
    }

    public double getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(double monthlyPay) {
        this.monthlyPay = monthlyPay;
    }

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) return "create_project";
        else if(action.toUpperCase().equals("EDIT")) return "edit_project";
        else if(action.toUpperCase().equals("DELETE")) return "delete_project";
        else return "view_project";
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
        if (!(object instanceof ProjectUnit)) {
            return false;
        }
        ProjectUnit other = (ProjectUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.ProjectUnit[ id=" + id + " ]";
    }

    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    @XmlTransient
    public Collection<OrderItem> getOrderItemCollection() {
        return orderItemCollection;
    }

    public void setOrderItemCollection(Collection<OrderItem> orderItemCollection) {
        this.orderItemCollection = orderItemCollection;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    
    
}