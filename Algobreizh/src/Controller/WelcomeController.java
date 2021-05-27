package Controller;

import Model.Customer;
import Model.Meeting;
import View.WelcomeView;
import Context.Context;
import DAO.CustomerDAO;
import DAO.MeetingDAO;
import Database.DatabaseConnection;
import View.NewMeetingView;
import View.PlanificationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author braul
 */
public class WelcomeController {
    
	private WelcomeView  m_view;
        
	public WelcomeController (WelcomeView welcomeView)
	{
            m_view = welcomeView;
            m_view.addBtnNewMeetingListener(new WelcomeController.BtnNewMeetingListener());
            m_view.addBtnPlanificationListener(new WelcomeController.BtnPlanificationListener());
	}
        
        class BtnNewMeetingListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getInstance());
                NewMeetingView newMeetingView = new NewMeetingView();
                NewMeetingController newMeetingController = new NewMeetingController(newMeetingView, m_view);
                List<Customer> customers = customerDAO.getCustomerByArea(Context.currUser.getArea());
                DefaultComboBoxModel model = (DefaultComboBoxModel) newMeetingView.getCustomerName().getModel();
                model.removeAllElements();
                newMeetingView.getCustomerName().addItem("Choisir");
                
                for(Customer customer : customers) {
                    newMeetingView.getCustomerName().addItem(customer.getId() + " - " + customer.getUsername());

                }
                newMeetingView.setVisible(true);
                
           
            }
        }
        
        class BtnPlanificationListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlanificationView planificationView = new PlanificationView();
                PlanificationController planificationController = new PlanificationController(planificationView);
                MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
                List<Meeting> meetings = meetingDAO.getMeetingsBySalesman(Context.currUser.getId());
                DefaultTableModel model = (DefaultTableModel) planificationView.getjTable2().getModel();
                model.setRowCount(0);
                for (Meeting meeting : meetings) {
                    model.addRow(new Object[]{meeting.getMeeting_id(), meeting.getMeeting_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), meeting.getCustomer().getUsername() , meeting.getContact_first_name() + " " + meeting.getContact_last_name(), meeting.getCustomer().getCity().getCity_name(), meeting.getDescription()});
                }
                planificationView.setVisible(true);
            }
        }
       
}