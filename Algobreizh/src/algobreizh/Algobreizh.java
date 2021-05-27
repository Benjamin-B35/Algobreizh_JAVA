/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algobreizh;



import View.LoginView;
import Controller.LoginController;


/**
 *
 * @author paul
 */
public class Algobreizh {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {     
        LoginView loginView = new LoginView();
        LoginController loginControler = new LoginController(loginView);
        loginControler.setVisible(true);   
    }
    
}