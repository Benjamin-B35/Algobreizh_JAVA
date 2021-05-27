/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author braul
 */
public class Meeting {
    
    private int meeting_id;
    private LocalDateTime meeting_date;
    private String contact_first_name;
    private String contact_last_name;
    private String description;
    private Salesman Salesman;
    private Customer Customer;    

    public Meeting(int meeting_id, LocalDateTime meeting_date, String contact_first_name, String contact_last_name, String description, Salesman Salesman, Customer Customer) {
        this.meeting_id = meeting_id;
        this.meeting_date = meeting_date;
        this.contact_first_name = contact_first_name;
        this.contact_last_name = contact_last_name;
        this.description = description;
        this.Salesman = Salesman;
        this.Customer = Customer;
    }
    
        public Meeting(LocalDateTime meeting_date, String contact_first_name, String contact_last_name, String description, Salesman Salesman, Customer Customer) {
        this.meeting_date = meeting_date;
        this.contact_first_name = contact_first_name;
        this.contact_last_name = contact_last_name;
        this.description = description;
        this.Salesman = Salesman;
        this.Customer = Customer;
    }

    public int getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    public LocalDateTime getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(LocalDateTime meeting_date) {
        this.meeting_date = meeting_date;
    }

    public String getContact_first_name() {
        return contact_first_name;
    }

    public void setContact_first_name(String contact_first_name) {
        this.contact_first_name = contact_first_name;
    }

    public String getContact_last_name() {
        return contact_last_name;
    }

    public void setContact_last_name(String contact_last_name) {
        this.contact_last_name = contact_last_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Salesman getSalesman() {
        return Salesman;
    }

    public void setSalesman(Salesman Salesman) {
        this.Salesman = Salesman;
    }

    public Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(Customer Customer) {
        this.Customer = Customer;
    }
    
    
}
