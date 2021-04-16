package services;

import dataaccess.ContactDB;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Contact;

/**
 * This class contains operations such as retrieve, insert update and emailing for the contact objects.
 */
public class ContactService {
    
    /**
     * This returns a list of all the contacts in the database.
     * @return a list of all the contacts
     */
    public List<Contact> getAll() {
        ContactDB cdb = new ContactDB();
        return cdb.getAll();
    }
    
    /**
     * This returns a list of all the contacts sorted using a particular attribute
     * @param orderBy - sorting attribute
     * @return list of all contacts in a sorted fashion
     */
    public List<Contact> getAllSorted(String orderBy) {
        ContactDB cdb = new ContactDB();
        return cdb.getAllSorted(orderBy);
    }
    
    /**
     * This returns a contact object that corresponds to the searched contactID
     * @param contactID - this is a unique ID for a contact object; id being searched
     * @return contact object found
     */
    public Contact getByContactID(String contactID) {
        ContactDB cdb = new ContactDB();
        return cdb.getByContactID(contactID);
    }
    
    /**
     * This returns a contact that corresponds to the email being searched.
     * @param email - email address of a contact; email being searched.
     * @return contact object found
     */
    public Contact getByEmail(String email) {
        ContactDB cdb = new ContactDB();
        return cdb.getByEmail(email);
    }
    
    /**
     * This send an email to an admin user in the system.
     * @param from - email of the sender
     * @param name - name of sender
     * @param subject - subject of email being sent
     * @param body - details of the email being sent
     * @param path - beginning of the path to the location of the email templates.
     */
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
    
    /**
     * This send an email from an executive
     * @param contacts - list of recipients 
     * @param from - email of sender
     * @param subject - subject of email
     * @param body - body of email
     * @param path - beginning of the path to the location of the email templates.
     */
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
    
    /**
     * This inserts a new contact object into the database
     * @param firstName - first name of new contact
     * @param lastName - last name of new contact
     * @param address - home address of new contact
     * @param city - city of residence for new contact
     * @param postal - postal code of the new contact
     * @param email - email address of the new contact
     * @param phone - phone number of the new contact 
     */
    public void insert(String firstName, String lastName, String address, String city, String postal, String email, String phone) {
        ContactDB cdb = new ContactDB();
        
        String contactID = generateContactID();
        Contact contact = new Contact(contactID, firstName, lastName, address, city, postal, email, phone);
        cdb.insert(contact);
    }
    
    /**
     * This updates a contact object in the system with new details.
     * @param contactID - unique ID for a contact object
     * @param firstName - first name of contact
     * @param lastName - last name of contact
     * @param address - home address of contact
     * @param city - city of residence for contact
     * @param postal - postal code of the contact
     * @param email - email address of the contact
     * @param phone - phone number of the contact 
     */
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
    
    /**
     * This method generates a contactID for a new contact object
     * @return contactID - newly generated ID
     */
    public String generateContactID() {
        List<Contact> contacts = getAll();
        int idNum;
        
        if (contacts == null || contacts.isEmpty())
            idNum = 0;
        else {
            String idStr = contacts.get(contacts.size() - 1).getContactID();
            idNum = Integer.parseInt(idStr.substring(2, idStr.length()));
        }
        
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
