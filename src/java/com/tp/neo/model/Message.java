/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Message.findByModifiedBy", query = "SELECT m FROM Message m WHERE m.modifiedBy = :modifiedBy"),
    //@NamedQuery(name = "Message.findByModifiedBy", query = "SELECT m FROM Message m WHERE m.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Message.findByTarget", query = "SELECT m FROM Message m WHERE m.target = :target"),
    @NamedQuery(name = "Message.findByParentId", query = "SELECT m FROM Message m WHERE m.parentId = :parentId"),
    @NamedQuery(name = "Message.findAllThreadByAgent", query = "SELECT m , msg "
            + "FROM Message m left join Message msg ON  msg.parentId = m.id AND (msg.createdBy = :cust_id AND msg.creatorUserType = 3 OR msg.createdBy = :agent_id AND msg.creatorUserType = 2)  "
            + "WHERE ( "
            + "(m.creatorUserType = 2 AND m.createdBy = :agent_id)"
            + " OR (m.id IN (select recipient.messageId.id FROM MessageToRecipient recipient where recipient.recipientId = :agent_id AND recipient.recipientType = 2 AND m.createdBy = :cust_id )))"
            + "  AND m.parentId = 0 AND :cust_id IN (select recipient.recipientId FROM MessageToRecipient recipient where recipient.recipientId = :cust_id AND recipient.recipientType = 3 AND m.createdBy = :agent_id AND m.id = recipient.messageId.id) ORDER BY m.id DESC, msg.id DESC "),
    @NamedQuery(name = "Message.findAllThreadByAgent2Admin", query = "SELECT m , msg "
            + "FROM Message m left join Message msg ON  msg.parentId = m.id AND (msg.createdBy = :agent_id AND msg.creatorUserType = 2  OR  msg.creatorUserType = 1 ) "
            + "WHERE ( "
            + " (m.creatorUserType = 1) "
            + " OR (m.id IN (select recipient.messageId.id FROM MessageToRecipient recipient where recipient.recipientId = :agent_id AND recipient.recipientType = 1 AND m.createdBy = :agent_id )))"
            + "  AND m.parentId = 0 AND :agent_id IN (select recipient.recipientId FROM MessageToRecipient recipient where recipient.recipientId = :agent_id AND recipient.recipientType = 2 AND m.createdBy = :admin_id AND m.id = recipient.messageId.id) ORDER BY m.id DESC, msg.id DESC "),
    @NamedQuery(name = "Message.findAllThreadByAdmin", query = "SELECT m , msg "
            + "FROM Message m left join Message msg ON  msg.parentId = m.id AND (msg.createdBy = :agent_id AND msg.creatorUserType = 2  OR msg.createdBy = :admin_id AND msg.creatorUserType = 1 ) "
            + "WHERE ( "
            + " (m.creatorUserType = 1 AND m.createdBy = :admin_id) "
            + " OR (m.id IN (select recipient.messageId.id FROM MessageToRecipient recipient where recipient.recipientId = :admin_id AND recipient.recipientType = 1 AND m.createdBy = :agent_id )))"
            + "  AND m.parentId = 0 AND :agent_id IN (select recipient.recipientId FROM MessageToRecipient recipient where recipient.recipientId = :agent_id AND recipient.recipientType = 2 AND m.createdBy = :admin_id AND m.id = recipient.messageId.id) ORDER BY m.id DESC, msg.id DESC "),
    @NamedQuery(name = "Message.findAllThreadByCustomer", query = "SELECT m , msg "
            + "FROM Message m left join Message msg ON  msg.parentId = m.id AND (msg.createdBy = :agent_id AND msg.creatorUserType = 2 OR msg.createdBy = :cust_id AND msg.creatorUserType = 3) "
            + "WHERE ( "
            + "(m.creatorUserType = 3 AND m.createdBy = :cust_id)"
            + " OR (m.id IN (select recipient.messageId.id FROM MessageToRecipient recipient where recipient.recipientId = :cust_id AND recipient.recipientType = 3 AND m.createdBy = :agent_id )))"
            + "  AND m.parentId = 0 AND :agent_id IN (select recipient.recipientId FROM MessageToRecipient recipient where recipient.recipientId = :agent_id AND recipient.recipientType = 2 AND m.createdBy = :cust_id AND m.id = recipient.messageId.id) ORDER BY m.id DESC, msg.id DESC "),
    
    @NamedQuery(name = "Message.findByCreatorUserType", query = "SELECT m FROM Message m WHERE m.creatorUserType = :creatorUserType")})


public class Message implements Serializable {

    @Column(name = "target")
    private Short target;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "replied")
    private Short replied;
    @Column(name = "creator_user_type")
    private Short creatorUserType;
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
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId")
    private List<MessageToRecipient> messageToRecipientList;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "messageId")
    //private Collection<MessageToRecipient> messageToRecipientCollection;
    @Column(name = "subject")
    private String subject;
    
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

    public Short getTarget() {
        return target;
    }

    public void setTarget(Short target) {
        this.target = target;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public Short getCreatorUserType() {
        return creatorUserType;
    }

    public void setCreatorUserType(Short creatorUserType) {
        this.creatorUserType = creatorUserType;
    }

    @XmlTransient
    public List<MessageToRecipient> getMessageToRecipientList() {
        return messageToRecipientList;
    }

    public void setMessageToRecipientList(List<MessageToRecipient> messageToRecipientList) {
        this.messageToRecipientList = messageToRecipientList;
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

    

    public Short getReplied() {
        return replied;
    }

    public void setReplied(Short replied) {
        this.replied = replied;
    }
    
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}
