/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p")
    , @NamedQuery(name = "Position.findByPositionID", query = "SELECT p FROM Position p WHERE p.positionID = :positionID")
    , @NamedQuery(name = "Position.findByPositionName", query = "SELECT p FROM Position p WHERE p.positionName = :positionName")})
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "positionID")
    private Integer positionID;
    @Basic(optional = false)
    @Column(name = "positionName")
    private String positionName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position", fetch = FetchType.EAGER)
    private List<Registration> registrationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position", fetch = FetchType.EAGER)
    private List<Player> playerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "position", fetch = FetchType.EAGER)
    private List<Spare> spareList;

    public Position() {
    }

    public Position(Integer positionID) {
        this.positionID = positionID;
    }

    public Position(Integer positionID, String positionName) {
        this.positionID = positionID;
        this.positionName = positionName;
    }

    public Integer getPositionID() {
        return positionID;
    }

    public void setPositionID(Integer positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @XmlTransient
    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    @XmlTransient
    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    @XmlTransient
    public List<Spare> getSpareList() {
        return spareList;
    }

    public void setSpareList(List<Spare> spareList) {
        this.spareList = spareList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (positionID != null ? positionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Position)) {
            return false;
        }
        Position other = (Position) object;
        if ((this.positionID == null && other.positionID != null) || (this.positionID != null && !this.positionID.equals(other.positionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Position[ positionID=" + positionID + " ]";
    }
    
}
