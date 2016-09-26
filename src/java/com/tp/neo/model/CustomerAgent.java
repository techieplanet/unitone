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
 * @author John
 */
@Entity
@Table(name = "customer_agent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomerAgent.findAll", query = "SELECT c FROM CustomerAgent c"),
    @NamedQuery(name = "CustomerAgent.findByCaId", query = "SELECT c FROM CustomerAgent c WHERE c.caId = :caId")})
public class CustomerAgent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ca_id")
    private Long caId;
    @JoinColumn(name = "agent_id", referencedColumnName = "agent_id")
    @ManyToOne(optional = false)
    private Agent agentId;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerId;

    public CustomerAgent() {
    }

    public CustomerAgent(Long caId) {
        this.caId = caId;
    }

    public Long getCaId() {
        return caId;
    }

    public void setCaId(Long caId) {
        this.caId = caId;
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
        hash += (caId != null ? caId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerAgent)) {
            return false;
        }
        CustomerAgent other = (CustomerAgent) object;
        if ((this.caId == null && other.caId != null) || (this.caId != null && !this.caId.equals(other.caId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.CustomerAgent[ caId=" + caId + " ]";
    }
    
}
