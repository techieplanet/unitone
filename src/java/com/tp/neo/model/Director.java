/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

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

/**
 *
 * @author SWEDGE
 */

@Entity
@Table(name="director")
@NamedQueries({
    @NamedQuery(name="Director.findByAgent" ,query = "select d from Director d where d.agent =:agent"),
     @NamedQuery(name="Director.findByAgentId" ,query = "select d from Director d where d.agent.agentId =:agentId")
})
public class Director {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    
    @ManyToOne
    @JoinColumn(name="agent_id", referencedColumnName = "agent_id")
    private Agent agent;
    
    @Column(name="name")
    private String name ;
    
    @Column(name="passport")
    private Long passport ;

    @Column(name="id_card")
    private Long iDCard;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the agentId
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * @param AgentId the agentId to set
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the passport
     */
    public Long getPassport() {
        return passport;
    }

    /**
     * @param passport the passport to set
     */
    public void setPassport(Long passport) {
        this.passport = passport;
    }

    /**
     * @return the iDCard
     */
    public Long getiDCard() {
        return iDCard;
    }

    /**
     * @param iDCard the iDCard to set
     */
    public void setiDCard(Long iDCard) {
        this.iDCard = iDCard;
    }
}
