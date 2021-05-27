/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO.Factory;

import DAO.AreaDAO;
import Database.DatabaseConnection;
import Model.City;
import Model.Customer;
import Model.Meeting;
import Model.Salesman;
import DAO.CityDAO;
import DAO.CustomerDAO;
import DAO.MeetingDAO;
import DAO.SalesmanDAO;
import DAO.DAO;
import Model.Area;
import java.sql.Connection;

/**
 *
 * @author braul
 */
public class DAOFactory  extends AbstractDAOFactory{
	
    protected static final Connection conn = DatabaseConnection.getInstance();   

    @Override
    public DAO<City> getCitiesDAO() {
      return new CityDAO(conn);
    }

    @Override
    public DAO<Customer> getCustomersDAO() {
        return new CustomerDAO(conn); 
    }

    @Override
    public DAO<Meeting> getMeetingsDAO() {
        return new MeetingDAO(conn);
    };

    @Override
    public DAO<Salesman> getSalesmansDAO() {
        return new SalesmanDAO(conn);
    }

    @Override
    public DAO<Area> getAreasDAO() {
        return new AreaDAO(conn);
    }

}
