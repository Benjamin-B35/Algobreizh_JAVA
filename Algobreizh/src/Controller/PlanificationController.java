/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Context.Context;
import DAO.MeetingDAO;
import Database.DatabaseConnection;
import View.PlanificationView;
import View.ModifyMeetingView;
import Model.Meeting;
import View.DeleteMeetingView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author braul
 */
public class PlanificationController {
    
    private PlanificationView planification_view;
    
    public PlanificationController (PlanificationView planificationView) {
        
        planification_view = planificationView;
        planification_view.addBtnModifyMeetingListener(new PlanificationController.BtnModifyMeetingListener());
        planification_view.addBtnDeleteMeetingListener(new PlanificationController.BtnDeleteMeetingListener());
    }

    class BtnModifyMeetingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
            ModifyMeetingView modifyMeetingView = new ModifyMeetingView();
            ModifyMeetingController modifyMeetingController = new ModifyMeetingController(modifyMeetingView, planification_view);
            List<Meeting> meetings = meetingDAO.getMeetingsBySalesman(Context.currUser.getId());
            DefaultComboBoxModel model = (DefaultComboBoxModel) modifyMeetingView.getMeeting().getModel();
            model.removeAllElements();
            modifyMeetingView.getMeeting().addItem("Choisir");
                
            for(Meeting meeting : meetings) {
                modifyMeetingView.getMeeting().addItem(meeting.getMeeting_id() + " - " + meeting.getCustomer().getUsername() + " - " + meeting.getMeeting_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            }
            modifyMeetingView.setVisible(true);
                
           
            }
    }
    
    class BtnDeleteMeetingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MeetingDAO meetingDAO = new MeetingDAO(DatabaseConnection.getInstance());
            DeleteMeetingView deleteMeetingView = new DeleteMeetingView();
            DeleteMeetingController deleteMeetingController = new DeleteMeetingController(deleteMeetingView, planification_view);
            List<Meeting> meetings = meetingDAO.getMeetingsBySalesman(Context.currUser.getId());
            DefaultComboBoxModel model = (DefaultComboBoxModel) deleteMeetingView.getMeeting().getModel();
            model.removeAllElements();
            deleteMeetingView.getMeeting().addItem("Choisir");
                
            for(Meeting meeting : meetings) {
                deleteMeetingView.getMeeting().addItem(meeting.getMeeting_id() + " - " + meeting.getCustomer().getUsername() + " - " + meeting.getMeeting_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            }
            deleteMeetingView.setVisible(true);
        }
        
    }
}