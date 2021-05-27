/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Context.Context;
import DAO.MeetingDAO;
import Database.DatabaseConnection;
import Model.Meeting;
import View.DeleteMeetingView;
import View.PlanificationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author braul
 */
public class DeleteMeetingController {
    
    private DeleteMeetingView  delete_meeting_view;
    private PlanificationView planification_view;
        
    public DeleteMeetingController (DeleteMeetingView deleteMeetingView, PlanificationView planificationView)
    {
        delete_meeting_view = deleteMeetingView;
        planification_view = planificationView;
        delete_meeting_view.addBtnDeleteMeetingListener(new DeleteMeetingController.BtnDeleteMeetingListener());
        delete_meeting_view.addComboBoxMeetingListener(new DeleteMeetingController.ComboBoxMeetingListener());
    }
    
    class BtnDeleteMeetingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
             if (delete_meeting_view.getMeeting().getSelectedItem().toString() != "Choisir") {
                MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
                Meeting meeting = meetingDAO.get(Integer.parseInt(delete_meeting_view.getMeeting().getSelectedItem().toString().split(" - ")[0]));
                boolean success = meetingDAO.delete(meeting);
                DefaultTableModel model = (DefaultTableModel) planification_view.getjTable2().getModel();
                model.setRowCount(0);
                List<Meeting> meetings = meetingDAO.getMeetingsBySalesman(Context.currUser.getId());
                for (Meeting meet : meetings) {
                    model.addRow(new Object[]{meet.getMeeting_id(), meet.getMeeting_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), meet.getCustomer().getUsername() , meet.getContact_first_name() + " " + meet.getContact_last_name(), meet.getCustomer().getCity().getCity_name(), meet.getDescription()});
                }
                delete_meeting_view.dispose();
             }
        }
    }
    
    class ComboBoxMeetingListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (delete_meeting_view.getMeeting().getItemCount() > 0 ) {
                if (delete_meeting_view.getMeeting().getSelectedItem().toString() == "Choisir") {
                } else {
                    int meeting_id =  Integer.parseInt(delete_meeting_view.getMeeting().getSelectedItem().toString().split(" - ")[0]);
                    MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
                    Meeting meeting = meetingDAO.get(meeting_id);
                }
            }
        }
    }
    
}
