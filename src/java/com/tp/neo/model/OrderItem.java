/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.math.BigInteger;
import java.text.DecimalFormat;
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
@Table(name = "order_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o"),
    @NamedQuery(name = "OrderItem.findById", query = "SELECT o FROM OrderItem o WHERE o.id = :id"),
    @NamedQuery(name = "OrderItem.findByQuantity", query = "SELECT o FROM OrderItem o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "OrderItem.findByInitialDep", query = "SELECT o FROM OrderItem o WHERE o.initialDep = :initialDep"),
    @NamedQuery(name = "OrderItem.findByDiscountAmt", query = "SELECT o FROM OrderItem o WHERE o.discountAmt = :discountAmt"),
    @NamedQuery(name = "OrderItem.findByDiscountPercentage", query = "SELECT o FROM OrderItem o WHERE o.discountPercentage = :discountPercentage"),
    @NamedQuery(name = "OrderItem.findByDeleted", query = "SELECT o FROM OrderItem o WHERE o.deleted = :deleted"),
    @NamedQuery(name = "OrderItem.findByCreatedDate", query = "SELECT o FROM OrderItem o WHERE o.createdDate = :createdDate"),
    @NamedQuery(name = "OrderItem.findByCreatedBy", query = "SELECT o FROM OrderItem o WHERE o.createdBy = :createdBy"),
    @NamedQuery(name = "OrderItem.findByModifiedDate", query = "SELECT o FROM OrderItem o WHERE o.modifiedDate = :modifiedDate"),
    @NamedQuery(name="OrderItem.findByOrderAndUattendedItem", query = "SELECT o FROM OrderItem o WHERE o.order = :orderId AND o.approvalStatus = :approvalStatus ORDER BY o.id DESC"),
    @NamedQuery(name="OrderItem.findByOrder", query = "SELECT o FROM OrderItem o WHERE o.order = :order"),
    @NamedQuery(name="OrderItem.findByUnit", query = "SELECT o FROM OrderItem o WHERE o.unit = :unit"),
    @NamedQuery(name="OrderItem.findByCustomer", query = "SELECT o FROM OrderItem o WHERE o.order.id in (select p.id from ProductOrder p where p.customer = :customer )"),
    @NamedQuery(name = "OrderItem.findByModifiedBy", query = "SELECT o FROM OrderItem o WHERE o.modifiedBy = :modifiedBy"),
    
    @NamedQuery(name = "OrderItem.findByUncompletedOrder", query = "SELECT o FROM OrderItem o JOIN FETCH o.order po WHERE po.approvalStatus < :approvalStatus"),
    @NamedQuery(name = "OrderItem.findByUncompletedMortgage", query = "SELECT o FROM OrderItem o JOIN FETCH o.order po WHERE po.mortgageStatus < :mortgageStatus"),
    @NamedQuery(name = "OrderItem.findTotalApprovedSum", query = "SELECT COALESCE(SUM(o.quantity * p.cpu),0)  FROM OrderItem o JOIN o.unit p WHERE o.approvalStatus = :approvalStatus"),
    @NamedQuery(name = "OrderItem.findByUncompletedOrderAndLodgementSum", query = "SELECT item, COALESCE(SUM(l.amount),0) FROM ProductOrder p JOIN p.orderItemCollection item JOIN item.lodgementItemCollection l " 
                                                                                    + "WHERE l.approvalStatus = :aps AND item.approvalStatus = :item_aps GROUP BY item.id ORDER  BY item.id"),
    @NamedQuery(name = "OrderItem.findByApprovedLodgementItemsSum", query = "SELECT item, COALESCE(SUM(l.amount),0) FROM OrderItem item JOIN item.lodgementItemCollection l " 
                                                                                    + "WHERE l.approvalStatus = :aps AND item.approvalStatus = :item_aps GROUP BY item.id ORDER  BY item.id"),

    @NamedQuery(name = "OrderItem.findMyTotalOutstandingAmountPerOrderItem", query = "SELECT item, COALESCE(SUM(item.quantity * u.cpu),0) - COALESCE(SUM(l.amount),0)" 
                                                                                        + "FROM OrderItem item JOIN item.unit u JOIN item.lodgementItemCollection l "
                                                                                        + "WHERE item.approvalStatus = :aps AND l.approvalStatus = :aps "
                                                                                        + "GROUP BY item.id ORDER  BY item.id"),
    
        @NamedQuery(name = "OrderItem.findSalesSumByProject", query = "SELECT p, COALESCE(SUM(item.quantity),0), COALESCE(SUM(l.amount),0)  " 
                                                                    + "FROM Project p LEFT JOIN p.projectUnitCollection u LEFT JOIN u.orderItemCollection item ON item.approvalStatus = :item_aps "
                                                                    + "LEFT JOIN item.lodgementItemCollection l On l.approvalStatus = :aps "
                                                                    + "WHERE p.deleted =0 "
                                                                    + "GROUP BY p.id ORDER  BY p.id"),
        
        @NamedQuery(name = "OrderItem.findSalesSumByProjectUnit", query = "SELECT u, COALESCE(SUM(item.quantity),0), COALESCE(SUM(l.amount),0)  " 
                                                                    + "FROM ProjectUnit u LEFT JOIN u.orderItemCollection item ON item.approvalStatus = :item_aps "
                                                                    + "LEFT JOIN item.lodgementItemCollection l ON l.approvalStatus = :aps "
                                                                    + "WHERE u.project.id = :projectId AND u.project.deleted = 0 "
                                                                    + "GROUP BY u.id ORDER  BY u.id"),
       @NamedQuery(name = "OrderItem.findByDayOfReminder", query = "SELECT OI, PO.customer FROM OrderItem OI "
               + "                                                  JOIN ProductOrder PO ON OI.order.customer.customerId = PO.customer.customerId  WHERE OI.monthlyPayDay = :notificationDay ORDER BY PO.customer.customerId "),
        
    })


