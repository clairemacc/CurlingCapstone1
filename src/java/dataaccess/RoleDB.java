package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

/**
 * This class is used to access the roles of users within the application. A 
 * user can either be a player or an executive.
 * @author CurlingCapstone
 */
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
}