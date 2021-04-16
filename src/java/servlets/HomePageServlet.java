package servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.NewsPost;
import services.NewsPostService;
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        NewsPostService newsService = new NewsPostService();
        List<NewsPost> newsPosts = newsService.getAll();
        Collections.reverse(newsPosts);
        request.setAttribute("newsPosts", newsPosts);
        
        getServletContext().getRequestDispatcher("/WEB-INF/homePage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

       
        getServletContext().getRequestDispatcher("/WEB-INF/homePage.jsp").forward(request, response);
    }
}
