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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserID", query = "SELECT u FROM User u WHERE u.userID = :userID")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByResetUUID", query = "SELECT u FROM User u WHERE u.resetUUID = :resetUUID")
    , @NamedQuery(name = "User.findByIsActive", query = "SELECT u FROM User u WHERE u.isActive = :isActive")})
public class User implements Serializable {

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private Executive executive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submitter", fetch = FetchType.EAGER)
    private List<Score> scoreList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "submitter", fetch = FetchType.EAGER)
    private List<SpareRequest> spareRequestList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "userID")
    private String userID;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "resetUUID")
    private String resetUUID;
    @Basic(optional = false)
    @Column(name = "isActive")
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID", fetch = FetchType.EAGER)
    private List<NewsPost> newsPostList;
    @JoinColumn(name = "contactID", referencedColumnName = "contactID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Contact contactID;
    @JoinColumn(name = "role", referencedColumnName = "roleID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Role role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID", fetch = FetchType.EAGER)
    private List<Player> playerList;

    public User() {
    }

    public User(String userID) {
        this.userID = userID;
    }

    public User(String userID, String email, boolean isActive) {
        this.userID = userID;
        this.email = email;
        this.isActive = isActive;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetUUID() {
        return resetUUID;
    }

    public void setResetUUID(String resetUUID) {
        this.resetUUID = resetUUID;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Executive getExecutive() {
        return executive;
    }

    public void setExecutive(Executive executive) {
        this.executive = executive;
    }

    @XmlTransient
    public List<NewsPost> getNewsPostList() {
        return newsPostList;
    }

    public void setNewsPostList(List<NewsPost> newsPostList) {
        this.newsPostList = newsPostList;
    }

    public Contact getContactID() {
        return contactID;
    }

    public void setContactID(Contact contactID) {
        this.contactID = contactID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.User[ userID=" + userID + " ]";
    }


    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @XmlTransient
    public List<SpareRequest> getSpareRequestList() {
        return spareRequestList;
    }

    public void setSpareRequestList(List<SpareRequest> spareRequestList) {
        this.spareRequestList = spareRequestList;
    }
    
}
