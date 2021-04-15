package services;

import dataaccess.SpareDB;
import dataaccess.SpareRequestDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Contact;
import models.League;
import models.Position;
import models.Spare;
import models.SpareAssigned;

public class SpareService {
    
    public List<Spare> getAll() {
        SpareDB sdb = new SpareDB();
        return sdb.getAll();
    }
    
    public List<Spare> getAllOrdered() {
        SpareDB sdb = new SpareDB();
        return sdb.getAllOrdered();
    }
    
    public Spare getBySpareID(String spareID) {
        SpareDB sdb = new SpareDB();
        return sdb.getBySpareID(spareID);
    }

    public Spare get(String email) {
        SpareDB sdb = new SpareDB();
        return sdb.get(email);
    }
    
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
    
    public void delete(String spareID) {
        SpareDB sdb = new SpareDB();
        Spare spare = get(spareID);
        sdb.delete(spare);
    }
    
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
