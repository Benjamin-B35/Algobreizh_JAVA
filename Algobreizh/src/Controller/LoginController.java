/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.DatabaseConnection;
import View.LoginView;
import View.WelcomeView;
import Context.Context;
import DAO.CustomerDAO;
import DAO.SalesmanDAO;
import Model.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author braul
 */
public class LoginController {
    
    private LoginView view;
    
    public LoginController(LoginView loginView) {
        view = loginView;
        view.addBtnConnectListener(new BtnConnectionListener());
    }
    
    public void setVisible(Boolean visible) { view.setVisible(visible); }
    
    class BtnConnectionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
            SalesmanDAO salesmanDAO = new SalesmanDAO(DatabaseConnection.getInstance());
            CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getInstance());
            Context.currUser = salesmanDAO.getByCredentials(view.getEmail(),view.getPassword());
            if (Context.currUser != null)
            { 
                view.setVisible(false);
                WelcomeView meetingsView = new WelcomeView();
                WelcomeController welcomeControler = new WelcomeController(meetingsView);
                List<Customer> customers = customerDAO.getCustomerByArea(Context.currUser.getArea());    
                // Clear the table
                DefaultTableModel model = (DefaultTableModel) meetingsView.getjTable1().getModel();
                model.setRowCount(0);
                for (Customer customer : customers) {
                    model.addRow(new Object[]{customer.getUsername(), customer.getFirstName(), customer.getLastName(), customer.getCity().getCity_name(), customer.getLastMeetingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))});
                }
                meetingsView.setVisible(true);
            }
            else 
            {
                JOptionPane.showMessageDialog(view,
                "Identifiant ou mot de passe incorrect",
                "Erreur authentification",
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
