package services;

import java.util.List;
import dataaccess.PlayerDB;
import dataaccess.TeamDB;
import models.Player;
import models.Position;
import models.Team;
import models.User;

/**
 * This class provides functionality such as retrieve, update, insert and delete for players
 * 
 */
public class PlayerService { 
    
    /**
     * This returns a list of all the players in the system
     * @return list of players in the system
     */
    public List<Player> getAll() {
        PlayerDB pdb = new PlayerDB();
        return pdb.getAll();
    }
    
    /**
     * This provides a list of players in the system ordered by their playerID
     * @return ordered list of players
     */
    public List<Player> getAllOrdered() {
        PlayerDB pdb = new PlayerDB();
        return pdb.getAllOrdered();
    }
    
    /**
     * This returns a player object that corresponds with the playerID being searched
     * @param playerID - identifier of the player object
     * @return player object found
     */
    public Player getByPlayerID(String playerID) {
        PlayerDB pdb = new PlayerDB(); 
        return pdb.getByPlayerID(playerID);
    }
    
    /**
     * This returns a list of player objects that corresponds with the userID being searched
     * @param userID - identifier of the user object that is in the player object
     * @return list of player objects found
     */
    public List<Player> getByUserID(String userID) {
        PlayerDB pdb = new PlayerDB(); 
        
        AccountService accService = new AccountService();
        User user = accService.getByUserID(userID);
        return pdb.getByUserID(user);
    }
    
    /**
     * This method inserts a new player into the systemS
     * @param user - user object for the player
     * @param team - the team which the player belongs
     * @param position - position of the player
     * @return player - newly created player
     */
    public Player insert(User user, Team team, Position position) {
        PlayerDB pdb = new PlayerDB();
        String playerID = generatePlayerID();
        Player player = new Player(playerID);
        
        player.setUserID(user);
        player.setTeamID(team);
        player.setPosition(position);
        
        pdb.insert(player);
        return player;
    }
    
    /**
     * This method updates a currently existing player object
     * @param playerID - playerId of the object
     * @param user - user object that corresponds with the player
     * @param team - team of the player
     * @param position - position of the player
     */
    public void update(String playerID, User user, Team team, Position position) {
        PlayerDB pdb = new PlayerDB();
        Player player = getByPlayerID(playerID);
        
        Team oldTeam = player.getTeamID();
        if (!oldTeam.getTeamID().equals(team.getTeamID())){
            oldTeam.getPlayerList().remove(player);
            TeamDB tdb = new TeamDB();
            tdb.update(oldTeam);
        }
        
        player.setUserID(user);
        player.setTeamID(team);
        player.setPosition(position);
        
        pdb.update(player);
    }
    
    /**
     * This method deletes a player from the database
     * @param playerID - playerId of the object
     */
    public void delete(String playerID) {
        PlayerDB pdb = new PlayerDB();
        Player player = getByPlayerID(playerID);
        pdb.delete(player);
    }
    
    /**
     * This generates a playerID for a new player object
     * @return playerID - the newly generated ID
     */
    public String generatePlayerID() {
        List<Player> players = getAllOrdered();
        int idNum;
        
        if (players == null || players.isEmpty())
            idNum = 0;
        else {
            String idStr = players.get(players.size() - 1).getPlayerID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }
        
        String playerID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            playerID = "P_000" + newIdNum;
        else if (newIdNum <= 99) 
            playerID = "P_00" + newIdNum;
        else if (newIdNum <= 999)
            playerID = "P_0" + newIdNum;
        else 
            playerID = "P_" + newIdNum;
        
        return playerID;
    }
}
