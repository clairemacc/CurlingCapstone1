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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents an Executive object in the database.
 * 
 * @author CurlingCapstone
 */
@Entity
@Table(name = "executive")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Executive.findAll", query = "SELECT e FROM Executive e")
    , @NamedQuery(name = "Executive.findByUserID", query = "SELECT e FROM Executive e WHERE e.userID = :userID")
    , @NamedQuery(name = "Executive.findByExecTitle", query = "SELECT e FROM Executive e WHERE e.execTitle = :execTitle")})
public class Executive implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userID")
    private String userID;
    @Basic(optional = false)
    @Column(name = "execTitle")
    private String execTitle;
    @JoinColumn(name = "leagueID", referencedColumnName = "leagueID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private League leagueID;
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public Executive() {
    }

    public Executive(String userID) {
        this.userID = userID;
    }

    public Executive(String userID, String execTitle) {
        this.userID = userID;
        this.execTitle = execTitle;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getExecTitle() {
        return execTitle;
    }

    public void setExecTitle(String execTitle) {
        this.execTitle = execTitle;
    }

    public League getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(League leagueID) {
        this.leagueID = leagueID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Executive)) {
            return false;
        }
        Executive other = (Executive) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Executive[ userID=" + userID + " ]";
    }
    
}
