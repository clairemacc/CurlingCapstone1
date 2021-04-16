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
 * This class is used if a user forgets their password and needs to reset it.
 * If a registered user requests a password reset they will receive an email
 * with instructions for a new password. Once a new password is created the old
 * password is overwritten in the database.
 * @author CurlingCapstone
 */
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String uuid = request.getParameter("uuid");

        if (uuid != null) {
            AccountService accService = new AccountService();
            User user = accService.getByResetUUID(uuid);
            
            if (user != null) {
                session.setAttribute("uuid", uuid);
                response.sendRedirect("changePassword");
                return;
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message;
        String email = request.getParameter("email");
        String lastName = request.getParameter("lastName");

        if(email == null || email.equals("") || lastName == null || lastName.equals(""))
            message = "nullFields";
        else {
            message = "emailSent";
            request.setAttribute("emailEntered", email);

            AccountService accService = new AccountService();
            User user = accService.getByEmail(email);
            if (user != null) {
                if(user.getContactID().getLastName().equals(lastName)){
                    String path = getServletContext().getRealPath("/WEB-INF");
                    String url = request.getRequestURL().toString();
                    accService.resetPassword(email, path, url);
                }
            }
        }
        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/WEB-INF/forgotPassword.jsp").forward(request, response);
    }
}