/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;


import com.tp.neo.interfaces.IRestricted;
import com.tp.neo.interfaces.ITrailable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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

/**
 *
 * @author Swedge
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findById", query = "SELECT p FROM Project p WHERE p.id = :id"),
    @NamedQuery(name = "Project.findByName", query = "SELECT p FROM Project p WHERE p.name = :name"),
    @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description"),
    @NamedQuery(name = "Project.findByLocation", query = "SELECT p FROM Project p WHERE p.location = :location"),
    @NamedQuery(name = "Project.findByProjectManager", query = "SELECT p FROM Project p WHERE p.projectManager = :projectManager"),
    @NamedQuery(name = "Project.findByTargetSalesUnits", query = "SELECT p FROM Project p WHERE p.targetSalesUnits = :targetSalesUnits"),
    @NamedQuery(name = "Project.findByDeleted", query = "SELECT p FROM Project p WHERE p.deleted = :deleted"),
    @NamedQuery(name = "Project.findByActive", query = "SELECT p FROM Project p WHERE p.active = :active"),
    @NamedQuery(name = "Project.findByCreatedDate", query = "SELECT p FROM Project p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "Project.findByCreatedBy", query = "SELECT p FROM Project p WHERE p.createdBy = :createdBy"),
    @NamedQuery(name = "Project.findByModifiedDate", query = "SELECT p FROM Project p WHERE p.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Project.findByModifiedBy", query = "SELECT p FROM Project p WHERE p.modifiedBy = :modifiedBy"),



        
    })// end NamedQuery

public class Project extends BaseModel{

    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_by")
    private Long modifiedBy;

    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "project")
    private Collection<ProjectUnit> projectUnitCollection;

    @Basic(optional = false)
    @Column(name = "deleted")
    private short deleted;
    @Basic(optional = false)
    @Column(name = "active")
    private short active;

    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "project")


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "location")
    private String location;
    @Column(name = "project_manager")
    private String projectManager;
    @Column(name = "target_sales_units")
    private Integer targetSalesUnits;


    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public Project() {
    }

    public Project(Integer id) {
        this.id = id;
    }

    public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getTargetSalesUnits() {
        return targetSalesUnits;
    }

    public void setTargetSalesUnits(Integer targetSalesUnits) {
        this.targetSalesUnits = targetSalesUnits;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Project[ id=" + id + " ]";
    }


    public short getDeleted() {
        return deleted;
    }

    public void setDeleted(short deleted) {
        this.deleted = deleted;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
    }

    

    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) return "create_project";
        else if(action.toUpperCase().equals("EDIT")) return "edit_project";
        else if(action.toUpperCase().equals("DELETE")) return "delete_project";
        else return "view_project";
    }

    @XmlTransient
    public Collection<ProjectUnit> getProjectUnitCollection() {
        return projectUnitCollection;
    }

    public void setProjectUnitCollection(Collection<ProjectUnit> projectUnitCollection) {
        this.projectUnitCollection = projectUnitCollection;
    }
    
}