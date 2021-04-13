package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Standing;
import services.StandingService;

public class StandingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        StandingService standService = new StandingService();
        List<Standing> standings = standService.getAll();
        request.setAttribute("standings", standings);

        getServletContext().getRequestDispatcher("/WEB-INF/standings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        getServletContext().getRequestDispatcher("/WEB-INF/standings.jsp").forward(request, response);
    }
}
