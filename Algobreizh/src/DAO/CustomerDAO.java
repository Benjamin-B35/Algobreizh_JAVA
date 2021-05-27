/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DatabaseConnection;
import Model.City;
import Model.Area;
import Model.Customer;
import DAO.Factory.AbstractDAOFactory;
import Model.Meeting;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author braul
 */
public class CustomerDAO extends DAO<Customer>{
   
    AbstractDAOFactory adf  = null;
    public CustomerDAO(Connection conn)
    {
        super(conn);
        adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
    }

    @Override
    public boolean create(Customer c) {
       try {
            String querry = "INSERT INTO users (username, email, password, contact_first_name, contact_last_name, city_id, confirmed_at, last_meeting_date) VALUES (?,?,?,?,?,?,NOW(),NOW())"; 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, c.getUsername());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPassword());
            ps.setString(4, c.getFirstName());
            ps.setString(5, c.getLastName());
            ps.setInt(6, c.getCity().getCity_id());
            
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Customer obj) {
        String query = "DELETE FROM users WHERE user_id = " + obj.getId();
        this.execute(query);
        return true;
    }

    @Override
    public boolean update(Customer obj) {
        try {
            Timestamp timestamp = Timestamp.valueOf(obj.getLastMeetingDate());
            String querry = "UPDATE users SET username=?, email=?, password=?, contact_first_name=?, contact_last_name=?, city_id=?, last_meeting_date=? WHERE user_id = " + obj.getId(); 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getUsername());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword());
            ps.setString(4, obj.getFirstName());
            ps.setString(5, obj.getLastName());
            ps.setInt(6, obj.getCity().getCity_id());
            ps.setTimestamp(7, timestamp);
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;        
    }
    
    public boolean updateLastMeetingDate(Customer obj) {
        try {
            Timestamp timestamp = Timestamp.valueOf(obj.getLastMeetingDate());
            String querry = "UPDATE users SET last_meeting_date=? WHERE user_id = " + obj.getId(); 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setTimestamp(1, timestamp);
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;        
    }

    @Override
    public Customer get(int id) {    
        Customer customer = null;
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<City> CityDAO = adf.getCitiesDAO();
        try {
            ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
                .executeQuery("SELECT * FROM users WHERE role = 'customer' AND user_id = " + id);
                if (res != null) {
                    while (res.next()) {
                        String username = res.getString("username");
                        String lastname = res.getString("contact_last_name");
                        String firstname = res.getString("contact_first_name");
                        String email = res.getString("email");
                        String password =res.getString("password");
                        Timestamp lastDate = res.getTimestamp("last_meeting_date");
                        City city = CityDAO.get(res.getInt("city_id"));
                        customer = new Customer(id, username, firstname, lastname, email, password, city, lastDate.toLocalDateTime());
                    }
                }
            } catch (SQLException e) {
            
        }
        return customer;
    }

    @Override
    public List<Customer> getAll() {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<City> CityDAO = adf.getCitiesDAO();
        List<Customer> customers = new ArrayList<Customer>();
        try {
                ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
                .executeQuery("SELECT * FROM users WHERE role = 'customer'");
                if (res != null) {
                    while (res.next()) {
                        int id = res.getInt("user_id");
                        String username = res.getString("username");
                        String lastname = res.getString("contact_last_name");
                        String firstname = res.getString("contact_first_name");
                        String email = res.getString("email");
                        String password = res.getString("password");
                        Timestamp lastDate = res.getTimestamp("last_meeting_date");
                        City city = CityDAO.get(res.getInt("city_id"));
                        customers.add(new Customer(id, username, firstname, lastname, email, password, city, lastDate.toLocalDateTime()));
                    }
                }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }
    
    public List<Customer> getCustomerByArea(Area area) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<City> CityDAO = adf.getCitiesDAO();
        List<Customer> customers = new ArrayList<Customer>();
        try {
                ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
                    .executeQuery("SELECT * FROM users INNER JOIN cities ON users.city_id = cities.city_id INNER JOIN areas ON cities.area_id = areas.area_id WHERE areas.area_id = " + area.getArea_id() + " ORDER BY last_meeting_date ASC");
                if (res != null) {
                    while (res.next()) {
                        int id = res.getInt("user_id");
                        String username = res.getString("username");
                        String lastname = res.getString("contact_last_name");
                        String firstname = res.getString("contact_first_name");
                        String email = res.getString("email");
                        String password = res.getString("password");
                        Timestamp lastDate = res.getTimestamp("last_meeting_date");
                        City city = CityDAO.get(res.getInt("city_id"));
                        customers.add(new Customer(id, username, firstname, lastname, email, password, city, lastDate.toLocalDateTime()));
                    }
                }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }
    
    public Customer getCustomerByMeeting(int meeting_id) {
        Customer customer = null;
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<Meeting> MeetingDAO = adf.getMeetingsDAO();
        DAO<City> CityDAO = adf.getCitiesDAO();
        try {
            ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
            .executeQuery("SELECT * FROM users INNER JOIN meetings ON users.user_id = meetings.user_id WHERE meetings.meeting_id = " + meeting_id);
            if (res != null) {
                while (res.next()) {
                    int id = res.getInt("user_id");
                    String username = res.getString("username");
                    String lastname = res.getString("contact_last_name");
                    String firstname = res.getString("contact_first_name");
                    String email = res.getString("email");
                    String password = res.getString("password");
                    Timestamp lastDate = res.getTimestamp("last_meeting_date");
                    City city = CityDAO.get(res.getInt("city_id"));
                    customer = new Customer(id, username, firstname, lastname, email, password, city, lastDate.toLocalDateTime());
                }
            }            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
}
