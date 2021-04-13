package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
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
}
