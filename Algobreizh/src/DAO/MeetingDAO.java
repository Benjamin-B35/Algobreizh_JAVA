/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DatabaseConnection;
import Model.Customer;
import Model.Meeting;
import Model.Salesman;
import DAO.Factory.AbstractDAOFactory;
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
public class MeetingDAO  extends DAO<Meeting>{
    public MeetingDAO(Connection conn)
    {
        super(conn);
    }

    @Override
    public boolean create(Meeting obj) {
       try {
            Timestamp timestamp = Timestamp.valueOf(obj.getMeeting_date());
            String querry = "INSERT INTO meetings(meeting_date, contact_first_name, contact_last_name, description, salesman_id, user_id) VALUES (?,?,?,?,?,?)"; 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setTimestamp(1, timestamp);
            ps.setString(2, obj.getContact_first_name());
            ps.setString(3, obj.getContact_last_name());
            ps.setString(4, obj.getDescription());
            ps.setInt(5, obj.getSalesman().getId());
            ps.setInt(6, obj.getCustomer().getId());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Meeting obj) {
        String query = "DELETE FROM meetings WHERE meeting_id = " + obj.getMeeting_id();
        this.execute(query);
        return true;
    }

    @Override
    public boolean update(Meeting obj) {
        try {
            Timestamp timestamp = Timestamp.valueOf(obj.getMeeting_date());
            String querry = "UPDATE meetings SET meeting_date=?, contact_first_name=?, contact_last_name=?, description=? WHERE meeting_id = " + obj.getMeeting_id(); 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setTimestamp(1, timestamp);
            ps.setString(2, obj.getContact_first_name());
            ps.setString(3, obj.getContact_last_name());
            ps.setString(4, obj.getDescription());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;        
    }

    @Override
    public List<Meeting> getAll() {
      AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<Salesman> salesmanDAO = adf.getSalesmansDAO();
        DAO<Customer> customerDAO = adf.getCustomersDAO();
        
        List<Meeting> meetings = new ArrayList<>();
        
        String query = "SELECT * FROM meetings";
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    int id = res.getInt("meeting_id");
                    Timestamp date = res.getTimestamp("meeting_date");
                    String firstName = res.getString("contact_first_name");
                    String lastname = res.getString("contact_last_name");
                    String description = res.getString("description");
                    Salesman salesman = salesmanDAO.get(res.getInt("salesman_id"));
                    Customer customer = customerDAO.get(res.getInt("user_id"));
                    meetings.add(new Meeting(id, date.toLocalDateTime(), firstName, lastname, description, salesman, customer));                    
		}
            } catch (SQLException e) {
                System.out.println("Algobreizh SQL Exception: " + e);
            }
        }
        return null;       
    }

    @Override
    public Meeting get(int id) {
      AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<Salesman> salesmanDAO = adf.getSalesmansDAO();
        DAO<Customer> customerDAO = adf.getCustomersDAO();
        
        String query = "SELECT * FROM meetings WHERE meeting_id = " + id;
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    Timestamp date = res.getTimestamp("meeting_date");
                    String firstName = res.getString("contact_first_name");
                    String lastname = res.getString("contact_last_name");
                    String description = res.getString("description");
                    Salesman salesman = salesmanDAO.get(res.getInt("salesman_id"));
                    Customer customer = customerDAO.get(res.getInt("user_id"));
                    return new Meeting(id, date.toLocalDateTime(), firstName, lastname, description, salesman, customer);                    
		}
            } catch (SQLException e) {
                System.out.println("Algobreizh SQL Exception: " + e);
            }
        }
        return null;       
    }
    
    public List<Meeting> getMeetingsBySalesman(int salesman_id) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);        
        DAO<Salesman> salesmanDAO = adf.getSalesmansDAO();
        DAO<Customer> customerDAO = adf.getCustomersDAO();
        List<Meeting> meetings = new ArrayList<>();
        try {
                ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
                .executeQuery("SELECT * FROM meetings WHERE salesman_id =" + salesman_id + " AND meeting_date > NOW() ORDER BY meeting_date ASC" );
                if (res != null) {
                    while (res.next()) {
                        int id = res.getInt("meeting_id");
                        Timestamp date = res.getTimestamp("meeting_date");
                        String firstName = res.getString("contact_first_name");
                        String lastname = res.getString("contact_last_name");
                        String description = res.getString("description");
                        Salesman salesman = salesmanDAO.get(res.getInt("salesman_id"));
                        Customer customer = customerDAO.get(res.getInt("user_id"));
                        meetings.add(new Meeting(id, date.toLocalDateTime(), firstName, lastname, description, salesman, customer));
                    }
                }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return meetings;
    }

   
    
}