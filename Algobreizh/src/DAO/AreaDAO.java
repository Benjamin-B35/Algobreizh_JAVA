/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.Factory.AbstractDAOFactory;
import Database.DatabaseConnection;
import Model.Customer;
import Model.Salesman;
import Model.Area;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author braul
 */
public class AreaDAO  extends DAO<Area>{
    public AreaDAO(Connection conn)
    {
        super(conn);
    }

    @Override
    public boolean create(Area obj) {
       try {
            String querry = "INSERT INTO areas (area_name) VALUES (?)"; 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getArea_name());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Area obj) {
        String query = "DELETE FROM areas WHERE area_id = " + obj.getArea_id();
        this.execute(query);
        return true;
    }

    @Override
    public boolean update(Area obj) {
        String querry = "UPDATE areas SET area_name = ? WHERE area_id = " + obj.getArea_id(); 
        PreparedStatement ps;
        try {
            ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getArea_name());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public List<Area> getAll() {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        
        
        DAO<Salesman> salesmanDAO = adf.getSalesmansDAO();
        DAO<Customer> customersDAO = adf.getCustomersDAO();
        
        List<Area> areas = new ArrayList<>();
        
        String query = "SELECT * FROM areas";
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    
                    int id = res.getInt("area_id");
                    String name = res.getString("area_name");
                    Area a = new Area(id, name);
                    areas.add(a);
		}
            } catch (SQLException e) {
                System.out.println("Algobreizh SQL Exception: " + e);
            }
        }
        return areas;   
    }

    @Override
    public Area get(int id) {
        
        List<Area> areas = new ArrayList<>();
        
        String query = "SELECT * FROM areas WHERE area_id = " + id;
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    String name = res.getString("area_name");
                    return new Area(id, name);            
		}
            } catch (SQLException e) {
                System.out.println("Algobreizh SQL Exception: " + e);
            }
        }
        return null;       
    }

   
    
}
