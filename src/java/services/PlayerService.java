package services;

import java.util.List;
import dataaccess.PlayerDB;
import dataaccess.TeamDB;
import models.Player;
import models.Position;
import models.Team;
import models.User;

public class PlayerService { 
    
    public List<Player> getAll() {
        PlayerDB pdb = new PlayerDB();
        return pdb.getAll();
    }
    
    public List<Player> getAllOrdered() {
        PlayerDB pdb = new PlayerDB();
        return pdb.getAllOrdered();
    }
    
    public Player getByPlayerID(String playerID) {
        PlayerDB pdb = new PlayerDB(); 
        return pdb.getByPlayerID(playerID);
    }
    
    public List<Player> getByUserID(String userID) {
        PlayerDB pdb = new PlayerDB(); 
        
        AccountService accService = new AccountService();
        User user = accService.getByUserID(userID);
        return pdb.getByUserID(user);
    }
    
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
    
    public void delete(String playerID) {
        PlayerDB pdb = new PlayerDB();
        Player player = getByPlayerID(playerID);
        pdb.delete(player);
    }
    
    
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
