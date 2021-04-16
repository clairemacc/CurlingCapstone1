package services;

import dataaccess.GameDB;
import dataaccess.ScoreDB;
import dataaccess.StandingDB;
import dataaccess.TeamDB;
import dataaccess.UserDB;
import java.util.Date;
import java.util.List;
import models.Game;
import models.User;
import models.Score;
import models.Standing;
import models.Team;

public class ScoreService {
    
    public List<Score> getAll() {
        ScoreDB sdb = new ScoreDB();
        return sdb.getAll();
    }
    
    public Score get(String gameID) {
        ScoreDB sdb = new ScoreDB();
        return sdb.get(gameID);
    }
    
    public void insert(String gameID, User submitter, int homeScore, int awayScore, String winner, Date today) {
        Score score = new Score(gameID, homeScore, awayScore, today);
        String loser = "";
        
        StandingService standService = new StandingService();
        
        GameDB gdb = new GameDB();
        Game game = gdb.get(gameID);
        score.setGame(game);
        
        String homeTeamID = game.getHomeTeam().getTeamID();
        String awayTeamID = game.getAwayTeam().getTeamID();
        
        if (!winner.equals("tie")) {
            TeamDB tdb = new TeamDB();
            score.setWinner(tdb.get(winner));
            
            if (winner.equals(homeTeamID)) 
                loser = awayTeamID;
            else if (winner.equals(awayTeamID)) 
                loser = homeTeamID;

            standService.update(winner, 'w');
            standService.update(loser, 'l');
        }
        else {
            standService.update(homeTeamID, 't');
            standService.update(awayTeamID, 't');
        }
        
        score.setSubmitter(submitter);
                
        ScoreDB sdb = new ScoreDB();
        sdb.insert(score);
    }
    
    public void delete(String gameID) {
        ScoreDB sdb = new ScoreDB();
        Score score = get(gameID);
        Game game = score.getGame();
        String homeTeam = game.getHomeTeam().getTeamID();
        String awayTeam = game.getAwayTeam().getTeamID();
        
        int gamesWon;
        int gamesLost;
        int gamesPlayed;
        
        StandingService ss = new StandingService();
        for (Standing s : ss.getAll()) {
            if (s.getTeamID().equals(homeTeam) || s.getTeamID().equals(awayTeam)) {
                gamesWon = s.getGamesWon();
                gamesLost = s.getGamesLost();
                gamesPlayed = s.getGamesPlayed() - 1;
                
                if (score.getWinner() != null) {
                    if (s.getTeamID().equals(score.getWinner().getTeamID()))
                        gamesWon = gamesWon - 1;
                    else 
                        gamesLost = gamesLost - 1;
                }
                
                ss.updateGames(s.getTeamID(), gamesWon, gamesLost, gamesPlayed);
            }
        }
        
        sdb.delete(score);
    }
}
