/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.FetchType;

/**
 *
 * @author swedge-mac
 */
@Entity
@Table(name = "project_unit_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectUnitType.findAll", query = "SELECT p FROM ProjectUnitType p"),
    @NamedQuery(name = "ProjectUnitType.findById", query = "SELECT p FROM ProjectUnitType p WHERE p.id = :id"),
    @NamedQuery(name = "ProjectUnitType.findByTitle", query = "SELECT p FROM ProjectUnitType p WHERE p.title = :title"),
    @NamedQuery(name = "ProjectUnitType.findByActive", query = "SELECT p FROM ProjectUnitType p WHERE p.active = :active ORDER BY p.id")})
public class ProjectUnitType implements Serializable {

    @Column(name = "active")
    private Short active;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @OneToMany(fetch=FetchType.LAZY ,mappedBy = "unitType")
    private Collection<ProjectUnit> projectUnitCollection;

    public ProjectUnitType() {
    }

    public ProjectUnitType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlTransient
    public Collection<ProjectUnit> getProjectUnitCollection() {
        return projectUnitCollection;
    }

    public void setProjectUnitCollection(Collection<ProjectUnit> projectUnitCollection) {
        this.projectUnitCollection = projectUnitCollection;
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
        if (!(object instanceof ProjectUnitType)) {
            return false;
        }
        ProjectUnitType other = (ProjectUnitType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.ProjectUnitType[ id=" + id + " ]";
    }

    public Short getActive() {
        return active;
    }

    public void setActive(Short active) {
        this.active = active;
    }
    
}
