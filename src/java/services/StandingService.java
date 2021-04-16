package services;

import dataaccess.StandingDB;
import dataaccess.TeamDB;
import java.util.List;
import models.Standing;
import models.Team;

public class StandingService {
    
    public List<Standing> getAll() {
        StandingDB stdb = new StandingDB();
        return stdb.getAll();
    }
    
    public Standing get(String teamID) {
        StandingDB stdb = new StandingDB();
        return stdb.get(teamID);
    }
    
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
    
    public void updateGames(String teamID, int gamesWon, int gamesLost, int gamesPlayed) {
        StandingDB stdb = new StandingDB();
        Standing standing = stdb.get(teamID);
        
        standing.setGamesWon(gamesWon);
        standing.setGamesLost(gamesLost);
        standing.setGamesPlayed(gamesPlayed);
        
        stdb.update(standing);
    }
}

