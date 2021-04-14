package services;

import dataaccess.GameDB;
import dataaccess.LeagueDB;
import dataaccess.PlayerDB;
import dataaccess.StandingDB;
import dataaccess.TeamDB;
import java.util.ArrayList;
import java.util.List;
import models.Game;
import models.League;
import models.Player;
import models.Standing;
import models.Team;

public class TeamService {
    public List<Team> getAll() {
        TeamDB tdb = new TeamDB();
        List<Team> teams = tdb.getAll();
        for (Team t : teams) {
            String teamName = t.getTeamName();
            teamName = teamName.replace("'", "\\\'");
            t.setTeamName(teamName);
            System.out.println(t.getTeamName());
        }
        return teams;
    }
    
    public List<Team> getAllByLeague(League leagueID) {
        TeamDB tdb = new TeamDB();
        List<Team> teams = tdb.getAllByLeague(leagueID);
        for (Team t : teams) {
            String teamName = t.getTeamName();
            teamName = teamName.replace("'", "\\\'");
            t.setTeamName(teamName);
            System.out.println(t.getTeamName());
        }
        return teams;
    }
    
    public List<Team> getAllOrdered() {
        TeamDB tdb = new TeamDB();
        return tdb.getAllOrdered();
    }
    
    public Team get(String teamID) {
        TeamDB tdb = new TeamDB();
        return tdb.get(teamID);
    }
    
    public Team insert(League league, String teamName) {
        StandingDB sdb = new StandingDB();
        TeamDB tdb = new TeamDB();
        String teamID = generateTeamID();
        
        Standing standing = new Standing(teamID);
        
        Team team = new Team(teamID, teamName);
        team.setLeagueID(league);
        
        tdb.insert(team);
        sdb.insert(standing);
        return team;
    }
    
    public void update(String teamID, String teamName, League league, List<Player> playerList, List<Game> gameList, List<Game> gameList1) {
        TeamDB tdb = new TeamDB();
        Team team = get(teamID);
        
        League oldLeague = team.getLeagueID();
        if (!oldLeague.getLeagueID().equals(league.getLeagueID())) {
            oldLeague.getTeamList().remove(team);
            LeagueDB ldb = new LeagueDB();
            ldb.update(oldLeague);
        }
        
        team.setTeamName(teamName);
        team.setPlayerList(playerList);
        team.setLeagueID(league);
        tdb.update(team);
    }
    
    public void delete(String teamID) {
        Team team = get(teamID);
        
        TeamDB tdb = new TeamDB();
        PlayerDB pdb = new PlayerDB();
        GameDB gdb = new GameDB();
        
        ArrayList<String> pids = new ArrayList<>();
        List<String> gids = new ArrayList<>();
        
        List<Player> playerList = team.getPlayerList();   
        
        for (Player p : playerList) 
            pids.add(p.getPlayerID());
        
        for (String s : pids) 
            pdb.delete(pdb.getByPlayerID(s));

        for (Game g : team.getGameList()) 
            gids.add(g.getGameID());
        for (Game g : team.getGameList1())
            gids.add(g.getGameID());
        
        team.getGameList().clear();
        team.getGameList1().clear();
        
        for (String s : gids)
            gdb.delete(gdb.get(s));
        
        tdb.delete(team);
    }
    
    public String generateTeamID() {
        List<Team> teams = getAllOrdered();
        String idStr = teams.get(teams.size() - 1).getTeamID();
        int idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        
        String teamID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            teamID = "T_000" + newIdNum;
        else if (newIdNum <= 99) 
            teamID = "T_00" + newIdNum;
        else if (newIdNum <= 999)
            teamID = "T_0" + newIdNum;
        else 
            teamID = "T_" + newIdNum;
        
        return teamID;
    }
}
