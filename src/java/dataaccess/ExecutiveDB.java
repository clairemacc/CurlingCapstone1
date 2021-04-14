package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Executive;

public class ExecutiveDB {
    
    public List<Executive> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Executive.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Executive get(String userID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Executive.class, userID);
        } finally {
            em.close();
        }
    }
    
    public void delete(Executive executive) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.remove(em.merge(executive));
            eTr.commit();
        //} catch (Exception e) {
          //  eTr.rollback();
        } finally {
            em.close();
        }
    }
}
