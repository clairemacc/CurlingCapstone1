package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class LoginServlet extends HttpServlet {

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