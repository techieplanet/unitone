/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

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
import javax.persistence.Lob;
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
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRoleId", query = "SELECT r FROM Role r WHERE r.roleId = :roleId"),
    @NamedQuery(name = "Role.findByTitle", query = "SELECT r FROM Role r WHERE r.title = :title"),
    @NamedQuery(name = "Role.findByDescription", query = "SELECT r FROM Role r WHERE r.description = :description"),
    @NamedQuery(name = "Role.findByActive", query = "SELECT r FROM Role r WHERE r.active = :active"),
    @NamedQuery(name = "Role.findByCreatedDate", query = "SELECT r FROM Role r WHERE r.createdDate = :createdDate"),
    @NamedQuery(name = "Role.findByCreatedBy", query = "SELECT r FROM Role r WHERE r.createdBy = :createdBy"),
    @NamedQuery(name = "Role.findByModifiedDate", query = "SELECT r FROM Role r WHERE r.modifiedDate = :modifiedDate"),
    @NamedQuery(name = "Role.findByModifiedBy", query = "SELECT r FROM Role r WHERE r.modifiedBy = :modifiedBy"),
    @NamedQuery(name = "Role.findRolesLowerThan", query = "SELECT r FROM Role r WHERE r.tier > :tier"),
    @NamedQuery(name = "Role.findRolesHigherThan", query = "SELECT r FROM Role r WHERE r.tier < :tier AND r.tier !=0"),
    @NamedQuery(name = "Role.findRolesWithTier", query = "SELECT r FROM Role r WHERE r.tier = :tier"),
    @NamedQuery(name = "Role.findRolesWithAlias", query = "SELECT r FROM Role r WHERE r.alias = :alias"),
    @NamedQuery(name = "Role.findRolesLowerThanOrEqual", query = "SELECT r FROM Role r WHERE r.tier >= :tier")
  })

public class Role extends BaseModel {
    
    @Column(name="tier")
    private Integer tier;
    @Column(name="role_alias")
    private String alias;
    @Column(name="role_supervisor")
    private String supervisor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private Collection<User> userCollection;


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "permissions")
    private String permissions;
    @Basic(optional = false)
    @Column(name = "active")
    private short active;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "created_by")
    private Long createdBy;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modified_by")
    private Long modifiedBy;

    public Role() {
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Role(Integer roleId, short active) {
        this.roleId = roleId;
        this.active = active;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public short getActive() {
        return active;
    }

    public void setActive(short active) {
        this.active = active;
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
    
    public String getPermissionName(String action){
        if(action.toUpperCase().equals("NEW")) return "create_role";
        else if(action.toUpperCase().equals("EDIT")) return "edit_role";
        else if(action.toUpperCase().equals("DELETE")) return "delete_role";
        else if(action.toUpperCase().equals("PERMISSIONS")) return "view_system_permissions";
        else return "view_role";
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleId != null ? roleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleId == null && other.roleId != null) || (this.roleId != null && !this.roleId.equals(other.roleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.Role[ roleId=" + roleId + " ]";
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    /**
     * @return the teir
     */
    public int getTier() {
        return tier;
    }

    /**
     * @param teir the teir to set
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the supervisor
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }
       
}
