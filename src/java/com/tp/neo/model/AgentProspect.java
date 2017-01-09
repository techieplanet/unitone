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
 * @author swedge-mac
 */
@Entity
@Table(name = "agent_prospect")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgentProspect.findAll", query = "SELECT a FROM AgentProspect a"),
    @NamedQuery(name = "AgentProspect.findById", query = "SELECT a FROM AgentProspect a WHERE a.id = :id"),
    @NamedQuery(name = "AgentProspect.findByFirstName", query = "SELECT a FROM AgentProspect a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "AgentProspect.findByMiddleName", query = "SELECT a FROM AgentProspect a WHERE a.middleName = :middleName"),
    @NamedQuery(name = "AgentProspect.findByLastName", query = "SELECT a FROM AgentProspect a WHERE a.lastName = :lastName"),
    @NamedQuery(name = "AgentProspect.findByState", query = "SELECT a FROM AgentProspect a WHERE a.state = :state"),
    @NamedQuery(name = "AgentProspect.findByCity", query = "SELECT a FROM AgentProspect a WHERE a.city = :city"),
    @NamedQuery(name = "AgentProspect.findByStreet", query = "SELECT a FROM AgentProspect a WHERE a.street = :street"),
    @NamedQuery(name = "AgentProspect.findByEmail", query = "SELECT a FROM AgentProspect a WHERE a.email = :email"),
    @NamedQuery(name = "AgentProspect.findByPhoneNo", query = "SELECT a FROM AgentProspect a WHERE a.phoneNo = :phoneNo"),
    @NamedQuery(name = "AgentProspect.findByCompany", query = "SELECT a FROM AgentProspect a WHERE a.company = :company"),
    @NamedQuery(name = "AgentProspect.findByPost", query = "SELECT a FROM AgentProspect a WHERE a.post = :post")})
public class AgentProspect implements Serializable {

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
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "phone_no")
    private String phoneNo;
    @Column(name = "company")
    private String company;
    @Column(name = "post")
    private String post;
    @JoinColumn(name = "agent", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agent;

    public AgentProspect() {
    }

    public AgentProspect(Long id) {
        this.id = id;
    }

    public AgentProspect(Long id, String firstName, String lastName, String state, String city, String street, String email, String phoneNo) {
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

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
        if (!(object instanceof AgentProspect)) {
            return false;
        }
        AgentProspect other = (AgentProspect) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.AgentProspect[ id=" + id + " ]";
    }
    
    
    public String getFullName(){
        String fname = getFirstName();
        String mname = (getMiddleName().equals("")) ? " " : " " + getMiddleName() + " ";
        String lname = getLastName();
        
        return lname + mname + fname;
    }
    
}
