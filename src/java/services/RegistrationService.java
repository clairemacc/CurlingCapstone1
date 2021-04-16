package services;

import dataaccess.ContactDB;
import dataaccess.PositionDB;
import dataaccess.RegistrationDB;
import static java.lang.System.currentTimeMillis;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Registration;

/**
 * This class provides functionality such as retrieve, insert, update and delete for the registration object
 * 
 */
public class RegistrationService {
    
    /**
     * This returns a list of all the registrations in the system
     * @return list of registrations in the system
     */
    public List<Registration> getAll() {
        RegistrationDB rdb = new RegistrationDB();
        return rdb.getAll();
    }
    
    /**
     * This returns a list of all the active registrations in the system
     * @return list of active registrations in the system
     */
    public List<Registration> getAllActive() {
        RegistrationDB rdb = new RegistrationDB();
        return rdb.getAllActive();
    }
    
    /**
     * This returns a list of registrations with the same groupID
     * @param groupID - ID of group
     * @return list of registrations found
     */
    public List<Registration> getByGroup(String groupID) {
        RegistrationDB rdb = new RegistrationDB();
        return rdb.getByGroup(groupID);
    }
    
    /**
     * This method returns a registration for contact using a given contactID
     * @param contactID - identifier of a contact used for the search
     * @return registration found
     */
    public Registration getByContactID(String contactID) {
        RegistrationDB rdb = new RegistrationDB();
        return rdb.getByContactID(contactID);
    }
    
