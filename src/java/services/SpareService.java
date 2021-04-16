package services;

import dataaccess.SpareDB;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Contact;
import models.League;
import models.Position;
import models.Spare;

/**
 * This class provides functionality such as retrieve, insert, update and delete for the spare object
 * @author CurlingCapston
 */
public class SpareService {
    
    /**
     * This returns a list of all the spares in the system
     * @return list of spares in the system
     */
    public List<Spare> getAll() {
        SpareDB sdb = new SpareDB();
        return sdb.getAll();
    }
    
    /**
     * This provides a list of spares in the system ordered by their spareID
     * @return ordered list of spares
     */
    public List<Spare> getAllOrdered() {
        SpareDB sdb = new SpareDB();
        return sdb.getAllOrdered();
    }
    
    /**
     * This returns a spares object that corresponds with the spareID being searched
     * @param spareID - identifier of the spare object
     * @return spare object found
     */
    public Spare getBySpareID(String spareID) {
        SpareDB sdb = new SpareDB();
        return sdb.getBySpareID(spareID);
    }

    /**
     * This returns a spare object that corresponds with the email being searched
     * @param email - email of the spare object
     * @return spare object found
     */
    public Spare get(String email) {
        SpareDB sdb = new SpareDB();
        return sdb.get(email);
    }
    
     /**
      * This puts together details for an email to be sent as a spare request
      * @param requestID - requestID for spare request
      * @param league - league for spare request
      * @param emails - list of emails of spares
      * @param path - beginning of the location of the email template
      * @param date - date of spare request
      * @param gameDate - date of game in need of spare
      * @param homeTeam - home team for game
      * @param awayTeam - away team for game
      * @param team - team in need of spare
      * @param position - position spare needs to fill
      * @return sent - true if sent else false
      */
    public boolean emailSpares(String requestID, League league, List<String> emails, String path, String date, String gameDate, String homeTeam, String awayTeam, String team, String position) {
        boolean sent = false;
        ContactService ss = new ContactService();

        String template = path + "\\emailtemplates\\spareReqTemplate.html";
        for (int i = 0; i < emails.size(); i++) {
            Contact contact = ss.getByEmail(emails.get(i));
            try {
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", contact.getFirstName());
                tags.put("lastname", contact.getLastName());
                tags.put("email", emails.get(i));
                tags.put("date", date);
                tags.put("leagueID", league.getLeagueID());
                tags.put("weekday", league.getWeekday());
                tags.put("gameDate", gameDate);
                tags.put("homeTeam", homeTeam);
                tags.put("awayTeam", awayTeam);
                tags.put("team", team);
                tags.put("position", position);
                tags.put("requestID", requestID);

                GmailService.sendMail(emails.get(i), "ARC Curling - Request for a Spare", template, tags);
            } catch (Exception ex) {
                Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (contact != null) {
                sent = true;
            }
        }
        emailExecutive(requestID, league, path, date, gameDate, homeTeam, awayTeam, team, position);
        
        return sent;
    }
    
    public void emailExecutive(String requestID, League league, String path, String date, String gameDate, String homeTeam, String awayTeam, String team, String position) {
        String template = path + "\\emailtemplates\\execSpareRequest.html";
        try {
            HashMap<String, String> tags = new HashMap<>();
            tags.put("date", date);
            tags.put("leagueID", league.getLeagueID());
            tags.put("weekday", league.getWeekday());
            tags.put("gameDate", gameDate);
            tags.put("homeTeam", homeTeam);
            tags.put("awayTeam", awayTeam);
            tags.put("team", team);
            tags.put("position", position);
            tags.put("requestID", requestID);

            GmailService.sendMail("curlingproject@gmail.com", "ARC Curling - New Spare Request", template, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    /**
     * This inserts a new spare into the system
     * @param contact - this is a contact object containing all the details for the spare
     * @param league - league for the apre
     * @param position - position of the spare
     * @param flexibleP - true if the spare can play other positions
     */
    public void insert(Contact contact, League league, Position position, boolean flexibleP) {
        SpareDB sdb = new SpareDB();
        
        String spareID = generateSpareID();
        Spare spare = new Spare(spareID);
        
        spare.setContactID(contact);
        spare.setLeagueID(league);
        spare.setPosition(position);
        spare.setFlexibleP(flexibleP);
        
        sdb.insert(spare);
    }
    
    /**
     * Deletes a spare from the system
     * @param spareID - id of spare
     */
    public void delete(String spareID) {
        SpareDB sdb = new SpareDB();
        Spare spare = get(spareID);
        sdb.delete(spare);
    }
    
    /**
     * Generates an ID for a new spare
     * @return newly generated ID
     */
    public String generateSpareID() {
        List<Spare> spares = getAllOrdered();
        int idNum;
        
        if (spares == null || spares.isEmpty()) 
            idNum = 0;
        else {
            String idStr = spares.get(spares.size() - 1).getSpareID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }
        
        String spareID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            spareID = "S_000" + newIdNum;
        else if (newIdNum <= 99) 
            spareID = "S_00" + newIdNum;
        else if (newIdNum <= 999)
            spareID = "S_0" + newIdNum;
        else 
            spareID = "S_" + newIdNum;
        
        return spareID;
    }
}
