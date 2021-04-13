package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Role;

public class RoleDB {
    
    public List<Role> getAll() {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
       
       try {
           return em.createNamedQuery("Role.findAll").getResultList();
       } finally {
           em.close();
       }
    }
    
    public Role getByRoleID(int roleID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Role.class, roleID);
        } finally {
            em.close();
        }
    }
    
//    public List<User> getAllNotVerify() {
//       EntityManager em = DBUtil.getEmFactory().createEntityManager();
//       
//       try {
//           return em.createNamedQuery("User.findByNotVerify").getResultList();
//       } finally {
//           em.close();
//       }
//    }
//    
//    public boolean verifyUser(String id) {
//        int updateCount = 0;
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        try {
////            Query query = em.createNamedQuery("User.updateUserVerify");
////            query.setParameter("verify",1).setParameter("userID",id).getSingleResult();
//            em.getTransaction().begin();
//            Query query = em.createQuery("UPDATE User u SET u.verify= :verify WHERE u.userID= :userID");
//            query.setParameter("verify", 1);
//            query.setParameter("userID", id);
//            updateCount = query.executeUpdate();
//            em.getTransaction().commit();
//            
//        } finally {
//            em.close();
//        }
//        
//        return updateCount>0;
//    }
//    
//    public boolean changePassword(String email, String password){
//        int updateCount = 0;
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        try {
////            Query query = em.createNamedQuery("User.updateUserVerify");
////            query.setParameter("verify",1).setParameter("userID",id).getSingleResult();
//            em.getTransaction().begin();
//            Query query = em.createQuery("UPDATE User u SET u.password= :password WHERE u.email= :email");
//            query.setParameter("password", password);
//            query.setParameter("email", email);
//            updateCount = query.executeUpdate();
//            em.getTransaction().commit();
//            
//        } finally {
//            em.close();
//        }
//        
//        return updateCount>0;
//    }
//    
//    public List<User> getAllVerify() {
//       EntityManager em = DBUtil.getEmFactory().createEntityManager();
//       
//       try {
//           return em.createNamedQuery("User.findByVerify").getResultList();
//       } finally {
//           em.close();
//       }
//    }
//    
//    public User get (String userID) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        
//        try {
//            return em.find(User.class, userID);
//        } finally {
//            em.close();
//        }
//    }
//    
//    public User getByEmail (String email) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        User user = null;
//        try {
//            user = (User) em.createNamedQuery("User.findByEmail").setParameter("email",email).getSingleResult();
//        } catch (Exception e) {
//        } finally {
//            em.close();
//        }
//        return user;
//    }
}
