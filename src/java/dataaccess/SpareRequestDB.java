package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.SpareAssigned;
import models.SpareRequest;

public class SpareRequestDB {
    
    public SpareRequest get(String requestID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(SpareRequest.class, requestID);
        } finally {
            em.close();
        }
    }
    
    public SpareAssigned getSpAssignedByID(String requestID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(SpareAssigned.class, requestID);
        } finally {
            em.close();
        }
    }
    
    public List<SpareRequest> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("SpareRequest.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<SpareRequest> getAllActive() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("SpareRequest.findByIsActive").setParameter("isActive", true).getResultList();
        } finally {
            em.close();
        }
    }
    
    public void insert(SpareRequest spRequest) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.persist(spRequest);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public boolean insertSpareAssigned(SpareAssigned spAssigned) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        boolean valid = false;
        try {
            eTr.begin();
            em.persist(spAssigned);
            eTr.commit();
            valid = true;
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
        return valid;
    }
    
    public void update(SpareRequest spRequest) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.merge(spRequest);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void deleteSpareAssigned(SpareAssigned spAssigned) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.remove(em.merge(spAssigned));
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
}
