package services;


import dataaccess.ExecutiveDB;
import dataaccess.LeagueDB;
import dataaccess.SpareDB;
import dataaccess.TeamDB;
import java.util.ArrayList;
import java.util.List;
import models.Executive;
import models.League;
import models.Spare;
import models.Team;

/**
 * Provides operations such as retrieve, insert, update and delete pertaining to leagues
 * 
 */
public class LeagueService {
    
    /**
     * This returns a list of of the leagues
     * @return list of leagues
     */
    public List<League> getAll() {
        LeagueDB ldb = new LeagueDB();
        return ldb.getAll();
    }
    
    /**
     * This method returns a league object corresponding to the leagueID being searched
     * @param leagueID - identifier for a particular league 
     * @return league object found
     */
    public League getByLeagueID(String leagueID) {
        LeagueDB ldb = new LeagueDB();
        return ldb.getByLeagueID(leagueID);
    }
    
    /**
     * This returns a league that corresponds with weekday provided
     * @param weekday - weekday being searched
     * @return league object found
     */
    public League getByWeekday(String weekday) {
        LeagueDB ldb = new LeagueDB();
        return ldb.getByWeekday(weekday);
    }
    
    /**
     * This inserts a new league into the system.
     * @param weekday - the weekday of the new leeague
     */
    public void insert(String weekday) {
        LeagueDB ldb = new LeagueDB();
        String leagueID = generateLeagueID();
        
        League league = new League(leagueID, weekday);
        ldb.insert(league);
    }
    
    /**
     * This updates an existing league in the system.
     * @param leagueID - the id of the league to be updated
     * @param weekday - weekday of the league
     * @param executiveList - list of executives for that league
     * @param teamList - list of teams for the league
     * @param spareList  - list of the spares for the league
     */
    public void update(String leagueID, String weekday, List<Executive> executiveList, List<Team> teamList, List<Spare> spareList) {
        LeagueDB ldb = new LeagueDB();
        League league = getByLeagueID(leagueID);
        
        league.setWeekday(weekday);
        league.setExecutiveList(executiveList);
        league.setTeamList(teamList);
        league.setSpareList(spareList);
        
        ldb.update(league);
    }
    
    /**
     * This removes a league from the database
     * @param leagueID - id for the league to be removed
     */
    public void delete(String leagueID) {
        LeagueDB ldb = new LeagueDB();
        League league = getByLeagueID(leagueID);
        
        ExecutiveDB edb = new ExecutiveDB();
        TeamService ts = new TeamService();
        SpareDB sdb = new SpareDB();
        
        ArrayList<String> eids = new ArrayList<>();
        ArrayList<String> tids = new ArrayList<>();
        ArrayList<String> sids = new ArrayList<>();
        
        for (Executive e : league.getExecutiveList()) 
            eids.add(e.getUserID());
        for (Team t : league.getTeamList()) 
            tids.add(t.getTeamID());
        for (Spare s : league.getSpareList()) 
            sids.add(s.getSpareID());

        league.getExecutiveList().clear();
        league.getTeamList().clear();
        league.getSpareList().clear();

        for (String s : eids) 
            edb.delete(edb.get(s));
        for (String s : tids) 
            ts.delete(s);
        for (String s : sids) 
            sdb.delete(sdb.getBySpareID(s));
        
        ldb.delete(league);
    }
    
    /**
     * This method generates a leagueID for a new league.
     * @return leagueID - newly generated ID
     */
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
