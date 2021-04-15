package services;

import java.util.List;
import dataaccess.GameDB;
import java.util.Date;
import models.Game;
import models.League;
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
    
    public List<Game> getByLeague(League league) {
        GameDB gdb = new GameDB();
        return gdb.getByLeague(league);
    }
    
    public Game get(String gameID) {
        GameDB gdb = new GameDB();
        return gdb.get(gameID);
    }
    
    public void insert(Team homeTeam, Team awayTeam, Date date) {
        GameDB gdb = new GameDB();
        String gameID = generateGameID();
        Game game = new Game(gameID);
        
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setDate(date);
        
        gdb.insert(game);
    }
    
    public void delete(String gameID) {
        GameDB gdb = new GameDB();
        Game game = get(gameID);
        gdb.delete(game);
    }
    
    public String generateGameID() {
        List<Game> games = getAll();
        int idNum; 
        
        if (games == null || games.isEmpty()) 
            idNum = 0;
        else {
            String idStr = games.get(games.size() - 1).getGameID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }

        String gameID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            gameID = "G_000" + newIdNum;
        else if (newIdNum <= 99) 
            gameID = "G_00" + newIdNum;
        else if (newIdNum <= 999)
            gameID = "G_0" + newIdNum;
        else 
            gameID = "G_" + newIdNum;
        
        return gameID;        
    }
}