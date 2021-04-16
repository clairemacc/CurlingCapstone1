package dataaccess;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Player;
import models.Team;
import models.User;

/**
 * This class is used to access the information of a player within the 
 * application. It is also used for deleting, creating and editing of a player.
 * @author CurlingCapstone
 */
public class PlayerDB {
    
    public List<Player> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Player.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Player> getAllOrdered() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createQuery("SELECT p FROM Player p ORDER BY p.playerID").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Player getByPlayerID(String playerID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Player.class, playerID);
        } finally {
            em.close();
        }
    }
    
    public List<Player> getByUserID(User userID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return (List<Player>) em.createQuery("SELECT p FROM Player p WHERE p.userID = :userID").setParameter("userID", userID).getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    
    public void insert(Player player) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            User user = player.getUserID();
            
            if (user.getPlayerList() == null) 
                user.setPlayerList(new ArrayList<>());
            user.getPlayerList().add(player);
            
            Team team = player.getTeamID();
            if (team.getPlayerList() == null) 
                team.setPlayerList(new ArrayList<>());
            team.getPlayerList().add(player);
            
            eTr.begin();
            em.persist(player);
            em.merge(user);
            em.merge(team);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Player player) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            Team team = player.getTeamID();
            if (team.getPlayerList() == null) 
                team.setPlayerList(new ArrayList<>());
            
            if (!team.getPlayerList().contains(player)) 
                team.getPlayerList().add(player);
            
            eTr.begin();
            em.merge(player);
            em.merge(team);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Player player) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            System.out.println(player.getUserID().getContactID().getFirstName());
            Team team = player.getTeamID();
            team.getPlayerList().remove(player);
            
            User user = player.getUserID();
            user.getPlayerList().remove(player);
            
            eTr.begin();
            em.merge(team);
            em.merge(user);
            em.remove(em.merge(player));
            eTr.commit();
      //  } catch (Exception e) {
        //    eTr.rollback();
        } finally {
            em.close();
        }
    }
}
