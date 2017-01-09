/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hp
 */
@Entity
@Table(name = "prospect_customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProspectCustomer.findAll", query = "SELECT p FROM ProspectCustomer p"),
    @NamedQuery(name = "ProspectCustomer.findById", query = "SELECT p FROM ProspectCustomer p WHERE p.id = :id"),
    @NamedQuery(name = "ProspectCustomer.findByFirstName", query = "SELECT p FROM ProspectCustomer p WHERE p.firstName = :firstName"),
    @NamedQuery(name = "ProspectCustomer.findByMiddleName", query = "SELECT p FROM ProspectCustomer p WHERE p.middleName = :middleName"),
    @NamedQuery(name = "ProspectCustomer.findByLastName", query = "SELECT p FROM ProspectCustomer p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "ProspectCustomer.findByState", query = "SELECT p FROM ProspectCustomer p WHERE p.state = :state"),
    @NamedQuery(name = "ProspectCustomer.findByCity", query = "SELECT p FROM ProspectCustomer p WHERE p.city = :city"),
    @NamedQuery(name = "ProspectCustomer.findByStreet", query = "SELECT p FROM ProspectCustomer p WHERE p.street = :street"),
    @NamedQuery(name = "ProspectCustomer.findByEmail", query = "SELECT p FROM ProspectCustomer p WHERE p.email = :email"),
    @NamedQuery(name = "ProspectCustomer.findByAgent", query = "SELECT p FROM ProspectCustomer p WHERE p.agent = :agent ORDER BY p.id DESC"),
    @NamedQuery(name = "ProspectCustomer.findByPhoneNo", query = "SELECT p FROM ProspectCustomer p WHERE p.phoneNo = :phoneNo")})
public class ProspectCustomer implements Serializable {
    @Column(name = "company")
    private String company;
    @Column(name = "post")
    private String post;
    @JoinColumn(name = "agent", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agent;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "street")
    private String street;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "phone_no")
    private String phoneNo;

    public ProspectCustomer() {
    }

    public ProspectCustomer(Long id) {
        this.id = id;
    }

    public ProspectCustomer(Long id, String firstName, String lastName, String state, String city, String street, String email, String phoneNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.state = state;
        this.city = city;
        this.street = street;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public String getFullName(){
        String fname = getFirstName();
        String mname = (getMiddleName().equals("")) ? " " : " " + getMiddleName() + " ";
        String lname = getLastName();
        
        return lname + mname + fname;
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
        if (!(object instanceof ProspectCustomer)) {
            return false;
        }
        ProspectCustomer other = (ProspectCustomer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.ProspectCustomer[ id=" + id + " ]";
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    
}
