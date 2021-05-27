/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Database.DatabaseConnection;
import DAO.Factory.AbstractDAOFactory;
import Model.Area;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import Model.Salesman;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author braul
 */
public class SalesmanDAO  extends DAO<Salesman>{
    
    public SalesmanDAO(Connection conn)
    {
        super(conn);
    }
    
    @Override
    public boolean create(Salesman obj) {
       try {
            String querry = "INSERT INTO salesmans (first_name, last_name, email, password, area_id) VALUES (?,?,?,?,?)"; 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getFirstName());
            ps.setString(2, obj.getLastName());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setInt(5, obj.getArea().getArea_id());           
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean update(Salesman obj) {
        try {
            String querry = "UPDATE salesmans SET first_name=?, last_name=?, email=?, password=?, area_id=? WHERE salesman_id = " + obj.getId();
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getFirstName());
            ps.setString(1, obj.getLastName());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword());
            ps.setInt(6, obj.getArea().getArea_id());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;        
    }
    
    @Override
    public boolean delete(Salesman obj) {
        String query = "DELETE FROM salesmans WHERE salesman_id = " + obj.getId();
        this.execute(query);
        return true;
    }
    
        @Override
    public Salesman get(int id) {    
        Salesman salesman = null;
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<Area> AreaDAO = adf.getAreasDAO();
        try {
            ResultSet res = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
                .executeQuery("SELECT * FROM salesmans WHERE salesman_id =" + id);
                if (res != null) {
                    while (res.next()) {
                        String firstname = res.getString("first_name");
                        String lastname = res.getString("last_name");
                        String email = res.getString("email");
                        String password = res.getString("password");                        
                        Area area = AreaDAO.get(res.getInt("area_id"));
                    
                        salesman = new Salesman(id, firstname, lastname, email, password, area);
                    }
                }
            } catch (SQLException e) {
            
        }
        return salesman;
    }

    @Override
    public List<Salesman> getAll() {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<Area> AreaDAO = adf.getAreasDAO();
        List<Salesman> salesmans = new ArrayList<Salesman>();
        String query = "SELECT * FROM salesmans";
        Salesman salesman = null;
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    int id = res.getInt("salesman_id");
                    String firstname = res.getString("first_name");
                    String lastname = res.getString("last_name");
                    String email = res.getString("email");
                    String password = res.getString("password");  
                    Area area = AreaDAO.get(res.getInt("area_id"));
                    salesman = new Salesman(id, firstname, lastname, email, password, area);
		}
            } catch (SQLException e) {
                return null;
            }
        }
        return salesmans;
    }
    
    public Salesman getByCredentials(String email, String password) {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        DAO<Area> AreaDAO = adf.getAreasDAO();
        String query = "SELECT * FROM salesmans WHERE email = '" + email + "' AND password = SHA1('"  + password + "')";
        Salesman salesman = null;
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    int id = res.getInt("salesman_id");
                    String firstname = res.getString("first_name");
                    String lastname = res.getString("last_name");
                    Area area = AreaDAO.get(res.getInt("area_id"));
                    salesman = new Salesman(id, firstname, lastname, email, password, area);
		}
            } catch (SQLException e) {
                return null;
            }
        }
        return salesman;
    }
    
}
