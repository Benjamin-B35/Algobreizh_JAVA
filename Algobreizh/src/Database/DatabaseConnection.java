/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

/**
 *
 * @author braul
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	// URL de connexion
	private String url = "jdbc:mysql://algobreizh.benjamin-brault-renault.fr:3306/iwli0272_algobreizh";
        //private String url = "jdbc:mysql://localhost:3306/algo";
	// Nom du user
	private String user = "iwli0272_admin";
        //private String user = "root";
        
	// Mot de passe de l'utilisateur
	private String passwd = "@dmin355";
        //private String passwd = "";

	// Objet Connection
	private static Connection connect;
	// Constructeur privé
	private DatabaseConnection() {
		try {
                    connect = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
                    e.printStackTrace();
		}
	}

	// Méthode d'accès au singleton
	public static Connection getInstance() {
		if (connect == null) {
                    new DatabaseConnection();
                }
		return connect;
	}
}
