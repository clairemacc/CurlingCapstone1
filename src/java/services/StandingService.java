package services;

import dataaccess.StandingDB;
import dataaccess.TeamDB;
import java.util.List;
import models.Standing;
import models.Team;

/**
 * This class provides functionality such as retrieve, insert, update and delete for the standing object
 * @author CurlingCapston
 */
public class StandingService {
    
     /**
     * This returns a list of all the standings in the system
     * @return list of standings in the system
     */
    public List<Standing> getAll() {
        StandingDB stdb = new StandingDB();
        return stdb.getAll();
    }
    
     /**
     * This returns a standing object that corresponds with the teamID being searched
     * @param teamID - identifier of the team object used in search
     * @return standing object found
     */
    public Standing get(String teamID) {
        StandingDB stdb = new StandingDB();
        return stdb.get(teamID);
    }
    
    /**
     * This inserts a new Standing into the system
     * @param teamID - identifier of the team object
     * @param wlt - win, loss, or tie
     */
    public void insert(String teamID, char wlt) {
        Standing standing = new Standing(teamID);
        standing.setGamesPlayed(standing.getGamesPlayed() + 1);
        
        TeamDB tdb = new TeamDB();
        Team team = tdb.get(teamID);
        
        standing.setTeam(team);
        
        switch (wlt) {
            case 'w':
                standing.setGamesWon(standing.getGamesWon() + 1);
                break;
            case 'l':
                standing.setGamesLost(standing.getGamesLost() + 1);
                break;
            case 't':
                break;
        }
        
        StandingDB stdb = new StandingDB();
        stdb.insert(standing);
    }
    
    /**
     * This updates an existing Standing for a particular team
     * @param teamID - identifier of the team object
     * @param wlt - win, loss or tie
     */
    public void update(String teamID, char wlt) {
        StandingDB stdb = new StandingDB();
        Standing standing = stdb.get(teamID);
        standing.setGamesPlayed(standing.getGamesPlayed() + 1);
        
        switch (wlt) {
            case 'w':
                standing.setGamesWon(standing.getGamesWon() + 1);
                break;
            case 'l':
                standing.setGamesLost(standing.getGamesLost() + 1);
                break;
            case 't':
                break;
        }
        
        stdb.update(standing);
    }
    
    /**
     * This updates a standing in the system
     * @param teamID - identifier of the team object
     * @param gamesWon - number of games won for the team
     * @param gamesLost - number of games lost for the team
     * @param gamesPlayed - number of games played fort the team
     */
    public void updateGames(String teamID, int gamesWon, int gamesLost, int gamesPlayed) {
        StandingDB stdb = new StandingDB();
        Standing standing = stdb.get(teamID);
        
        standing.setGamesWon(gamesWon);
        standing.setGamesLost(gamesLost);
        standing.setGamesPlayed(gamesPlayed);
        
        stdb.update(standing);
    }
}

