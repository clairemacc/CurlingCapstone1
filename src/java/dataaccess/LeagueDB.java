package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.League;

/**
 * This class is used to access the information of leagues within the 
 * application.
 * @author CurlingCapstone
 */
public class LeagueDB {
    public List<League> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("League.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public League getByLeagueID (String leagueID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(League.class, leagueID);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public League getByWeekday (String weekday) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List list = em.createNamedQuery("League.findByWeekday").setParameter("weekday", weekday).getResultList();
            
            int count = 0;
            for (Object o : list) 
                count++;
            
            if (count == 0 || count > 1)
                throw new Exception();
            
            return (League) list.get(0);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void insert(League league) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.persist(league);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(League league) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.merge(league);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(League league) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            league = em.merge(league);
            em.remove(league);
            eTr.commit();
        //} catch (Exception e) {
          //  eTr.rollback();
        } finally {
            em.close();
        }
    }
}
