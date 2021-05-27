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
public class Customer {
    
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private City City;
    private LocalDateTime lastMeetingDate;

    public Customer(int id, String username, String firstName, String lastName, String email, String password, City city, LocalDateTime lastMeetingDate) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.City = city;
        this.lastMeetingDate = lastMeetingDate;
    }

    public Customer(String username, String firstName, String lastName, String email, String password, City City, LocalDateTime lastMeetingDate) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.City = City;
        this.lastMeetingDate = lastMeetingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public City getCity() {
        return City;
    }

    public void setCity(City city) {
        this.City = city;
    }

    public LocalDateTime getLastMeetingDate() {
        return lastMeetingDate;
    }

    public void setLastMeetingDate(LocalDateTime lastMeetingDate) {
        this.lastMeetingDate = lastMeetingDate;
    }
    
}
