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
import models.League;
import models.Player;
import models.Position;
import models.Spare;
import models.Team;
import models.User;
import services.AccountService;
import services.GameService;
import services.LeagueService;
import services.PlayerService;
import services.PositionService;
import services.SpareService;
import services.TeamService;

/**
 *
 * @author 818736
 */
public class SpareRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        session.removeAttribute("nullValue");
        
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
                    if (g.getDate().after(today))
                        playerGames.add(g); 
               }

                for (Game g1 : t.getGameList1()) {
                    if (g1.getDate().after(today))
                        playerGames.add(g1);
                }
            }
        }
        else if (user.getRole().getRoleID() == 1) {
            session.setAttribute("isAdmin", true);
            TeamService teamService = new TeamService();
            playerTeams = teamService.getAll();
            
            for (Game g : gameService.getAll()) {
                if (g.getDate().after(today)) 
                    playerGames.add(g);
            }
            
            session.setAttribute("teams", playerTeams);
        }
        
        session.setAttribute("playerGames", playerGames);
       

        PositionService positionService = new PositionService();
        List<Position> positions = positionService.getAll();
        session.setAttribute("positions", positions);

        request.setAttribute("differentTeam", false);
        session.setAttribute("review", 1);

        getServletContext().getRequestDispatcher("/WEB-INF/spareRequest.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        
        if (action.equals("review")) {
            String position = request.getParameter("positionSelect");
            String teamID = request.getParameter("teamSelect");
            String gameID = request.getParameter("gameSelect");
            
            if(position == null || position.equals("") || teamID == null || teamID.equals("") || gameID == null || gameID.equals("")){
                session.setAttribute("review", 1);
                request.setAttribute("nullValue", true);
                getServletContext().getRequestDispatcher("/WEB-INF/spareRequest.jsp").forward(request, response);
                return;
            }
            
            GameService gs = new GameService();
            Game gameChosen = gs.get(gameID);

            TeamService ts = new TeamService();
            Team teamChosen = ts.get(teamID);

            
            if (!teamID.equals(gameChosen.getHomeTeam().getTeamID()) && !teamID.equals(gameChosen.getAwayTeam().getTeamID())) {
                session.setAttribute("differentTeam", true);
                session.setAttribute("review", 1);
            } else {
                PositionService ps = new PositionService();
                Position positionChosen = ps.getByPosID(Integer.parseInt(position));
               
                LeagueService ls = new LeagueService();
                 League league = null;
                
                if(gameChosen.getDate().getDay() == 2){
                   league = ls.getByWeekday("tuesday");
                }
                else if(gameChosen.getDate().getDay() == 4){
                    league = ls.getByWeekday("thursday");
                }
                
                session.setAttribute("league", league);
                session.setAttribute("position", positionChosen);
                session.setAttribute("game", gameChosen);
                session.setAttribute("team", teamChosen);
                session.setAttribute("review", 2);
            }

            getServletContext().getRequestDispatcher("/WEB-INF/spareRequest.jsp").forward(request, response);
        } else {
            SpareService ss = new SpareService();
            List<Spare> spares = ss.getAll();
            Game gameChosen = (Game) session.getAttribute("game");
            Position positionChosen = (Position) session.getAttribute("position");
            Team teamChosen = (Team) session.getAttribute("team");
            
            List<String> emails = new ArrayList<>();
            for (int i = 0; i < spares.size(); i++) {
                if(spares.get(i).getPosition().getPositionID().equals(positionChosen.getPositionID()) || spares.get(i).getFlexibleP() == true) {
                    if (!emails.contains(spares.get(i).getContactID().getEmail())) 
                        emails.add(spares.get(i).getContactID().getEmail());
                }
            }
            
            String path = getServletContext().getRealPath("/WEB-INF");

            Date today = (Date) session.getAttribute("today");
            String date = today.toString();

            String gameDate = gameChosen.getDate().toString();
            String homeTeam = gameChosen.getHomeTeam().getTeamName();
            String awayTeam = gameChosen.getAwayTeam().getTeamName();
            String team = teamChosen.getTeamName();
            String position = positionChosen.getPositionName();

            ss.emailSpares(emails, path, date, gameDate, homeTeam, awayTeam, team, position);
            request.setAttribute("review", 3);
            getServletContext().getRequestDispatcher("/WEB-INF/spareRequest.jsp").forward(request, response);

        }
    }

}