/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmApp.view;

import irmApp.model.Patient;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import irmApp.database.ConnexionOracle;
import irmApp.IRMCare;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author Laure
 */
public class ListePatientsTechnicienController implements Initializable {

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> idColumn;
    @FXML
    private TableColumn<Patient, String> groupeColumn;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> ageColumn;
    @FXML
    private TableColumn<Patient, String> statutColumn;
    @FXML
    private TableColumn<Patient, String> sexeColumn;
    @FXML
    private TableColumn<Patient, String> gradeColumn;
    @FXML
    private TextField motcle;
    
    private Stage dialogStage;
    
    // Reference au main.
    private IRMCare irmCare;
    
    ConnexionOracle maconnection = new ConnexionOracle();
    Statement stmt; //créer une variable de la requête
    
    
    
     /**
     * Constructeur doit etre avant methode initialize
     * 
     */
    public ListePatientsTechnicienController() {
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        groupeColumn.setCellValueFactory(cellData -> cellData.getValue().groupeProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        statutColumn.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
        sexeColumn.setCellValueFactory(cellData -> cellData.getValue().sexeProperty());
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
        
        patientTable.setItems(recuperationPatients());
    }   
    
    public ObservableList<Patient> recuperationPatients()
    {
        ObservableList<Patient> data = FXCollections.observableArrayList();
        Patient patient;
        String requete = "select * from Patient;";

        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requete);
            while(result.next())
            {
                patient = new Patient(result.getInt("IDPATIENT"), result.getInt("IDGROUPE"), result.getString("PRENOM"), result.getString("NOM"), result.getInt("AGE"), result.getBoolean("EXCLUS"), result.getBoolean("PROGRAMMEFINI"), result.getString("SEXE").charAt(0), result.getInt("GRADEGLIOMEACTUEL"));
                data.add(patient);
            }
            System.out.println("Liste remplie par la bdd");
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println("Liste non remplie par la bdd");
        }
        catch(NullPointerException e){
            System.out.println(e);
            System.out.println("Liste non remplie par la bdd");
        }
        data.add(new Patient(1,1,"uvjb","iugig",45,false,true,'H',2));
        return data;
    }
    
    @FXML
    public void handleRechercher(){
        ObservableList<Patient> data = FXCollections.observableArrayList();
        Patient patient;
        String requete = "select * from Patient;";
        
        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requete);
            while(result.next())
            {
                patient = new Patient(result.getInt("IDPATIENT"), result.getInt("IDGROUPE"), result.getString("PRENOM"), result.getString("NOM"), result.getInt("AGE"), result.getBoolean("EXCLUS"), result.getBoolean("PROGRAMMEFINI"), result.getString("SEXE").charAt(0), result.getInt("GRADEGLIOMEACTUEL"));
                //on garde le patient si on trouve le mot clé dans son nom ou son prénom
                if(patient.getFirstName().contains(motcle.getText()) || patient.getLastName().contains(motcle.getText()))
                {
                    data.add(patient);
                }
            }
            System.out.println("Liste remplie par la bdd");
        }
        catch(SQLException e){
            System.out.println(e);
            System.out.println("Liste non remplie par la bdd");
        }
        catch(NullPointerException e){
            System.out.println(e);
            System.out.println("Liste non remplie par la bdd");
        }
        
        //debut test
        patient = new Patient(1,1,"uvjb","iugig",45,false,true,'H',2);
        if(patient.getFirstName().contains(motcle.getText()) || patient.getLastName().contains(motcle.getText()))
        {
            data.add(patient);
        }
        //fin test
        
        patientTable.setItems(data);
    }
    
    @FXML
    public void handleAjoutIrm(){
        Patient aPatient;
        aPatient = patientTable.getSelectionModel().getSelectedItem();
        boolean okClicked;
    
        if (aPatient != null) {
            System.out.println("Vous êtes prêt a ajouter l'irm");

            okClicked = irmCare.showExamForm(aPatient);


        } 
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Attention !");
            alert.setHeaderText("Rien n'a été sélectionné !");
            alert.setContentText("Il faut sélectionner un patient");

            alert.showAndWait();
        }
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
