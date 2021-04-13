package services;

import dataaccess.ContactDB;
import dataaccess.ExecutiveDB;
import dataaccess.PlayerDB;
import dataaccess.RoleDB;
import java.util.List;
import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Contact;
import models.Player;
import models.Role;
import models.Score;
import models.Spare;
import models.User;

public class AccountService { 
    
    public User login(String email, String password) {
        
        try {
            User user = getByEmail(email);
            if (user.getPassword().equals(password)) 
                return user;
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
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
    
    public User setResetUUID(User user, String uuid) {
        UserDB udb = new UserDB();
        user.setResetUUID(uuid);
        udb.update(user);
        
        return user;
    }

    public List<User> getAll() {
        UserDB udb = new UserDB();
        return udb.getAll();
    }
    
    public List<User> getAllOrdered() {
        UserDB udb = new UserDB();
        return udb.getAllOrdered();
    }
    
    public List<User> getAllAdmins() {
        UserDB udb = new UserDB();
        return null;
    }
    
    public User getByUserID(String userID) {
        UserDB udb = new UserDB();
        return udb.getByUserID(userID);
    }
    
    public User getByContactID(Contact contactID) {
        UserDB udb = new UserDB();
        return udb.getByContactID(contactID);
    }
    
    public User getByEmail(String email) {
        UserDB udb = new UserDB();
        return udb.getByEmail(email);
    } 
    
    public User getByResetUUID(String uuid) {
        UserDB udb = new UserDB();
        return udb.getByResetUUID(uuid);
    }
    
    public List<User> getAllNotVerify() {
        UserDB udb = new UserDB();
        return udb.getAllNotVerify();
    } 
    
        
   /* public List<User> getAllVerify() {
        UserDB udb = new UserDB();
        return udb.getAllVerify();
    } */
    
    public void changeUserPassword(User user, String password){
        UserDB udb = new UserDB();
        user.setPassword(password);
        user.setResetUUID("");
        udb.update(user);
    }
    
    public Role getByRoleID(int roleID) {
        RoleDB rdb = new RoleDB();
        return rdb.getByRoleID(roleID);
    }
    
    public List<Role> getAllRoles() {
        RoleDB rdb = new RoleDB();
        return rdb.getAll();
    } 
    
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
    

    
    public String generateUserID() {
        List<User> users = getAllOrdered();
        String idStr = users.get(users.size() - 1).getUserID();
        int idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        
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
