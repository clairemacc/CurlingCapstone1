package services;

import dataaccess.ContactDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import models.Contact;

public class ContactService {
    public List<Contact> getAll() {
        ContactDB cdb = new ContactDB();
        return cdb.getAll();
    }
    
    public List<Contact> getAllSorted(String orderBy) {
        ContactDB cdb = new ContactDB();
        return cdb.getAllSorted(orderBy);
    }
    
    public Contact getByContactID(String contactID) {
        ContactDB cdb = new ContactDB();
        return cdb.getByContactID(contactID);
    }
    
    public Contact getByEmail(String email) {
        ContactDB cdb = new ContactDB();
        return cdb.getByEmail(email);
    }
    
    public void emailExec(String from, String name, String subject, String body, String path) {
        String to = "curlingproject@gmail.com";
        String template = path + "\\emailTemplates\\emailExec.html";
        
        
        body = body.replaceAll("(\\r\\n|\\n)", "<br />");
        

        HashMap<String, String> tags = new HashMap<>();
        tags.put("name", name);
        tags.put("from", from);
        tags.put("subject", subject);
        tags.put("body", body);
        
        subject = "New Message: " + subject;
        try {
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(ContactService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void emailFromExec(List<Contact> contacts, String from, String subject, String body, String path) {
        String template = path + "\\emailTemplates\\emailFromExec.html";
        body = body.replaceAll("(\\r\\n|\\n)", "<br />");
        
        for (Contact c : contacts) {
            HashMap<String, String> tags = new HashMap<>();
            
            tags.put("name", c.getFirstName());
            tags.put("from", from);
            tags.put("body", body);
            
            try {
                GmailService.sendMail(c.getEmail(), subject, template, tags);
            } catch (Exception e) {
                Logger.getLogger(ContactService.class.getName()).log(Level.SEVERE, null, e);
            }
            
        }
    }
    
    public void insert(String firstName, String lastName, String address, String city, String postal, String email, String phone) {
        ContactDB cdb = new ContactDB();
        
        String contactID = generateContactID();
        Contact contact = new Contact(contactID, firstName, lastName, address, city, postal, email, phone);
        cdb.insert(contact);
    }
    
    public void update(String contactID, String firstName, String lastName, String address, String city, String postal, String email, String phone) {
        ContactDB cdb = new ContactDB();
        Contact contact = cdb.getByContactID(contactID);
        
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setAddress(address);
        contact.setCity(city);
        contact.setPostal(postal);
        contact.setEmail(email);
        contact.setPhone(phone);
        
        System.out.println(contact.toString());
        cdb.update(contact);
    }
    
    public String generateContactID() {
        List<Contact> contacts = getAll();
        String idStr = contacts.get(contacts.size() - 1).getContactID();
        int idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        
        String contactID;
        int newIdNum = idNum + 1;
        if (newIdNum <= 99) 
            contactID = "C_00" + newIdNum;
        else if (newIdNum <= 999)
            contactID = "C_0" + newIdNum;
        else 
            contactID = "C_" + newIdNum;
        
        return contactID;
    }
}
