package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Game;
import models.League;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import services.GameService;
import services.LeagueService;

@MultipartConfig
public class ScheduleServlet extends HttpServlet {

    public final String DIR = "uploads\\";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        LeagueService leagueService = new LeagueService();
        GameService gameService = new GameService();
        
        List<League> leagues = leagueService.getAll();
        request.setAttribute("leagues", leagues);
        
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("selectLeague")) {
                String leagueID = request.getParameter("viewScheduleButton");
                League league = leagueService.getByLeagueID(leagueID);
                System.out.println(leagueID);
                
                List<Game> leagueGames = gameService.getByLeague(league);
                System.out.println(leagueGames.size());
                request.setAttribute("leagueGames", leagueGames);
            }
        }
        
        request.getRequestDispatcher("/WEB-INF/schedules.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uploadMessage = null;

        String realPath = request.getServletContext().getRealPath("");
        String filePath = realPath + DIR;
        File file = null;
        DiskFileItemFactory dfif = new DiskFileItemFactory(5000 * 1024, new File(filePath));
        ServletFileUpload sfu = new ServletFileUpload(dfif);
        sfu.setFileSizeMax(5000 * 1024);

        try {
            List partsList = sfu.parseRequest(request);
            Iterator iter = partsList.iterator();
                            System.out.println("request: " + request.toString());
            System.out.println("parts: " + partsList);
            System.out.println("request.getParts: " + request.getParts());

            while (iter.hasNext()) {
                FileItem fi = (FileItem) iter.next();
                if (!fi.isFormField()) {

                    Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, filePath + fi.getName());

                    String[] split = fi.getName().split("\\.");
                    String extension = split[split.length - 1];
                    

                    Logger.getLogger(ScheduleServlet.class.getName()).log(Level.INFO, extension);

                    if (extension.equals("csv") || extension.equals("xlsx")) {
                        file = new File(filePath + fi.getName());

                        fi.write(file);
                        uploadMessage = "success";
                    } else {
                        uploadMessage = "wrongFileType";
                    }
                }
            }

        } catch (Exception e) {
            Logger.getLogger(ScheduleServlet.class
                    .getName()).log(Level.INFO, "Catch exception");
            e.printStackTrace();
        }
        request.setAttribute("uploadMessage", uploadMessage);
        if (uploadMessage != null && uploadMessage.equals("success")) {
            response.sendRedirect("viewSchedule?selectFile=" + file.getName());
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
    }

}
