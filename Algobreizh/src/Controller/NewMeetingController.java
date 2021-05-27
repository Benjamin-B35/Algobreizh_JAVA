/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Context.Context;
import DAO.CustomerDAO;
import DAO.MeetingDAO;
import DAO.SalesmanDAO;
import Database.DatabaseConnection;
import Model.Customer;
import Model.Meeting;
import Model.Salesman;
import View.NewMeetingView;
import View.WelcomeView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author braul
 */
public class NewMeetingController {
    
	private NewMeetingView  new_meeting_view;
        private WelcomeView welcome_view;
        
	public NewMeetingController (NewMeetingView newMeetingView, WelcomeView welcomeView)
	{
            new_meeting_view = newMeetingView;
            welcome_view = welcomeView;
            new_meeting_view.addBtnConfirmMeetingListener(new NewMeetingController.BtnConfirmMeetingListener());
            new_meeting_view.addComboBoxCustomerNameListener(new NewMeetingController.ComboBoxCustomerNameListener());
	}
        
        class BtnConfirmMeetingListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (new_meeting_view.getCustomerName().getSelectedItem().toString() != "Choisir" &&
                        !new_meeting_view.getDateMeeting().getText().isEmpty() &&
                        !new_meeting_view.getContactFirstName().getText().isEmpty() &&
                        !new_meeting_view.getContactLastName().getText().isEmpty()
                        ){
                    MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
                    CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getInstance());
                    SalesmanDAO salesmanDAO = new SalesmanDAO(DatabaseConnection.getInstance());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime meeting_date = LocalDateTime.parse(new_meeting_view.getDateMeeting().getText(), formatter);
                    String contact_first_name = new_meeting_view.getContactFirstName().getText();
                    String contact_last_name = new_meeting_view.getContactLastName().getText();
                    String description = new_meeting_view.getComments().getText();
                    Salesman salesman = Context.currUser;
                    int customer_id =  Integer.parseInt(new_meeting_view.getCustomerName().getSelectedItem().toString().split(" - ")[0]);
                    Customer customer = customerDAO.get(customer_id);           
                    Meeting meeting = new Meeting(meeting_date, contact_first_name, contact_last_name, description, salesman, customer);
                    boolean success = meetingDAO.create(meeting);
                    LocalDateTime new_meeting_date = getLastMeetingDate(customer, meeting_date);
                    customer.setLastMeetingDate(new_meeting_date);
                    boolean successUpdate = customerDAO.updateLastMeetingDate(customer);
                    DefaultTableModel model = (DefaultTableModel) welcome_view.getjTable1().getModel();
                    model.setRowCount(0);
                    List<Customer> customers = customerDAO.getCustomerByArea(Context.currUser.getArea());
                    for (Customer cust : customers) {
                        model.addRow(new Object[]{cust.getUsername(), cust.getFirstName(), cust.getLastName(), cust.getCity().getCity_name(), cust.getLastMeetingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))});
                    }
                    new_meeting_view.dispose();
                } else {
                    JOptionPane.showMessageDialog(new_meeting_view,
                    "Champs incomplets",
                    "Erreur formulaire d'ajout de rendez-vous",
                    JOptionPane.WARNING_MESSAGE);                    
                }
            }
        }
        
        class ComboBoxCustomerNameListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (new_meeting_view.getCustomerName().getItemCount() > 0 ) {
                    if (new_meeting_view.getCustomerName().getSelectedItem().toString() == "Choisir") {
                        new_meeting_view.getDateMeeting().setText("17/06/2021 14:00");
                        new_meeting_view.getContactFirstName().setText("");
                        new_meeting_view.getContactLastName().setText("");
                        new_meeting_view.getComments().setText("");
                    } else {
                        int customer_id =  Integer.parseInt(new_meeting_view.getCustomerName().getSelectedItem().toString().split(" - ")[0]);
                        CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getInstance());
                        Customer customer = customerDAO.get(customer_id);
                        new_meeting_view.getDateMeeting().setText(customer.getLastMeetingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                        new_meeting_view.getContactFirstName().setText(customer.getFirstName());
                        new_meeting_view.getContactLastName().setText(customer.getLastName());
                        new_meeting_view.getComments().setText("");
                    }
                }
            }
        }
        
        public LocalDateTime getLastMeetingDate (Customer customer, LocalDateTime newMeetingDate) {
            LocalDateTime meetingDate = customer.getLastMeetingDate();
            LocalDateTime now = LocalDateTime.now();
            if (meetingDate.isBefore(now) && newMeetingDate.isAfter(meetingDate)) {
                return newMeetingDate;
            } else if (meetingDate.isAfter(now) && meetingDate.isAfter(newMeetingDate)) {
                return meetingDate;
            }
            return meetingDate;
        }
}
