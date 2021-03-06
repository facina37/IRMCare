/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irmApp.view;

import irmApp.IRMCare;
import irmApp.database.ConnexionOracle;
import irmApp.model.Patient;
import irmApp.model.Examen;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author Laure
 */
public class Accueil_medecinController implements Initializable {

    //tableau patient
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
    private TextField motclePatient;
    
    //tableau examen
    @FXML
    private TableView<Examen> examenTable;
    @FXML
    private TableColumn<Examen, String> idExamColumn;
    @FXML
    private TableColumn<Examen, String> dateColumn;
    @FXML
    private TableColumn<Examen, String> prenomColumn;
    @FXML
    private TableColumn<Examen, String> nomColumn;
    
    @FXML
    private TextField motcleExamen;
    
    private Stage dialogStage;
    
    // Reference au main.
    private IRMCare irmCare;
    
    ConnexionOracle maconnection = new ConnexionOracle();
    Statement stmt; //créer une variable de la requête
    
    
    
     /**
     * Constructeur doit etre avant methode initialize
     * 
     */
    public Accueil_medecinController() {
        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //partie patient
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        groupeColumn.setCellValueFactory(cellData -> cellData.getValue().groupeProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty());
        statutColumn.setCellValueFactory(cellData -> cellData.getValue().statutProperty());
        sexeColumn.setCellValueFactory(cellData -> cellData.getValue().sexeProperty());
        gradeColumn.setCellValueFactory(cellData -> cellData.getValue().gradeProperty());
        
        patientTable.setItems(recuperationPatients());
        
        //partie examens
        idExamColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        examenTable.setItems(recuperationExamens());
    }   
    
    /*
        Affiche la liste de ts les patients dans le tableau
    */
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
    
    /*
        Affiche la liste de ts les patients recherchés
    */
    @FXML
    public void handleRechercherPatients(){
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
                if(patient.getFirstName().contains(motclePatient.getText()) || patient.getLastName().contains(motclePatient.getText()))
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
        if(patient.getFirstName().contains(motclePatient.getText()) || patient.getLastName().contains(motclePatient.getText()))
        {
            data.add(patient);
        }
        //fin test
        
        patientTable.setItems(data);
    }
    
    /*
        Passe a la page AjoutVisite quand un patient est sélectionné
    */
    @FXML
    public void handleAjoutVisite(){
        Patient aPatient;
        aPatient = patientTable.getSelectionModel().getSelectedItem();
        boolean okClicked;
    
        if (aPatient != null) {
            System.out.println("Vous êtes prêt a ajouter l'irm");

            okClicked = irmCare.showVisiteForm(aPatient);
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
    
    
    /*
        Récupère tous les examens à valider
    */
    public ObservableList<Examen> recuperationExamens()
    {
        ObservableList<Examen> data = FXCollections.observableArrayList();
        Examen examen;
        String requete = "select * from Examen join patient on examen.idpatient = patient.idpatient where examen.gradeMedecin = null;";

        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requete);
            while(result.next())
            {
                examen = new Examen(result.getInt("IDEXAMEN"), result.getDate("DATEEXAM"), result.getString("PRENOM"), result.getString("NOM"));
                data.add(examen);
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
        return data;
    }
    
    /*
        Affiche la liste de ts les examens recherchés
    */
    @FXML
    public void handleRechercherExamens(){
        ObservableList<Examen> data = FXCollections.observableArrayList();
        Examen examen;
        String requete = "select * from Examen join patient on examen.idpatient = patient.idpatient where examen.gradeMedecin = null;";
        
        try{
            stmt = maconnection.ObtenirConnection().createStatement();
            ResultSet result = stmt.executeQuery(requete);
            while(result.next())
            {
                examen = new Examen(result.getInt("IDEXAMEN"), result.getDate("DATEEXAM"), result.getString("PRENOM"), result.getString("NOM"));
                
                //on garde le patient si on trouve le mot clé dans son nom ou son prénom
                if(examen.getFirstName().contains(motcleExamen.getText()) || examen.getLastName().contains(motcleExamen.getText()))
                {
                    data.add(examen);
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
        examenTable.setItems(data);
    }
    
    /*
        Passe a la page Examen perso quand un patient est sélectionné
    */
    @FXML
    public void handleVerifExam(){
        Examen examen;
        examen = examenTable.getSelectionModel().getSelectedItem();
        boolean okClicked;
    
        if (examen != null) {
            System.out.println("Vous êtes prêt a valider l'IRM");

            okClicked = irmCare.showVerifIRMForm(examen);
        } 
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Attention !");
            alert.setHeaderText("Rien n'a été sélectionné !");
            alert.setContentText("Il faut sélectionner un examen");

            alert.showAndWait();
        }
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    } 
    
}
