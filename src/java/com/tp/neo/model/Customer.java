/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerId", query = "SELECT c FROM Customer c WHERE c.customerId = :customerId"),
    @NamedQuery(name = "Customer.findByFirstname", query = "SELECT c FROM Customer c WHERE c.firstname = :firstname"),
    @NamedQuery(name = "Customer.findByMiddlename", query = "SELECT c FROM Customer c WHERE c.middlename = :middlename"),
    @NamedQuery(name = "Customer.findByLastname", query = "SELECT c FROM Customer c WHERE c.lastname = :lastname"),
    @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"),
    @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"),
    @NamedQuery(name = "Customer.findByStreet", query = "SELECT c FROM Customer c WHERE c.street = :street"),
    @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city"),
    @NamedQuery(name = "Customer.findByState", query = "SELECT c FROM Customer c WHERE c.state = :state"),
    @NamedQuery(name = "Customer.findByPhotoPath", query = "SELECT c FROM Customer c WHERE c.photoPath = :photoPath"),
    @NamedQuery(name = "Customer.findByPassword", query = "SELECT c FROM Customer c WHERE c.password = :password"),
    @NamedQuery(name = "Customer.findByKinName", query = "SELECT c FROM Customer c WHERE c.kinName = :kinName"),
    @NamedQuery(name = "Customer.findByKinPhone", query = "SELECT c FROM Customer c WHERE c.kinPhone = :kinPhone"),
    @NamedQuery(name = "Customer.findByKinAddress", query = "SELECT c FROM Customer c WHERE c.kinAddress = :kinAddress"),
    @NamedQuery(name = "Customer.findByKinPhotoPath", query = "SELECT c FROM Customer c WHERE c.kinPhotoPath = :kinPhotoPath"),
    @NamedQuery(name = "Customer.findByDeleted", query = "SELECT c FROM Customer c WHERE c.deleted = :deleted"),
    @NamedQuery(name = "Customer.findByActive", query = "SELECT c FROM Customer c WHERE c.active = :active"),
    @NamedQuery(name = "Customer.findByVerificationStatus", query = "SELECT c FROM Customer c WHERE c.verificationStatus = :verificationStatus"),
    @NamedQuery(name = "Customer.findByCreatedDate", query = "SELECT c FROM Customer c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Customer.findByCreatedBy", query = "SELECT c FROM Customer c WHERE c.createdBy = :createdBy"),
    @NamedQuery(name = "Customer.findByModifiedDate", query = "SELECT c FROM Customer c WHERE c.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Customer.findByModifiedBy", query = "SELECT c FROM Customer c WHERE c.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Customer.findAllCount", query = "SELECT COUNT(c) FROM Customer c")})
public class Customer implements Serializable, ITrailable, SystemUser {

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne
    private Account account;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<ProductOrder> productOrderCollection;
    
    @JoinColumn(name = "agent_id", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agentId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "lastname")
    private String lastname;
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
    @Basic(optional = false)
    @Column(name = "photo_path")
    private String photoPath;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "kin_name")
    private String kinName;
    @Column(name = "kin_phone")
    private String kinPhone;
    @Column(name = "kin_address")
    private String kinAddress;
    @Basic(optional = false)
    @Column(name = "kin_photo_path")
    private String kinPhotoPath;
    @Column(name = "deleted")
    private Short deleted;
    @Column(name = "active")
    private Short active;
    @Column(name = "verification_status")
    private Short verificationStatus;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Collection<CustomerAgent> customerAgentCollection;

    //Extra
    transient final Integer USERTYPEID = 3;
    
    public Customer() {
    }

    public Customer(Long customerId) {
        this.customerId = customerId;
    }

    public Customer(Long customerId, String photoPath, String password, String kinPhotoPath) {
        this.customerId = customerId;
        this.photoPath = photoPath;
        this.password = password;
        this.kinPhotoPath = kinPhotoPath;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @XmlTransient
    public Collection<CustomerAgent> getCustomerAgentCollection() {
        return customerAgentCollection;
    }

    public void setCustomerAgentCollection(Collection<CustomerAgent> customerAgentCollection) {
        this.customerAgentCollection = customerAgentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    public Long getSystemUserId(){
       return getCustomerId();
    }
    
    public Integer getSystemUserTypeId(){
        return USERTYPEID;
    }
     
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Customer[ customerId=" + customerId + " ]";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPermissions(String permissions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

    public Agent getAgentId() {
        return agentId;
    }

    public void setAgentId(Agent agentId) {
        this.agentId = agentId;
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
}