package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Contact;
import models.Executive;
import models.League;
import models.Player;
import models.Position;
import models.Role;
import models.Spare;
import models.Team;
import models.User;
import services.AccountService;
import services.ContactService;
import services.ExecutiveService;
import services.LeagueService;
import services.PositionService;
import services.TeamService;

public class ContactServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user != null && user.getRole().getRoleID() == 1) {
            String display = (String) session.getAttribute("display");
            if (display == null) 
                display = "selectContacts";
            
            if (request.getParameter("writeMessage") == null) {
                display = "selectContacts";
                session.removeAttribute("selectedContacts");
            }
            
            if (request.getParameter("filter") == null) {
                if (request.getParameter("action") == null){
                    System.out.println("removing...");
                    session.removeAttribute("updatedLeagues");
                    session.removeAttribute("updatedTeams");
                    session.removeAttribute("updatedPositions");
                    session.removeAttribute("updatedRoles");
                    session.removeAttribute("updatedContacts");
                    session.removeAttribute("sparesIncl");
                    session.removeAttribute("searchField");
                    session.removeAttribute("searchBy");
                    session.removeAttribute("sortBy");
                }
            }
            
            if (display.equals("selectContacts")) {
                String searchField = (String) session.getAttribute("searchField");
                String searchBy = (String) session.getAttribute("searchBy");
                String sortBy = (String) session.getAttribute("sortBy");
                
                if (searchField == null) 
                    searchField = "";
                
                if (searchBy == null)
                    searchBy = "lastName";
                
                if (sortBy == null)
                    sortBy = "lastNameAZ";
                
                LeagueService leagueService = new LeagueService();
                List<League> allLeagues = leagueService.getAll();
                List<League> updatedLeagues = new ArrayList<>();

                TeamService teamService = new TeamService();
                List<Team> allTeams = teamService.getAll();
                List<Team> updatedTeams = new ArrayList<>();

                PositionService posService = new PositionService();
                List<Position> allPositions = posService.getAll();
                List<Position> updatedPositions = new ArrayList<>();

                AccountService accService = new AccountService();
                List<Role> allRoles = accService.getAllRoles();
                List<Role> updatedRoles = new ArrayList<>();
                
                ContactService contService = new ContactService();
                List<Contact> allContacts = contService.getAllSorted("lastName");

                session.setAttribute("leagues", allLeagues);
                session.setAttribute("teams", allTeams);
                session.setAttribute("positions", allPositions);
                session.setAttribute("roles", allRoles);

                String filter = request.getParameter("filter");
                String action = request.getParameter("action");

                if (filter == null) {
                    if (session.getAttribute("updatedContacts") == null) {
                        session.setAttribute("updatedContacts", allContacts);
                    }
                }
                
                else {
                    if (filter.equals("league")) {
                        for (League l : allLeagues) {
                            if (request.getParameter(l.getLeagueID()) != null) 
                                updatedLeagues.add(l);
                        }

                        for (League l : updatedLeagues) {
                            List<Team> leagueTeams = l.getTeamList();
                            for (Team t : leagueTeams) {
                                updatedTeams.add(t);
                            }
                        }

                        session.setAttribute("updatedLeagues", updatedLeagues);
                        session.setAttribute("updatedTeams", updatedTeams);
                    }

                    else if (filter.equals("team")) {
                        for (Team t : allTeams) {
                            if (request.getParameter(t.getTeamID()) != null) 
                                updatedTeams.add(t);
                        }

                        session.setAttribute("updatedTeams", updatedTeams);
                    }

                    else if (filter.equals("position")) {
                        for (Position p : allPositions) {
                            if (request.getParameter(p.getPositionName()) != null) 
                                updatedPositions.add(p);
                        }

                        session.setAttribute("updatedPositions", updatedPositions);
                    }

                    else if (filter.equals("role")) {
                        for (Role r : allRoles) {
                            if (request.getParameter(Integer.toString(r.getRoleID())) != null) 
                                updatedRoles.add(r);
                        }

                        session.setAttribute("updatedRoles", updatedRoles);
                    }

                    else if (filter.equals("spares")) {
                        String sparesIncl = request.getParameter("spares");
                        session.setAttribute("sparesIncl", sparesIncl);
                    }

                    updatedLeagues = (List<League>) session.getAttribute("updatedLeagues");
                    updatedTeams = (List<Team>) session.getAttribute("updatedTeams");
                    updatedPositions = (List<Position>) session.getAttribute("updatedPositions");
                    updatedRoles = (List<Role>) session.getAttribute("updatedRoles");
                    String sparesIncl = (String) session.getAttribute("sparesIncl");

                    if (updatedLeagues == null || updatedLeagues.isEmpty()) 
                        updatedLeagues = allLeagues;

                    if (updatedTeams == null || updatedTeams.isEmpty())
                        updatedTeams = allTeams;

                    if (updatedPositions == null || updatedPositions.isEmpty())
                        updatedPositions = allPositions;

                    if (updatedRoles == null || updatedRoles.isEmpty()) 
                        updatedRoles = allRoles;

                    if (sparesIncl == null)
                        sparesIncl = "no";

                    List<Contact> updatedContacts = new ArrayList<>();
                    List<Executive> updatedExecs = new ArrayList<>();
                    List<Player> updatedPlayers = new ArrayList<>();

                    boolean containsPlayers = false;
                    boolean containsExecs = false;
                    for (Role r : updatedRoles) {
                        if (r.getRoleID() == 1)
                            containsExecs = true;
                        else if (r.getRoleID() == 2)
                            containsPlayers = true;
                    }

                    if (containsExecs) {
                        for (League l : updatedLeagues) {
                            for (Executive e : l.getExecutiveList())
                                updatedExecs.add(e);
                        }

                        for (Executive e : updatedExecs) {
                        Contact contact = contService.getByContactID(e.getUser().getContactID().getContactID());
                        if (!updatedContacts.contains(contact))
                            updatedContacts.add(contact);
                        }
                    }

                    if (containsPlayers) {
                        for (Team t : updatedTeams) {
                            for (Player pl : t.getPlayerList()){
                                if (updatedPositions.contains(pl.getPosition())) 
                                    updatedPlayers.add(pl);
                            }
                        }

                        for (Player p : updatedPlayers) {
                            Contact contact = contService.getByContactID(p.getUserID().getContactID().getContactID());
                            if (!updatedContacts.contains(contact))
                                updatedContacts.add(contact);
                        }
                    }

                    if (!sparesIncl.equals("no")) {
                        if (sparesIncl.equals("only")) 
                            updatedContacts = new ArrayList<>();

                        for (League l : updatedLeagues) {
                            for (Spare s : l.getSpareList()) {
                                Contact contact = contService.getByContactID(s.getContactID().getContactID());
                                if (updatedPositions.contains(s.getPosition())){
                                    if (!updatedContacts.contains(contact))
                                        updatedContacts.add(contact);
                                }
                            }
                        }
                    }
                    if (action != null) {
                        if (!action.equals("resetSearch")) {
                            filterList(updatedContacts, searchField, searchBy);
                            sortList(updatedContacts, sortBy);
                        }
                    }
                    else {
                        filterList(updatedContacts, searchField, searchBy);
                        sortList(updatedContacts, sortBy);
                    }
                    session.setAttribute("searchField", searchField);
                    session.setAttribute("searchBy", searchBy);
                    session.setAttribute("sortBy", sortBy);
                    session.setAttribute("updatedContacts", updatedContacts);
                }
            
                if (action != null) {
                    if (action.equals("resetSearch")) {
                        session.removeAttribute("searchField");
                        session.removeAttribute("searchBy");
                        session.removeAttribute("sortBy");
                        response.sendRedirect("contact?filter");
                        return;
                    }
                    else if (action.equals("search")) {
                        searchField = request.getParameter("searchField");
                        searchBy = request.getParameter("searchBy");
                        sortBy = request.getParameter("sortBy");

                        List<Contact> updatedContacts = (List<Contact>) session.getAttribute("updatedContacts");
                        
                        filterList(updatedContacts, searchField, searchBy);
                        sortList(updatedContacts, sortBy);
                        
                        session.setAttribute("updatedContacts", updatedContacts);
                        session.setAttribute("searchField", searchField);
                        session.setAttribute("searchBy", searchBy);
                        session.setAttribute("sortBy", sortBy);
                    }
                    
                    else if (action.equals("confirmContacts")) {
                        List<Contact> selectedContacts = new ArrayList<>();

                        for (Contact c : allContacts) {
                            if (request.getParameter(c.getContactID() + "cont") != null) 
                                selectedContacts.add(c);
                        }
                        
                        session.setAttribute("display", "writeMessage");
                        session.setAttribute("selectedContacts", selectedContacts);
                        response.sendRedirect("contact?writeMessage");
                        return;
                    }
                }
            }
            
