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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "standing")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Standing.findAll", query = "SELECT s FROM Standing s")
    , @NamedQuery(name = "Standing.findByTeamID", query = "SELECT s FROM Standing s WHERE s.teamID = :teamID")
    , @NamedQuery(name = "Standing.findByGamesPlayed", query = "SELECT s FROM Standing s WHERE s.gamesPlayed = :gamesPlayed")
    , @NamedQuery(name = "Standing.findByGamesWon", query = "SELECT s FROM Standing s WHERE s.gamesWon = :gamesWon")
    , @NamedQuery(name = "Standing.findByGamesLost", query = "SELECT s FROM Standing s WHERE s.gamesLost = :gamesLost")})
public class Standing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "teamID")
    private String teamID;
    @Basic(optional = false)
    @Column(name = "gamesPlayed")
    private int gamesPlayed;
    @Basic(optional = false)
    @Column(name = "gamesWon")
    private int gamesWon;
    @Basic(optional = false)
    @Column(name = "gamesLost")
    private int gamesLost;
    @JoinColumn(name = "teamID", referencedColumnName = "teamID", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Team team;

    public Standing() {
    }

    public Standing(String teamID) {
        this.teamID = teamID;
    }

    public Standing(String teamID, int gamesPlayed, int gamesWon, int gamesLost) {
        this.teamID = teamID;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamID != null ? teamID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Standing)) {
            return false;
        }
        Standing other = (Standing) object;
        if ((this.teamID == null && other.teamID != null) || (this.teamID != null && !this.teamID.equals(other.teamID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Standing[ teamID=" + teamID + " ]";
    }
    
}
