/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "plugin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plugin.findAll", query = "SELECT p FROM Plugin p"),
    @NamedQuery(name = "Plugin.findById", query = "SELECT p FROM Plugin p WHERE p.id = :id"),
    @NamedQuery(name = "Plugin.findByPluginName", query = "SELECT p FROM Plugin p WHERE p.pluginName = :pluginName"),
    @NamedQuery(name = "Plugin.findByInstallationStatus", query = "SELECT p FROM Plugin p WHERE p.installationStatus = :installationStatus"),
    @NamedQuery(name = "Plugin.findByActive", query = "SELECT p FROM Plugin p WHERE p.active = :active"),
    @NamedQuery(name = "Plugin.findBySettings", query = "SELECT p FROM Plugin p WHERE p.settings = :settings"),
    @NamedQuery(name = "Plugin.findByDeleted", query = "SELECT p FROM Plugin p WHERE p.deleted = :deleted"),
    @NamedQuery(name = "Plugin.findByCreatedBy", query = "SELECT p FROM Plugin p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "Plugin.findByCreatedDate", query = "SELECT p FROM Plugin p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "Plugin.findByModifiedBy", query = "SELECT p FROM Plugin p WHERE p.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Plugin.findByModifiedDate", query = "SELECT p FROM Plugin p WHERE p.modifiedDate = :modifiedDate")})
public class Plugin extends BaseModel{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "plugin_name")
    private String pluginName;
    @Column(name = "installation_status")
    private Short installationStatus;
    @Column(name = "active")
    private Short active;
    @Column(name = "settings")
    private String settings;
    @Column(name = "deleted")
    private Short deleted;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_by")
    private Long modifiedBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Plugin() {
    }

    public Plugin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public Short getInstallationStatus() {
        return installationStatus;
    }

    public void setInstallationStatus(Short installationStatus) {
        this.installationStatus = installationStatus;
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) return "create_plugins";
        else if(action.toUpperCase().equals("EDIT")) return "edit_plugins";
        else if(action.toUpperCase().equals("DELETE")) return "delete_plugins";
        else return "view_plugins";
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plugin)) {
            return false;
        }
        Plugin other = (Plugin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Plugin[ id=" + id + " ]";
    }
    
}
