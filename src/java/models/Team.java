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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t")
    , @NamedQuery(name = "Team.findByTeamID", query = "SELECT t FROM Team t WHERE t.teamID = :teamID")
    , @NamedQuery(name = "Team.findByTeamName", query = "SELECT t FROM Team t WHERE t.teamName = :teamName")})
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "teamID")
    private String teamID;
    @Basic(optional = false)
    @Column(name = "teamName")
    private String teamName;
    @OneToMany(mappedBy = "awayTeam", fetch = FetchType.EAGER)
    private List<Game> gameList;
    @OneToMany(mappedBy = "homeTeam", fetch = FetchType.EAGER)
    private List<Game> gameList1;
    @JoinColumn(name = "leagueID", referencedColumnName = "leagueID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private League leagueID;
    @OneToMany(mappedBy = "winner", fetch = FetchType.EAGER)
    private List<Score> scoreList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "team", fetch = FetchType.EAGER)
    private Standing standing;
    @OneToMany(mappedBy = "teamID", fetch = FetchType.EAGER)
    private List<Player> playerList;

    public Team() {
    }

    public Team(String teamID) {
        this.teamID = teamID;
    }

    public Team(String teamID, String teamName) {
        this.teamID = teamID;
        this.teamName = teamName;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @XmlTransient
    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    @XmlTransient
    public List<Game> getGameList1() {
        return gameList1;
    }

    public void setGameList1(List<Game> gameList1) {
        this.gameList1 = gameList1;
    }

    public League getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(League leagueID) {
        this.leagueID = leagueID;
    }

    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public Standing getStanding() {
        return standing;
    }

    public void setStanding(Standing standing) {
        this.standing = standing;
    }

    @XmlTransient
    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
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
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamID == null && other.teamID != null) || (this.teamID != null && !this.teamID.equals(other.teamID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Team[ teamID=" + teamID + " ]";
    }
    
}
