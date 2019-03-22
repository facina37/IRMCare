/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmcare;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Laure
 */
public class Examen_persoController implements Initializable{
    //données
    @FXML
    private Label gradeMachine;
    @FXML
    private Label risqueTotal;
    @FXML
    private Label risqueMeta;
    @FXML
    private Label risquePerfu;
    @FXML
    private Label volCrane;
    @FXML
    private Label axeCrane;
    @FXML
    private Label volTumeur;
    @FXML
    private Label mtt;
    @FXML
    private Label ttp;
    @FXML
    private Label rcbv;
    @FXML
    private Label rcbf;
    @FXML
    private Label cho_cr;
    @FXML
    private Label naa_cr;
    @FXML
    private Label naa_cho;
    @FXML
    private Label lac;
    @FXML
    private Label lip_cr;
    private boolean valide;
    
    //partie erreur
    @FXML
    private Label messageErreur;
    @FXML
    private RadioButton refaire;
    @FXML
    private RadioButton suppression;
    @FXML
    private Button valideErreur;
    
    //partie choix du grade
    @FXML
    private ComboBox grade;
    @FXML
    private Button valideGrade;
    
    private int idExamen; //récupéré de la page précédente
    private Stage dialogStage;
    
    ConnexionOracle maconnection = new ConnexionOracle();
    Statement stmt; //créer une variable de la requête
    
    
    /*Qd on valide une des deux decisions lors d'une erreur d'IRM*/
    @FXML
    private void handleValideErreur(ActionEvent event) {
        if(isErrorChoiceValid()){
            if (refaire.isSelected()){
                //==> on doit supprimer les données de cet examen
                //==> on doit en reprogrammer un dans deux jours
            }
            if (suppression.isSelected()){
                //==>on supprime l'avant dernier examen effectué par ce patient
            }
        }
    }
    
    private boolean isErrorChoiceValid() {
        String errorMessage = "";
        
        if (refaire.isSelected() == false && suppression.isSelected() == false) {
            errorMessage += "Vous devez faire un choix avant de valider";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Attention !");
            alert.setHeaderText("Veuillez corriger.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    /*Qd on valide une des deux decisions lors d'une erreur d'IRM*/
    @FXML
    private void handleValideGrade(ActionEvent event) {
        if(isGradeChoiceValid())
        {
            //on rentre le grade medecin dans la bdd
        }
    }
    
    private boolean isGradeChoiceValid() {
        String errorMessage = "";
        
        if (grade.getValue() != "I" && grade.getValue() != "II" && grade.getValue() != "III" && grade.getValue() != "IV") {
            errorMessage += "Grade invalide!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Attention !");
            alert.setHeaderText("Veuillez corriger.");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    /*Affiche les données de l'examen*/
    public void recuperationInfos(){
        String requete = "select * from examen where idexamen = "+idExamen+";";
        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requete);
            while(result.next()){
                //concaténer avec les résultats
                gradeMachine.setText(gradeMachine.getText());
                risqueTotal.setText(risqueTotal.getText());
                risqueMeta.setText(risqueMeta.getText());
                risquePerfu.setText(risquePerfu.getText());
                volCrane.setText(volCrane.getText());
                axeCrane.setText(axeCrane.getText());
                volTumeur.setText(volTumeur.getText());
                ttp.setText(ttp.getText());
                rcbv.setText(rcbv.getText());
                mtt.setText(mtt.getText());
                rcbf.setText(rcbf.getText());
                lac.setText(lac.getText());
                naa_cho.setText(naa_cho.getText());
                cho_cr.setText(cho_cr.getText());
                lip_cr.setText(lip_cr.getText());
                naa_cr.setText(naa_cr.getText());
                valide = true;
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    /*Si on n'a pas d'erreurs anatomiques on ne peut pas accéder à la zone d'erreur,
    sinon on ne peut pas laisser le médecin décider d'un grade*/
    public void gestionErreurs(){
        if(valide == true){
            messageErreur.setText("L'IRM n'a pas présenté d'erreurs anatomiques");
            refaire.setDisable(true);
            suppression.setDisable(true);
            valideErreur.setDisable(true);
        }
        else{
            messageErreur.setText("L'IRM a présenté des erreurs anatomiques");
            grade.setDisable(true);
            valideGrade.setDisable(true);
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recuperationInfos();
        gestionErreurs();
    }
}
