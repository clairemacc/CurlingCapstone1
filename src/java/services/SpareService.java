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
            for (String e : emails) 
                System.out.println(e);
            Contact contact = ss.getByEmail(emails.get(i));
            System.out.println(contact == null);
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
        return sent;
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
    
    public String generateSpareID() {
        List<Spare> spares = getAllOrdered();
        String idStr = spares.get(spares.size() - 1).getSpareID();
        int idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        
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
