package servlets;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Game;
import models.Player;
import models.Score;
import models.Team;
import models.User;
import services.GameService;
import services.ScoreService;

/**
 * This servlet will be used to submit game results for a past game played by
 * the team of the user logged in. This servlet is only accessed once a user 
 * logs in. After game results have been submitted the game will no longer
 * appear in the list of available scores to submit.
 * @author CurlingCapstone
 */
public class SubmitGameResultsServlet extends HttpServlet {
    
    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        long date = System.currentTimeMillis();
        Date today = new Date(date);
        session.setAttribute("today", today);
        
        GameService gameService = new GameService();
        List<Player> playerList = user.getPlayerList();
        List<Team> playerTeams = new ArrayList<>();
        
        List<Game> playerGames = new ArrayList<>();
        
        if (user.getRole().getRoleID() == 2) {
            for (Player p : playerList) 
                playerTeams.add(p.getTeamID());

            for (Team t : playerTeams) {
                for (Game g : t.getGameList()) {
                    if (g.getDate().before(today))
                        playerGames.add(g); 
               }

                for (Game g1 : t.getGameList1()) {
                    if (g1.getDate().after(today))
                        playerGames.add(g1);
                }
            }
        }
        else if (user.getRole().getRoleID() == 1) {
            for (Game g : gameService.getAll()) {
                if (g.getDate().before(today))
                    playerGames.add(g);
            }
        }

        ScoreService scoreService = new ScoreService();
        List<Score> scores = scoreService.getAll();

        for(int i = 0; i < playerGames.size(); i++){
            for (int j = 0; j < scores.size(); j++){
                if (playerGames.get(i).getGameID().equals(scores.get(j).getGameID()))
                    playerGames.remove(i);
            }
        }
        
        if (playerGames.isEmpty()) 
            request.setAttribute("noGames", true);
        
        session.setAttribute("games", playerGames);

        String gameSelect = "";
        Game game = null;
        boolean invalid = false;
        String message = "";
        String display = "";

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("confirmGame")) {
                gameSelect = request.getParameter("gameSelect");

                if (gameSelect == null || gameSelect.equals("")) {
                    invalid = true;
                    message = "nullGame";
                } else {
                    game = gameService.get(gameSelect);

                    request.setAttribute("selectedGame", gameSelect);
                    request.setAttribute("game", game);
                    display = "displayEnterScore";
                }
            } else if (action.equals("confirmScore")) {
                String cancel = request.getParameter("cancelButton");
                if (cancel == null) {
                    gameSelect = request.getParameter("scoreConfirm");
                    game = gameService.get(gameSelect);

                    String winner = "";

                    String homeScoreStr = request.getParameter("homeScore");
                    String awayScoreStr = request.getParameter("awayScore");

                    if (homeScoreStr == null || homeScoreStr.equals("") || awayScoreStr == null || awayScoreStr.equals("")) {
                        invalid = true;
                        message = "nullScore";
                    } else {
                        int homeScore = 0;
                        int awayScore = 0;

                        try {
                            homeScore = Integer.parseInt(homeScoreStr);
                            awayScore = Integer.parseInt(awayScoreStr);

                            if (homeScore < 0 || awayScore < 0) {
                                invalid = true;
                                message = "negativeScore";
                            } else {
                                if (homeScore > awayScore) {
                                    request.setAttribute("winner", game.getHomeTeam().getTeamName());
                                    winner = game.getHomeTeam().getTeamID();
                                } else if (homeScore < awayScore) {
                                    request.setAttribute("winner", game.getAwayTeam().getTeamName());
                                    winner = game.getAwayTeam().getTeamID();
                                } else if (homeScore == awayScore) {
                                    request.setAttribute("winner", "Tie");
                                    winner = "tie";
                                }

                                session.setAttribute("winnerID", winner);

                                request.setAttribute("homeTeamFinal", game.getHomeTeam().getTeamName());
                                session.setAttribute("homeScoreFinal", homeScoreStr);

                                request.setAttribute("awayTeamFinal", game.getAwayTeam().getTeamName());
                                session.setAttribute("awayScoreFinal", awayScoreStr);
                                
                                request.setAttribute("submitter", user);
                                display = "displayReview";
                            }
                        } catch (NumberFormatException e) {
                            invalid = true;
                            message = "scoreNAN";
                        }
                    }
                }
                else 
                    display = null;
                
                if (invalid) 
                    display = "displayEnterScore";
            }
            
            request.setAttribute("selectedGame", gameSelect);
            session.setAttribute("game", game);
            request.setAttribute("message", message);
            request.setAttribute("display", display);
        }
        
        request.setAttribute("userName", user.getContactID().getFirstName() + " " + user.getContactID().getLastName());

        getServletContext().getRequestDispatcher("/WEB-INF/submitGameResults.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String cancel = request.getParameter("cancelButton");
        String goBack = request.getParameter("goBackButton");

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("submitScoreFinal")) {
                if (cancel == null) {
                    if (goBack == null) {
                        Game game = (Game) session.getAttribute("game");
                        String gameID = game.getGameID();

                        int homeScore = Integer.parseInt((String) session.getAttribute("homeScoreFinal"));
                        int awayScore = Integer.parseInt((String) session.getAttribute("awayScoreFinal"));
                        String winner = (String) session.getAttribute("winnerID");
                        
                        User user = (User) session.getAttribute("user");
                        
                        long timeMillis = System.currentTimeMillis();
                        Date today = new Date(timeMillis);
                        
                        ScoreService scoreService = new ScoreService();
                        scoreService.insert(gameID, user, homeScore, awayScore, winner, today);
                        
                        request.setAttribute("score", scoreService.get(gameID));
                        request.setAttribute("display", "displaySubmitComplete");
                    } else {
                        request.setAttribute("display", "displayEnterScore");
                    }
                }
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/submitGameResults.jsp").forward(request, response);
    }
}
