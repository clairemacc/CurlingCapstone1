package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Game;
import models.Team;

public class GameDB {
    
    public List<Game> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Game.findAll").getResultList();
        } finally {
            em.close();
        }
    }

    public List<Game> getByTeam(Team team) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createQuery("SELECT g FROM Game g WHERE g.homeTeam = '" + team.getTeamID() + "' OR g.awayTeam = '" + team.getTeamID()).getResultList();
        } finally {
            em.close();
        }
    }
    
    public Game get(String gameID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Game.class, gameID);
        } finally {
            em.close();
        }
    }
    
    public void delete(Game game) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            eTr.begin();
            em.remove(em.merge(game));
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
}
