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
 * This class represents a SpareAssigned object in the database. 
 * 
 * @author CurlingCapstone
 */
@Entity
@Table(name = "spareassigned")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpareAssigned.findAll", query = "SELECT s FROM SpareAssigned s")
    , @NamedQuery(name = "SpareAssigned.findByRequestID", query = "SELECT s FROM SpareAssigned s WHERE s.requestID = :requestID")})
public class SpareAssigned implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "requestID")
    private String requestID;
    @JoinColumn(name = "spareID", referencedColumnName = "spareID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Spare spareID;
    @JoinColumn(name = "requestID", referencedColumnName = "requestID", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private SpareRequest spareRequest;

    public SpareAssigned() {
    }

    public SpareAssigned(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Spare getSpareID() {
        return spareID;
    }

    public void setSpareID(Spare spareID) {
        this.spareID = spareID;
    }

    public SpareRequest getSpareRequest() {
        return spareRequest;
    }

    public void setSpareRequest(SpareRequest spareRequest) {
        this.spareRequest = spareRequest;
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
        if (!(object instanceof SpareAssigned)) {
            return false;
        }
        SpareAssigned other = (SpareAssigned) object;
        if ((this.requestID == null && other.requestID != null) || (this.requestID != null && !this.requestID.equals(other.requestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.SpareAssigned[ requestID=" + requestID + " ]";
    }
    
}
