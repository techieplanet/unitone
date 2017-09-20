/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SWEDGE
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findById", query = "SELECT d FROM Document d WHERE d.id = :id"),
    @NamedQuery(name = "Document.findByOwnerId", query = "SELECT d FROM Document d WHERE d.ownerId = :ownerId"),
    @NamedQuery(name = "Document.findByOwnerTypeId", query = "SELECT d FROM Document d WHERE d.ownerTypeId = :ownerTypeId"),
    @NamedQuery(name = "Document.findByPath", query = "SELECT d FROM Document d WHERE d.path = :path"),
    @NamedQuery(name = "Document.findByCreatedDate", query = "SELECT d FROM Document d WHERE d.createdDate = :createdDate"),
    @NamedQuery(name = "Document.findByModifiedDate", query = "SELECT d FROM Document d WHERE d.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Document.findByOwnerIdDoctypeIdOwnerTypeId", query = "SELECT d FROM Document d WHERE d.ownerId = :ownerID AND d.docTypeId = :docTypeId AND d.ownerTypeId = :ownerTypeId"),
    @NamedQuery(name = "Document.findByOwnerIdAndOwnerTypeId", query = "SELECT d FROM Document d WHERE d.ownerId = :ownerId AND d.ownerTypeId = :ownerTypeId ORDER by d.docTypeId.weight")})
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "owner_id")
    private BigInteger ownerId;
    @Column(name = "owner_type_id")
    private Integer ownerTypeId;
    @Column(name = "path")
    private String path;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "doc_type_id", referencedColumnName = "id")
    @ManyToOne
    private DocumentType docTypeId;

    public Document() {
    }

    public Document(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(BigInteger ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setOwnerTypeId(Integer ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public DocumentType getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(DocumentType docTypeId) {
        this.docTypeId = docTypeId;
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
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Document[ id=" + id + " ]";
    }
    
}