            else if (display.equals("writeMessage")) {
                List<Contact> selectedContacts = (List<Contact>) session.getAttribute("selectedContacts");
                request.setAttribute("selectedContacts", selectedContacts);
            }
            
            session.setAttribute("display", display);
        }
        else if (user == null || user.getRole().getRoleID() == 2) {
            ExecutiveService execService = new ExecutiveService();
            List<Executive> execs = execService.getAll();
            request.setAttribute("admins", execs);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/contact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String message = "";
        boolean invalid = false;
        
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("sendEmail")) {
                if (session.getAttribute("selectedContacts") != null && request.getParameter("sendFromExec") != null) {
                    List<Contact> selectedContacts = (List<Contact>) session.getAttribute("selectedContacts");
                    if (selectedContacts != null && selectedContacts.size() > 0) {
                        String subject = request.getParameter("subject");
                        String body = request.getParameter("body");
                        
                        if (subject == null || subject.equals("") || body == null || body.equals("")) {
                            request.setAttribute("message", "nullFields");
                            request.setAttribute("subject", subject);
                            request.setAttribute("body", body);
                        }
                        else {
                            String path = getServletContext().getRealPath("/WEB-INF");
                            
                            ContactService contService = new ContactService();
                            contService.emailFromExec(selectedContacts, user.getContactID().getFirstName(), subject, body, path);
                            request.setAttribute("messageSent", true);
                            session.removeAttribute("display");
                            getServletContext().getRequestDispatcher("/WEB-INF/contact.jsp").forward(request, response);
                            return;
                        }
                    }
                }
                else {
                    String name;
                    String email;
                    if (user == null) {
                        name = request.getParameter("nameEntered");
                        email = request.getParameter("emailEntered");
                    }
                    else {
                        name = user.getContactID().getFirstName() + " " + user.getContactID().getLastName();
                        email = user.getEmail();
                    }

                    String subject = request.getParameter("subject");
                    String body = request.getParameter("body");

                    if (name == null || name.equals("") || email == null || email.equals("") || 
                            subject == null || subject.equals("") || body == null || body.equals("")) {
                        message = "nullFields";
                        invalid = true;
                    }

                    if (invalid) {
                        request.setAttribute("name", name);
                        request.setAttribute("email", email);
                        request.setAttribute("subject", subject);
                        request.setAttribute("body", body);
                        request.setAttribute("admins", new ExecutiveService().getAll());
                    }
                    else {
                        String path = getServletContext().getRealPath("/WEB-INF");
                        ContactService contactService = new ContactService();
                        contactService.emailExec(email, name, subject, body, path);
                        request.setAttribute("messageSent", true);
                    }
                }
            }
        }

        request.setAttribute("message", message);
        getServletContext().getRequestDispatcher("/WEB-INF/contact.jsp").forward(request, response);
    }
    
    protected List<Contact> filterList(List<Contact> contacts, String searchField, String searchBy) {
        
        if (searchBy.equals("lastName")) {
            for (int i = 0; i < contacts.size(); i++) {
                if (!searchField.equals("") && !contacts.get(i).getLastName().toLowerCase().startsWith(searchField.toLowerCase())) {
                    contacts.remove(contacts.get(i));
                    i--;
                }
            }
        }
        else if (searchBy.equals("firstName")) {
            for (int i = 0; i < contacts.size(); i++) {
                if (!searchField.equals("") && !contacts.get(i).getFirstName().toLowerCase().startsWith(searchField.toLowerCase())) {
                    contacts.remove(contacts.get(i));
                    i--;
                }
            }
        }
        else if (searchBy.equals("email")) {
            for (int i = 0; i < contacts.size(); i++) {
                if (!searchField.equals("") && !contacts.get(i).getEmail().toLowerCase().startsWith(searchField.toLowerCase())) {
                    contacts.remove(contacts.get(i));
                    i--;
                }
            }
        }
        
        return contacts;
    }
    
    protected List<Contact> sortList(List<Contact> contacts, String sortBy) {
        for (int i = 1; i < contacts.size(); i++) {
            int j = i - 1;
            
            if (sortBy.endsWith("AZ")){
                if (sortBy.startsWith("first")) {
                    while (j >= 0 && contacts.get(i).getFirstName().compareToIgnoreCase(contacts.get(j).getFirstName()) < 0) {
                        contacts = swap(contacts, i, j);
                        i--;
                        j--;
                    }
                }
                else if (sortBy.startsWith("last")) {
                    while (j >= 0 && contacts.get(i).getLastName().compareToIgnoreCase(contacts.get(j).getLastName()) < 0) {
                        contacts = swap(contacts, i, j);
                        i--;
                        j--;
                    }
                }
            }
            else if (sortBy.endsWith("ZA")) {
                if (sortBy.startsWith("first")) {
                    while (j >= 0 && contacts.get(j).getFirstName().compareToIgnoreCase(contacts.get(i).getFirstName()) < 0) {
                        contacts = swap(contacts, i, j);
                        i--;
                        j--;
                    }
                }
                else if (sortBy.startsWith("last")) {
                    while (j >= 0 && contacts.get(j).getLastName().compareToIgnoreCase(contacts.get(i).getLastName()) < 0) {
                        contacts = swap(contacts, i, j);
                        i--;
                        j--;
                    }
                }
            }
        }
        return contacts;
    }
    
    protected List<Contact> swap(List<Contact> contacts, int i, int j) {
        Contact contact = contacts.get(j);
        contacts.remove(contact);
        contacts.add(i, contact);
        return contacts;
    }
}
