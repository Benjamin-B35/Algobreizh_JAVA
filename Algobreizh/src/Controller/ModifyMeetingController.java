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
import View.ModifyMeetingView;
import View.PlanificationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author braul
 */
public class ModifyMeetingController {
    
    private ModifyMeetingView  modify_meeting_view;
    private PlanificationView planification_view;
        
    public ModifyMeetingController (ModifyMeetingView modifyMeetingView, PlanificationView planificationView)
    {
        modify_meeting_view = modifyMeetingView;
        planification_view = planificationView;
        modify_meeting_view.addBtnModifyMeetingListener(new ModifyMeetingController.BtnModifyMeetingListener());
        modify_meeting_view.addComboBoxCustomerNameListener(new ModifyMeetingController.ComboBoxMeetingListener());
    }
    
    class BtnModifyMeetingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (modify_meeting_view.getMeeting().getSelectedItem().toString() != "Choisir" &&
                modify_meeting_view.getDateMeeting().getText() != "" &&
                modify_meeting_view.getContactFirstName().getText() != "" &&
                modify_meeting_view.getContactLastName().getText() != ""
                ){
                    MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
                    CustomerDAO customerDAO = new CustomerDAO(DatabaseConnection.getInstance());
                    SalesmanDAO salesmanDAO = new SalesmanDAO(DatabaseConnection.getInstance());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    LocalDateTime meeting_date = LocalDateTime.parse(modify_meeting_view.getDateMeeting().getText(), formatter);
                    String contact_first_name = modify_meeting_view.getContactFirstName().getText();
                    String contact_last_name = modify_meeting_view.getContactLastName().getText();
                    String description = modify_meeting_view.getComments().getText();
                    Salesman salesman = Context.currUser;
                    int meeting_id =  Integer.parseInt(modify_meeting_view.getMeeting().getSelectedItem().toString().split(" - ")[0]);
                    Customer customer = customerDAO.getCustomerByMeeting(meeting_id);           
                    Meeting meeting = new Meeting(meeting_id, meeting_date, contact_first_name, contact_last_name, description, salesman, customer);
                    boolean success = meetingDAO.update(meeting);
                    DefaultTableModel model = (DefaultTableModel) planification_view.getjTable2().getModel();
                    model.setRowCount(0);
                    List<Meeting> meetings = meetingDAO.getMeetingsBySalesman(Context.currUser.getId());
                    for (Meeting meet : meetings) {
                        model.addRow(new Object[]{meet.getMeeting_id(), meet.getMeeting_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), meet.getCustomer().getUsername() , meet.getContact_first_name() + " " + meet.getContact_last_name(), meet.getCustomer().getCity().getCity_name(), meet.getDescription()});
                    }
                    modify_meeting_view.dispose();
            }
        }
    }
    
    class ComboBoxMeetingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                if (modify_meeting_view.getMeeting().getItemCount() > 0 ) {
                    if (modify_meeting_view.getMeeting().getSelectedItem().toString() == "Choisir") {
                        modify_meeting_view.getCustomerName().setText("");
                        modify_meeting_view.getDateMeeting().setText("17/06/2021 14:00");
                        modify_meeting_view.getContactFirstName().setText("");
                        modify_meeting_view.getContactLastName().setText("");
                        modify_meeting_view.getComments().setText("");
                    } else {
                        int meeting_id =  Integer.parseInt(modify_meeting_view.getMeeting().getSelectedItem().toString().split(" - ")[0]);
                        MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
                        Meeting meeting = meetingDAO.get(meeting_id);
                        modify_meeting_view.getCustomerName().setText(meeting.getCustomer().getUsername());
                        modify_meeting_view.getDateMeeting().setText("17/06/2021 14:00");
                        modify_meeting_view.getContactFirstName().setText(meeting.getContact_first_name());
                        modify_meeting_view.getContactLastName().setText(meeting.getContact_last_name());
                        modify_meeting_view.getComments().setText(meeting.getDescription());
                    }
                }
        }
    }
    
}
