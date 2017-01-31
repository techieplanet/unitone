/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByDate", query = "SELECT m FROM Message m WHERE m.date = :date"),
    @NamedQuery(name = "Message.findByMessage", query = "SELECT m FROM Message m WHERE m.message = :message"),
    @NamedQuery(name = "Message.findByChannel", query = "SELECT m FROM Message m WHERE m.channel = :channel"),
    @NamedQuery(name = "Message.findByStatus", query = "SELECT m FROM Message m WHERE m.status = :status"),
    @NamedQuery(name = "Message.findByCreatedDate", query = "SELECT m FROM Message m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "Message.findByCreatedBy", query = "SELECT m FROM Message m WHERE m.createdBy = :createdBy"),
    @NamedQuery(name = "Message.findByModifiedDate", query = "SELECT m FROM Message m WHERE m.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Message.findByModifiedBy", query = "SELECT m FROM Message m WHERE m.modifiedBy = :modifiedBy")})
public class Message implements Serializable {

    @Column(name = "target")
    private Short target;
    @Column(name = "parent_id")
    private BigInteger parentId;
    @Column(name = "replied")
    private Short replied;
    @Column(name = "creator_user_type")
    private BigInteger creatorUserType;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "message")
    private String message;
    @Column(name = "channel")
    private Short channel;
    @Column(name = "status")
    private Short status;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId")
    private Collection<MessageToRecipient> messageToRecipientCollection;

    public Message() {
    }

    public Message(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Short getChannel() {
        return channel;
    }

    public void setChannel(Short channel) {
        this.channel = channel;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    @XmlTransient
    public Collection<MessageToRecipient> getMessageToRecipientCollection() {
        return messageToRecipientCollection;
    }

    public void setMessageToRecipientCollection(Collection<MessageToRecipient> messageToRecipientCollection) {
        this.messageToRecipientCollection = messageToRecipientCollection;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Message[ id=" + id + " ]";
    }

    public Short getTarget() {
        return target;
    }

    public void setTarget(Short target) {
        this.target = target;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public Short getReplied() {
        return replied;
    }

    public void setReplied(Short replied) {
        this.replied = replied;
    }

    public BigInteger getCreatorUserType() {
        return creatorUserType;
    }

    public void setCreatorUserType(BigInteger creatorUserType) {
        this.creatorUserType = creatorUserType;
    }
    
}
