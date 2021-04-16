package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Score;

/**
 * This class is used to access the score of games within the database and make
 * it accessible elsewhere in the application.
 * @author CurlingCapstone
 */
public class ScoreDB {
    
    public List<Score> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Score.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Score get(String gameID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Score.class, gameID);
        } finally {
            em.close();
        }
    }
    
    public void insert(Score score) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.persist(score);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
       
    }
    
    public void delete(Score score) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.remove(em.merge(score));
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
}
