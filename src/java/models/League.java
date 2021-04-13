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
@Table(name = "league")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "League.findAll", query = "SELECT l FROM League l")
    , @NamedQuery(name = "League.findByLeagueID", query = "SELECT l FROM League l WHERE l.leagueID = :leagueID")
    , @NamedQuery(name = "League.findByWeekday", query = "SELECT l FROM League l WHERE l.weekday = :weekday")})
public class League implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "leagueID")
    private String leagueID;
    @Basic(optional = false)
    @Column(name = "weekday")
    private String weekday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leagueID", fetch = FetchType.EAGER)
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leagueID", fetch = FetchType.EAGER)
    private List<Executive> executiveList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "leagueID", fetch = FetchType.EAGER)
    private List<Spare> spareList;

    public League() {
    }

    public League(String leagueID) {
        this.leagueID = leagueID;
    }

    public League(String leagueID, String weekday) {
        this.leagueID = leagueID;
        this.weekday = weekday;
    }

    public String getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(String leagueID) {
        this.leagueID = leagueID;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @XmlTransient
    public List<Executive> getExecutiveList() {
        return executiveList;
    }

    public void setExecutiveList(List<Executive> executiveList) {
        this.executiveList = executiveList;
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
        hash += (leagueID != null ? leagueID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof League)) {
            return false;
        }
        League other = (League) object;
        if ((this.leagueID == null && other.leagueID != null) || (this.leagueID != null && !this.leagueID.equals(other.leagueID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.League[ leagueID=" + leagueID + " ]";
    }
    
}
