package services;

import java.util.List;
import dataaccess.GameDB;
import models.Game;
import models.Team;

public class GameService { 
    
    public List<Game> getAll() {
        GameDB gdb = new GameDB();
        return gdb.getAll();
    }
    
    public List<Game> getByTeam(Team team) {
        GameDB gdb = new GameDB();
        return gdb.getByTeam(team);
    }
    
    public Game get(String gameID) {
        GameDB gdb = new GameDB();
        return gdb.get(gameID);
    }
}