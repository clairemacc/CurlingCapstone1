package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Standing;
import services.StandingService;

/**
 * This servlet is used to retrieve the standings from the database. It 
 * updates automatically once a player submits a score. All users have
 * access to the same data when viewing this section of the site.
 * */
public class StandingsServlet extends HttpServlet {

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
        
        StandingService standService = new StandingService();
        List<Standing> standings = standService.getAll();
        request.setAttribute("standings", standings);

        getServletContext().getRequestDispatcher("/WEB-INF/standings.jsp").forward(request, response);
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
        
        getServletContext().getRequestDispatcher("/WEB-INF/standings.jsp").forward(request, response);
    }
}
