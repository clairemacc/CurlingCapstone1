package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.League;
import models.Position;

public class PositionDB {
    public List<Position> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Position.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Position getByPosID(int positionID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Position.class, positionID);
        } finally {
            em.close();
        }
    }
    
    public Position getByPosName (String positionName) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            List list = em.createNamedQuery("Position.findByPositionName").setParameter("positionName", positionName).getResultList();
            
            int count = 0;
            for (Object o : list) 
                count++;
            
            if (count == 0 || count > 1)
                throw new Exception();
            
            return (Position) list.get(0);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
