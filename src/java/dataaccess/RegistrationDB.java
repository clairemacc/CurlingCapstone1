package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Registration;

public class RegistrationDB {
    
    public List<Registration> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Registration.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Registration> getAllActive() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Registration.findByIsActive").setParameter("isActive", true).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Registration> getByGroup(String groupID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Registration.findByGroupID").setParameter("groupID", groupID).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Registration getByContactID(String contactID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Registration.class, contactID);
        } finally {
            em.close();
        }
    }

    public boolean insert(Registration registration) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        boolean success = false;
        
        try {
            eTr.begin();
            em.persist(registration);
            eTr.commit();
            success = true;
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
        return success;
    }
    
    public void update(Registration registration) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.merge(registration);
            eTr.commit();
        //} catch (Exception e) {
          //  eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Registration registration) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            registration = em.merge(registration);
            em.remove(registration);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
}