    /**
     * This puts together details for an email to be sent to notify admin of a new user registration
     * @param registration - registration object for the new user
     * @param path - beginning path for the location of the email template
     */
    public void emailNewIndivReg(Registration registration, String path) {
        String to = "curlingproject@gmail.com";
        String subject = "ARC Curling - New Registration";
        String template = path + "\\emailTemplates\\newIndivReg.html";

        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstName", registration.getContact().getFirstName());
        tags.put("lastName", registration.getContact().getLastName());
        tags.put("address", registration.getContact().getAddress());
        tags.put("city", registration.getContact().getCity());
        tags.put("postal", registration.getContact().getPostal());
        tags.put("email", registration.getContact().getEmail());
        tags.put("phone", registration.getContact().getPhone());
        tags.put("position", registration.getPosition().getPositionName());
        tags.put("flexibleP", registration.getFlexibleP().toString());
        tags.put("leagues", registration.getLeagues());
        tags.put("signupAll", registration.getSignupAll().toString());
        
        try {
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * This puts together details for an email to be sent to notify admin of a new spare registration
     * @param registration - registration object for the new spare
     * @param path - beginning path for the location of the email template
     */
    public void emailNewSpareReg(Registration registration, String path) {
        String to = "curlingproject@gmail.com";
        String subject = "ARC Curling - New Spare Registration";
        String template = path + "\\emailTemplates\\newSpareReg.html";

        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstName", registration.getContact().getFirstName());
        tags.put("lastName", registration.getContact().getLastName());
        tags.put("address", registration.getContact().getAddress());
        tags.put("city", registration.getContact().getCity());
        tags.put("postal", registration.getContact().getPostal());
        tags.put("email", registration.getContact().getEmail());
        tags.put("phone", registration.getContact().getPhone());
        tags.put("position", registration.getPosition().getPositionName());
        tags.put("flexibleP", registration.getFlexibleP().toString());
        tags.put("leagues", registration.getLeagues());
        tags.put("signupAll", registration.getSignupAll().toString());
        
        try {
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * This puts together details for an email to be sent to notify admin of a new group registration
     * @param registration - registration object for the new group
     * @param path - beginning path for the location of the email template
     */
    public void emailNewGroupReg(List<Registration> regs, String path) {
        String to = "curlingproject@gmail.com";
        String subject = "ARC Curling - New Group Registration";
        String template = path + "\\emailTemplates\\newGroupReg.html";
        
        String teamName;
        if (regs.get(0).getTeamName() == null || regs.get(0).getTeamName().equals(""))
            teamName = "N/A";
        else 
            teamName = regs.get(0).getTeamName();
        
        
        HashMap<String, String> tags = new HashMap<>();
        tags.put("groupID", regs.get(0).getGroupID());
        tags.put("leagues", regs.get(0).getLeagues());
        tags.put("signupAll", regs.get(0).getSignupAll().toString());
        tags.put("teamName", teamName);
        tags.put("num", Integer.toString(regs.size()));
        
        String firstName;
        String lastName;
        String address;
        String city;
        String postal;
        String email;
        String phone;
        String position;
        String flexibleP;
        
        for (int i = 0; i < regs.size(); i++) {
            firstName = "firstName" + (i + 1);
            lastName = "lastName" + (i + 1);
            address = "address" + (i + 1);
            city = "city" + (i + 1);
            postal = "postal" + (i + 1);
            email = "email" + (i + 1);
            phone = "phone" + (i + 1);
            position = "position" + (i + 1);
            flexibleP = "flexibleP" + (i + 1);
            
            tags.put(firstName, regs.get(i).getContact().getFirstName());
            tags.put(lastName, regs.get(i).getContact().getLastName());
            tags.put(address, regs.get(i).getContact().getAddress());
            tags.put(city, regs.get(i).getContact().getCity());
            tags.put(postal, regs.get(i).getContact().getPostal());
            tags.put(email, regs.get(i).getContact().getEmail());
            tags.put(phone, regs.get(i).getContact().getPhone());
            tags.put(position, regs.get(i).getPosition().getPositionName());
            tags.put(flexibleP, regs.get(i).getFlexibleP().toString());
        }
        
        try {
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Inserts a new registration into the database
     * @param contactID - contact ID for the contact
     * @param positionName - name of position for the new player
     * @param flexibleP - true if the player can play more than one position
     * @param leagues - leagues of player
     * @param signupAll
     * @param regType
     * @param groupID - ID of group
     * @param teamName - name of team
     * @return registration - newly created registration object
     */
    public Registration insert(String contactID, String positionName, boolean flexibleP, String leagues, boolean signupAll, String regType, String groupID, String teamName) {
        RegistrationDB rdb = new RegistrationDB();
        Registration registration = new Registration(contactID);
        
        registration.setContactID(contactID);
        registration.setContact(new ContactDB().getByContactID(contactID));
        registration.setPosition(new PositionDB().getByPosName(positionName));
        registration.setFlexibleP(flexibleP);
        registration.setLeagues(leagues);
        registration.setSignupAll(signupAll);
        registration.setRegType(regType);
        
        if (regType.equals("group")) {
            registration.setGroupID(groupID);
            registration.setTeamName(teamName);
        }
        
        long timeMillis = currentTimeMillis();
        Date today = new Date(timeMillis);
        registration.setRegDate(today);
        registration.setIsActive(true);
        
        rdb.insert(registration);
        return registration;
    }
    
    /**
     * This method updates an existing registration in the system
     * @param contactID - contact ID for the contact
     * @param positionName - name of position for the new player
     * @param flexibleP - true if the player can play more than one position
     * @param leagues - leagues of player
     * @param signupAll
     * @param regType
     * @param groupID - ID of group
     * @param teamName - name of team
     * @param date - date of registration
      * @return registration - newly created registration object
     */
    public Registration update(String contactID, String positionName, boolean flexibleP, String leagues, boolean signupAll, String regType, String groupID, String teamName, Date date) {
        RegistrationDB rdb = new RegistrationDB();
        Registration registration = rdb.getByContactID(contactID);
        
        registration.setContactID(contactID);
        registration.setContact(new ContactDB().getByContactID(contactID));
        registration.setPosition(new PositionDB().getByPosName(positionName));
        registration.setFlexibleP(flexibleP);
        registration.setLeagues(leagues);
        registration.setGroupID(groupID);
        registration.setSignupAll(signupAll);
        registration.setRegType(regType);
        registration.setGroupID(groupID);
        registration.setTeamName(teamName);
        registration.setRegDate(date);
        
        rdb.update(registration);
        return registration;
    }
    
    /**
     * This method deactivates a registration
     * @param reg - registration object to be deactivated
     */
    public void deactivate(Registration reg) {
        RegistrationDB rdb = new RegistrationDB();
        System.out.println("to string:\n" + reg);
        reg.setIsActive(false);
        rdb.update(reg);
    }
    
    /**
     * This method deletes a registration from the system
     * @param contactID - id of the contact within the registration
     */
    public void delete(String contactID) {
        RegistrationDB rdb = new RegistrationDB();
        Registration registration = rdb.getByContactID(contactID);
        System.out.println(registration.toString());
        rdb.delete(registration);
    }
}
