package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Contact;
import models.User;

public class UserDB {
    
    public List<User> getAll() {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
       
       try {
           return em.createNamedQuery("User.findAll").getResultList();
       } finally {
           em.close();
       }
    }
    
    public List<User> getAllOrdered() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createQuery("SELECT u FROM User u ORDER BY u.userID").getResultList();
        } finally {
            em.close();
        }
    }
    
    public User getByUserID(String userID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(User.class, userID);
        } finally {
            em.close();
        }
    }
    
    public User getByContactID (Contact contactID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = null;
        
        try {
            user = (User) em.createQuery("SELECT u FROM User u WHERE u.contactID = :contactID").setParameter("contactID", contactID).getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
        return user;
    }
    
    public User getByEmail (String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        User user = null;
        
        try {
            user = (User) em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
        return user;
    }
    
    public User getByResetUUID(String uuid) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return (User) em.createNamedQuery("User.findByResetUUID").setParameter("resetUUID", uuid).getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public void update(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.merge(user);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void setResetUUID(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.merge(user);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    
    public List<User> getAllNotVerify() {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
       
       try {
           return em.createNamedQuery("User.findByNotVerify").getResultList();
       } finally {
           em.close();
       }
    }
    
    public void insert(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.persist(user);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.remove(em.merge(user));
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    /*public boolean modifyUser(User user) {
        boolean result = false;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            User getExistUser = getByEmail(user.getContactID().getEmail());
            
            if((getExistUser!=null) && (!getExistUser.getUserID().equals(user.getUserID()))){
                result = false;
            }
            else{
                User userModify = em.find(User.class, user.getUserID());
                em.getTransaction().begin();
                em.merge(userModify);
                em.getTransaction().commit();
                result=true;
            }
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public boolean deleteUser(String id){
        boolean result = false;
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            User userDelete = em.find(User.class, id);
            
            if(userDelete==null){
                result = false;
            }
            else{
                em.getTransaction().begin();
                em.remove(userDelete);
                em.getTransaction().commit();
                result=true;
            }
        } finally {
            em.close();
        }
        
        return result;
    }

    public List<User> getAllVerify() {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
       
       try {
           return em.createNamedQuery("User.findByVerify").getResultList();
       } finally {
           em.close();
       }
    }*/
}
