/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import com.tp.neo.interfaces.IRestricted;
import com.tp.neo.interfaces.ITrailable;
import com.tp.neo.interfaces.SystemUser;
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
import javax.persistence.Lob;
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
@Table(name = "agent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agent.findAll", query = "SELECT a FROM Agent a"),
    @NamedQuery(name = "Agent.findByAgentId", query = "SELECT a FROM Agent a WHERE a.agentId = :agentId"),
    @NamedQuery(name = "Agent.findByFirstname", query = "SELECT a FROM Agent a WHERE a.firstname = :firstname"),
    @NamedQuery(name = "Agent.findByMiddlename", query = "SELECT a FROM Agent a WHERE a.middlename = :middlename"),
    @NamedQuery(name = "Agent.findByLastname", query = "SELECT a FROM Agent a WHERE a.lastname = :lastname"),
    @NamedQuery(name = "Agent.findByPassword", query = "SELECT a FROM Agent a WHERE a.password = :password"),
    @NamedQuery(name = "Agent.findByPhone", query = "SELECT a FROM Agent a WHERE a.phone = :phone"),
    @NamedQuery(name = "Agent.findByEmail", query = "SELECT a FROM Agent a WHERE a.email = :email"),
    @NamedQuery(name = "Agent.findByStreet", query = "SELECT a FROM Agent a WHERE a.street = :street"),
    @NamedQuery(name = "Agent.findByCity", query = "SELECT a FROM Agent a WHERE a.city = :city"),
    @NamedQuery(name = "Agent.findByState", query = "SELECT a FROM Agent a WHERE a.state = :state"),
    @NamedQuery(name = "Agent.findByKinName", query = "SELECT a FROM Agent a WHERE a.kinName = :kinName"),
    @NamedQuery(name = "Agent.findByKinPhone", query = "SELECT a FROM Agent a WHERE a.kinPhone = :kinPhone"),
    @NamedQuery(name = "Agent.findByKinAddress", query = "SELECT a FROM Agent a WHERE a.kinAddress = :kinAddress"),
    @NamedQuery(name = "Agent.findByBankName", query = "SELECT a FROM Agent a WHERE a.bankName = :bankName"),
    @NamedQuery(name = "Agent.findByBankAcctName", query = "SELECT a FROM Agent a WHERE a.bankAcctName = :bankAcctName"),
    @NamedQuery(name = "Agent.findByBankAcctNumber", query = "SELECT a FROM Agent a WHERE a.bankAcctNumber = :bankAcctNumber"),
    @NamedQuery(name = "Agent.findByDeleted", query = "SELECT a FROM Agent a WHERE a.deleted = :deleted"),
    @NamedQuery(name = "Agent.findByActive", query = "SELECT a FROM Agent a WHERE a.active = :active"),
    @NamedQuery(name = "Agent.findByApprovalStatusAndDeleted", query="SELECT a FROM Agent a WHERE a.deleted  = :deleted AND a.approvalStatus = :approvalStatus ORDER BY a.agentId ASC"),
    @NamedQuery(name = "Agent.findByActiveAndDeletedAndApprovalStatus", query="SELECT a FROM Agent a WHERE a.deleted  = :deleted AND a.active = :active AND a.approvalStatus = :approvalStatus ORDER BY a.agentId ASC"),
    @NamedQuery(name = "Agent.findByApprovalStatus", query = "SELECT a FROM Agent a WHERE a.approvalStatus = :approvalStatus"),
    @NamedQuery(name = "Agent.findByPhotoPath", query = "SELECT a FROM Agent a WHERE a.photoPath = :photoPath"),
    @NamedQuery(name = "Agent.findByAgreementStatus", query = "SELECT a FROM Agent a WHERE a.agreementStatus = :agreementStatus"),
    @NamedQuery(name = "Agent.findByCreatedDate", query = "SELECT a FROM Agent a WHERE a.createdDate = :createdDate"),
    @NamedQuery(name = "Agent.findByCreatedBy", query = "SELECT a FROM Agent a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "Agent.findByModifiedDate", query = "SELECT a FROM Agent a WHERE a.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Agent.findByModifiedBy", query = "SELECT a FROM Agent a WHERE a.modifiedBy = :modifiedBy"),

    
    @NamedQuery(name = "Agent.findByTopSellingLocations", query = "SELECT COUNT(a.agentId) acount, a.state FROM Agent a JOIN a.customerCollection c ON a = c.agent AND a.approvalStatus = 1 GROUP BY a.state ORDER By acount")
    
})

