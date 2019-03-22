/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmcare;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author Laure
 */
public class Accueil_connexionController implements Initializable {

    @FXML
    private TextField login;
    @FXML
    private PasswordField pwd;
    @FXML
    private Label message;
    
    
    
    ConnexionOracle maconnection = new ConnexionOracle();
    Statement stmt; //créer une variable de la requêteperche jaunepercha 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        login.setPromptText("Login");
        pwd.setPromptText("Mot de passe");
        message.setText("");
    }    
    
    @FXML
    private void handleConnexion(ActionEvent event) {

        //dialogStage.close();
        String requeteVerifLog = "select * from Medecin where login = "+login+" and pwd = "+pwd+";";
        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requeteVerifLog);
            while(result.next()){
                //se connecter a la page medecin
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        requeteVerifLog = "select * from Technicien where login = "+login+" and pwd = "+pwd+";";
        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requeteVerifLog);
            while(result.next()){
                //se connecter a la page technicien
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
        
        //si on ne se connecte pas à une des deux pages
        message.setText("Identifiant et mot de passe incorrects");
    }
    
    @FXML
    private void handleReset(ActionEvent event) {
        login.setText("");
        pwd.setText("");
        message.setText("");
    }
}
