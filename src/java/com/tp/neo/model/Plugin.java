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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "Plugin.findBySettings", query = "SELECT p FROM Plugin p WHERE p.settings = :settings")})
public class Plugin implements Serializable {

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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
