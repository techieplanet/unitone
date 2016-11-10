/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "agent_balance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgentBalance.findAll", query = "SELECT a FROM AgentBalance a"),
    @NamedQuery(name = "AgentBalance.findByAgentId", query = "SELECT a FROM AgentBalance a WHERE a.agentId = :agentId"),
    @NamedQuery(name = "AgentBalance.findByAccountCode", query = "SELECT a FROM AgentBalance a WHERE a.accountCode = :accountCode"),
    @NamedQuery(name = "AgentBalance.findByTotaldebit", query = "SELECT a FROM AgentBalance a WHERE a.totaldebit = :totaldebit"),
    @NamedQuery(name = "AgentBalance.findByTotalcredit", query = "SELECT a FROM AgentBalance a WHERE a.totalcredit = :totalcredit"),
    @NamedQuery(name = "AgentBalance.findAllBalanceSum", query = "SELECT COALESCE(SUM(a.totalcredit - a.totaldebit),0) FROM AgentBalance a WHERE a.accountCode <> ''")})

public class AgentBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "agent_id")
    private Long agentId;
    @Column(name = "account_code")
    private String accountCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "totaldebit")
    private Double totaldebit;
    @Column(name = "totalcredit")
    private Double totalcredit;

    public AgentBalance() {
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Double getTotaldebit() {
        return totaldebit;
    }

    public void setTotaldebit(Double totaldebit) {
        this.totaldebit = totaldebit;
    }

    public Double getTotalcredit() {
        return totalcredit;
    }

    public void setTotalcredit(Double totalcredit) {
        this.totalcredit = totalcredit;
    }
    
    public String toString(){
        String str = "Total Debit : " + getTotaldebit() + ", Total Credit : " + getTotaldebit() + ", Agent Id: " + getAgentId();
        return str;
    }
}
