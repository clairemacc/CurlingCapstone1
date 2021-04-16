package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.NewsPost;

public class NewsPostDB {
    public List<NewsPost> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("NewsPost.findAll").getResultList();
        } finally {
            em.close();
        }
    }
}
