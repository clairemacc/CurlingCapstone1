package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents a NewsPost object in the database. 
 * 
 * @author CurlingCapstone
 */
@Entity
@Table(name = "newspost")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NewsPost.findAll", query = "SELECT n FROM NewsPost n")
    , @NamedQuery(name = "NewsPost.findByPostID", query = "SELECT n FROM NewsPost n WHERE n.postID = :postID")
    , @NamedQuery(name = "NewsPost.findByTitle", query = "SELECT n FROM NewsPost n WHERE n.title = :title")
    , @NamedQuery(name = "NewsPost.findByPostDate", query = "SELECT n FROM NewsPost n WHERE n.postDate = :postDate")})
public class NewsPost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "postID")
    private String postID;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "body")
    private String body;
    @Basic(optional = false)
    @Column(name = "postDate")
    @Temporal(TemporalType.DATE)
    private Date postDate;
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User userID;

    public NewsPost() {
    }

    public NewsPost(String postID) {
        this.postID = postID;
    }

    public NewsPost(String postID, String title, String body, Date postDate) {
        this.postID = postID;
        this.title = title;
        this.body = body;
        this.postDate = postDate;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postID != null ? postID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NewsPost)) {
            return false;
        }
        NewsPost other = (NewsPost) object;
        if ((this.postID == null && other.postID != null) || (this.postID != null && !this.postID.equals(other.postID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.NewsPost[ postID=" + postID + " ]";
    }
    
}
