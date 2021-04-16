package services;

import dataaccess.ContactDB;
import dataaccess.PlayerDB;
import dataaccess.RoleDB;
import java.util.List;
import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import models.Contact;
import models.Player;
import models.Role;
import models.User;

/**
 * This class provides operations pertaining to a user's account.
 * It provides methods to retrieve, update, create and delete anything pertaining to a user's account.
 * 
 */
public class AccountService { 
   
    /**
     * This method determines if a user has the correct credentials to login to the applciation.
     * @param email - the email provided by the user
     * @param password - the password provided by the user
     * @return user - the user object if the email and password corresponds to a specific user.
     */
    public User login(String email, String password) {
        
        try {
            User user = getByEmail(email);// searches for the user using the email address
            if (user.getPassword().equals(password)) //checks to see if the password for the user object found in the step above matches the supplied password
                return user;
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    /**
     * This allows for the password of a user to be reset.
     * @param email -email address provided by the user
     * @param path - this is the beginning path of the where the email templates can be found.
     * @param url - url to the page that allows the user to change their password.
     * @return true if the password was successfully reset
     */
    public boolean resetPassword(String email, String path, String url) {
        try {
            User user = getByEmail(email);
            if (user != null) {
                String uuid = UUID.randomUUID().toString();
                user = setResetUUID(user, uuid);
                
                String to = email;
                String subject = "ARC Curling - Account Password Reset";
                String template = path + "\\emailTemplates\\resetPassword.html";
                
                String link = url + "?uuid=" + uuid;
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstName", user.getContactID().getFirstName());
                tags.put("lastName", user.getContactID().getLastName());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
                return true;
            }    
        } catch (Exception e) {
            return false;
        }
        return false;        
    }
    
    /**
     * This allows an email to be sent to a new user to activate their account.
     * @param email - the email provided by the new user
     * @param path - beginning of the path to where you can find the email template.
     * @param url - the url to the page where the new user can activate their account.
     * @param uuid  - Universally unique identifier
     */
    public void sendAccountCreatedEmail(String email, String path, String url, String uuid) {
        try {
            User user = getByEmail(email);
            if (user != null) {
                String to = email;
                String subject = "ARC Curling - Account Created";
                String template = path + "\\emailTemplates\\activateAccount.html";
                
                String link = url + "?uuid=" + uuid;
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstName", user.getContactID().getFirstName());
                tags.put("lastName", user.getContactID().getLastName());
                tags.put("link", link);
                
                GmailService.sendMail(to, subject, template, tags);
            }    
        } catch (Exception e) {
        }
    }
    
    /**
     * This allows us to reset hte UUID for a particular user.
     * @param user - this is the user for which the UUID needs to be reset
     * @param uuid - this is the new uuid for that user.
     * @return user - the updated version of that user
     */
    public User setResetUUID(User user, String uuid) {
        UserDB udb = new UserDB();
        user.setResetUUID(uuid);
        udb.update(user);
        
        return user;
    }

    /**
     * This returns all the users of the system in the database.
     * @return a list of all the users.
     */
    public List<User> getAll() {
        UserDB udb = new UserDB();
        return udb.getAll();
    }
    
    /**
     * This returns all the users in an ordered list using their user ID.
     * @return an ordered list of all users
     */
    public List<User> getAllOrdered() {
        UserDB udb = new UserDB();
        return udb.getAllOrdered();
    }
    
    /**
     * This returns a list of all the admin users.
     * @return a list of admin users.
     */
    public List<User> getAllAdmins() {
        UserDB udb = new UserDB();
        return null;
    }
    
    /**
     * This method returns a user object that is identified by the given userID.
     * @param userID - userID provided by the system that is used to search for a user object.
     * @return  user - user object that is found 
     */
    public User getByUserID(String userID) {
        UserDB udb = new UserDB();
        return udb.getByUserID(userID);
    }
    
    /**
     * This returns a user object where its contactID matches the one being searched.
     * @param contactID - contactID provided by the system used in the search; links the user table to the contact table which provides additional details about the user.
     * @return user - object that is found
     */
    public User getByContactID(Contact contactID) {
        UserDB udb = new UserDB();
        return udb.getByContactID(contactID);
    }
    
    /**
     * This returns the user object where its email matches the one used in the search.
     * @param email - email being searched
     * @return user - object found
     */
    public User getByEmail(String email) {
        UserDB udb = new UserDB();
        return udb.getByEmail(email);
    } 
    
    /**
     * This returns the users who's uuid matches the one in the search
     * @param uuid - universally unique identifier being searched for
     * @return user - user object found
     */
    public User getByResetUUID(String uuid) {
        UserDB udb = new UserDB();
        return udb.getByResetUUID(uuid);
    }
    
    /**
     * This changes the password of the given user to the provided password.
     * @param user - user object to be updated
     * @param password - new password
     */
    public void changeUserPassword(User user, String password){
        UserDB udb = new UserDB();
        user.setPassword(password);
        user.setResetUUID("");
        udb.update(user);
    }
    
    /**
     * This returns a role that corresponds to the roleID being searched for.
     * @param roleID - roleID being searched; unique id for a role.
     * @return role - object found
     */ 
    public Role getByRoleID(int roleID) {
        RoleDB rdb = new RoleDB();
        return rdb.getByRoleID(roleID);
    }
    
    /**
     * This returns all the roles in the system
     * @return a list of all the roles.
     */
    public List<Role> getAllRoles() {
        RoleDB rdb = new RoleDB();
        return rdb.getAll();
    } 
    
    /**
     * This inserts a user into the database and returns the new user.
     * @param contact - this is a contact object containing additional information about the new user.
     * @param email - email address of the new user
     * @return user - newly created user object
     */
    public User insert(Contact contact, String email) {
        UserDB udb = new UserDB();
        String userID = generateUserID();
        
        User user = new User(userID);
        user.setContactID(contact);
        user.setEmail(email);
        user.setRole(getByRoleID(2));
        user.setIsActive(false);
        
        udb.insert(user);
        return user;
    }
    
    /**
     * This method updates an already existing user in the system.
     * @param userID - userID of the user object to be updated
     * @param email - email address of the user
     * @param password - password of the user
     * @param roleID - roleID for the role of the user
     * @param isActive - true if the user account is active or false if inactive
     */
    public void update(String userID, String email, String password, int roleID, boolean isActive) {
        try {
            UserDB udb = new UserDB();
            User user = udb.getByUserID(userID);

            ContactDB cdb = new ContactDB();
            Contact contact = cdb.getByContactID(user.getContactID().getContactID());
            contact.setEmail(email);

            user.setEmail(email);
            user.setPassword(password);
            user.setRole(getByRoleID(roleID));
            user.setIsActive(isActive);
            udb.update(user);
        }
        catch (Exception e) {
        }
    }
    
    /**
     * This deletes a user object from the system
     * @param userID - id of the user object to be deleted.
     */
    public void delete(String userID) {
        User user = getByUserID(userID);
        if (user != null) {
            UserDB udb = new UserDB();
            PlayerDB pdb = new PlayerDB();
            
            ArrayList<String> pids = new ArrayList<>();
            for (Player p : user.getPlayerList()) {
                System.out.println(p.getUserID().getContactID().getFirstName());
                pids.add(p.getPlayerID());
            }
            
            for (String s : pids)
                pdb.delete(pdb.getByPlayerID(s));
            
            udb.delete(user);
        }
    }
    

    /**
     * This generates a new userID for a user object
     * @return userID - newly created userID
     */
    public String generateUserID() {
        List<User> users = getAllOrdered();
        int idNum;
        
        if (users == null || users.isEmpty())
            idNum = 0;
        else {
            String idStr = users.get(users.size() - 1).getUserID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }
        
        String userID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 9)
            userID = "U_000" + newIdNum;
        else if (newIdNum <= 99) 
            userID = "U_00" + newIdNum;
        else if (newIdNum <= 999)
            userID = "U_0" + newIdNum;
        else 
            userID = "U_" + newIdNum;
        
        return userID;
    }
}
