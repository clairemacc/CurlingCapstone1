package dataaccess;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.League;
import models.Spare;

public class SpareDB {
        
    public List<Spare> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Spare.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Spare> getAllOrdered() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createQuery("SELECT s FROM Spare s ORDER BY s.spareID").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Spare getBySpareID(String spareID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Spare.class, spareID);
        } finally {
            em.close();
        }
    }

    public Spare get(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Spare spare = null;
        try {
            spare = (Spare) em.createNamedQuery("Spare.findByEmail").setParameter("email",email).getSingleResult();
        } catch (Exception e) {
        } finally {
            em.close();
        }
        return spare;
    }
    
    public void insert(Spare spare) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            League league = spare.getLeagueID();
            if (league.getSpareList() == null) 
                league.setSpareList(new ArrayList<>());
            league.getSpareList().add(spare);
            
            eTr.begin();
            em.persist(spare);
            em.merge(league);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Spare spare) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.remove(em.merge(spare));
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
}
