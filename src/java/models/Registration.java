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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 822408
 */
@Entity
@Table(name = "registration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r")
    , @NamedQuery(name = "Registration.findByContactID", query = "SELECT r FROM Registration r WHERE r.contactID = :contactID")
    , @NamedQuery(name = "Registration.findByFlexibleP", query = "SELECT r FROM Registration r WHERE r.flexibleP = :flexibleP")
    , @NamedQuery(name = "Registration.findByLeagues", query = "SELECT r FROM Registration r WHERE r.leagues = :leagues")
    , @NamedQuery(name = "Registration.findBySignupAll", query = "SELECT r FROM Registration r WHERE r.signupAll = :signupAll")
    , @NamedQuery(name = "Registration.findByRegType", query = "SELECT r FROM Registration r WHERE r.regType = :regType")
    , @NamedQuery(name = "Registration.findByGroupID", query = "SELECT r FROM Registration r WHERE r.groupID = :groupID")
    , @NamedQuery(name = "Registration.findByTeamName", query = "SELECT r FROM Registration r WHERE r.teamName = :teamName")})
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "contactID")
    private String contactID;
    @Column(name = "flexibleP")
    private Boolean flexibleP;
    @Basic(optional = false)
    @Column(name = "leagues")
    private String leagues;
    @Column(name = "signupAll")
    private Boolean signupAll;
    @Basic(optional = false)
    @Column(name = "regType")
    private String regType;
    @Column(name = "groupID")
    private String groupID;
    @Column(name = "teamName")
    private String teamName;
    @JoinColumn(name = "contactID", referencedColumnName = "contactID", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Contact contact;
    @JoinColumn(name = "position", referencedColumnName = "positionID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Position position;

    public Registration() {
    }

    public Registration(String contactID) {
        this.contactID = contactID;
    }

    public Registration(String contactID, String leagues, String regType) {
        this.contactID = contactID;
        this.leagues = leagues;
        this.regType = regType;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public Boolean getFlexibleP() {
        return flexibleP;
    }

    public void setFlexibleP(Boolean flexibleP) {
        this.flexibleP = flexibleP;
    }

    public String getLeagues() {
        return leagues;
    }

    public void setLeagues(String leagues) {
        this.leagues = leagues;
    }

    public Boolean getSignupAll() {
        return signupAll;
    }

    public void setSignupAll(Boolean signupAll) {
        this.signupAll = signupAll;
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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
        hash += (contactID != null ? contactID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.contactID == null && other.contactID != null) || (this.contactID != null && !this.contactID.equals(other.contactID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Registration[ contactID=" + contactID + " ]";
    }
    
}
