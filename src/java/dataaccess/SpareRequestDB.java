package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.SpareRequest;

public class SpareRequestDB {
    
    public SpareRequest get(String requestID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(SpareRequest.class, "requestID");
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
}