public class Agent extends BaseModel implements SystemUser  {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Withdrawal> withdrawalCollection;

    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne
    private Account account;

    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<ProductOrder> productOrderCollection;

    @Basic(optional = false)
    @Column(name = "generic_id")
    private long genericId;
    @Basic(optional = false)
    @Column(name = "agreement_status")
    private short agreementStatus;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private Collection<Customer> customerCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "agent_id")
    private Long agentId;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "kin_name")
    private String kinName;
    @Basic(optional = false)
    @Column(name = "kin_phone")
    private String kinPhone;
    @Column(name = "kin_address")
    private String kinAddress;
    @Basic(optional = false)
    @Lob
    @Column(name = "kin_photo_path")
    private String kinPhotoPath;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "bank_acct_name")
    private String bankAcctName;
    @Column(name = "bank_acct_number")
    private String bankAcctNumber;
    @Column(name = "deleted")
    private Short deleted;
    @Column(name = "active")
    private Short active;
    @Column(name = "approval_status")
    private Short approvalStatus;
    @Basic(optional = false)
    @Column(name = "photo_path")
    private String photoPath;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    //Extra
    transient final Integer USERTYPEID = 2;
    transient String permissions = "";
    
    public Agent() {
    }

    public Agent(Long agentId) {
        this.agentId = agentId;
    }

    public Agent(Long agentId, String password, String kinPhone, String kinPhotoPath, String photoPath, short agreementStatus) {
        this.agentId = agentId;
        this.password = password;
        this.kinPhone = kinPhone;
        this.kinPhotoPath = kinPhotoPath;
        this.photoPath = photoPath;
        this.agreementStatus = agreementStatus;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getKinPhone() {
        return kinPhone;
    }

    public void setKinPhone(String kinPhone) {
        this.kinPhone = kinPhone;
    }

    public String getKinAddress() {
        return kinAddress;
    }

    public void setKinAddress(String kinAddress) {
        this.kinAddress = kinAddress;
    }

    public String getKinPhotoPath() {
        return kinPhotoPath;
    }

    public void setKinPhotoPath(String kinPhotoPath) {
        this.kinPhotoPath = kinPhotoPath;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAcctName() {
        return bankAcctName;
    }

    public void setBankAcctName(String bankAcctName) {
        this.bankAcctName = bankAcctName;
    }

    public String getBankAcctNumber() {
        return bankAcctNumber;
    }

    public void setBankAcctNumber(String bankAcctNumber) {
        this.bankAcctNumber = bankAcctNumber;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public Short getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Short approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    
    public short getAgreementStatus() {
        return agreementStatus;
    }

    public void setAgreementStatus(short agreementStatus) {
        this.agreementStatus = agreementStatus;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agentId != null ? agentId.hashCode() : 0);
        return hash;
    }

    
    public Long getSystemUserId(){
        long id = getAgentId();
        return id;
    }
    
    public Integer getSystemUserTypeId(){
        return USERTYPEID;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        if ((this.agentId == null && other.agentId != null) || (this.agentId != null && !this.agentId.equals(other.agentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Agent[ agentId=" + agentId + " ]";
    }

    @Override
    public String getUsername() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    @Override
    public void setUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(String permission) {
        this.permissions = permission;
    }
    

    public long getGenericId() {
        return genericId;
    }

    public void setGenericId(long genericId) {
        this.genericId = genericId;
    }


    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) return "create_agent";
        else if(action.toUpperCase().equals("EDIT")) return "edit_agent";
        else if(action.toUpperCase().equals("DELETE")) return "delete_agent";
        else return "view_agent";
    }
    

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }


    @XmlTransient
    public Collection<ProductOrder> getProductOrderCollection() {
        return productOrderCollection;
    }

    public void setProductOrderCollection(Collection<ProductOrder> productOrderCollection) {
        this.productOrderCollection = productOrderCollection;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
    @XmlTransient
    public Collection<Withdrawal> getWithdrawalCollection() {
        return withdrawalCollection;
    }

    public void setWithdrawalCollection(Collection<Withdrawal> withdrawalCollection) {
        this.withdrawalCollection = withdrawalCollection;
    }

  
    
}