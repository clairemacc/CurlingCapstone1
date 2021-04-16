package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Contact;

/**
 * This class is used to gather the contact information of database contacts. 
 * @author CurlingCapstone
 */
public class ContactDB {
    
    
    public List<Contact> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Contact.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Contact> getAllSorted(String orderBy) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            if (orderBy != null && !orderBy.equals("")) 
                return em.createQuery("SELECT c FROM Contact c ORDER BY c." + orderBy).getResultList();
            else 
                return null;
        }
        finally {
            em.close();
        }
    }
    
    public Contact getByContactID(String contactID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Contact.class, contactID);
        } finally {
            em.close();
        }
    }
    
    public Contact getByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List list = em.createNamedQuery("Contact.findByEmail").setParameter("email", email).getResultList();
            
            int count = 0;
            for (Object o : list) 
                count++;
            
            if (count == 0 || count > 1) {
                throw new Exception();
            }
            return (Contact) list.get(0);
            
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void insert(Contact contact) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.persist(contact);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Contact contact) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.merge(contact);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
}
