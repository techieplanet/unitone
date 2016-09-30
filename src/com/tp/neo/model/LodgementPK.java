/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author John
 */
@Embeddable
public class LodgementPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "lodgement_id")
    private long lodgementId;
    @Basic(optional = false)
    @Column(name = "sale_id")
    private long saleId;

    public LodgementPK() {
    }

    public LodgementPK(long lodgementId, long saleId) {
        this.lodgementId = lodgementId;
        this.saleId = saleId;
    }

    public long getLodgementId() {
        return lodgementId;
    }

    public void setLodgementId(long lodgementId) {
        this.lodgementId = lodgementId;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) lodgementId;
        hash += (int) saleId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LodgementPK)) {
            return false;
        }
        LodgementPK other = (LodgementPK) object;
        if (this.lodgementId != other.lodgementId) {
            return false;
        }
        if (this.saleId != other.saleId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tp.neo.model.LodgementPK[ lodgementId=" + lodgementId + ", saleId=" + saleId + " ]";
    }
    
}
