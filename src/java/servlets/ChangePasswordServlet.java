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
 * This servlet is used if a user wishes to change their password. If a user is in
 * the myaccount section of the application and wishes to change their password
 * they will be redirected here. Once the password is changed the old password 
 * is overwritten in the database.
 * @author CurlingCapstone
 */
public class ChangePasswordServlet extends HttpServlet {

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
        String uuid = (String) session.getAttribute("uuid");
        String display = "";
        boolean invalid = false;
        
        if (uuid == null || uuid.equals("")) 
            invalid = true;
        else {
            AccountService accService = new AccountService();
            User userTemp = accService.getByResetUUID(uuid);
            
            if (userTemp == null) 
                invalid = true;            
            else {
                session.setAttribute("userTemp", userTemp);
                display = "granted";
            }
        }
        
        if (invalid) 
            display = "unauthorized";

        request.setAttribute("display", display);
        getServletContext().getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(request, response);
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
        String uuid = (String) session.getAttribute("uuid");
        boolean invalid = false;
        
        if (session.getAttribute("user") != null) {
            session.removeAttribute("uuid");
            response.sendRedirect("home");
            return;
        }
        
        if (uuid != null && session.getAttribute("userTemp") != null) {
            AccountService accService = new AccountService();
            User user = accService.getByResetUUID(uuid);

            if (user == null) {
                response.sendRedirect("login");
                return;
            }
            
            String message = "";
            String display = "";
            String currPass = user.getPassword();

            String password = request.getParameter("password");
            String confPassword = request.getParameter("confPassword");

            if (password == null || password.equals("") || confPassword == null || confPassword.equals("")) {
                message = "nullFields";
                invalid = true;
            }
            else if (!password.equals(confPassword)) {
                message = "passMismatch";
                invalid = true;
            }
            else if (password.length() < 8) {
                message = "passTooShort";
                invalid = true;
            }
            else if (password.equals(currPass)) {
                message = "passUnchanged";
                invalid = true;
            }

            if (invalid) 
                display = "granted";
            else { 
                accService.changeUserPassword(user, password);
                display = "changeSuccess";
                session.removeAttribute("uuid");
                session.removeAttribute("user");
            }
            
        request.setAttribute("display", display);
        request.setAttribute("message", message);
        }  
        getServletContext().getRequestDispatcher("/WEB-INF/changePassword.jsp").forward(request, response); 
    }
}