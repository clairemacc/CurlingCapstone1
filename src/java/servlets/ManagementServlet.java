package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Executive;
import models.League;
import models.Player;
import models.Registration;
import models.Position;
import models.Role;
import models.Spare;
import models.Team;
import models.User;
import services.AccountService;
import services.ContactService;
import services.ExecutiveService;
import services.LeagueService;
import services.PlayerService;
import services.PositionService;
import services.RegistrationService;
import services.SpareService;
import services.TeamService;

public class ManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user"); 
        String display = request.getParameter("display"); 
        
        if (display == null) {
            display = (String) session.getAttribute("display");
            if (display == null || display.equals("selectContacts")) {
                display = "verifyRegs";
            }
        }
        if (request.getParameter("teamAction") == null) {
            session.removeAttribute("newMems");
            session.removeAttribute("updatedList");
            session.removeAttribute("toRemoveList");
        }
        
        if (display.equals("verifyRegs")) {
            RegistrationService regService = new RegistrationService();
            List<Registration> regs = regService.getAll();
            
            List<String> groupRegIDs = new ArrayList<>();
            List<Registration> indivRegs = new ArrayList<>();
            List<Registration> spareRegs = new ArrayList<>();
            
            if (regs != null) {
                request.setAttribute("num", regs.size());

                for (Registration pr : regs) {
                    if (pr.getGroupID() != null) {
                        if (!groupRegIDs.contains(pr.getGroupID())) 
                            groupRegIDs.add(pr.getGroupID());
                    }
                    else if (pr.getRegType().equals("individual"))
                        indivRegs.add(pr);
                    else if (pr.getRegType().equals("spare")) {
                        spareRegs.add(pr);
                    }
                    
                }
                
                session.setAttribute("groupRegIDs", groupRegIDs);
                session.setAttribute("indivRegs", indivRegs);
                session.setAttribute("spareRegs", spareRegs);
                request.setAttribute("groupNum", groupRegIDs.size());
                request.setAttribute("indivNum", indivRegs.size());
                request.setAttribute("spareNum", spareRegs.size());
            }
            else {
                request.setAttribute("num", "0");
                request.setAttribute("groupNum", "0");
                request.setAttribute("indivNum", "0");
                request.setAttribute("spareNum", "0");
            }
        }
        else if (display.equals("registrationSetup")) {
            String leagueOpt = "";
            LeagueService leagueService = new LeagueService();
            List<League> leagues = leagueService.getAll();
            request.setAttribute("display", "registrationSetup");
            
            if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                List<Registration> regs = (List<Registration>) session.getAttribute("regs");
                session.setAttribute("groupID", regs.get(0).getGroupID());
                session.setAttribute("memNum", regs.size());
                request.setAttribute("signupAll", regs.get(0).getSignupAll());

                if (regs.get(0).getSignupAll()) 
                    leagueOpt = "Register for all chosen leagues";
                else {
                    leagueOpt = "Register for one league only";
                }

                session.setAttribute("regs", regs);
                request.setAttribute("leagueIDs", regs.get(0).getLeagues());
            }
            else if (session.getAttribute("isIndiv") != null || session.getAttribute("isSpare") != null) {
                Registration reg = (Registration) session.getAttribute("reg");
                if (reg.getSignupAll())
                    leagueOpt = "Register for all chosen leagues";
                else {
                    leagueOpt = "Register for one league only";
                }
                session.setAttribute("reg", reg);
                request.setAttribute("leagueIDs", reg.getLeagues());
            }
            
            request.setAttribute("innerDisplay", "selectLeague");
            request.setAttribute("displayFormingTeam", false);
            request.setAttribute("displayAddingSpare", false);

            request.setAttribute("leagueOpt", leagueOpt);
            session.setAttribute("leagues", leagues);
        }
        
        else if (display.equals("manageAccounts")) {
            session.setAttribute("display", "manageAccounts");
            AccountService accService = new AccountService();
            
            List<User> allUsers = accService.getAll();
            request.setAttribute("allUsers", allUsers);
            
            List<Role> roles = accService.getAllRoles();
            session.setAttribute("roles", roles); 
        }
        else if (display.equals("manageTeams")) {
            session.setAttribute("display", "manageTeams");
            TeamService teamService = new TeamService();
            
            List<Team> allTeams = teamService.getAll();
            session.setAttribute("allTeams", allTeams);
            
            PlayerService playerService = new PlayerService();
            List<Player> updatedList = (List<Player>) session.getAttribute("updatedList");
            if (updatedList == null) 
                updatedList = playerService.getAll();
            session.setAttribute("updatedList", updatedList);
            
            LeagueService leagueService = new LeagueService();
            List<League> allLeagues = leagueService.getAll();
            session.setAttribute("allLeagues", allLeagues);
            
            PositionService posService = new PositionService();
            List<Position> allPositions = posService.getAll();
            session.setAttribute("allPositions", allPositions);
            
            AccountService accService = new AccountService();
            List<User> allUsers = accService.getAll();
            session.setAttribute("allUsers", allUsers);
        }
        else if (display.equals("manageLeagues")) {
            session.setAttribute("display", "manageLeagues");
            
            LeagueService leagueService = new LeagueService();
            List<League> allLeagues = leagueService.getAll();
            session.setAttribute("allLeagues", allLeagues);
            
            ExecutiveService execService = new ExecutiveService();
            List<Executive> allExecutives = execService.getAll();
            session.setAttribute("allExecutives", allExecutives);
        }
        
        
        
        String action = request.getParameter("action");
        if (action != null) {
            RegistrationService regService = new RegistrationService();
            if (action.equals("viewRegs")) {                
                if (request.getParameter("viewGroupRegs") != null) {
                    display = "verifyGroupRegs";
                    session.setAttribute("display", display);
                    session.removeAttribute("isIndiv");
                    session.removeAttribute("isSpare");
                }
                else {
                    if (request.getParameter("viewIndivRegs") != null) {
                        session.setAttribute("isIndiv", true);
                        session.removeAttribute("isSpare");
                    }
                    else if (request.getParameter("viewSpareRegs") != null) {
                        session.setAttribute("isSpare", true);
                        session.removeAttribute("isIndiv");
                    }
                        
                    display = "verifyIndivRegs";
                    session.setAttribute("display", display);
                }
            }
            else if (action.equals("selectGroupID")) {
                display = "verifyGroupRegs";
                String thisGroupID = request.getParameter("viewGroup");
                request.setAttribute("innerDisplay", "viewGroup");
                session.setAttribute("thisGroupID", thisGroupID);
                
                
                List<Registration> regs = regService.getByGroup(thisGroupID);
                session.setAttribute("regInfo", regs.get(0));
                session.setAttribute("regs", regs);
            }
            else if (action.equals("selectRegID")) {
                display = "verifyIndivRegs";
                String thisReg = request.getParameter("viewReg");
                
                request.setAttribute("innerDisplay", "viewIndiv");
                
                Registration reg = regService.getByContactID(thisReg);
                session.setAttribute("reg", reg);
            }
            else if (action.equals("selectSpareRegID")) {
                display = "verifySpareRegs";
                String thisReg = request.getParameter("viewSpareReg");
                
                request.setAttribute("innerDisplay", "viewSpare");
                
                Registration reg = regService.getByContactID(thisReg);
                session.setAttribute("reg", reg);
            }
            
            else if (action.equals("selectMember")) {
                request.setAttribute("innerDisplay", "viewGroup");
                String thisContactID;
                
                if (request.getParameter("viewMember") != null) {
                    request.setAttribute("innerDisplayMember", "viewMember");
                    thisContactID = request.getParameter("viewMember");
                    Registration playerReg = regService.getByContactID(thisContactID);
                    request.setAttribute("groupMember", playerReg);
                    request.setAttribute("selectedPos", playerReg.getPosition().getPositionName());
                    request.setAttribute("selectedFlex", playerReg.getFlexibleP().toString());
                }
                else {
                    if (request.getParameter("assignTeamButton") != null) {
                        if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                            List<Registration> regs = (List<Registration>) session.getAttribute("regs");
                            List<Registration> newRegs = new ArrayList<>();

                            for (Registration r : regs) {
                                if (request.getParameter(r.getContactID()) != null)
                                    newRegs.add(r);
                                else 
                                    regService.update(r.getContactID(), r.getPosition().getPositionName(), r.getFlexibleP(), r.getLeagues(), r.getSignupAll(), "individual", null, null);
                            }
                            session.setAttribute("regs", newRegs);
                        } 
                    }
                    
                    session.setAttribute("display", "registrationSetup");
                    response.sendRedirect("management");
                    return;
                }
                
            }
            else if (action.equals("chooseLeagueButton")) {
                session.removeAttribute("displayThisTeam");
                
                List<League> leagues = (List<League>) session.getAttribute("leagues");
                List<League> newLeagues = (List<League>) session.getAttribute("newLeagues"); 
                if (newLeagues == null || newLeagues.isEmpty()) {
                    newLeagues = new ArrayList<>();
                    for (League l : leagues) {
                        if (request.getParameter("lea" + l.getLeagueID()) != null) 
                            newLeagues.add(l);
                    }
                    session.setAttribute("newLeagues", newLeagues);
                }
                
                String indexStr = (String) session.getAttribute("index");
                int index;
                if (indexStr == null) 
                    index = 0;
                else
                    index = Integer.parseInt(indexStr);
                
                if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                    List<Registration> regs = (List<Registration>) session.getAttribute("regs");
                    String teamName = regs.get(0).getTeamName();
                    if (teamName == null || teamName.equals(""))
                        teamName = "";

                    request.setAttribute("tempTeamName", teamName);
                }
                
                session.setAttribute("thisLeague", newLeagues.get(index));
                indexStr = Integer.toString(index);
                session.setAttribute("index", indexStr);
                request.setAttribute("leagueNum", leagues.size());
                
                if (session.getAttribute("isSpare") != null) {
                    request.setAttribute("innerDisplay", "spareSetup");
                    request.setAttribute("displayAddingSpare", true);
                    request.setAttribute("positions", new PositionService().getAll());
                }
                else {
                    request.setAttribute("innerDisplay", "selectTeam");
                    request.setAttribute("displayFormingTeam", true);
                }           
                
            }
            else if (action.equals("chooseTeamTypeButton")) {
                League thisLeague = (League) session.getAttribute("thisLeague");
                request.setAttribute("thisLeague", thisLeague);
                request.setAttribute("displayFormingTeam", true);
                
                if (request.getParameter("newTeamButton") != null) {
                    request.setAttribute("innerDisplay", "createNewTeam");
                    
                    if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                        List<Registration> regs = (List<Registration>) session.getAttribute("regs");
                        request.setAttribute("newTeamName", regs.get(0).getTeamName());
                    }
                    
                }
                else if (request.getParameter("existingTeamButton") != null) {
                    request.setAttribute("innerDisplay", "addToExistingTeam");
                    
                    TeamService teamService = new TeamService();
                    List<Team> leagueTeams = teamService.getAllByLeague(thisLeague);
                    session.setAttribute("teams", leagueTeams);
                }
                else if (request.getParameter("displayTeamButton") != null) {
                    request.setAttribute("innerDisplay", "addToExistingTeam");
                    session.setAttribute("displayThisTeam", "display");
                    
                    String teamID = request.getParameter("selectedTeam");
                    TeamService teamService = new TeamService();
                    Team thisTeam = teamService.get(teamID);
                    request.setAttribute("thisTeam", thisTeam);
                }
                
                else if (request.getParameter("addThisToTeam") != null) {
                    List<Position> positions = new PositionService().getAll();
                    request.setAttribute("positions", positions);
                    String teamID = request.getParameter("selectedTeam");
                    TeamService teamService = new TeamService();
                    Team thisTeam = teamService.get(teamID);
                    
                    int teamSize = thisTeam.getPlayerList().size();
                    int groupSize = 1;
                    
                    if (session.getAttribute("isIndiv") == null) {
                        List<Registration> regs = (List<Registration>) session.getAttribute("regs");
                        groupSize = regs.size();
                    }
                    
                    if ((6 - teamSize) < groupSize) {
                        String displayThisTeam = (String) session.getAttribute("displayThisTeam");
                        request.setAttribute("innerDisplay", "addToExistingTeam");
                        request.setAttribute("displayThisTeam", displayThisTeam);
                        request.setAttribute("message", "notEnoughSpots");
                        request.setAttribute("thisTeam", thisTeam);
                    }
                    else {
                        request.setAttribute("innerDisplay", "teamSetup");
                        session.setAttribute("thisTeam", thisTeam);
                        session.setAttribute("addToExisting", true);
                        request.setAttribute("displayFormingTeam", false);
                        request.setAttribute("displayTeamCreated", true);
                    }
                }
            }
            
            else if (action.equals("teamOverview")) {
                if (session.getAttribute("isSpare") == null) {
                    request.setAttribute("displayTeamCreated", true);
                    request.setAttribute("innerDisplay", "teamOverview");
                }
                else {
                    request.setAttribute("displayAddingSpare", true);
                    request.setAttribute("innerDisplay", "spareOverview");
                }
                
                PositionService posService = new PositionService();
                
                if (session.getAttribute("addToExisting") != null) {
                    Team thisTeam = (Team) session.getAttribute("thisTeam");
                    
                    List<Player> updatedPlayers = new ArrayList<>();
                    List<Player> players = thisTeam.getPlayerList();
                    int i = 0;
                    for (Player p : players) {
                        String posID = request.getParameter(p.getPlayerID() + "pos");
                        Position position = posService.getByPosID(Integer.parseInt(posID));
                        
                        Player newPlayer = new Player(p.getPlayerID());
                        newPlayer.setUserID(p.getUserID());
                        newPlayer.setPosition(position);
                        
                        updatedPlayers.add(newPlayer);
                        i++;
                    }
                    session.setAttribute("updatedPlayers", updatedPlayers);
                    session.setAttribute("addToExisting", true);
                }
                
                if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                    List<Registration> updatedRegs = new ArrayList<>();
                    List<Registration> regs = (List<Registration>) session.getAttribute("regs");

                    for (Registration p : regs) {
                        String posID = request.getParameter(p.getContactID() + "pos");
                        Position position = posService.getByPosID(Integer.parseInt(posID));

                        Registration newReg = new Registration(p.getContactID());
                        newReg.setContact(p.getContact());
                        newReg.setPosition(position);
                        updatedRegs.add(newReg);
                    }

                    session.setAttribute("updatedRegs", updatedRegs);
                }
                else {
                    Registration reg = (Registration) session.getAttribute("reg");
                    
                    String posID = request.getParameter(reg.getContactID() + "pos");
                    Position position = posService.getByPosID(Integer.parseInt(posID));
                    
                    Registration updatedReg = new Registration(reg.getContactID());
                    updatedReg.setContact(reg.getContact());
                    updatedReg.setPosition(position);
                    updatedReg.setFlexibleP(reg.getFlexibleP());
                    session.setAttribute("updatedReg", updatedReg);
                }
            }
        }
        
        //////
        
        String teamAction = request.getParameter("teamAction");
        if (teamAction != null) {
            AccountService accService = new AccountService();
            TeamService teamService = new TeamService();
            PositionService posService = new PositionService();
            if (teamAction.equals("addToTeam")) {                
                String teamID = request.getParameter("addToTeamBtn");
                String newUserID = request.getParameter(teamID + "newUser");
                String newUserPos = request.getParameter(teamID + "newPos");
                boolean invalid = false;
                
                if (newUserID == null || newUserID.equals("") || newUserPos == null || newUserPos.equals("")) {
                    request.setAttribute("message", "nullFields");
                    invalid = true;
                }
                else {
                    Team currentTeam = teamService.get(teamID);
                    List<Player> playerList = currentTeam.getPlayerList();
                    
                    for (Player p : playerList) {
                        if (p.getUserID().getUserID().equals(newUserID)) {
                            request.setAttribute("message", "existsOnTeam");
                            invalid = true;
                        }
                    }
                }
                
                if (invalid) {
                    
                }
                else {
                    List<Player> newMems = (List<Player>) session.getAttribute("newMems");
                    if (newMems == null) 
                        newMems = new ArrayList<>();
                    
                    boolean exists = false;
                    
                    for (Player m : newMems) {
                        if (m.getUserID().getUserID().equals(newUserID)) {
                            exists = true;
                        }
                    }
                    if (!exists) {
                        User tempPlayerUser = accService.getByUserID(newUserID);
                        Position tempPlayerPos = posService.getByPosName(newUserPos);
                        Team tempPlayerTeam = teamService.get(teamID);
                        Player tempPlayer = new Player();

                        tempPlayer.setUserID(tempPlayerUser);
                        tempPlayer.setPosition(tempPlayerPos);
                        tempPlayer.setTeamID(tempPlayerTeam);

                        newMems.add(tempPlayer);
                    }

                    session.setAttribute("newMems", newMems);
                    request.setAttribute("editing", teamID);
                }
            }
        }
        
        
        
        request.setAttribute("display", display);
        getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String action = request.getParameter("action");
        
        if (action != null) {
            if (action.equals("createNewTeam")) {
                TeamService teamService = new TeamService();
                
                League league = (League) session.getAttribute("thisLeague");
                String teamName = request.getParameter("newTeamName");
                Team team = null;
                
                if (teamName == null || teamName.equals("")) {
                    request.setAttribute("message", "nullTeamName");
                    request.setAttribute("innerDisplay", "createNewTeam");
                }
                else {
                    team = teamService.insert(league, teamName);
                    session.setAttribute("thisTeam", team);
                    session.setAttribute("display", "registrationSetup");
                    request.setAttribute("innerDisplay", "teamSetup");

                    if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                        List<Registration> regs = (List<Registration>) session.getAttribute("regs");
                        request.setAttribute("regs", regs);
                    }

                    PositionService posService = new PositionService();
                    List<Position> positions = posService.getAll();
                    request.setAttribute("positions", positions);
                    System.out.println(positions.get(0).getPositionName());
                    request.setAttribute("displayTeamCreated", true);
                }
                
            }
            
            else if (action.equals("confirmRegistration")) {
                Team thisTeam = (Team) session.getAttribute("thisTeam");

                AccountService accService = new AccountService();
                PlayerService playerService = new PlayerService();
                SpareService spareService = new SpareService();

                String path = getServletContext().getRealPath("/WEB-INF");
                String url = request.getRequestURL().toString();
                url = url.substring(0, url.indexOf("management"));
                url = url + "forgot";

                if (session.getAttribute("addToExisting") != null) {
                    List<Player> updatedPlayers = (List<Player>) session.getAttribute("updatedPlayers");

                    for (Player p : updatedPlayers)  {
                        playerService.update(p.getPlayerID(), p.getUserID(), thisTeam, p.getPosition());
                    }
                }

                List<Registration> updatedRegs = (List<Registration>) session.getAttribute("updatedRegs");
                Registration updatedReg = (Registration) session.getAttribute("updatedReg");

                if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null) {
                    for (Registration r : updatedRegs) {
                        User newUser = accService.getByContactID(r.getContact());
                        if (newUser == null) {
                            newUser = accService.insert(r.getContact(), r.getContact().getEmail());

                            String uuid = UUID.randomUUID().toString();
                            accService.setResetUUID(newUser, uuid);
                            accService.sendAccountCreatedEmail(newUser.getEmail(), path, url, uuid);
                        }
                        Player player = playerService.insert(newUser, thisTeam, r.getPosition());
                    }
                    request.setAttribute("innerDisplay", "teamCreatedSuccess");
                }
                else {
                    if (session.getAttribute("isIndiv") != null) {
                        User newUser = accService.getByContactID(updatedReg.getContact());
                        if (newUser == null) {
                            newUser = accService.insert(updatedReg.getContact(), updatedReg.getContact().getEmail());

                            String uuid = UUID.randomUUID().toString();
                            accService.setResetUUID(newUser, uuid);
                            accService.sendAccountCreatedEmail(newUser.getEmail(), path, url, uuid);
                        }
                        Player player = playerService.insert(newUser, thisTeam, updatedReg.getPosition());
                        request.setAttribute("innerDisplay", "teamCreatedSuccess");
                    }
                    else if (session.getAttribute("isSpare") != null) {
                        League thisLeague = (League) session.getAttribute("thisLeague");
                        spareService.insert(updatedReg.getContact(), thisLeague, updatedReg.getPosition(), updatedReg.getFlexibleP());
                        request.setAttribute("innerDisplay", "teamCreatedSuccess");
                        
                    }

                }
                
                List<League> newLeagues = (List<League>) session.getAttribute("newLeagues");
                String indexStr = (String) session.getAttribute("index");

                int index = Integer.parseInt(indexStr);
                boolean removeReg = false;

                if (newLeagues.size() > 1) {
                    if ((index + 1) < newLeagues.size()) {
                        index++;
                        request.setAttribute("addAnotherTeam", true);
                        session.setAttribute("index", Integer.toString(index));
                        session.setAttribute("thisLeague", newLeagues.get(index));
                    }
                    else 
                        removeReg = true;
                }
                else 
                    removeReg = true;

                if (removeReg) {
                    RegistrationService regService = new RegistrationService();

                    if (session.getAttribute("isIndiv") == null && session.getAttribute("isSpare") == null){
                        for (Registration r : updatedRegs) 
                            regService.delete(r.getContactID());
                    }
                    else 
                        regService.delete(updatedReg.getContactID());

                    session.removeAttribute("groupRegIDs");
                    session.removeAttribute("indivRegIDs");
                    session.removeAttribute("groupID");
                    session.removeAttribute("memNum");
                    session.removeAttribute("regs");
                    session.removeAttribute("updatedRegs");
                    session.removeAttribute("leagues");
                    session.removeAttribute("thisLeague");
                    session.removeAttribute("newLeagues");
                    session.removeAttribute("index");
                    session.removeAttribute("thisTeam");
                    session.removeAttribute("addToExisting");
                    session.removeAttribute("updatedPlayers");
                    session.removeAttribute("reg");
                    session.removeAttribute("updatedReg");
                    session.removeAttribute("isIndiv");
                    session.removeAttribute("isSpare");
                    String display = (String) session.getAttribute("display");
                    String thisGroupID = (String) session.getAttribute("thisGroupID");
                    session.removeAttribute("display");
                    session.removeAttribute("thisGroupID");
                    request.setAttribute("display", display);
                    request.setAttribute("thisGroupID", thisGroupID);
                }
                
            }   
            else if (action.equals("saveUser")) {
                if (request.getParameter("cancelButton") == null) {
                    if (request.getParameter("saveUserButton") != null) {
                        String editID = request.getParameter("saveUserButton");
                        AccountService accService = new AccountService();
                        
                        User editUser = accService.getByUserID(editID);
                        String message = "";
                        
                        String firstName = request.getParameter("firstName");
                        String lastName = request.getParameter("lastName");
                        String address = request.getParameter("address");
                        String city = request.getParameter("city");
                        String postal = request.getParameter("postal");
                        String phone = request.getParameter("phone");
                        String email = request.getParameter("email");
                        String password = request.getParameter("password");
                        String roleStr = request.getParameter("userRole");
                        String isActiveStr = request.getParameter(editID + "active");
                        
                        
                        if (firstName == null || firstName.equals("") || lastName == null || lastName.equals("") || address == null || address.equals("") ||
                                city == null || city.equals("") || postal == null || postal.equals("") || phone == null || phone.equals("") || 
                                email == null || email.equals("") || password == null || password.equals("") || roleStr == null || roleStr.equals("") || 
                                isActiveStr == null || isActiveStr.equals("")) {
                            message = "nullFields";
                        }
                        else {
                            
                            int roleID = Integer.parseInt(roleStr);
                            boolean isActive = true;
                            
                            if (isActiveStr.equals("active"))
                                isActive = true;
                            else if (isActiveStr.equals("inactive"))
                                isActive = false;
                            
                            
                            accService.update(editID, email, password, roleID, isActive);
                            
                            ContactService contService = new ContactService();
                            contService.update(editUser.getContactID().getContactID(), firstName, lastName, address, city, postal, email, phone);
                            request.setAttribute("allUsers", accService.getAll());
                        }
                    }
                }
                else {
                    response.sendRedirect("management");
                    return;
                }
            }
            
            else if (action.equals("deleteUser")) {
                String deleteUserID = request.getParameter("realDeleteUserButton");
                AccountService accService = new AccountService();
                accService.delete(deleteUserID);
                session.setAttribute("allUsers", accService.getAll());
            }
        }
        
        /////
        
        String teamAction = request.getParameter("teamAction");
        if (teamAction != null) {
            PlayerService playerService = new PlayerService();
            if (request.getParameter("cancel") == null) {
                TeamService teamService = new TeamService();
                PositionService posService = new PositionService();
                AccountService accService = new AccountService();
                LeagueService leagueService = new LeagueService();
                
                if (request.getParameter("saveTeamSettingsBtn") != null) {
                    boolean invalid = false;
                    
                    String teamID = request.getParameter("saveTeamSettingsBtn");
                    Team thisTeam = teamService.get(teamID);
                    
                    String teamName = request.getParameter(teamID + "teamName");
                    String leagueID = request.getParameter(teamID + "league");
                    League league = leagueService.getByLeagueID(leagueID);
                    
                    if (teamName == null || teamName.equals("")) {
                        invalid = true;
                        request.setAttribute("editing", request.getParameter("saveTeamSettingsBtn"));
                    }
                    if (!invalid) {
                        List<Player> updatedList = (List<Player>) session.getAttribute("updatedList");
                        if (updatedList == null)
                            updatedList = playerService.getAll();
                        
                        List<Player> newList = new ArrayList<>();
                        
                        int i = 0;
                        for (Player p : updatedList) {
                            if (p.getTeamID().getTeamID().equals(teamID)) {
                                String newPosStr = request.getParameter(p.getPlayerID() + "pos");
                                Position newPos = posService.getByPosName(newPosStr);
                                playerService.update(p.getPlayerID(), p.getUserID(), thisTeam, newPos);
                                newList.add(p);
                            }
                        }
                        
                        List<Player> newMems = (List<Player>) session.getAttribute("newMems");
                        
                        if (newMems != null) {
                            for (Player s :  newMems) 
                                newList.add(playerService.insert(s.getUserID(), thisTeam, s.getPosition()));
                        }
                        
                        if (session.getAttribute("toRemoveList") != null) {
                            List<Player> toRemoveList = (List<Player>) session.getAttribute("toRemoveList");
                            for (Player c : toRemoveList) 
                                playerService.delete(c.getPlayerID());
                        }
                        
                        teamService.update(teamID, teamName, league, newList);
                        session.setAttribute("allTeams", teamService.getAll());
                        session.removeAttribute("newMems");
                        session.removeAttribute("toRemoveList");
                        session.setAttribute("updatedList", playerService.getAll());
                    }
                }
                else if (request.getParameter("removeBtn") != null) {
                    String teamID = request.getParameter("removeBtn").split(";")[0];
                    String playerID = request.getParameter("removeBtn").split(";")[1];
                    
                    List<Player> updatedList = (List<Player>) session.getAttribute("updatedList");
                    if (updatedList == null) 
                        updatedList = playerService.getAll();
                    
                    List<Player> toRemoveList = (List<Player>) session.getAttribute("toRemoveList");
                    if (toRemoveList == null) 
                        toRemoveList = new ArrayList<>();
           
                    
                    updatedList.remove(playerService.getByPlayerID(playerID));
                    toRemoveList.add(playerService.getByPlayerID(playerID));
                    
                    session.setAttribute("updatedList", updatedList);
                    session.setAttribute("toRemoveList", toRemoveList);
                    request.setAttribute("editing", teamID);
                }
                
                else if (request.getParameter("realDeleteTeamButton") != null) {
                    String teamID = request.getParameter("realDeleteTeamButton");
                    teamService.delete(teamID);
                    session.setAttribute("allTeams", teamService.getAll());
                    session.setAttribute("updatedList", playerService.getAll());
                }
            }
            else {
                request.setAttribute("editing", null);
                session.setAttribute("updatedList", playerService.getAll());
                session.removeAttribute("toRemoveList");
                session.removeAttribute("newMems");
            }
        }
        
        
        ////  
        
        
        String leagueAction = request.getParameter("leagueAction");
        if (leagueAction != null) {
            if (request.getParameter("cancel") == null) {
                LeagueService leagueService = new LeagueService();
                ExecutiveService execService = new ExecutiveService();

                if (leagueAction.equals("saveLeagueSettings")) {
                    String leagueID = request.getParameter("saveLeagueSettings");
                    League league = leagueService.getByLeagueID(leagueID);
                    
                    String weekday = request.getParameter(leagueID + "wk");
                    String execUserID = request.getParameter(leagueID + "ex");
                    
                    List<Executive> execList = league.getExecutiveList();
                    if (execList == null)
                        execList = new ArrayList<>();
                    else
                        execList.clear();
                    
                    execList.add(execService.getByUser(execUserID));
                                        
                    List<Team> teamList = league.getTeamList();
                    if (teamList == null) 
                        teamList = new ArrayList<>();
                    
                    List<Spare> spareList = league.getSpareList();
                    if (spareList == null)
                        spareList = new ArrayList<>();
                    
                    leagueService.update(leagueID, weekday, execList, teamList, spareList);
                    
                    session.setAttribute("allLeagues", leagueService.getAll());
                }
                
                else if (leagueAction.equals("deleteLeague")) {
                    String leagueID = request.getParameter("realDeleteLeagueButton");
                    League league = leagueService.getByLeagueID(leagueID);
                    
                    
                }
            }
        }
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
    }
}

            

        
        /*
        AccountService accountService = new AccountService();
        String action = request.getParameter("action");
        switch(action){
            case "validate":
            {
                String id = request.getParameter("id");
                if(true){
                    request.setAttribute("error", "User does not exists, please refresh your page and try again!");
                }
                else{
                    request.setAttribute("info", "Validate successful!");
                }
                //request.setAttribute("users", accountService.getAllNotVerify());
                request.setAttribute("type", 0);//verify users
                getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
            }
            break;
            case "modify":
            {
                String id = request.getParameter("id");
                String email = request.getParameter("email");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String phone = request.getParameter("phone");
                String roleID = request.getParameter("role");
                Role role = new Role(Integer.parseInt(roleID));
                User user = new User(id);
                if(!accountService.modifyUser(user)){
                    request.setAttribute("error", "Email already taken by someone else!");
                }
                else{
                    request.setAttribute("info", "Modify successful!");
                }
                //request.setAttribute("users", accountService.getAllVerify());
                request.setAttribute("roles", accountService.getAllRoles());
                request.setAttribute("type", 1);
                getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
            }
            break;
            case "invalidate":
            {
                String id = request.getParameter("id");
                if(true){
                    request.setAttribute("error", "User does not exists, please refresh your page and try again!");
                }
                else{
                    request.setAttribute("info", "Invalidate successful!");
                }
                //request.setAttribute("users", accountService.getAllVerify());
                request.setAttribute("roles", accountService.getAllRoles());
                request.setAttribute("type", 1);
                getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
            }
            break;
            case "delete":
            {
                String id = request.getParameter("id");
                if(!accountService.deleteUser(id)){
                    request.setAttribute("error", "User does not exists, please refresh your page and try again!");
                }
                else{
                    request.setAttribute("info", "Delete successful!");
                }
                //request.setAttribute("users", accountService.getAllVerify());
                request.setAttribute("roles", accountService.getAllRoles());
                request.setAttribute("type", 1);
                getServletContext().getRequestDispatcher("/WEB-INF/management.jsp").forward(request, response);
            }
            break;
            default:
        }
        
    }
}*/