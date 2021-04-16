package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a SpareRequest object in the database. 
 * 
 * @author CurlingCapstone
 */
@Entity
@Table(name = "sparerequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpareRequest.findAll", query = "SELECT s FROM SpareRequest s")
    , @NamedQuery(name = "SpareRequest.findByRequestID", query = "SELECT s FROM SpareRequest s WHERE s.requestID = :requestID")
    , @NamedQuery(name = "SpareRequest.findByRequestDate", query = "SELECT s FROM SpareRequest s WHERE s.requestDate = :requestDate")
    , @NamedQuery(name = "SpareRequest.findByIsActive", query = "SELECT s FROM SpareRequest s WHERE s.isActive = :isActive")})
public class SpareRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "requestID")
    private String requestID;
    @Basic(optional = false)
    @Column(name = "requestDate")
    @Temporal(TemporalType.DATE)
    private Date requestDate;
    @Column(name = "isActive")
    private Boolean isActive;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "spareRequest", fetch = FetchType.EAGER)
    private SpareAssigned spareAssigned;
    @JoinColumn(name = "gameID", referencedColumnName = "gameID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Game gameID;
    @JoinColumn(name = "position", referencedColumnName = "positionID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Position position;
    @JoinColumn(name = "teamID", referencedColumnName = "teamID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team teamID;
    @JoinColumn(name = "submitter", referencedColumnName = "userID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User submitter;

    public SpareRequest() {
    }

    public SpareRequest(String requestID) {
        this.requestID = requestID;
    }

    public SpareRequest(String requestID, Date requestDate) {
        this.requestID = requestID;
        this.requestDate = requestDate;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public SpareAssigned getSpareAssigned() {
        return spareAssigned;
    }

    public void setSpareAssigned(SpareAssigned spareAssigned) {
        this.spareAssigned = spareAssigned;
    }

    public Game getGameID() {
        return gameID;
    }

    public void setGameID(Game gameID) {
        this.gameID = gameID;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Team getTeamID() {
        return teamID;
    }

    public void setTeamID(Team teamID) {
        this.teamID = teamID;
    }

    public User getSubmitter() {
        return submitter;
    }

    public void setSubmitter(User submitter) {
        this.submitter = submitter;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestID != null ? requestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpareRequest)) {
            return false;
        }
        SpareRequest other = (SpareRequest) object;
        if ((this.requestID == null && other.requestID != null) || (this.requestID != null && !this.requestID.equals(other.requestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.SpareRequest[ requestID=" + requestID + " ]";
    }
    
}
