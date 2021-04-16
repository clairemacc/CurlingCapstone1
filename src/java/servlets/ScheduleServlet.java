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

/**
 * This servlet is used to upload a schedule using a .xlsx or .csv file format.
 * Once an executive has uploaded a schedule it will be viewable using the 
 * viewScheduleServlet. The .csv or .xlsx file need to be in a specific format 
 * for the schedule to upload properly.
 * @author 819466
 */
@MultipartConfig
public class ScheduleServlet extends HttpServlet {
    public final String DIR = "uploads\\";
    
    /**
     * Handles the HTTP GET method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Handles the HTTP POST method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
