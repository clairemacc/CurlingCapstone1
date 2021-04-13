package servlets;

import services.LeagueService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.League;
import models.Member;
import models.Position;
import models.Registration;
import models.User;
import services.AccountService;
import services.ContactService;
import services.PositionService;
import services.RegistrationService;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        List<Member> members = (List<Member>) session.getAttribute("members");
        if (members == null) {
            members = new ArrayList<>();
        }

        int number = 0;
        boolean invalid = false;
        String message = "";
        String title = "Registration";
        String action = request.getParameter("action");
        
        PositionService posService = new PositionService();
        List<Position> positions = posService.getAll();
        
        LeagueService leagueService = new LeagueService();
        List<League> leaguesList = leagueService.getAll();
        
        if (action != null) {
            if (action.equals("selectRegType")) {
                session.removeAttribute("members");
                        
                session.setAttribute("positions", positions);
                session.setAttribute("leaguesList", leaguesList);
                String regType = request.getParameter("regTypeButton");
                if (regType.equals("indiv")) {
                    title = "Individual Registration";
                    request.setAttribute("display", "displayIndivReg");
                    request.setAttribute("indivTitle", true);
                }
                if (regType.equals("group")) {
                    request.setAttribute("display", "displayGroupReg");
                    title = "Group Registration";
                }
                if (regType.equals("spare")) {
                    request.setAttribute("display", "displaySpareReg");
                    title = "Spare Registration";
                    request.setAttribute("spareTitle", true);
                }
            }
            else if (action.equals("addMember")) {
                String saveMemCh = request.getParameter("saveMemCh");
                String discardMemCh = request.getParameter("discardMemCh");
                
                request.setAttribute("display", "displayGroupReg");
                title = "Group Registration";
                
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String address = request.getParameter("address");
                String city = request.getParameter("city");                
                String postal = request.getParameter("postal");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String position = request.getParameter("positionName");
                boolean flexibleP = false;
                
                if (request.getParameter("flexibleP") != null)
                    flexibleP = true;
                
                if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("") ||
                        address == null || address.equals("") || city == null || city.equals("") || postal == null ||
                        postal.equals("") || email == null || email.equals("") || phone == null || phone.equals("") ||
                        position == null || position.equals("")) {
                    message = "nullFields";
                    invalid = true;
                }
                else {
                    AccountService accService = new AccountService();
                    User user = accService.getByEmail(email);
                    
                    if (user != null) {
                        message = "playerExists";
                        invalid = true;
                    }
                    else {
                        if (saveMemCh != null) {
                            number = Integer.parseInt(saveMemCh);
                            members.remove(number - 1);
                        }

                        if (discardMemCh == null) {
                            for (Member m : members) {
                                if (email.equals(m.getEmail())) {
                                    message = "memberExists";
                                    invalid = true;
                                }
                            }
                        }
                    }
                    
                }
                
                if (invalid) {
                    Member member = new Member(firstName, lastName, address, city, postal, email, phone);
                    request.setAttribute("member", member);
                    request.setAttribute("selectedPos", position);
                    request.setAttribute("flexibleP", flexibleP);
                }
                else {
                    Member member = new Member(firstName, lastName, address, city, postal, email, phone);
                    
                    if (saveMemCh != null) {
                        members.add((number - 1), member);
                        member.setNumber(number);
                    }
                    else if (discardMemCh == null) {
                        members.add(member);
                        member.setNumber(members.size());
                    }
                    
                    member.setPosition(position);
                    member.setFlexibleP(flexibleP);
                    
                    if (discardMemCh != null)
                        request.setAttribute("member", null);
                    
                    session.setAttribute("members", members);  
                }
            }
            
            if (action.equals("groupRegSubmit")) {
                request.setAttribute("display", "displayGroupReg");
                
                title = "Group Registration";
                members = (List<Member>) session.getAttribute("members");
                
                if (request.getParameter("editButton") != null) {
                    request.setAttribute("editing", true);
                    int editButton = Integer.parseInt(request.getParameter("editButton"));

                    for (Member m : members) {
                        if (m.getNumber() == editButton) {
                            request.setAttribute("member", m);
                            request.setAttribute("selectedPos", m.getPosition());
                            request.setAttribute("flexibleP", m.getFlexibleP());
                            break;
                        }
                    }
                }
                else if (request.getParameter("removeButton") != null) {
                    int removeButton = Integer.parseInt(request.getParameter("removeButton"));
                    
                    for (Member m : members) {
                        if (m.getNumber() == removeButton) {
                            members.remove(m);
                            for (int i = 0; i < members.size(); i++) 
                                members.get(i).setNumber(i + 1);
                            break;
                        }
                    }
                }
            }
        }
        request.setAttribute("regTitle", title);
        request.setAttribute("members", members);
        request.setAttribute("num", (members.size() + 1));
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String message = "";
        String from = "";
        String display = "displayGroupReg";
        String title = "Registration";
        boolean invalid = false;
        
        List<Member> members = (List<Member>) session.getAttribute("members");
        session.setAttribute("members", members);
        
        String action = request.getParameter("action");
        String editButton = request.getParameter("editButton");
        String removeButton = request.getParameter("removeButton");
        if (action != null) {
            if (editButton != null) {
                response.sendRedirect("register?action=" + action + "&editButton=" + editButton);
                return;
            }
            else if (removeButton != null) {
                response.sendRedirect("register?action=" + action + "&removeButton=" + removeButton);
                return;
            }
            
            String leagues = "";
            boolean signupAll = false;
            String lr = "";
                            
            LeagueService leagueService = new LeagueService();
            List<League> allLeagues = leagueService.getAll();
            List<League> selectedLeagues = (List<League>) session.getAttribute("selectedLeagues");
            if (selectedLeagues == null) 
                selectedLeagues = new ArrayList<>();

            for (League l : allLeagues) {
                if (request.getParameter(l.getLeagueID()) != null) {
                    selectedLeagues.add(l);
                    leagues += l.getLeagueID() + ", ";
                }
            }

            if (selectedLeagues.size() > 1) {
                if (request.getParameter("leagueReg") != null) {
                    lr = request.getParameter("leagueReg");
                    if (lr.equals("all"))
                        signupAll = true;
                    else if (lr.equals("one")) {
                        signupAll = false;
                    }
                }    
            }
            
            if (action.equals("indivSubmit")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String postal = request.getParameter("postal");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String position = request.getParameter("positionName");
                boolean flexibleP = false;
                
                if (request.getParameter("flexibleP") != null) 
                    flexibleP = true;

                
                if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("") ||
                        address == null || address.equals("") || city == null || city.equals("") || postal == null ||
                        postal.equals("") || email == null || email.equals("") || phone == null || phone.equals("") ||
                        position == null || position.equals("") || leagues.equals("")) {
                    message = "nullFields";
                    invalid = true;
                }
                else if (!email.equals("")){
                    AccountService accService = new AccountService();
                    User user = accService.getByEmail(email);
                    if (user != null) {
                        message = "playerExists";
                        invalid = true;
                    }
                }
                
                if (invalid) {
                    Member member = new Member(firstName, lastName, address, city, postal, email, phone);
                    request.setAttribute("member", member);
                    request.setAttribute("selectedPos", position);
                    request.setAttribute("flexibleP", flexibleP);
                    request.setAttribute("selectedLeagues", leagues);
                    request.setAttribute("leagueReg", lr);
                    request.setAttribute("indivTitle", true);
                    title = "Individual Registration";
                    display = "displayIndivReg";
                }
                else {
                    leagues = leagues.substring(0, leagues.length() - 2);
                    ContactService contService = new ContactService();
                    RegistrationService regService = new RegistrationService();
                    
                    contService.insert(firstName, lastName, address, city, postal, email, phone);
                    Registration registration = regService.insert(contService.getByEmail(email).getContactID(), position, flexibleP, leagues, signupAll, "individual", null, null);
                    
                    String path = getServletContext().getRealPath("/WEB-INF");
                    regService.emailNewIndivReg(registration, path);
                    
                    display = "displaySuccess";
                    title = "Registration Complete";
                }
            }
            else if (action.equals("groupRegSubmit")) {
                
                String teamName = request.getParameter("teamName");
                
                if (members == null) {
                    message = "nullMembers";
                    request.setAttribute("num", 1);
                    from = "null members";
                    invalid = true;
                }
                else if (members.isEmpty() || members.size() < 2) {
                    message = "notEnoughMems";
                    request.setAttribute("num", members.size() + 1);
                    from = "empty or 1 member";
                    invalid = true;
                }
                else if (leagues.equals("")) {
                    message = "nullLeague";
                    request.setAttribute("num", members.size() + 1);
                    from = "null league";
                    invalid = true;
                }
                
                if (invalid) {
                    request.setAttribute("selectedLeagues", leagues);
                    request.setAttribute("leagueReg", lr);
                    request.setAttribute("teamName", teamName);
                }
                else {
                    leagues = leagues.substring(0, leagues.length() - 2);
                    String groupID;

                    Random rand = new Random();
                    char c = (char) ('A' + rand.nextInt(26));
                    String n = Integer.toString(1 + rand.nextInt(99));
                    if (n.length() < 2) 
                        n = "0" + n;
                    groupID = c + n;
                    
                    ContactService contService = new ContactService();
                    RegistrationService regService = new RegistrationService();
                    try {
                        for (Member m : members) {
                            contService.insert(m.getFirstName(), m.getLastName(), m.getAddress(), m.getCity(), m.getPostal(), m.getEmail(), m.getPhone());
                            regService.insert(contService.getByEmail(m.getEmail()).getContactID(), m.getPosition(), m.getFlexibleP(), leagues, signupAll, "group", groupID, teamName);
                        }
                        
                        List<Registration> regs = regService.getByGroup(groupID);
                        String path = getServletContext().getRealPath("/WEB-INF");
                        regService.emailNewGroupReg(regs, path);
                        
                        display = "displaySuccess";
                        title = "Registration Complete";
                        session.removeAttribute("members");
                        
                    } catch (Exception e) {
                        System.out.println("something weird happened.");
                    }
                }
            }
            
            else if (action.equals("spareSubmit")) {
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String address = request.getParameter("address");
                String city = request.getParameter("city");
                String postal = request.getParameter("postal");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String position = request.getParameter("positionName");
                boolean flexibleP = false;
                
                if (request.getParameter("flexibleP") != null) 
                    flexibleP = true;

                
                if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("") ||
                        address == null || address.equals("") || city == null || city.equals("") || postal == null ||
                        postal.equals("") || email == null || email.equals("") || phone == null || phone.equals("") ||
                        position == null || position.equals("") || leagues.equals("")) {
                    message = "nullFields";
                    invalid = true;
                }
                
                if (invalid) {
                    Member member = new Member(firstName, lastName, address, city, postal, email, phone);
                    request.setAttribute("member", member);
                    request.setAttribute("selectedPos", position);
                    request.setAttribute("flexibleP", flexibleP);
                    request.setAttribute("selectedLeagues", leagues);
                    request.setAttribute("leagueReg", lr);
                    request.setAttribute("spareTitle", true);
                    title = "Spare Registration";
                    display = "displaySparevReg";
                }
                else {
                    leagues = leagues.substring(0, leagues.length() - 2);
                    ContactService contService = new ContactService();
                    RegistrationService regService = new RegistrationService();
                    
                    contService.insert(firstName, lastName, address, city, postal, email, phone);
                    Registration registration = regService.insert(contService.getByEmail(email).getContactID(), position, flexibleP, leagues, signupAll, "spare", null, null);
                    
                    String path = getServletContext().getRealPath("/WEB-INF");
                    regService.emailNewSpareReg(registration, path);
                    
                    display = "displaySuccess";
                    title = "Registration Complete";
                }
            }
        }
        request.setAttribute("display", display);
        request.setAttribute("message", message);
        request.setAttribute("regTitle", title);
        
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request,response);

    }
}