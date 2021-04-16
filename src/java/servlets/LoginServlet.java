package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

/**
 * This servlet allows a player or executive to login. User input is checked against 
 * the database to authenticate the user and grant them access. Once the user is logged
 * in, they will have access to more restricted areas of the website, like score
 * submission and account management. 
 * @author CurlingCapstone
 */
public class LoginServlet extends HttpServlet {

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
        HttpSession session=request.getSession();
        
        String logout = request.getParameter("logout");

        if(logout != null){  
            session.invalidate();
            session = request.getSession();
            request.setAttribute("message", "logout");
        }
        
        if (session.getAttribute("user")!= null){
            response.sendRedirect("home");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);  
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
        
        HttpSession session = request.getSession();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        
        if (email == null || email.equals("") || password == null || password.equals("")) {
            request.setAttribute("email", email);
            request.setAttribute("message", "nullField");
        }
        else {
            AccountService accService = new AccountService();
            User user = accService.login(email, password);

            if (user == null) {
                request.setAttribute("email", email);
                request.setAttribute("message", "invalid");
            }
            else {
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());
                response.sendRedirect("home");
                return;
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }
}