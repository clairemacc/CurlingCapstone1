package services;


import dataaccess.ExecutiveDB;
import dataaccess.LeagueDB;
import dataaccess.TeamDB;
import java.util.List;
import models.Executive;
import models.League;
import models.Spare;
import models.Team;

public class LeagueService {
    public List<League> getAll() {
        LeagueDB ldb = new LeagueDB();
        return ldb.getAll();
    }
    
    public League getByLeagueID(String leagueID) {
        LeagueDB ldb = new LeagueDB();
        return ldb.getByLeagueID(leagueID);
    }
    
    public League getByWeekday(String weekday) {
        LeagueDB ldb = new LeagueDB();
        return ldb.getByWeekday(weekday);
    }
    
    public void insert(String weekday) {
        LeagueDB ldb = new LeagueDB();
        String leagueID = generateLeagueID();
        
        League league = new League(leagueID, weekday);
        ldb.insert(league);
    }
    
    public void update(String leagueID, String weekday, List<Executive> executiveList, List<Team> teamList, List<Spare> spareList) {
        LeagueDB ldb = new LeagueDB();
        League league = getByLeagueID(leagueID);
        
        league.setWeekday(weekday);
        league.setExecutiveList(executiveList);
        league.setTeamList(teamList);
        league.setSpareList(spareList);
        
        ldb.update(league);
    }
    
    public void delete(String leagueID) {
        LeagueDB ldb = new LeagueDB();
        /*
        ExecutiveDB edb = new ExecutiveDB();
        TeamDB tdb = new TeamDB();
        ArrayList<String> pids = new ArrayList<>();
        ArrayList<String> gids = new ArrayList<>();
        
        List<Player> playerList = team.getPlayerList();
        for (Player p : playerList) {
            pids.add(p.getPlayerID());
        }
        
        List<Game> gameList = team.getGameList();
        List<Game> gameList1 = team.getGameList1();
        
        for (Game g : gameList) 
            gids.add(g.getGameID());
        
        for (Game g : gameList1) 
            gids.add(g.getGameID());
        
        for (String s : pids) 
            pdb.delete(pdb.getByPlayerID(s));
        
        for (String s : gids)
            gdb.delete(gdb.get(s));
        
        
        tdb.delete(team);*/
    }
    
    public String generateLeagueID() {
        List<League> leagues = getAll();
        String idStr = leagues.get(leagues.size() - 1).getLeagueID();
        int idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        
        String leagueID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            leagueID = "L_0" + newIdNum;
        else 
            leagueID = "T_" + newIdNum;
        
        return leagueID;
    }
    
}
