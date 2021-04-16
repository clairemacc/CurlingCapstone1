package dataaccess;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.League;
import models.Team;

/**
 * This class allows the application to create, edit, delete and access teams
 * within the database.
 * @author CurlingCapstone
 */
public class TeamDB {
    
    public List<Team> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createNamedQuery("Team.findAll").getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Team> getAllByLeague(League leagueID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createQuery("SELECT t FROM Team t WHERE t.leagueID = :leagueID").setParameter("leagueID", leagueID).getResultList();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public List<Team> getAllOrdered() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.createQuery("SELECT t FROM Team t ORDER BY t.teamID").getResultList();
        } finally {
            em.close();
        }
    }
    
    public Team get(String teamID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            return em.find(Team.class, teamID);
        } finally {
            em.close();
        }
    }
    
    public void insert(Team team) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            League league = team.getLeagueID();
            if (league.getTeamList() == null)
                league.setTeamList(new ArrayList<>());
            
            league.getTeamList().add(team);
            
            eTr.begin();
            em.persist(team);
            em.merge(league);
            eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(Team team) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            League league = team.getLeagueID();
            if (league.getTeamList() == null) 
                league.setTeamList(new ArrayList<>());
            
            if (!league.getTeamList().contains(team))
                league.getTeamList().add(team);

            eTr.begin();
            em.merge(league);
            em.merge(em.merge(team));
            eTr.commit();
        //} catch (Exception e) {
          //  eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    public void delete(Team team) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction eTr = em.getTransaction();
        
        try {
            League league = team.getLeagueID();
            league.getTeamList().remove(team);

            eTr.begin();
            em.merge(league);
            em.remove(em.merge(team));
           eTr.commit();
        } catch (Exception e) {
            eTr.rollback();
        } finally {
            em.close();
        }
    }
    
    //when inserting new team, make sure to insert new standing as well
}