public class OrderItem extends BaseModel {

    @Column(name = "created_by")
    private BigInteger createdBy;
    @Column(name = "modified_by")
    private BigInteger modifiedBy;
    @Column(name = "amount_payable")
    private Double amountPayable;
    @Column(name = "cost_price")
    private Double costPrice;
    @Column(name = "reward_point")
    private Double rewardPoint;
    @Column(name = "montly_payment")
    private Double montlyPayment;
    @Column(name = "service_value")
    private Double serviceValue;
    @Column(name = "max_payment_duration")
    private Integer maxPaymentDuration;

    @Column(name = "vat_percentage")
    private Double vatPercentage;
    @Column(name = "annual_maintenance_percentage")
    private Double annualMaintenancePercentage;
    

    @Column(name = "projectunit_discount_percentage")
    private double projectUnitDiscountPercentage;
    
    transient Double rewardAmount = 0.0;
    transient Integer rewardPoints = 0;
    
    @Column(name = "approval_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalDate;
//    @OneToMany(mappedBy = "itemId")
//    private Collection<LoyaltyHistory> loyaltyHistoryCollection;
    @Column(name = "monthly_pay_day")
    private Integer monthlyPayDay;
    @Column(name = "commission_percentage")
    private Double commissionPercentage;
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ManyToOne
    private ProjectUnit unit;
    @Column(name = "approval_status")
    private Short approvalStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private Collection<LodgementItem> lodgementItemCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
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
    private ProductOrder order;

    public OrderItem() {
    }

    public OrderItem(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return createdBy.longValue();
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = BigInteger.valueOf(createdBy);
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy.longValue();
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = BigInteger.valueOf(modifiedBy);
    }

    public ProductOrder getOrder() {
        return order;
    }

    public void setOrder(ProductOrder order) {
        this.order = order;
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
        if (!(object instanceof OrderItem)) {
            return false;
        }
        OrderItem other = (OrderItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.OrderItem[ id=" + id + " ]";
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

    public ProjectUnit getUnit() {
        return unit;
    }

    public void setUnit(ProjectUnit unit) {
        this.unit = unit;
    } 

    public Double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(Double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }
    
    /****************** UTIL ********************/
    /**
     * Old Implementation
    public double getCommissionAmount(){
        DecimalFormat df = new DecimalFormat(".##");
        double amount = this.getUnit().getCpu() * this.getUnit().getCommissionPercentage() / 100;
        String amountString = df.format(amount); //rounded to two decimal places
        return Double.parseDouble(amountString); //change back to double and return
    }
    */
    
    public double getCommissionAmount(double lodgementAmount){
        DecimalFormat df = new DecimalFormat(".##");
        //System.out.println("lodgementAmount: " + lodgementAmount);
        //System.out.println("comm perc: " + this.getCommissionPercentage());
        double amount = lodgementAmount * this.getCommissionPercentage() / 100;
        String amountString = df.format(amount); //rounded to two decimal places
        return Double.parseDouble(amountString); //change back to double and return
    }

    public Integer getMonthlyPayDay() {
        return monthlyPayDay;
    }

    public void setMonthlyPayDay(Integer monthlyPayDay) {
        this.monthlyPayDay = monthlyPayDay;
    }

//    @XmlTransient
//    public Collection<LoyaltyHistory> getLoyaltyHistoryCollection() {
//        return loyaltyHistoryCollection;
//    }
//
//    public void setLoyaltyHistoryCollection(Collection<LoyaltyHistory> loyaltyHistoryCollection) {
//        this.loyaltyHistoryCollection = loyaltyHistoryCollection;
//    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    
    public Double getRewardAmount() {
        return rewardAmount;
    }
    
    public void setRewardAmount(Double rewardAmount) {
        this.rewardAmount = rewardAmount;
    }
    
    public Integer getRewardPoints() {
        return rewardPoints;
    }
    
    public void setRewardPoints(Integer rewardPoints) {
        this.rewardPoints = rewardPoints;
    }


    public double getTotalAmountPaid(){
        
        double total = 0;
        List<LodgementItem> LItems = (List)getLodgementItemCollection();
        
        for(LodgementItem LI : LItems){
            
            double rewardPoint = LI.getRewardAmount();
            total += (LI.getAmount() + rewardPoint);
        }
        
        return total;
    }
    
    public Double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(Double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public double calculateVatAmount(double amount){
        return (amount * vatPercentage)/(100 + vatPercentage);
    }
    
    public double calculateAnnualMaintenanceAmount(double amount){
        return (amount * getAnnualMaintenancePercentage() )/100;
    }

    /**
     * @return the annualMaintenancePercentage
     */
    public Double getAnnualMaintenancePercentage() {
        return annualMaintenancePercentage;
    }

    /**
     * @param annualMaintenancePercentage the annualMaintenancePercentage to set
     */
    public void setAnnualMaintenancePercentage(Double annualMaintenancePercentage) {
        this.annualMaintenancePercentage = annualMaintenancePercentage;
    }

    public void setCreatedBy(BigInteger createdBy) {
        this.createdBy = createdBy;
    }


    public void setModifiedBy(BigInteger modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Double getAmountPayable() {
        return amountPayable;
    }

    public void setAmountPayable(Double amountPayable) {
        this.amountPayable = amountPayable;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getRewardPoint() {
        return rewardPoint;
    }

    public void setRewardPoint(Double rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    public Double getMontlyPayment() {
        return montlyPayment;
    }

    public void setMontlyPayment(Double montlyPayment) {
        this.montlyPayment = montlyPayment;
    }

    public Double getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(Double serviceValue) {
        this.serviceValue = serviceValue;
    }

    public Integer getMaxPaymentDuration() {
        return maxPaymentDuration;
    }

    public void setMaxPaymentDuration(Integer maxPaymentDuration) {
        this.maxPaymentDuration = maxPaymentDuration;
    }

    /**
     * @return the projectUnitDiscountPercentage
     */
    public double getProjectUnitDiscountPercentage() {
        return projectUnitDiscountPercentage;
    }

    /**
     * @param projectUnitDiscountPercentage the projectUnitDiscountPercentage to set
     */
    public void setProjectUnitDiscountPercentage(double projectUnitDiscountPercentage) {
        this.projectUnitDiscountPercentage = projectUnitDiscountPercentage;
    }

}
