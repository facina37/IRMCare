/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmcare;

import java.net.URL;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    ConnexionOracle maconnection = new ConnexionOracle();
    Statement stmt; //créer une variable de la requête
    
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
        data.add(new Patient(1,1,"uvjb","iugig",45,false,true,'H',2));
        return data;
    }
    
    
    public void handleRechercher(){
        String requeteTri = "select * from patient where nom = "+motcle.getText()+" or prenom = "+motcle.getText()+";";
        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requeteTri);
            while(result.next())
            {
                //afficher les personnes dans le tableau
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
}
