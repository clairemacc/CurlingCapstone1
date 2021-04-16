package models;

import java.io.Serializable;

/**
 * This object represents a Member object, which is used to create new registrations. 
 * 
 * @author CurlingCapstone
 */
public class Member implements Serializable{
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String city;
    public final String postal;
    public final String email;
    public final String phone;
    
    public int number;    
    public String position;
    public boolean flexibleP;
    
    public Member(String firstName, String lastName, String address, String city, String postal, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postal = postal;
        this.email = email;
        this.phone = phone;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getPostal() {
        return postal;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getPosition() {
        return position;
    }
    
    public boolean getFlexibleP() {
        return flexibleP;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setFlexibleP(boolean flexibleP) {
        this.flexibleP = flexibleP;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}