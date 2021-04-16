package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.NewsPost;
import services.NewsPostService;


/**
 * This servlet is used to load and display the news postings on the home 
 * page of the website. All site viewers can see the same information, 
 * regardless of their level of authentication. No session information 
 * is needed.
 * @author CurlingCapstone
 */
public class HomePageServlet extends HttpServlet {

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
        NewsPostService newsService = new NewsPostService();
        List<NewsPost> newsPosts = newsService.getAll();
        Collections.reverse(newsPosts);
        request.setAttribute("newsPosts", newsPosts);
        
        getServletContext().getRequestDispatcher("/WEB-INF/homePage.jsp").forward(request, response);
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
        
        getServletContext().getRequestDispatcher("/WEB-INF/homePage.jsp").forward(request, response);
    }
}
