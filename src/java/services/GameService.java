package services;

import java.util.List;
import dataaccess.GameDB;
import java.util.Date;
import models.Game;
import models.League;
import models.Team;

/**
 * This class provides functionality such as retrieve, insert, update and delete for the game objects.
 * 
 */
public class GameService { 
    
    /**
     * This returns a list of all the games currently recorded in the database.
     * @return list of games
     */
    public List<Game> getAll() {
        GameDB gdb = new GameDB();
        return gdb.getAll();
    }
    
    /**
     * This returns the list of games of a particular team
     * @param team - team object representing a team in the curling league
     * @return list of games for that team
     */
    public List<Game> getByTeam(Team team) {
        GameDB gdb = new GameDB();
        return gdb.getByTeam(team);
    }
    
    /**
     * This returns the list of games of a particular league 
     * @param league - a particular league provided by the user
     * @return list of games played in that league
     */
    public List<Game> getByLeague(League league) {
        GameDB gdb = new GameDB();
        return gdb.getByLeague(league);
    }
    
    /**
     * This method returns a game object that corresponds with the gameID being searched.
     * @param gameID - unqiue ID of a game; ID being searched
     * @return game object found
     */
    public Game get(String gameID) {
        GameDB gdb = new GameDB();
        return gdb.get(gameID);
    }
    
    /**
     * This method inserts a new game into the database.
     * @param homeTeam - team object representing the home team for the game.
     * @param awayTeam - team object representing the away team for the game.
     * @param date - date object representing the date of the game.
     */
    public void insert(Team homeTeam, Team awayTeam, Date date) {
        GameDB gdb = new GameDB();
        String gameID = generateGameID();
        Game game = new Game(gameID);
        
        game.setHomeTeam(homeTeam);
        game.setAwayTeam(awayTeam);
        game.setDate(date);
        
        gdb.insert(game);
    }
    
    /**
     * This method deletes a game from the database.
     * @param gameID - gameID for the game to be deletec
     */
    public void delete(String gameID) {
        GameDB gdb = new GameDB();
        Game game = get(gameID);
        gdb.delete(game);
    }
    
    /**
     * Generates a gameID for a new game
     * @return gameID - newly generated gameID
     */
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