/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "score")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Score.findAll", query = "SELECT s FROM Score s")
    , @NamedQuery(name = "Score.findByGameID", query = "SELECT s FROM Score s WHERE s.gameID = :gameID")
    , @NamedQuery(name = "Score.findByHomeScore", query = "SELECT s FROM Score s WHERE s.homeScore = :homeScore")
    , @NamedQuery(name = "Score.findByAwayScore", query = "SELECT s FROM Score s WHERE s.awayScore = :awayScore")
    , @NamedQuery(name = "Score.findBySubmitDate", query = "SELECT s FROM Score s WHERE s.submitDate = :submitDate")})
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "gameID")
    private String gameID;
    @Basic(optional = false)
    @Column(name = "homeScore")
    private int homeScore;
    @Basic(optional = false)
    @Column(name = "awayScore")
    private int awayScore;
    @Basic(optional = false)
    @Column(name = "submitDate")
    @Temporal(TemporalType.DATE)
    private Date submitDate;
    @JoinColumn(name = "gameID", referencedColumnName = "gameID", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Game game;
    @JoinColumn(name = "submitter", referencedColumnName = "userID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User submitter;
    @JoinColumn(name = "winner", referencedColumnName = "teamID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Team winner;

    public Score() {
    }

    public Score(String gameID) {
        this.gameID = gameID;
    }

    public Score(String gameID, int homeScore, int awayScore, Date submitDate) {
        this.gameID = gameID;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.submitDate = submitDate;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
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
        if (!(object instanceof Score)) {
            return false;
        }
        Score other = (Score) object;
        if ((this.gameID == null && other.gameID != null) || (this.gameID != null && !this.gameID.equals(other.gameID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Score[ gameID=" + gameID + " ]";
    }
    
}
