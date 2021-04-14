/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "game")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g")
    , @NamedQuery(name = "Game.findByGameID", query = "SELECT g FROM Game g WHERE g.gameID = :gameID")
    , @NamedQuery(name = "Game.findByDate", query = "SELECT g FROM Game g WHERE g.date = :date")})
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "gameID")
    private String gameID;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "awayTeam", referencedColumnName = "teamID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team awayTeam;
    @JoinColumn(name = "homeTeam", referencedColumnName = "teamID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team homeTeam;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.EAGER)
    private Score score;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameID", fetch = FetchType.EAGER)
    private List<SpareRequest> spareRequestList;

    public Game() {
    }

    public Game(String gameID) {
        this.gameID = gameID;
    }

    public Game(String gameID, Date date) {
        this.gameID = gameID;
        this.date = date;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    @XmlTransient
    public List<SpareRequest> getSpareRequestList() {
        return spareRequestList;
    }

    public void setSpareRequestList(List<SpareRequest> spareRequestList) {
        this.spareRequestList = spareRequestList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameID != null ? gameID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.gameID == null && other.gameID != null) || (this.gameID != null && !this.gameID.equals(other.gameID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Game[ gameID=" + gameID + " ]";
    }
    
}
