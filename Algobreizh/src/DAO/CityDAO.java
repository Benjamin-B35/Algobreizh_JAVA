/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.City;
import Model.Area;
import DAO.Factory.AbstractDAOFactory;
import Database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author braul
 */
public class CityDAO  extends DAO<City>{
    public CityDAO(Connection conn)
    {
        super(conn);
    }
    
    @Override
    public boolean create(City obj) {
       try {
            String querry = "INSERT INTO cities (city_name, area_id) VALUES (?, ?)"; 
            PreparedStatement ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getCity_name());
            ps.setInt(2, obj.getArea().getArea_id());
            ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean delete(City obj) {
        String querry = "DELETE * FROM cities WHERE city_id = " + obj.getCity_id();
        this.execute(querry);
        return true;
    }

    @Override
    public boolean update(City obj) {
        String querry = "UPDATE cities SET city_name = ? WHERE city_id = " + obj.getCity_id(); 
        PreparedStatement ps;
        try {
            ps = DatabaseConnection.getInstance().prepareStatement(querry);
            ps.setString(1, obj.getCity_name());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AreaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public City get(int id) {
        String query = "SELECT * FROM cities WHERE city_id = " + id;
        City city = null;
        try {            
            AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
            DAO<Area> areaDAO = adf.getAreasDAO();
            ResultSet res = this.execute(query);
            if (res != null) {
                try {
                    while (res.next()) {
                        String city_name = res.getString("city_name");
                        int area_id = res.getInt("area_id");
                        city = new City(id, city_name, areaDAO.get(area_id));
                    }
                } catch (SQLException e) {
            }
        }
	} catch (Exception e) {
            System.out.println(e);
	}
	
        return city;   
    }

    @Override
    public List<City> getAll() {
        AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
        
        
        DAO<Area> areaDAO = adf.getAreasDAO();       
        List<City> cities = new ArrayList<>();
        
        String query = "SELECT * FROM cities";
        ResultSet res = this.execute(query);
        if (res != null) {
            try {
		while (res.next()) {
                    
                    int id = res.getInt("city_id");
                    String name = res.getString("city_name");
                    Area area = areaDAO.get(res.getInt("area_id"));
                    City c = new City(id, name, area);
                    cities.add(c);
		}
            } catch (SQLException e) {
                System.out.println("Algobreizh SQL Exception: " + e);
            }
        }
        return cities;   
    }
}
