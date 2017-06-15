/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.math.BigInteger;
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


@Entity
@Table(name = "message_to_recipient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageToRecipient.findAll", query = "SELECT m FROM MessageToRecipient m"),
    @NamedQuery(name = "MessageToRecipient.findById", query = "SELECT m FROM MessageToRecipient m WHERE m.id = :id"),
    @NamedQuery(name = "MessageToRecipient.findByRecipientId", query = "SELECT m FROM MessageToRecipient m WHERE m.recipientId = :recipientId"),
    @NamedQuery(name = "MessageToRecipient.findByRecipientType", query = "SELECT m FROM MessageToRecipient m WHERE m.recipientType = :recipientType"),
    @NamedQuery(name = "MessageToRecipient.findByStatus", query = "SELECT m FROM MessageToRecipient m WHERE m.status = :status")})
public class MessageToRecipient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "recipient_id")
    private Long recipientId;
    @Column(name = "recipient_type")
    private Short recipientType;
    @Column(name = "status")
    private Short status;
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Message messageId;

    public MessageToRecipient() {
    }

    public MessageToRecipient(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Short getRecipientType() {
        return recipientType;
    }

    public void setRecipientType(Short recipientType) {
        this.recipientType = recipientType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Message getMessageId() {
        return messageId;
    }

    public void setMessageId(Message messageId) {
        this.messageId = messageId;
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
        if (!(object instanceof MessageToRecipient)) {
            return false;
        }
        MessageToRecipient other = (MessageToRecipient) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.MessageToRecipient[ id=" + id + " ]";
    }
}