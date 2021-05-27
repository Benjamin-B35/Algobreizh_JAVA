/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author braul
 */
public abstract class DAO<T> {
	  protected Connection connect = null;  
	  public DAO(Connection conn){
	    this.connect = conn;
	  }
	   /**
	  * MÈthode de d'éxecution des requêtes SQL
	  * @param requete
	  * @return ResultSet 
	  */
            protected ResultSet execute(String requete)
            {
                Statement stmt = null;
                ResultSet res = null;
                try {
                    stmt = connect.createStatement();
                    if(stmt.execute(requete)) {
                        res = stmt.getResultSet();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                return res;
            }
	  /**
	  * MÈthode de création
	  * @param obj
	  * @return boolean 
	  */
	  public abstract boolean create(T obj);

	  /**
	  * MÈthode pour effacer
	  * @param obj
	  * @return boolean 
	  */
	  public abstract boolean delete(T obj);

	  /**
	  * MÈthode de mise ‡ jour
	  * @param obj
	  * @return boolean
	  */
	  public abstract boolean update(T obj);

	  /**
	  * MÈthode de recherche des informations
	  * @param id
	  * @return T
	  */
	  public abstract T get(int id);
	  
           /**
	  * MÈthode de lecture des informations
	  * @param id
	  * @return T
	  */
          public abstract List<T> getAll();
}