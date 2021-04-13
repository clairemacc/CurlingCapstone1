package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Player;
import models.Team;
import models.User;
import services.AccountService;
import services.ContactService;
import services.PlayerService;

public class MyAccountServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String display = request.getParameter("display");
        if (display == null) {
            display = "personalInfo";
        }
        
        String action = request.getParameter("action");
        boolean editInProgress = false;
        
        if (display.equals("personalInfo")){
            if (action != null) {
                if (action.startsWith("save")) {
                    session.setAttribute("display", display);
                    session.setAttribute("action", action);
                    doPost(request, response);
                    return;
                }

                if (action.startsWith("edit")) {
                    editInProgress = true;
                    request.setAttribute("editing", action.substring(4, action.length()).toLowerCase());
                }
            }
        }
        else if (display.equals("teamInfo")) {
            PlayerService playerService = new PlayerService();
            //Player player = playerService.getByUserID(action);
            //Team team = player.getTeamID();
            
        }
        
        request.setAttribute("user", user);
        request.setAttribute("display", display);
        request.setAttribute("editInProgress", editInProgress);
        getServletContext().getRequestDispatcher("/WEB-INF/myAccount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        User user = (User) session.getAttribute("user");
        
        String display = (String) session.getAttribute("display");
        String action = (String) session.getAttribute("action");
        String message = "";
        String editing = "";
        
        if (display != null) {
            if (display.equals("personalInfo")) {
                if (action != null) {
                    String firstName = user.getContactID().getFirstName();
                    String lastName = user.getContactID().getLastName();
                    String address = user.getContactID().getAddress();
                    String city = user.getContactID().getCity();
                    String postal = user.getContactID().getPostal();
                    String email = user.getContactID().getEmail();
                    String phone = user.getContactID().getPhone();
                    
                    
                    if (action.equals("saveName")) {
                        firstName = request.getParameter("firstName");
                        lastName = request.getParameter("lastName");
                        
                    }
                    else if (action.equals("saveAddress")) {
                        address = request.getParameter("address");
                        city = request.getParameter("city");
                        postal = request.getParameter("postal");
                    }
                    else if (action.equals("savePhone"))
                        phone = request.getParameter("phone");
                    
                    if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || 
                            address == null || address.equals("") || city == null || city.equals("") || 
                            postal == null || postal.equals("") || phone == null || phone.equals("")) {
                        message = "nullFields";
                        editing = action.substring(4, action.length()).toLowerCase();
                    }
                    else {
                        ContactService contService = new ContactService();
                        contService.update(user.getContactID().getContactID(), firstName, lastName, address, city, postal, email, phone);
                    }
                }
            }
        }
        AccountService accService = new AccountService();
        user = accService.getByUserID(user.getUserID());
        request.setAttribute("editing", editing);
        session.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/myAccount.jsp").forward(request, response);
    }
}