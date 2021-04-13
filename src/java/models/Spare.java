/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "spare")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Spare.findAll", query = "SELECT s FROM Spare s")
    , @NamedQuery(name = "Spare.findBySpareID", query = "SELECT s FROM Spare s WHERE s.spareID = :spareID")
    , @NamedQuery(name = "Spare.findByFlexibleP", query = "SELECT s FROM Spare s WHERE s.flexibleP = :flexibleP")})
public class Spare implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "spareID")
    private String spareID;
    @Column(name = "flexibleP")
    private Boolean flexibleP;
    @JoinColumn(name = "contactID", referencedColumnName = "contactID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Contact contactID;
    @JoinColumn(name = "leagueID", referencedColumnName = "leagueID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private League leagueID;
    @JoinColumn(name = "position", referencedColumnName = "positionID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Position position;

    public Spare() {
    }

    public Spare(String spareID) {
        this.spareID = spareID;
    }

    public String getSpareID() {
        return spareID;
    }

    public void setSpareID(String spareID) {
        this.spareID = spareID;
    }

    public Boolean getFlexibleP() {
        return flexibleP;
    }

    public void setFlexibleP(Boolean flexibleP) {
        this.flexibleP = flexibleP;
    }

    public Contact getContactID() {
        return contactID;
    }

    public void setContactID(Contact contactID) {
        this.contactID = contactID;
    }

    public League getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(League leagueID) {
        this.leagueID = leagueID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (spareID != null ? spareID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Spare)) {
            return false;
        }
        Spare other = (Spare) object;
        if ((this.spareID == null && other.spareID != null) || (this.spareID != null && !this.spareID.equals(other.spareID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Spare[ spareID=" + spareID + " ]";
    }
    
}
