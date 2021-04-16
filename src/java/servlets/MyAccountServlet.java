package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import services.ContactService;

/**
 * This servlet is used for a user to look over the details of their account as 
 * well as any curling league related information. A user can also edit several 
 * details regarding their account.
 * @author CurlingCapstone
 */
public class MyAccountServlet extends HttpServlet {

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
        User user = (User) session.getAttribute("user");
        
        String display = request.getParameter("display");
        if (display == null) 
            display = "personalInfo";
        
        if (request.getParameter("editing") == null) {
            session.removeAttribute("editing");
            session.removeAttribute("editThis");
            session.setAttribute("editing", "");
        }
        else 
            request.setAttribute("message", "nullFields");
        
        request.setAttribute("user", user);
        request.setAttribute("display", display);
        getServletContext().getRequestDispatcher("/WEB-INF/myAccount.jsp").forward(request, response);
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
        
        User user = (User) session.getAttribute("user");
        
        String display = (String) session.getAttribute("display");
        String action = request.getParameter("action");
        
        if (action != null) {
            if (action.equals("savePersonal")) {
                String firstName = user.getContactID().getFirstName();
                String lastName = user.getContactID().getLastName();
                String address = user.getContactID().getAddress();
                String city = user.getContactID().getCity();
                String postal = user.getContactID().getPostal();
                String phone = user.getContactID().getPhone();

                String isNull = "";

                if (request.getParameter("saveFirstName") != null) {
                    firstName = request.getParameter("firstName");
                    if (firstName == null || firstName.equals("")) 
                        isNull = "firstName";
                }
                else if (request.getParameter("saveLastName") != null) {
                    lastName = request.getParameter("lastName");
                    if (lastName == null || lastName.equals("")) 
                        isNull = "lastName";
                }
                else if (request.getParameter("saveAddress") != null) {
                    address = request.getParameter("address");
                    if (address == null || address.equals("")) 
                        isNull = "address";
                }
                else if (request.getParameter("saveCity") != null) {
                    city = request.getParameter("city");
                    if (city == null || city.equals("")) 
                        isNull = "city";
                }
                else if (request.getParameter("savePostal") != null) {
                    postal = request.getParameter("postal");
                    if (postal == null || postal.equals("")) 
                        isNull = "postal";
                }
                else if (request.getParameter("savePhone") != null) {
                    phone = request.getParameter("phone");
                    System.out.println("phone: " + phone);
                    if (phone == null || phone.equals("")) {
                        isNull = "phone";
                    }
                }
                System.out.println("this is null: " + isNull);

                if (!isNull.equals("")) {
                    session.setAttribute("editThis", isNull);
                    session.setAttribute("editing", "editing");
                    response.sendRedirect("myAccount?editing=editing&editThis="+ isNull);
                    return;
                }
                else {
                    System.out.println("entering update");
                    ContactService contService = new ContactService();
                    contService.update(user.getContactID().getContactID(), firstName, lastName, address, city, postal, user.getContactID().getEmail(), phone);
                    display = "personalInfo";
                }
            }
            
            else if (action.equals("saveNewEmail")) {
                String newEmail = request.getParameter("newEmail");
                String confNewEmail = request.getParameter("confNewEmail");
                AccountService accService = new AccountService();
                
                String message = "";
                boolean invalid = false;
                
                if (newEmail == null || newEmail.equals("") || confNewEmail == null || confNewEmail.equals("")) {
                    message = "nullFields";
                    invalid = true;
                }
                else if (!newEmail.equals(confNewEmail)) {
                    message = "emailMismatch";
                    invalid = true;
                }
                else {
                    for (User u : accService.getAll()) {
                        if (u.getEmail().equals(newEmail)) {
                            message = "emailExists";
                            invalid = true;
                        }
                    }
                }
                
                if (invalid) {
                    request.setAttribute("message", message);
                    request.setAttribute("newEmail", newEmail);
                }
                else {
                    accService.update(user.getUserID(), newEmail, user.getPassword(), user.getRole().getRoleID(), true);
                    request.setAttribute("message", "success");
                }
                display = "changeEmail";
            }
            
            else if (action.equals("saveNewPassword")) {String currPassword = request.getParameter("currPassword"); 
                String newPassword = request.getParameter("newPassword");
                String confNewPassword = request.getParameter("confNewPassword");
                
                if (!user.getPassword().equals(currPassword)) 
                    request.setAttribute("message", "wrongPassword");
                
                else if (newPassword == null || newPassword.equals("") || confNewPassword == null || confNewPassword.equals("")) 
                    request.setAttribute("message", "nullFields");
                
                else if (!newPassword.equals(confNewPassword)) 
                    request.setAttribute("message", "passwordMismatch");
                
                else if (user.getPassword().length() < 8)
                    request.setAttribute("message", "passwordTooShort");
                
                else if (user.getPassword().equals(newPassword))
                    request.setAttribute("message", "samePassword");
               
                else {
                    AccountService accService = new AccountService();
                    accService.update(user.getUserID(), user.getEmail(), newPassword, user.getRole().getRoleID(), true);
                    request.setAttribute("message", "success");
                }
                display = "changePassword";
            }
        }
        AccountService accService = new AccountService();
        user = accService.getByUserID(user.getUserID());
        request.setAttribute("display", display);
        session.setAttribute("user", user);
        
        getServletContext().getRequestDispatcher("/WEB-INF/myAccount.jsp").forward(request, response);
    }
}
